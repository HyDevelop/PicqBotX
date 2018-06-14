package cc.moecraft.icq.event.events.message;

import cc.moecraft.icq.event.Event;
import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
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

    @SerializedName("user_id")
    @Expose
    public Long senderId;

    /**
     * 回复消息
     * @param message 消息
     */
    public ReturnData<RMessageReturnData> respond(String message)
    {
        return respond(message, false);
    }

    /**
     * 回复消息
     * @param message 消息
     * @param raw 是否无加工发送
     */
    public abstract ReturnData<RMessageReturnData> respond(String message, boolean raw);

    /**
     * 回复到私聊
     * @param message 消息
     */
    public ReturnData<RMessageReturnData> respondPrivateMessage(String message)
    {
        return respondPrivateMessage(message, false);
    }

    /**
     * 回复到私聊
     * @param message 消息
     * @param raw 是否无加工发送
     */
    public ReturnData<RMessageReturnData> respondPrivateMessage(String message, boolean raw)
    {
        return getBot().getHttpApi().sendPrivateMsg(getSenderId(), message, raw);
    }
}
