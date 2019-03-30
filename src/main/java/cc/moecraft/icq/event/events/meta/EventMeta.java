package cc.moecraft.icq.event.events.meta;

import cc.moecraft.icq.event.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * The class {@code EventMeta} is a pojo class for meta events.
 * <p>
 * Class created by the HyDEV Team on 2019-03-30!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-30 15:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventMeta extends Event
{
    @SerializedName("meta_event_type")
    @Expose
    private String metaEventType;
}
