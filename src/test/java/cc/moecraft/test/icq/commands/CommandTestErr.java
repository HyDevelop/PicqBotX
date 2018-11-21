package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/08/24 创建!
 * Created by Hykilpikonna on 2018/08/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CommandTestErr implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        return String.valueOf(1/0);
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("test-divide-zero");
    }
}
