package cc.moecraft.icq.event.events.message;

import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import cc.moecraft.icq.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 私聊消息事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventPrivateMessage extends EventMessage
{
    @SerializedName("sub_type")
    @Expose
    public String subType;

    @Override
    public ReturnData<RMessageReturnData> respond(String message, boolean raw)
    {
        return respondPrivateMessage(message, raw);
    }

    @Override
    public User getSender()
    {
        return getBot().getUserManager().getUserFromID(senderId);
    }
}
