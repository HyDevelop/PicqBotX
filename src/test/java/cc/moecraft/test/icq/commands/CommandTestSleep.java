package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ThreadUtils;

import java.util.ArrayList;

/**
 * A command to test Thread.sleep and async features.
 * <p>
 * Class created by the HyDEV Team on 2019-04-08!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-04-08 21:50
 */
public class CommandTestSleep implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        event.respond("Sleeping... (2 minutes)");
        ThreadUtils.safeSleep(2 * 60 * 1000);
        return "Finished";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("test-sleep");
    }
}
