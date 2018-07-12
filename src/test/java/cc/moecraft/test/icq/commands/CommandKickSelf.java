package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/07/11 创建!
 * Created by Hykilpikonna on 2018/07/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandKickSelf implements GroupCommand
{
    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args)
    {
        if (sender.isAdmin()) return "不好意思不好意思权限狗打扰了";
        if (!event.isAdmin()) return "但我不是管理员";

        String nickname = sender.getInfo().getNickname();
        event.kick(); // 踢掉
        return "为什么要作死呢? @" + nickname; // 不能用@就改成Nickname好了
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("kickself", "kickme", "我要飞", "踢我", "把我踢了");
    }
}
