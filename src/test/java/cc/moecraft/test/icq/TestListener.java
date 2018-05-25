package cc.moecraft.test.icq;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class TestListener extends IcqListener
{
    @EventHandler
    public void onPMEvent(EventPrivateMessage event)
    {
        System.out.println("接到消息");

        // 纯属测试没有嘲讽意思啦...
        if (event.getMessage().equals("你以为这是yangjinhe/maintain-robot?"))
            event.respond("其实是我Hykilpikonna/PicqBotX哒!");
    }
}
