package cc.moecraft.test.icq.features.annoy;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 此类由 Hykilpikonna 在 2018/06/12 创建!
 * Created by Hykilpikonna on 2018/06/12!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class AnnoyingListener extends IcqListener
{
    public static HashMap<Long, ArrayList<Long>> annoyingQqMap = new HashMap<>();
    public static ArrayList<Long> annoyingGroupList = new ArrayList<>();

    @EventHandler
    public void onGroupText(EventGroupMessage event)
    {
        if (annoyingGroupList.contains(event.getGroupId()))
        {
            event.respond(event.getMessage());
            return;
        }

        if (!annoyingQqMap.containsKey(event.getGroupId())) return;

        if (annoyingQqMap.get(event.getGroupId()).contains(event.getSenderId()))
        {
            event.respond(event.getMessage());
        }
    }
}
