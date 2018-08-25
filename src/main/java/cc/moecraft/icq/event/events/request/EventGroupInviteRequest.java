package cc.moecraft.icq.event.events.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 群邀请事件 ( 别人邀请机器人的时候 )
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class EventGroupInviteRequest extends EventRequest
{
    @SerializedName("group_id")
    @Expose
    public Long groupId;

    @SerializedName("sub_type")
    @Expose
    public String subType;

    @Override
    public void accept()
    {
        getHttpApi().approveGroupRequest(flag, subType); // TODO: 测试
    }

    @Override
    public void reject(String reason)
    {
        getHttpApi().rejectGroupRequest(flag, subType, reason);
    }
}
