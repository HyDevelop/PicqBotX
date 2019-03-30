package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 获取VIP信息
 *
 * @author Hykilpikonna
 */
@Data
@Setter(AccessLevel.NONE)
public class RVipInfo implements ReturnPojoBase
{
    @SerializedName("level")
    @Expose
    private Long level;

    @SerializedName("level_speed")
    @Expose
    private Double levelSpeed;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("user_id")
    @Expose
    private Long userId;

    @SerializedName("vip_growth_speed")
    @Expose
    private Long vipGrowthSpeed;

    @SerializedName("vip_growth_total")
    @Expose
    private Long vipGrowthTotal;

    @SerializedName("vip_level")
    @Expose
    private String vipLevel;
}
