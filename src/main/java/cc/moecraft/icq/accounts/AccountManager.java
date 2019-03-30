package cc.moecraft.icq.accounts;

import cc.moecraft.icq.event.events.local.EventLocalSendGroupMessage;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroup;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 此类由 Hykilpikonna 在 2018/08/25 创建!
 * Created by Hykilpikonna on 2018/08/25!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@Getter
public class AccountManager
{
    /**
     * 机器人账号 (酷Q地址) 列表
     */
    private final ArrayList<BotAccount> accounts;

    /**
     * 群索引, [群号, [加了群的机器人号, 发送消息数量]]
     */
    private Map<Long, Map<BotAccount, Long>> groupAccountIndex;

    /**
     * 机器人QQ号索引, [QQ号, 机器人号实例]
     */
    private Map<Long, BotAccount> idIndex;

    /**
     * 构造器: 初始化账号列表
     */
    public AccountManager()
    {
        accounts = new ArrayList<>();
        refreshCache();
    }

    /**
     * 添加账号
     *
     * @param accounts 账号信息
     */
    public void addAccount(BotAccount... accounts)
    {
        this.accounts.addAll(new ArrayList<>(Arrays.asList(accounts)));
        refreshCache();
    }

    /**
     * 获取当前发送频率最小的账号
     *
     * @param groupId 群号
     * @return 发送频率最小的账号
     */
    public BotAccount getOptimal(long groupId)
    {
        BotAccount minAccount = null;

        long minMessages = Long.MAX_VALUE;
        for (Entry<BotAccount, Long> entry : groupAccountIndex.get(groupId).entrySet())
        {
            if (entry.getValue() >= minMessages)
            {
                continue;
            }

            minAccount = entry.getKey();
            minMessages = entry.getValue();
        }

        return minAccount;
    }

    /**
     * 刷新缓存
     */
    public void refreshCache()
    {
        // Initialize indexes
        groupAccountIndex = new HashMap<>();
        idIndex = new HashMap<>();

        // Loop through all of the accounts.
        for (BotAccount account : accounts)
        {
            idIndex.put(account.getId(), account);

            // Loop through all of the groups for each account.
            for (RGroup group : account.getHttpApi().getGroupList().getData())
            {
                // Register the group to the index.
                if (!groupAccountIndex.containsKey(group.getGroupId()))
                {
                    groupAccountIndex.put(group.getGroupId(), new HashMap<>());
                }

                // Set the group message count to 0 and add to ID index.
                groupAccountIndex.get(group.getGroupId()).put(account, 0L);
            }
        }
    }

    /**
     * 获取未指定账号的API. (推荐只有一个账号的时候用)
     *
     * @return 第一个API.
     */
    public IcqHttpApi getNonAccountSpecifiedApi()
    {
        return accounts.size() < 1 ? null : accounts.get(0).getHttpApi();
    }

    /**
     * 记录消息
     *
     * @param event 事件
     */
    protected void recordMessage(EventLocalSendGroupMessage event)
    {
        Map<BotAccount, Long> map = groupAccountIndex.get(event.getId());
        map.put(event.getBotAccount(), map.get(event.getBotAccount()) + 1);
    }
}
