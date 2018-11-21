package cc.moecraft.test.icq.features.annoy;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此类由 Hykilpikonna 在 2018/06/12 创建!
 * Created by Hykilpikonna on 2018/06/12!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CommandAnnoy implements GroupCommand
{
    Pattern regexFindAt = Pattern.compile("(?<=\\[CQ:at,qq=).*?(?=])");

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("annoy", "复读");
    }

    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args)
    {
        if (sender.getId() != 871674895L) return "没有权限_(:з」∠)_";

        if (args.size() != 1) return "你要干什么?";

        if (args.get(0).equalsIgnoreCase("clear"))
        {
            AnnoyingListener.annoyingQqMap = new HashMap<>();
            AnnoyingListener.annoyingGroupList = new ArrayList<>();
            return "复读列表已清空";
        }

        long groupId = event.getGroupId();

        if (args.get(0).equalsIgnoreCase("all"))
        {
            if (AnnoyingListener.annoyingGroupList.contains(groupId))
            {
                AnnoyingListener.annoyingGroupList.remove(groupId);
                return "已从复读列表中移除" + groupId + "群里的所有人";
            }
            else
            {
                AnnoyingListener.annoyingGroupList.add(groupId);
                return "复读列表已添加" + groupId + "群里的所有人";
            }
        }

        long qq;

        Matcher matcher = regexFindAt.matcher(args.get(0));

        try
        {
            if (matcher.find()) qq = Long.parseLong(matcher.group());
            else qq = Long.parseLong(args.get(0));
        }
        catch (Exception e)
        {
            return "QQ号检测失败";
        }

        if (!AnnoyingListener.annoyingQqMap.containsKey(groupId)) AnnoyingListener.annoyingQqMap.put(groupId, new ArrayList<>());

        if (AnnoyingListener.annoyingQqMap.get(groupId).contains(qq))
        {
            AnnoyingListener.annoyingQqMap.get(groupId).remove(qq);
            return "已从复读列表中移除" + qq;
        }
        else
        {
            AnnoyingListener.annoyingQqMap.get(groupId).add(qq);
            return "复读列表已添加" + qq;
        }
    }
}
