package cc.moecraft.icq.event.events.notice.groupmember.increase;

import cc.moecraft.icq.event.events.notice.groupmember.EventNoticeGroupMemberChange;
import lombok.ToString;

/**
 * 群员增加事件
 *
 * @author Hykilpikonna
 */
@ToString(callSuper = true)
public class EventNoticeGroupMemberIncrease extends EventNoticeGroupMemberChange
{
    public void kick()
    {
        getHttpApi().setGroupKick(getGroupId(), getUserId());
    }
}
