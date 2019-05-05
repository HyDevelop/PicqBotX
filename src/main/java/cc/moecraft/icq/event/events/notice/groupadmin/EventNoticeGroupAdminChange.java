package cc.moecraft.icq.event.events.notice.groupadmin;

import cc.moecraft.icq.accounts.BotAccount;
import cc.moecraft.icq.event.events.notice.EventNotice;
import cc.moecraft.icq.event.methodsets.GroupEventMethods;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 群管理员更改事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventNoticeGroupAdminChange extends EventNotice
{
    @SerializedName("group_id")
    @Expose
    public Long groupId;

    @SerializedName("sub_type")
    @Expose
    public String subType;

    private GroupEventMethods groupMethods = null;

    @Override
    public boolean contentEquals(Object o)
    {
        if (!(o instanceof EventNoticeGroupAdminChange)) return false;
        EventNoticeGroupAdminChange other = (EventNoticeGroupAdminChange) o;

        return super.contentEquals(o) &&
                other.getGroupId().equals(getGroupId()) &&
                other.getSubType().equals(getSubType()) &&
                other.getUserId().equals(getUserId());
    }

    public GroupEventMethods getGroupMethods()
    {
        if (groupMethods != null)
        {
            return groupMethods;
        }
        return groupMethods = new GroupEventMethods(this, groupId);
    }

    @Override
    public BotAccount getBotAccount()
    {
        return getGroupMethods().getBotAccount();
    }
}
