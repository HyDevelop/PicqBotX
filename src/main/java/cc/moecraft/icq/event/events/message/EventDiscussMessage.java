package cc.moecraft.icq.event.events.message;

import cc.moecraft.icq.event.ContentComparable;
import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import static cc.moecraft.icq.event.ComparableConstants.TimeDetectionRangeInSeconds;
import static cc.moecraft.icq.utils.CQUtils.removeCqCode;

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
@ToString(callSuper = true)
public class EventDiscussMessage extends EventGroupOrDiscussMessage implements ContentComparable<EventDiscussMessage>
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

    @Override
    public boolean contentEquals(EventDiscussMessage other)
    {
        return removeCqCode(other.getMessage()).equals(removeCqCode(getMessage())) &&
                other.getSenderId().equals(getSenderId()) &&
                Math.abs(other.getTime() - getTime()) < TimeDetectionRangeInSeconds &&
                other.getDiscussId().equals(getDiscussId());
    }
}
