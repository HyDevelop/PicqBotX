package cc.moecraft.icq.event.events.notice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 群禁言事件
 * <p>
 * Class created by the HyDEV Team on 2019-10-14!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-10-14 15:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventNoticeGroupBan extends EventNotice
{
    @SerializedName("sub_type")
    @Expose
    protected BanType type;

    @SerializedName("group_id")
    @Expose
    protected Long groupId;

    @SerializedName("operator_id")
    @Expose
    protected Long operatorId;

    @SerializedName("user_id")
    @Expose
    protected Long userId;

    @SerializedName("duration")
    @Expose
    protected Long duration;

    public enum BanType
    {
        @SerializedName("ban")
        BAN,

        @SerializedName("lift_ban")
        LIFT_BAN;
    }

    @Override
    public boolean contentEquals(Object o)
    {
        if (!(o instanceof EventNoticeGroupBan)) return false;
        EventNoticeGroupBan other = (EventNoticeGroupBan) o;

        return super.contentEquals(o) &&
                other.getGroupId().equals(getGroupId()) &&
                other.getOperatorId().equals(getOperatorId()) &&
                other.getUserId().equals(getUserId()) &&
                other.getDuration().equals(getDuration()) &&
                other.getType().equals(getType());
    }
}
