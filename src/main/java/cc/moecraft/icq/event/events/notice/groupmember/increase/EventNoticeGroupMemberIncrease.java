package cc.moecraft.icq.event.events.notice.groupmember.increase;

import cc.moecraft.icq.event.events.notice.groupmember.EventNoticeGroupMemberChange;

/**
 * 群员增加事件
 *
 * @author Hykilpikonna
 */
public class EventNoticeGroupMemberIncrease extends EventNoticeGroupMemberChange
{
    public void kick()
    {
        getBot().getHttpApi().setGroupKick(getGroupId(), getUserId());
    }
}
