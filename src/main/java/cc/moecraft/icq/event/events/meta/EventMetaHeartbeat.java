package cc.moecraft.icq.event.events.meta;

import cc.moecraft.icq.sender.returndata.returnpojo.get.RStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * The class {@code EventMetaLifecycle} is the pojo class for the
 * heartbeat event.
 * <p>
 * Class created by the HyDEV Team on 2019-03-30!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-30 15:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventMetaHeartbeat extends EventMeta
{
    @SerializedName("status")
    @Expose
    private RStatus status;
}
