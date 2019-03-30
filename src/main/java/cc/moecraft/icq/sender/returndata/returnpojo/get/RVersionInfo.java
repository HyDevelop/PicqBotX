package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 版本信息
 *
 * @author Hykilpikonna
 */
@Data
@Setter(AccessLevel.NONE)
public class RVersionInfo implements ReturnPojoBase
{
    @SerializedName("coolq_directory")
    @Expose
    private String coolqDirectory;

    @SerializedName("coolq_edition")
    @Expose
    private String coolqEdition;

    @SerializedName("plugin_build_configuration")
    @Expose
    private String pluginBuildConfiguration;

    @SerializedName("plugin_build_number")
    @Expose
    private Long pluginBuildNumber;

    @SerializedName("plugin_version")
    @Expose
    private String pluginVersion;
}
