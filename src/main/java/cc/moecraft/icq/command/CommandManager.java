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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static cc.moecraft.icq.command.CommandArgsParser.parse;

/**
 * The class {@code CommandManager} is a manager for command registration
 * and execution.
 * <p>
 * Class created by the HyDEV Team on 2019-03-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-24 14:01
 */
@Getter
public class CommandManager
{
    private PicqBotX bot;

    /** 指令前缀 */
    private final String[] prefixes;

    /** 已注册的指令, [指令名, 指令对象] */
    private Map<String, IcqCommand> commands = new HashMap<>();

    /**
     * 构造一个指令管理器
     *
     * @param bot 机器人对象
     * @param prefixes 前缀
     */
    public CommandManager(PicqBotX bot, String... prefixes)
    {
        this.bot = bot;
        this.prefixes = prefixes;
    }

    /**
     * 注册多个指令
     *
     * @param commands 多个指令
     */
    public void registerCommands(IcqCommand ... commands)
    {
        for (IcqCommand command : commands)
        {
            registerCommand(command);
        }
    }

    /**
     * 注册指令
     *
     * @param command 指令
     */
    public void registerCommand(IcqCommand command)
    {
        commands.put(command.properties().getName().toLowerCase(), command);
        command.properties().getAlias().forEach(alias -> commands.put(alias.toLowerCase(), command));
    }

    /**
     * 自动找到注册过的指令对象运行
     * <p>
     * 例子:
     * !ecHO hi there
     *
     * @param event 事件
     */
    public void runCommand(EventMessage event)
    {
        PicqBotX bot = event.getBot();

        final boolean isGM = event instanceof EventGroupMessage;
        final boolean isDM = event instanceof EventDiscussMessage;
        final boolean isPM = event instanceof EventPrivateMessage;

        // 获取Args
        CommandArgs args;

        try
        {
            args = parse(this, event.getMessage(), isDM || isGM);
        }
        catch (NotACommandException | CommandNotFoundException e)
        {
            return;
        }

        // 判断维护
        if (bot.getConfig().isMaintenanceMode())
        {
            event.respond(bot.getConfig().getMaintenanceResponse());
            return;
        }

        // 获取发送者
        User user = bot.getUserManager().getUserFromID(event.getSenderId());

        // 获取群
        Group group = isGM ? bot.getGroupManager().getGroupFromID(((EventGroupMessage) event).getGroupId()) :
                isDM ? bot.getGroupManager().getGroupFromID(((EventDiscussMessage) event).getDiscussId()) : null;

        // 调用指令执行方法
        IcqCommand runner = args.getCommandRunner();

        if (runner instanceof EverywhereCommand)
        {
            event.respond(((EverywhereCommand) runner).run(event, user, args.getCommandName(), args.getArgs()));
        }
        else if (isGM && runner instanceof GroupCommand)
        {
            event.respond(((GroupCommand) runner).groupMessage((EventGroupMessage) event,
                    bot.getGroupUserManager().getUserFromID(user.getId(), group), group,
                    args.getCommandName(), args.getArgs()));
        }
        else if (isDM && runner instanceof DiscussCommand)
        {
            event.respond(((DiscussCommand) runner).discussMessage((EventDiscussMessage) event,
                    bot.getGroupUserManager().getUserFromID(user.getId(), group), group,
                    args.getCommandName(), args.getArgs()));
        }
        else if (isPM && runner instanceof PrivateCommand)
        {
            event.respond(((PrivateCommand) runner).privateMessage((EventPrivateMessage) event, user,
                    args.getCommandName(), args.getArgs()));
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
        commands.forEach((k, v) -> result.add(v));
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
        getCommandList().forEach(command -> result.add(command.properties().getName()));
        return result;
    }
}
