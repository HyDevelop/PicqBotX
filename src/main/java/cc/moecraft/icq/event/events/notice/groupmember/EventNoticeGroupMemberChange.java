package cc.moecraft.icq.event.events.notice.groupmember;

import cc.moecraft.icq.event.events.notice.EventNotice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 群员数量更改事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EventNoticeGroupMemberChange extends EventNotice
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
}
