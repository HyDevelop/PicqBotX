package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * 陌生人信息返回值
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
public class RStrangerInfo extends ReturnPojoBase
{
    @SerializedName("age")
    @Expose
    public Long age;

    @SerializedName("nickname")
    @Expose
    public String nickname;

    @SerializedName("sex")
    @Expose
    public String sex;

    @SerializedName("user_id")
    @Expose
    public Long userId;
}
