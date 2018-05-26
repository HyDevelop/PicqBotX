package cc.moecraft.icq.event.events.notice.groupadmin;

import cc.moecraft.icq.event.events.notice.EventNotice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 群管理员更改事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EventNoticeGroupAdminChange extends EventNotice
{
    @SerializedName("group_id")
    @Expose
    public Long groupId;

    @SerializedName("sub_type")
    @Expose
    public String subType;
}
