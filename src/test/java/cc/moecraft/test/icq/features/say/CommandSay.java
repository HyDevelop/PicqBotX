package cc.moecraft.test.icq.features.say;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ArrayUtils;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/06/13 创建!
 * Created by Hykilpikonna on 2018/06/13!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CommandSay implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        if (args.size() < 1) return "你要说什么?";

        return sender.getInfo().getNickname() + " >> " + ArrayUtils.getTheRestArgsAsString(args, 0);
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("say", "echo", "me", "说", "重复");
    }
}
