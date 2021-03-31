package cc.moecraft.icq.event.events.notice;

import cc.moecraft.icq.sender.returndata.returnpojo.get.RMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 群消息撤回事件
 *
 * @author CrazyKid (i@crazykid.moe)
 * @since 2020/12/29 21:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventNoticeGroupRecall extends EventNotice {
    /**
     * 群号
     */
    @SerializedName("group_id")
    @Expose
    protected Long groupId;

    /**
     * 撤回操作者 QQ 号
     */
    @SerializedName("operator_id")
    @Expose
    protected Long operatorId;

    /**
     * 被撤回的消息 ID
     */
    @SerializedName("message_id")
    @Expose
    protected Long messageId;

    /**
     * 获取群对象
     *
     * @return 群对象
     */
    public Group getGroup() {
        return getBot().getGroupManager().getGroupFromID(groupId);
    }

    /**
     * 获取撤回操作者的群用户对象
     *
     * @return 撤回操作者的群用户对象
     */
    public GroupUser getOperatorGroupUser() {
        return getBot().getGroupUserManager().getUserFromID(operatorId, getGroup());
    }

    /**
     * 获取被撤回消息的发送者的群用户对象
     *
     * @return 被撤回消息的发送者的群用户对象
     */
    public GroupUser getMessageSenderGroupUser() {
        return getBot().getGroupUserManager().getUserFromID(userId, getGroup());
    }

    /**
     * 撤回操作者是不是管理员
     *
     * @return 是不是管理员
     */
    public boolean isOperatorAdmin() {
        return getOperatorGroupUser().isAdmin();
    }

    /**
     * 被撤回消息的发送者是不是管理员
     *
     * @return 是不是管理员
     */
    public boolean isMessageSenderAdmin()
    {
        return getMessageSenderGroupUser().isAdmin();
    }

    /**
     * 自己是不是管理员
     *
     * @return 是不是管理员
     */
    public boolean isAdmin()
    {
        return getBot().getGroupUserManager().getUserFromID(selfId, getGroup()).isAdmin();
    }

    /**
     * 获取被撤回的消息
     *
     * @param raw 是否获取未经处理的消息
     * @return 消息内容
     */
    public String getMessage(boolean raw) {
        RMessage message = getHttpApi().getMsg(messageId).getData();
        if (raw && message.getMessageRaw() != null) {
            return message.getMessageRaw();
        }
        return message.getMessage();
    }

    @Override
    public boolean contentEquals(Object o) {
        if (!(o instanceof EventNoticeGroupRecall)) return false;
        EventNoticeGroupRecall other = (EventNoticeGroupRecall) o;

        return super.contentEquals(o) &&
                other.getGroupId().equals(this.getGroupId()) &&
                other.getOperatorId().equals(this.getOperatorId()) &&
                other.getMessageId().equals(this.getMessageId());
    }
}
