package cc.moecraft.icq.user;

import cc.moecraft.icq.PicqBotX;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@RequiredArgsConstructor
public class GroupUserManager
{
    private final PicqBotX bot;

    public Map<Long, GroupUser> userCache = new HashMap<>();

    /**
     * 用ID获取User
     *
     * @param id QQ号
     * @param group 群对象
     * @return User对象
     */
    public GroupUser getUserFromID(long id, Group group)
    {
        if (userCache.containsKey(id))
        {
            return userCache.get(id);
        }
        userCache.put(id, new GroupUser(bot, id, group));
        return getUserFromID(id, group);
    }
}
