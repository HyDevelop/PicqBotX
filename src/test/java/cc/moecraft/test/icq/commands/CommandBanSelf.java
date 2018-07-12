package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventGroupOrDiscussMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.returndata.RawReturnData;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/07/11 创建!
 * Created by Hykilpikonna on 2018/07/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandBanSelf implements GroupCommand
{
    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("banself", "banme", "禁言我", "闭嘴", "闭嘴!");
    }

    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args)
    {
        if (sender.isAdmin()) return "不好意思不好意思权限狗打扰了";
        if (!event.isAdmin()) return "但我不是管理员";

        long period;

        try
        {
            if (args.size() == 0) period = 5 * 60L;
            else period = Long.parseLong(args.get(0));
        }
        catch (NumberFormatException e)
        {
            return "错误: 输入无法转换为数字";
        }

        event.ban(period);

        return "为什么要作死呢? @" + sender.getInfo().getNickname();
    }
}
