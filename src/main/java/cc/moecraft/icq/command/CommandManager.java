package cc.moecraft.icq.command;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.command.exceptions.CommandNotFoundException;
import cc.moecraft.icq.command.exceptions.NotACommandException;
import cc.moecraft.icq.command.interfaces.*;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.User;
import lombok.Getter;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.*;

import static cc.moecraft.icq.command.CommandArgs.parse;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@Getter
public class CommandManager
{
    private final String[] prefixes;

    /** 已注册的指令, String 是指令名, IcqCommand 是指令对象 */
    private Map<String, ArrayList<IcqCommand>> registeredCommands = new HashMap<>();

    private ConflictOperation conflictOperation;

    public CommandManager(ConflictOperation conflictOperation, String... prefixes)
    {
        this.conflictOperation = conflictOperation;
        this.prefixes = prefixes;
    }

    public CommandManager(String... prefixes)
    {
        this(ConflictOperation.ENABLE_ALL, prefixes);
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
        PicqBotX bot = event.getBot();

        final boolean isGM = event instanceof EventGroupMessage;
        final boolean isDM = event instanceof EventDiscussMessage;
        final boolean isPM = event instanceof EventPrivateMessage;

        // 获取Args
        CommandArgs args;

        try
        {
            args = parse(getPrefixes(), getRegisteredCommands(), event.getMessage(), isDM || isGM);
        }
        catch (NotACommandException e)
        {
            return RunResult.NOT_A_COMMAND;
        }
        catch (CommandNotFoundException e)
        {
            return RunResult.COMMAND_NOT_FOUND;
        }

        // 判断维护
        if (bot.getConfig().isMaintenanceMode())
        {
            event.respond(bot.getConfig().getMaintenanceResponse());
            return RunResult.MAINTENANCE;
        }

        // 获取发送者
        User user = bot.getUserManager().getUserFromID(event.getSenderId());

        // 获取群
        Group group = isGM ? bot.getGroupManager().getGroupFromID(((EventGroupMessage) event).getGroupId()) :
                isDM ? bot.getGroupManager().getGroupFromID(((EventDiscussMessage) event).getDiscussId()) : null;

        // 调用指令执行方法
        for (IcqCommand runner : args.getCommandRunners())
        {
            if (isGM && runner instanceof GroupCommand)
            {
                event.respond(((GroupCommand) runner).groupMessage((EventGroupMessage) event,
                        bot.getGroupUserManager().getUserFromID(user.getId(), group), group,
                        args.getCommandName(), args.getArgs()));
            }
            if (isDM && runner instanceof DiscussCommand)
            {
                event.respond(((DiscussCommand) runner).discussMessage((EventDiscussMessage) event,
                        bot.getGroupUserManager().getUserFromID(user.getId(), group), group,
                        args.getCommandName(), args.getArgs()));
            }
            if (isPM && runner instanceof PrivateCommand)
            {
                event.respond(((PrivateCommand) runner).privateMessage((EventPrivateMessage) event, user,
                        args.getCommandName(), args.getArgs()));
            }
            if (runner instanceof EverywhereCommand)
            {
                event.respond(((EverywhereCommand) runner).run(event, user,
                        args.getCommandName(), args.getArgs()));
            }
        }

        // 成功
        return RunResult.SUCCESS;
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
