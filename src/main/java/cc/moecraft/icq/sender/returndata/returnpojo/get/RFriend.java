package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * This class is the model for the data returned from the
 * {@code /get_friend_list} api node.
 * <p>
 * Class created by the HyDEV Team on 2019-10-14!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-10-14 14:10
 */
@Data
@Setter(AccessLevel.NONE)
public class RFriend implements ReturnPojoBase
{
    @SerializedName("user_id")
    @Expose
    private Long userId;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("remark")
    @Expose
    private String remark;
}
