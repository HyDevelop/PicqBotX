package cc.moecraft.icq.event.events.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 加群请求事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class EventGroupAddRequest extends EventRequest
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

    @Override
    public boolean contentEquals(Object o)
    {
        if (!(o instanceof EventGroupAddRequest)) return false;
        EventGroupAddRequest other = (EventGroupAddRequest) o;

        return super.contentEquals(o) &&
                other.getFlag().equals(getFlag()) &&
                other.getUserId().equals(getUserId());
    }
}
