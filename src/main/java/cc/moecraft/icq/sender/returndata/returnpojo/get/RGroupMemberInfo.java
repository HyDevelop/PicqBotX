package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 获取群成员列表 / 群成员信息
 *
 * @author Hykilpikonna
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter(AccessLevel.NONE)
public class RGroupMemberInfo implements ReturnPojoBase
{
    @SerializedName("age")
    @Expose
    private Long age;

    @SerializedName("area")
    @Expose
    private String area;

    @SerializedName("card")
    @Expose
    private String card;

    @SerializedName("card_changeable")
    @Expose
    private Boolean cardChangeable;

    @SerializedName("group_id")
    @Expose
    private Long groupId;

    @SerializedName("join_time")
    @Expose
    private Long joinTime;

    @SerializedName("last_sent_time")
    @Expose
    private Long lastSentTime;

    @SerializedName("level")
    @Expose
    private String level;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("title_expire_time")
    @Expose
    private Long titleExpireTime;

    @SerializedName("unfriendly")
    @Expose
    private Boolean unfriendly;

    @SerializedName("user_id")
    @Expose
    private Long userId;
}
