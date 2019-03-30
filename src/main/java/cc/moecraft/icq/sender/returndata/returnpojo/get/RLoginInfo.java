package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 登陆信息返回值
 *
 * @author Hykilpikonna
 */
@Data
@Setter(AccessLevel.NONE)
public class RLoginInfo implements ReturnPojoBase
{
    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("user_id")
    @Expose
    private Long userId;
}
