package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

/**
 * The class {@code CommandCmdList} prints the list of commands
 * registered.
 * <p>
 * Class created by the HyDEV Team on 2019-03-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-24 14:38
 */
public class CommandCmdList implements EverywhereCommand
{

    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        MessageBuilder builder = new MessageBuilder().add("这个机器人只有这些指令嗯w:").newLine();
        for (String cmdName : event.getBot().getCommandManager().getCommandNameList())
        {
            builder.add(cmdName).add(", ");
        }
        return builder.toString();
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("cmdlist", "help");
    }
}
