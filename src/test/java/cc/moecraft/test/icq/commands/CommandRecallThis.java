package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.returndata.ReturnStatus;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/07/22 创建!
 * Created by Hykilpikonna on 2018/07/22!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CommandRecallThis implements GroupCommand
{
    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args)
    {
        if (sender.isAdmin()) return "不好意思不好意思权限狗打扰了";
        if (!event.isAdmin()) return "但我不是管理员";

        if (event.delete().getStatus() == ReturnStatus.ok) return "已撤回";
        else return "撤回失败!";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("recallthis", "撤回这条");
    }
}
