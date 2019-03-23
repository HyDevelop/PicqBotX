package cc.moecraft.icq.event.events.message;

import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import lombok.ToString;

/**
 * 此类由 Hykilpikonna 在 2018/07/11 创建!
 * Created by Hykilpikonna on 2018/07/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@ToString(callSuper = true)
public abstract class EventGroupOrDiscussMessage extends EventMessage
{
    /**
     * 获取群用户对象
     *
     * @return 发送消息的群用户
     */
    public GroupUser getGroupSender()
    {
        return getBot().getGroupUserManager().getUserFromID(senderId, getGroup());
    }

    /**
     * 获取群对象
     *
     * @return 群对象
     */
    public abstract Group getGroup();

    /**
     * 踢掉这个用户
     */
    public abstract void kick();
}
