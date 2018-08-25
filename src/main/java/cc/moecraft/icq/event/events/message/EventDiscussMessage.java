package cc.moecraft.icq.event.events.message;

import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

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
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventDiscussMessage extends EventGroupOrDiscussMessage
{
    @SerializedName("discuss_id")
    @Expose
    public Long discussId;

    @Override
    public ReturnData<RMessageReturnData> respond(String message, boolean raw)
    {
        return getHttpApi().sendDiscussMsg(discussId, message, raw);
    }

    @Override
    public Group getGroup()
    {
        return getBot().getGroupManager().getGroupFromID(discussId);
    }

    @Override
    public void kick()
    {
        getHttpApi().setGroupKick(discussId, senderId);
    }

    @Override
    public User getSender()
    {
        return getBot().getUserManager().getUserFromID(senderId);
    }
}
