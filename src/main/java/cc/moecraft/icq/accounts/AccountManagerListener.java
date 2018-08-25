package cc.moecraft.icq.accounts;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.local.EventLocalSendGroupMessage;
import lombok.AllArgsConstructor;

/**
 * 此类由 Hykilpikonna 在 2018/08/25 创建!
 * Created by Hykilpikonna on 2018/08/25!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@AllArgsConstructor
public class AccountManagerListener extends IcqListener
{
    private AccountManager accountManager;

    @EventHandler
    public void onGroupMessage(EventLocalSendGroupMessage event)
    {
        accountManager.recordMessage(event);
    }
}
