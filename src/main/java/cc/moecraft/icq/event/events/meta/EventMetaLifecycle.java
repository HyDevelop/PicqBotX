package cc.moecraft.icq.event.events.meta;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * The class {@code EventMetaLifecycle} is the pojo class for the
 * lifecycle event.
 * <p>
 * Class created by the HyDEV Team on 2019-03-30!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-30 15:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventMetaLifecycle extends EventMeta
{
    @SerializedName("sub_type")
    @Expose
    private LifecycleType lifecycleType;

    public enum LifecycleType
    {
        ENABLE, DISABLE
    }
}
