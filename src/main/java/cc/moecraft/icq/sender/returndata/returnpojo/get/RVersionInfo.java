package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * 版本信息
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
public class RVersionInfo extends ReturnPojoBase
{
    @SerializedName("coolq_directory")
    @Expose
    public String coolqDirectory;

    @SerializedName("coolq_edition")
    @Expose
    public String coolqEdition;

    @SerializedName("plugin_build_configuration")
    @Expose
    public String pluginBuildConfiguration;

    @SerializedName("plugin_build_number")
    @Expose
    public Long pluginBuildNumber;

    @SerializedName("plugin_version")
    @Expose
    public String pluginVersion;
}
