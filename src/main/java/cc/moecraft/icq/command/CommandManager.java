package cc.moecraft.icq.command;

import cc.moecraft.icq.command.exceptions.CommandNotFoundException;
import cc.moecraft.icq.command.exceptions.NotACommandException;
import cc.moecraft.icq.command.interfaces.*;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.*;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CommandManager
{
    private final GroupManager groupManager;

    private final UserManager userManager;

    private final GroupUserManager groupUserManager;

    @Getter
    private final String[] prefixes;

    @Getter
    private Map<String, ArrayList<IcqCommand>> registeredCommands = new HashMap<>();    // 已注册的指令, String 是指令名, IcqCommand 是指令对象

    @Setter
    @Getter
    private ConflictOperation conflictOperation;

    public CommandManager(GroupManager groupManager, UserManager userManager, GroupUserManager groupUserManager, ConflictOperation conflictOperation, String... prefixes)
    {
        this.groupUserManager = groupUserManager;
        this.groupManager = groupManager;
        this.userManager = userManager;
        this.conflictOperation = conflictOperation;
        this.prefixes = prefixes;
    }

    public CommandManager(GroupManager groupManager, UserManager userManager, GroupUserManager groupUserManager, String... prefixes)
    {
        this(groupManager, userManager, groupUserManager, ConflictOperation.ENABLE_ALL, prefixes);
    }

    /**
     * 自动循环commands下的所有包找指令类
     * 然后反射实例注册
     */
    public void registerAllCommands()
    {
        // 不填包名就是全局扫描
        Reflections reflections = new Reflections();

        // 获取包下的所有继承Command的类
        Set<Class<? extends IcqCommand>> commands = reflections.getSubTypesOf(IcqCommand.class);

        // 循环注册
        for (Class<? extends IcqCommand> command : commands)
        {
            if (!command.isInterface() && !Modifier.isAbstract(command.getModifiers()))
            {
                try
                {
                    registerCommand(command.newInstance());
                }
                catch (InstantiationException | IllegalAccessException e)
                {
                    // 忽略出错的指令
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 注册指令
     *
     * @param command 指令
     * @return 是否注册成功
     */
    public boolean registerCommand(IcqCommand command)
    {
        return registerCommandWithPrefix(command, "");
    }

    /**
     * 注册指令
     *
     * @param command 指令
     * @param prefix 指令独立前缀
     * @return 是否注册成功
     */
    public boolean registerCommandWithPrefix(IcqCommand command, String prefix)
    {
        if (conflictOperation == ConflictOperation.ENABLE_ALL)
        {
            String baseKey = prefix + command.properties().getName().toLowerCase();

            if (!registeredCommands.containsKey(baseKey))
            {
                registeredCommands.put(baseKey, new ArrayList<>());
            }
            registeredCommands.get(baseKey).add(command);

            command.properties().getAlias().forEach(alias ->
            {
                String key = prefix + alias.toLowerCase();

                if (!registeredCommands.containsKey(key))
                {
                    registeredCommands.put(key, new ArrayList<>());
                }
                registeredCommands.get(key).add(command);
            });
        }
        else
        {
            registeredCommands.put(prefix + command.properties().getName().toLowerCase(), new ArrayList<>(Collections.singletonList(command)));
            command.properties().getAlias().forEach(alias -> registeredCommands.put(prefix + alias.toLowerCase(), new ArrayList<>(Collections.singletonList(command))));
        }
        return true;
    }

    public boolean registerCommandWithAndWithoutPrefix(IcqCommand command, String prefix)
    {
        return registerCommandWithPrefix(command, prefix) && registerCommand(command);
    }

    /**
     * 自动找到注册过的指令对象运行
     * <p>
     * 例子:
     * !ecHO hi there
     *
     * @param event 事件
     * @return 执行结果
     */
    public RunResult runCommand(EventMessage event)
    {
        try
        {
            boolean eventIsGroup = event instanceof EventGroupMessage;
            boolean eventIsDiscuss = event instanceof EventDiscussMessage;
            boolean eventIsPrivate = event instanceof EventPrivateMessage;

            CommandArgs commandArgs = CommandArgs.parse(getPrefixes(), getRegisteredCommands(), event.getMessage(), eventIsDiscuss || eventIsGroup);
            User user = userManager.getUserFromID(event.getSenderId());

            if (event.getBot().getConfig().isMaintenanceMode())
            {
                event.respond("- 机器人正在维护 -");
                return RunResult.MAINTENANCE;
            }

            Group group =
                    eventIsGroup ? groupManager.getGroupFromID(((EventGroupMessage) event).getGroupId()) :
                            eventIsDiscuss ? groupManager.getGroupFromID(((EventDiscussMessage) event).getDiscussId()) : null;

            ArrayList<IcqCommand> commandRunners = commandArgs.getCommandRunners();

            commandRunners.forEach(commandRunner ->
            {
                boolean runnerIsGroup = commandRunner instanceof GroupCommand;
                boolean runnerIsDiscuss = commandRunner instanceof DiscussCommand;
                boolean runnerIsPrivate = commandRunner instanceof PrivateCommand;

                if (eventIsGroup && runnerIsGroup)
                {
                    event.respond(((GroupCommand) commandRunner).groupMessage(
                            (EventGroupMessage) event,
                            groupUserManager.getUserFromID(user.getId(), group), group,
                            commandArgs.getCommandName(),
                            commandArgs.getArgs()));
                }

                if (eventIsDiscuss && runnerIsDiscuss)
                {
                    event.respond(((DiscussCommand) commandRunner).discussMessage(
                            (EventDiscussMessage) event,
                            groupUserManager.getUserFromID(user.getId(), group), group,
                            commandArgs.getCommandName(),
                            commandArgs.getArgs()));
                }

                if (eventIsPrivate && runnerIsPrivate)
                {
                    event.respond(((PrivateCommand) commandRunner).privateMessage(
                            (EventPrivateMessage) event, user,
                            commandArgs.getCommandName(),
                            commandArgs.getArgs()));
                }

                if (commandRunner instanceof EverywhereCommand)
                {
                    event.respond(((EverywhereCommand) commandRunner).run(
                            event, user,
                            commandArgs.getCommandName(),
                            commandArgs.getArgs()));
                }
            });

            return RunResult.SUCCESS;
        }
        catch (NotACommandException e)
        {
            return RunResult.NOT_A_COMMAND;
        }
        catch (CommandNotFoundException e)
        {
            return RunResult.COMMAND_NOT_FOUND;
        }
    }

    /**
     * 获取指令列表
     *
     * @return 指令列表
     */
    public ArrayList<IcqCommand> getCommandList()
    {
        ArrayList<IcqCommand> result = new ArrayList<>();

        registeredCommands.forEach((k, v) -> v.forEach(command ->
        {
            if (!result.contains(command))
            {
                result.add(command);
            }
        }));

        return result;
    }

    /**
     * 获取指令名列表
     *
     * @return 指令名列表
     */
    public ArrayList<String> getCommandNameList()
    {
        ArrayList<String> result = new ArrayList<>();

        getCommandList().forEach(command ->
        {
            if (!result.contains(command.properties().getName()))
            {
                result.add(command.properties().getName());
            }
        });

        return result;
    }

    public enum RunResult
    {
        NOT_A_COMMAND, COMMAND_NOT_FOUND,
        SUCCESS, MAINTENANCE
    }
}
