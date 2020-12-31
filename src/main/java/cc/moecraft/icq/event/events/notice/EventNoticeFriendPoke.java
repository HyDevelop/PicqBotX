package cc.moecraft.icq.event.events.notice;

import cc.moecraft.icq.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 好友戳一戳事件
 *
 * @author CrazyKid (i@crazykid.moe)
 * @since 2020/12/30 21:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventNoticeFriendPoke extends EventNotice {
    /**
     * 被戳者 QQ 号
     */
    @SerializedName("target_id")
    @Expose
    protected Long targetId;

    /**
     * 获取戳人者的用户对象
     *
     * @return 戳人者的用户对象
     */
    public User getUser() {
        return getBot().getUserManager().getUserFromID(userId);
    }

    @Override
    public boolean contentEquals(Object o) {
        if (!(o instanceof EventNoticeFriendPoke)) return false;
        EventNoticeFriendPoke other = (EventNoticeFriendPoke) o;

        return super.contentEquals(o) &&
                other.getTargetId().equals(this.getTargetId());
    }
}
