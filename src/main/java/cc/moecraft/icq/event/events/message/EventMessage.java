package cc.moecraft.icq.event.events.message;

import cc.moecraft.icq.event.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class EventMessage extends Event
{
    @SerializedName("message_type")
    @Expose
    public String messageType;

    @SerializedName("font")
    @Expose
    public Long font;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("message_id")
    @Expose
    public Long messageId;

    @SerializedName("raw_message")
    @Expose
    public String rawMessage;

    @SerializedName("self_id")
    @Expose
    public Long selfId;

    @SerializedName("time")
    @Expose
    public Long time;

    @SerializedName("user_id")
    @Expose
    public Long userId;

    /**
     * 回复消息
     * @param message 消息
     */
    public abstract void respond(String message);
}
