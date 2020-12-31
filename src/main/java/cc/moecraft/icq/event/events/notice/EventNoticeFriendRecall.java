package cc.moecraft.icq.event.events.notice;

import cc.moecraft.icq.sender.returndata.returnpojo.get.RMessage;
import cc.moecraft.icq.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 好友消息撤回事件
 *
 * @author CrazyKid (i@crazykid.moe)
 * @since 2020/12/30 21:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventNoticeFriendRecall extends EventNotice {
    /**
     * 被撤回的消息 ID
     */
    @SerializedName("message_id")
    @Expose
    protected Long messageId;

    /**
     * 获取消息发送者的用户对象
     *
     * @return 消息发送者的用户对象
     */
    public User getSender() {
        return getBot().getUserManager().getUserFromID(userId);
    }

    /**
     * 获取被撤回的消息
     *
     * @param raw 是否获取未经处理的消息
     * @return 消息内容
     */
    public String getMessage(boolean raw) {
        RMessage message = getHttpApi().getMsg(messageId).getData();
        return raw ? message.getMessageRaw() : message.getMessage();
    }

    @Override
    public boolean contentEquals(Object o) {
        if (!(o instanceof EventNoticeFriendRecall)) return false;
        EventNoticeFriendRecall other = (EventNoticeFriendRecall) o;

        return super.contentEquals(o) &&
                other.getMessageId().equals(this.getMessageId());
    }
}
