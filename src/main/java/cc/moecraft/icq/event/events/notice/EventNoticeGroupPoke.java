package cc.moecraft.icq.event.events.notice;

import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 群戳一戳事件
 *
 * @author CrazyKid (i@crazykid.moe)
 * @since 2020/12/30 21:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventNoticeGroupPoke extends EventNotice {
    /**
     * 被戳者 QQ 号
     */
    @SerializedName("target_id")
    @Expose
    protected Long targetId;

    /**
     * 群号
     */
    @SerializedName("group_id")
    @Expose
    protected Long groupId;

    /**
     * 获取群对象
     *
     * @return 群对象
     */
    public Group getGroup() {
        return getBot().getGroupManager().getGroupFromID(groupId);
    }

    /**
     * 获取戳人者的群用户对象
     *
     * @return 戳人者的群用户对象
     */
    public GroupUser getSenderGroupUser() {
        return getBot().getGroupUserManager().getUserFromID(userId, getGroup());
    }

    /**
     * 获取被戳者的群用户对象
     *
     * @return 被戳者的群用户对象
     */
    public GroupUser getTargetGroupUser() {
        return getBot().getGroupUserManager().getUserFromID(targetId, getGroup());
    }


    @Override
    public boolean contentEquals(Object o) {
        if (!(o instanceof EventNoticeGroupPoke)) return false;
        EventNoticeGroupPoke other = (EventNoticeGroupPoke) o;

        return super.contentEquals(o) &&
                other.getTargetId().equals(this.getTargetId()) &&
                other.getGroupId().equals(this.getGroupId());
    }
}
