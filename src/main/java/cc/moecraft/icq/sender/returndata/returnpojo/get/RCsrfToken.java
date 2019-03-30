package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 获取 CSRF Token
 *
 * @author Hykilpikonna
 */
@Data
@Setter(AccessLevel.NONE)
public class RCsrfToken implements ReturnPojoBase
{
    @SerializedName("token")
    @Expose
    private Long token;
}
