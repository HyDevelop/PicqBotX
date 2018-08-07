package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取VIP信息
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RVipInfo extends ReturnPojoBase
{
    @SerializedName("level")
    @Expose
    public Long level;

    @SerializedName("level_speed")
    @Expose
    public Double levelSpeed;

    @SerializedName("nickname")
    @Expose
    public String nickname;

    @SerializedName("user_id")
    @Expose
    public Long userId;

    @SerializedName("vip_growth_speed")
    @Expose
    public Long vipGrowthSpeed;

    @SerializedName("vip_growth_total")
    @Expose
    public Long vipGrowthTotal;

    @SerializedName("vip_level")
    @Expose
    public String vipLevel;
}
