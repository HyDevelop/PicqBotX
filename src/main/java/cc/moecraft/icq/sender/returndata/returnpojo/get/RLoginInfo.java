package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登陆信息返回值
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RLoginInfo extends ReturnPojoBase
{
    @SerializedName("nickname")
    @Expose
    public String nickname;

    @SerializedName("user_id")
    @Expose
    public Long userId;
}
