package cc.moecraft.icq.event.events.notice.groupmember;

import cc.moecraft.icq.event.ContentComparable;
import cc.moecraft.icq.event.events.notice.EventNotice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 群员数量更改事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventNoticeGroupMemberChange extends EventNotice implements ContentComparable<EventNoticeGroupMemberChange>
{
    @SerializedName("group_id")
    @Expose
    public Long groupId;

    @SerializedName("operator_id")
    @Expose
    public Long operatorId;

    @SerializedName("sub_type")
    @Expose
    public String subType;

    @Override
    public boolean contentEquals(EventNoticeGroupMemberChange other)
    {
        return other.getGroupId().equals(getGroupId()) &&
                other.getOperatorId().equals(getOperatorId()) &&
                other.getSubType().equals(getSubType());
    }
}
