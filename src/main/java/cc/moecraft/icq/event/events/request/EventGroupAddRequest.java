package cc.moecraft.icq.event.events.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 加群请求事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
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
        getBot().getHttpApi().approveGroupRequest(flag, subType); // TODO: 测试
    }

    @Override
    public void reject(String reason)
    {
        getBot().getHttpApi().rejectGroupRequest(flag, subType, reason);
    }
}
