package cc.moecraft.test.icq.features.antirecall;

import cc.moecraft.icq.command.CommandArgs;
import cc.moecraft.icq.command.exceptions.CommandNotFoundException;
import cc.moecraft.icq.command.exceptions.NotACommandException;
import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 此类由 Hykilpikonna 在 2018/06/13 创建!
 * Created by Hykilpikonna on 2018/06/13!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class AntiRecallListener extends IcqListener
{
    public static HashMap<Long, ArrayList<String>> groupTexts = new HashMap<>();

    @EventHandler
    public void onGroupMessageEvent(EventGroupMessage event)
    {
        try
        {
            CommandArgs.parse(event.getBot().getCommandManager(), event.getMessage(), true);
            return; // 如果是指令, 就忽略
        }
        catch (NotACommandException | CommandNotFoundException ignored)
        {
            if (!groupTexts.containsKey(event.getGroupId())) groupTexts.put(event.getGroupId(), new ArrayList<>());

            groupTexts.get(event.getGroupId()).add(event.getBot().getUserManager().getUserFromID(event.getSenderId()).getInfo().getNickname() + " >> " + event.getMessage());
        }
    }
}
