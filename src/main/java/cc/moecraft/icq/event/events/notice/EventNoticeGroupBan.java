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
    public BanType type;

    @SerializedName("group_id")
    @Expose
    public Long groupId;

    @SerializedName("operator_id")
    @Expose
    public Long operatorId;

    @SerializedName("user_id")
    @Expose
    public Long userId;

    @SerializedName("duration")
    @Expose
    public Long duration;

    public enum BanType
    {
        @SerializedName("ban")
        BAN,

        @SerializedName("lift_ban")
        LIFT_BAN;
    }
}
