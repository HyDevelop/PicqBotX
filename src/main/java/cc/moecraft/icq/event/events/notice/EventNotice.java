package cc.moecraft.icq.event.events.notice;

import cc.moecraft.icq.event.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 通知事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventNotice extends Event
{
    @SerializedName("notice_type")
    @Expose
    public String noticeType;

    @SerializedName("user_id")
    @Expose
    public Long userId;

    @Override
    public boolean contentEquals(Object o)
    {
        if (!(o instanceof EventNotice)) return false;
        EventNotice other = (EventNotice) o;

        return super.contentEquals(o) &&
                other.getNoticeType().equals(this.getNoticeType()) &&
                other.getUserId().equals(this.getUserId());
    }
}
