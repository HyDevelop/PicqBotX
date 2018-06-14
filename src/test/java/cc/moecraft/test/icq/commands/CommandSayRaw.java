package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import cc.moecraft.icq.utils.ArrayUtils;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/06/13 创建!
 * Created by Hykilpikonna on 2018/06/13!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandSayRaw implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        if (args.size() < 1) return "你要说什么?";

        event.respond(sender.getInfo().getNickname() + " >> " + ArrayUtils.getTheRestArgsAsString(args, 0), true);
        return null;
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("sayraw", "echoraw", "meraw");
    }
}
