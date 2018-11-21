package cc.moecraft.test.icq.features.antirecall;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/06/13 创建!
 * Created by Hykilpikonna on 2018/06/13!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CommandAntiRecall implements GroupCommand
{
    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args)
    {
        int messageIndex;
        ArrayList<String> messages = AntiRecallListener.groupTexts.get(group.getId());
        if (messages == null) return "目前这个群里还没有消息记录";

        if (args.size() == 0)
        {
            messageIndex = messages.size() - 1;
        }
        else if (args.size() == 1)
        {
            if (args.get(0).equalsIgnoreCase("index")) return "到现在一共在这个群记录了" + AntiRecallListener.groupTexts.get(group.getId()).size() + "条消息";

            try
            {
                messageIndex = messages.size() - Integer.parseInt(args.get(0));
            }
            catch (Exception e)
            {
                return "只能输入数字或者abs哦!";
            }
        }
        else if (args.size() == 2)
        {
            if (!args.get(0).equalsIgnoreCase("abs")) return "你要干什么?";

            try
            {
                messageIndex = Integer.parseInt(args.get(1));
            }
            catch (Exception e)
            {
                return "只能输入数字或者abs哦!";
            }
        }
        else return "你要干什么?";

        try
        {
            return messages.get(messageIndex);
        }
        catch (IndexOutOfBoundsException e)
        {
            return "数字超出记录范围了, 目前这个群里只记录了" + messages.size() + "项消息";
        }
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("antirecall", "ar", "防撤回");
    }
}
