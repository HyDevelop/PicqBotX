package cc.moecraft.icq.listeners;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.local.EventLocalSendMessage;

/**
 * 此类由 Hykilpikonna 在 2018/08/26 创建!
 * Created by Hykilpikonna on 2018/08/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class HyExpressionListener extends IcqListener
{
    @EventHandler
    public void onMessageSendEvent(EventLocalSendMessage event)
    {
        if (event.getBot().getHyExpressionResolver() != null)
        {
            event.setMessage(event.getBot().getHyExpressionResolver().resolve(event.getMessage()));
        }
    }
}
