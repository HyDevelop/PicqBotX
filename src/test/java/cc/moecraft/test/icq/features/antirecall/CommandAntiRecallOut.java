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
public class CommandAntiRecallOut implements GroupCommand
{
    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args)
    {
        if (args.size() == 0)
        {
            StringBuilder ultimateMessage = new StringBuilder();

            for (String message : AntiRecallListener.groupTexts.get(group.getId()))
                ultimateMessage.append(message).append("\n");

            return ultimateMessage.toString();
        }
        if (args.size() == 2)
        {
            StringBuilder ultimateMessage = new StringBuilder();

            try
            {
                int from = Integer.parseInt(args.get(0));
                int to = Integer.parseInt(args.get(1));

                ArrayList<String> messageList = AntiRecallListener.groupTexts.get(group.getId());

                for (int i = from; i < to; i++)
                    ultimateMessage.append(messageList.get(i)).append("\n");

                return ultimateMessage.toString();
            }
            catch (NumberFormatException ignored)
            {

            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "未知错误, 见后台";
            }
        }

        return "bot -arout [index from] [index to]";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("arout");
    }
}
