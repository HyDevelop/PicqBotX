package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 获取 Cookies
 *
 * @author Hykilpikonna
 */
@Data
@Setter(AccessLevel.NONE)
public class RCookies implements ReturnPojoBase
{
    @SerializedName("cookies")
    @Expose
    private String cookies;
}
