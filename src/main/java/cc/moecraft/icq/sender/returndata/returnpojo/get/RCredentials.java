package cc.moecraft.icq.sender.returndata.returnpojo.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 获取Credentials
 *
 * @author Hykilpikonna
 */
@Data
public class RCredentials
{
    @SerializedName("cookies")
    @Expose
    public String cookies;

    @SerializedName("csrf_token")
    @Expose
    public Long csrfToken;
}
