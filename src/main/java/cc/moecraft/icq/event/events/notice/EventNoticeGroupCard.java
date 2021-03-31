package cc.moecraft.icq.event.events.notice;

import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 群成员名片更新事件
 * go-cqhttp 文档备注: 此事件不保证时效性, 仅在收到消息时校验卡片
 *
 * @author CrazyKid (i@crazykid.moe)
 * @since 2021/3/31 09:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventNoticeGroupCard extends EventNotice {
    /**
     * 群号
     */
    @SerializedName("group_id")
    @Expose
    protected Long groupId;

    /**
     * 新名片 (当名片是空时, 为空字符串, 并不是QQ昵称)
     */
    @SerializedName("card_new")
    @Expose
    protected String cardNew;

    /**
     * 旧名片 (当名片是空时, 为空字符串, 并不是QQ昵称)
     */
    @SerializedName("card_old")
    @Expose
    protected String cardOld;

    /**
     * 获取群对象
     *
     * @return 群对象
     */
    public Group getGroup() {
        return getBot().getGroupManager().getGroupFromID(groupId);
    }

    /**
     * 获取群名片变更的群用户对象
     *
     * @return 群名片变更的群用户对象
     */
    public GroupUser getGroupUser() {
        return getBot().getGroupUserManager().getUserFromID(userId, getGroup());
    }

    @Override
    public boolean contentEquals(Object o) {
        if (!(o instanceof EventNoticeGroupCard)) return false;
        EventNoticeGroupCard other = (EventNoticeGroupCard) o;

        return super.contentEquals(o) &&
                other.getCardNew().equals(this.getCardNew()) &&
                other.getCardOld().equals(this.getCardOld()) &&
                other.getGroupId().equals(this.getGroupId());
    }
}
