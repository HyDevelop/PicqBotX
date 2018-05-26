package cc.moecraft.icq.event.events.request;

import cc.moecraft.icq.event.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 请求事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class EventRequest extends Event
{
    @SerializedName("request_type")
    @Expose
    public String requestType;

    @SerializedName("flag")
    @Expose
    public String flag;

    @SerializedName("comment")
    @Expose
    public String comment;

    @SerializedName("user_id")
    @Expose
    public Long userId;

    /**
     * 同意申请
     */
    public abstract void accept();

    /**
     * 拒绝申请
     */
    public abstract void reject(String reason);
}
