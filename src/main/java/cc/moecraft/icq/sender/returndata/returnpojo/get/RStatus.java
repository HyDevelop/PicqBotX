package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取插件运行状态
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RStatus extends ReturnPojoBase
{
    @SerializedName("app_enabled")
    @Expose
    public Boolean appEnabled;

    @SerializedName("app_good")
    @Expose
    public Boolean appGood;

    @SerializedName("app_initialized")
    @Expose
    public Boolean appInitialized;

    @SerializedName("good")
    @Expose
    public Boolean good;

    @SerializedName("online")
    @Expose
    public Boolean online;

    @SerializedName("plugins_good")
    @Expose
    public Boolean pluginsGood;
}
