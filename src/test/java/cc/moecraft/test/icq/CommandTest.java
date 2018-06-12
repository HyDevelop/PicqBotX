package cc.moecraft.test.icq;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.DiscussCommand;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.command.interfaces.PrivateCommand;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandTest implements EverywhereCommand
{
    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("test", "测试", "测试2");
    }

    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        return "测试返回值 - 用户信息: " + sender.getInfo();
    }
}
