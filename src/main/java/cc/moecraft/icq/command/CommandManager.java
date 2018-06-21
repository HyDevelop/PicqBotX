package cc.moecraft.icq.command;

import cc.moecraft.icq.command.exceptions.CommandNotFoundException;
import cc.moecraft.icq.command.exceptions.NotACommandException;
import cc.moecraft.icq.command.interfaces.*;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@RequiredArgsConstructor
public class CommandManager
{
    @Getter
    private Map<String, ArrayList<IcqCommand>> registeredCommands = new HashMap<>();    // 已注册的指令, String 是指令名, IcqCommand 是指令对象

    @Setter @Getter
    private ConflictOperation conflictOperation;

    private final GroupManager groupManager;
    private final UserManager userManager;
    private final GroupUserManager groupUserManager;

    @Getter
    private final String[] prefixes;

    /**
     * 自动循环commands下的所有包找指令类
     * 然后反射实例注册
     */
    public void registerAllCommands() throws IllegalAccessException, InstantiationException
    {
        // 不填包名就是全局扫描
        Reflections reflections = new Reflections();

        // 获取包下的所有继承Command的类
        Set<Class<? extends IcqCommand>> commands = reflections.getSubTypesOf(IcqCommand.class);

        // 循环注册
        for (Class<? extends IcqCommand> command : commands)
            if (!command.isInterface() && !Modifier.isAbstract(command.getModifiers())) registerCommand(command.newInstance());
    }

    /**
     * 注册指令
     * @param command 指令
     * @return 是否注册成功
     */
    public boolean registerCommand(IcqCommand command)
    {
        if (registeredCommands.containsKey(command.properties().getName()))
        {
            return false;
        }
        else
        {
            registeredCommands.put(command.properties().getName().toLowerCase(), command);
            command.properties().getAlias().forEach(alias -> registeredCommands.put(alias.toLowerCase(), command));
            return true;
        }
    }

    /**
     * 自动找到注册过的指令对象运行
     *
     * 例子:
     *  !ecHO hi there
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

            CommandArgs commandArgs = CommandArgs.parse(this, event.getMessage(), eventIsDiscuss || eventIsGroup);
            User user = userManager.getUserFromID(event.getSenderId());

            Group group =
                    eventIsGroup ? groupManager.getGroupFromID(((EventGroupMessage) event).getGroupId()) :
                    eventIsDiscuss ? groupManager.getGroupFromID(((EventDiscussMessage) event).getDiscussId()) : null;

            IcqCommand commandRunner = commandArgs.getCommandRunner();

            boolean runnerIsGroup = commandRunner instanceof GroupCommand;
            boolean runnerIsDiscuss = commandRunner instanceof DiscussCommand;
            boolean runnerIsPrivate = commandRunner instanceof PrivateCommand;

            if (eventIsGroup && runnerIsGroup)
                event.respond(((GroupCommand) commandRunner).groupMessage((EventGroupMessage) event, groupUserManager.getUserFromID(user.id, group), group, commandArgs.getCommandName(), commandArgs.getArgs()));

            if (eventIsDiscuss && runnerIsDiscuss)
                event.respond(((DiscussCommand) commandRunner).discussMessage((EventDiscussMessage) event, groupUserManager.getUserFromID(user.id, group), group, commandArgs.getCommandName(), commandArgs.getArgs()));

            if (eventIsPrivate && runnerIsPrivate)
                event.respond(((PrivateCommand) commandRunner).privateMessage((EventPrivateMessage) event, user, commandArgs.getCommandName(), commandArgs.getArgs()));

            if (commandRunner instanceof EverywhereCommand)
                event.respond(((EverywhereCommand) commandRunner).run(event, user, commandArgs.getCommandName(), commandArgs.getArgs()));

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

    public enum RunResult
    {
        NOT_A_COMMAND, COMMAND_NOT_FOUND,
        SUCCESS
    }

    /**
     * 获取指令列表
     * @return 指令列表
     */
    public ArrayList<IcqCommand> getCommandList()
    {
        ArrayList<IcqCommand> result = new ArrayList<>();

        registeredCommands.forEach((k, v) ->
        {
            if (!result.contains(v)) result.add(v);
        });

        return result;
    }

    /**
     * 获取指令名列表
     * @return 指令名列表
     */
    public ArrayList<String> getCommandNameList()
    {
        ArrayList<String> result = new ArrayList<>();

        registeredCommands.forEach((k, v) ->
        {
            if (!result.contains(v.properties().getName())) result.add(v.properties().getName());
        });

        return result;
    }
}
