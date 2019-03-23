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
public class GroupManager
{
    private final PicqBotX bot;

    public Map<Long, Group> groupCache = new HashMap<>();

    /**
     * 用ID获取群对象
     *
     * @param id QQ号
     * @return 群对象
     */
    public Group getGroupFromID(long id)
    {
        if (groupCache.containsKey(id))
        {
            return groupCache.get(id);
        }
        groupCache.put(id, new Group(bot, id));
        return getGroupFromID(id);
    }
}
