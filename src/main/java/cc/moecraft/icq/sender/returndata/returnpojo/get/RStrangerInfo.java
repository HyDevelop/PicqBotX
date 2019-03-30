package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 陌生人信息返回值
 *
 * @author Hykilpikonna
 */
@Data
@Setter(AccessLevel.NONE)
public class RStrangerInfo implements ReturnPojoBase
{
    @SerializedName("age")
    @Expose
    private Long age;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("user_id")
    @Expose
    private Long userId;
}
