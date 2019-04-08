package cc.moecraft.test.fools.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;

/**
 * TODO: No description yet...
 * <p>
 * Class created by the HyDEV Team on 2019-03-31!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-31 22:25
 */
public class CommandHelp implements GroupCommand
{
    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args)
    {
        AFCommand.groups.remove(group.getId());
        return new ComponentImage("https://i.imgur.com/Ze8NAtD.jpg").toString();
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("help", "帮助", "?");
    }
}
