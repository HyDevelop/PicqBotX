package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/06/13 创建!
 * Created by Hykilpikonna on 2018/06/13!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CommandCls implements EverywhereCommand
{
    public static String clsMessage = null;

    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        if (clsMessage == null)
        {
            clsMessage = "";
            for (int i = 0; i < 2000; i++) clsMessage += "\n";
            clsMessage += "已清屏!";
        }

        return clsMessage;
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("clear", "cls", "清屏");
    }
}
