package cc.moecraft.icq.event.events.message;

import cc.moecraft.icq.event.Event;
import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import cc.moecraft.icq.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

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
@Setter(AccessLevel.NONE)
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
     * @return 消息发送结果
     */
    public ReturnData<RMessageReturnData> respond(String message)
    {
        return respond(message, false);
    }

    /**
     * 回复消息
     * @param message 消息
     * @param raw 是否无加工发送
     * @return 消息发送结果
     */
    public abstract ReturnData<RMessageReturnData> respond(String message, boolean raw);

    /**
     * 回复到私聊
     * @param message 消息
     * @return 消息发送结果
     */
    public ReturnData<RMessageReturnData> respondPrivateMessage(String message)
    {
        return respondPrivateMessage(message, false);
    }

    /**
     * 回复到私聊
     * @param message 消息
     * @param raw 是否无加工发送
     * @return 消息发送结果
     */
    public ReturnData<RMessageReturnData> respondPrivateMessage(String message, boolean raw)
    {
        return getBot().getHttpApi().sendPrivateMsg(getSenderId(), message, raw);
    }

    /**
     * 获取消息发送者的用户对象
     * @return 用户对象
     */
    public abstract User getSender();
}
