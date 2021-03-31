package cc.moecraft.icq.event.events.notice;

import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 群成员红包运气王事件
 *
 * @author CrazyKid (i@crazykid.moe)
 * @since 2021/3/31 09:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventNoticeGroupLuckyKing extends EventNotice {
    /**
     * 群号
     */
    @SerializedName("group_id")
    @Expose
    protected Long groupId;

    /**
     * 运气王id
     */
    @SerializedName("target_id")
    @Expose
    protected Long targetId;

    /**
     * 获取群对象
     *
     * @return 群对象
     */
    public Group getGroup() {
        return getBot().getGroupManager().getGroupFromID(groupId);
    }

    /**
     * 获取红包发送者的群用户对象
     *
     * @return 红包发送者的群用户对象
     */
    public GroupUser getRedpackSenderGroupUser() {
        return getBot().getGroupUserManager().getUserFromID(userId, getGroup());
    }

    /**
     * 获取运气王的群用户对象
     *
     * @return 红包发送者的群用户对象
     */
    public GroupUser getLuckyKingGroupUser() {
        return getBot().getGroupUserManager().getUserFromID(targetId, getGroup());
    }

    @Override
    public boolean contentEquals(Object o) {
        if (!(o instanceof EventNoticeGroupLuckyKing)) return false;
        EventNoticeGroupLuckyKing other = (EventNoticeGroupLuckyKing) o;

        return super.contentEquals(o) &&
                other.getTargetId().equals(this.getTargetId()) &&
                other.getGroupId().equals(this.getGroupId());
    }
}
