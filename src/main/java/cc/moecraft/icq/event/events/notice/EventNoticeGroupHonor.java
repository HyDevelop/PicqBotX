package cc.moecraft.icq.event.events.notice;

import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 群成员荣誉变更事件
 *
 * @author CrazyKid (i@crazykid.moe)
 * @since 2021/3/31 09:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventNoticeGroupHonor extends EventNotice {
    /**
     * 群号
     */
    @SerializedName("group_id")
    @Expose
    protected Long groupId;

    /**
     * 荣誉类型
     * talkative:龙王 performer:群聊之火 emotion:快乐源泉
     */
    @SerializedName("honor_type")
    @Expose
    protected String honorType;

    /**
     * 获取群对象
     *
     * @return 群对象
     */
    public Group getGroup() {
        return getBot().getGroupManager().getGroupFromID(groupId);
    }

    /**
     * 获取荣誉变更的群用户对象
     *
     * @return 荣誉变更的群用户对象
     */
    public GroupUser getGroupUser() {
        return getBot().getGroupUserManager().getUserFromID(userId, getGroup());
    }

    /**
     * 是否是龙王
     *
     * @return 是否是龙王
     */
    public boolean isDragonKing() {
        return "talkative".equals(honorType);
    }

    /**
     * 是否是群聊之火
     *
     * @return 是否是群聊之火
     */
    public boolean isPerformer() {
        return "performer".equals(honorType);
    }

    /**
     * 是否是快乐源泉
     *
     * @return 是否是快乐源泉
     */
    public boolean isEmotion() {
        return "emotion".equals(honorType);
    }

    @Override
    public boolean contentEquals(Object o) {
        if (!(o instanceof EventNoticeGroupHonor)) return false;
        EventNoticeGroupHonor other = (EventNoticeGroupHonor) o;

        return super.contentEquals(o) &&
                other.getHonorType().equals(this.getHonorType()) &&
                other.getGroupId().equals(this.getGroupId());
    }
}
