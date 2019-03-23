package cc.moecraft.icq.utils;

import cc.moecraft.icq.event.Event;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.moecraft.icq.user.User;

/**
 * 此类由 Hykilpikonna 在 2018/08/26 创建!
 * Created by Hykilpikonna on 2018/08/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class EventUtils
{
    /**
     * 获取用户对象
     *
     * @param event 事件
     * @param id 用户id
     * @return 用户对象
     */
    public static User getUser(Event event, long id)
    {
        return event.getBot().getUserManager().getUserFromID(id);
    }

    /**
     * 获取用户对象
     *
     * @param event 事件
     * @param groupId 用户id
     * @return 用户对象
     */
    public static Group getGroup(Event event, long groupId)
    {
        return event.getBot().getGroupManager().getGroupFromID(groupId);
    }

    /**
     * 获取群用户对象
     *
     * @param event 事件
     * @param groupId 群id
     * @param id 用户id
     * @return 用户对象
     */
    public static GroupUser getGroupUser(Event event, long groupId, long id)
    {
        return event.getBot().getGroupUserManager().getUserFromID(id, getGroup(event, groupId));
    }
}
