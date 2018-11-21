package cc.moecraft.icq.event.events.notice;

import cc.moecraft.icq.event.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 此类由 Hykilpikonna 在 2018/05/25 创建!
 * Created by Hykilpikonna on 2018/05/25!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
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
}
