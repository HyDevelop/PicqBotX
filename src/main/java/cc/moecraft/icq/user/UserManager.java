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
public class UserManager
{
    private final PicqBotX bot;

    public Map<Long, User> userCache = new HashMap<>();

    /**
     * 用ID获取User
     *
     * @param id QQ号
     * @return User对象
     */
    public User getUserFromID(long id)
    {
        if (userCache.containsKey(id))
        {
            return userCache.get(id);
        }
        userCache.put(id, new User(bot, id));
        return getUserFromID(id);
    }
}
