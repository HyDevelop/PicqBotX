package cc.moecraft.icq.accounts;

import cc.moecraft.icq.event.events.local.EventLocalSendGroupMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroup;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/08/25 创建!
 * Created by Hykilpikonna on 2018/08/25!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class AccountManager
{
    @Getter
    private final ArrayList<BotAccount> accounts;

    @Getter
    private Map<Long, Map<BotAccount, Long>> groupAccountIndex; // 群索引, <群号, <加了群的机器人号, 发送消息数量>>

    @Getter
    private Map<Long, BotAccount> idIndex; // 机器人QQ号索引, <QQ号, 机器人号实例>

    public AccountManager()
    {
        accounts = new ArrayList<>();
        refreshCache();
    }
    /**
     * 刷新缓存
     */
    public void refreshCache()
    {
        this.groupAccountIndex = new HashMap<>();
        this.idIndex = new HashMap<>();

        for (BotAccount account : accounts) for (RGroup rGroup : account.getHttpApi().getGroupList().getData())
        {
            if (!groupAccountIndex.containsKey(rGroup.groupId)) groupAccountIndex.put(rGroup.groupId, new HashMap<>());
            this.groupAccountIndex.get(rGroup.groupId).put(account, 0L);
            this.idIndex.put(account.getId(), account);
        }
    }
}
