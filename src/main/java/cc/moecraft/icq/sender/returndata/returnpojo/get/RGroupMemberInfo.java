package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取群成员列表 / 群成员信息
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RGroupMemberInfo extends ReturnPojoBase
{
    @SerializedName("age")
    @Expose
    public Long age;

    @SerializedName("area")
    @Expose
    public String area;

    @SerializedName("card")
    @Expose
    public String card;

    @SerializedName("card_changeable")
    @Expose
    public Boolean cardChangeable;

    @SerializedName("group_id")
    @Expose
    public Long groupId;

    @SerializedName("join_time")
    @Expose
    public Long joinTime;

    @SerializedName("last_sent_time")
    @Expose
    public Long lastSentTime;

    @SerializedName("level")
    @Expose
    public String level;

    @SerializedName("nickname")
    @Expose
    public String nickname;

    @SerializedName("role")
    @Expose
    public String role;

    @SerializedName("sex")
    @Expose
    public String sex;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("title_expire_time")
    @Expose
    public Long titleExpireTime;

    @SerializedName("unfriendly")
    @Expose
    public Boolean unfriendly;

    @SerializedName("user_id")
    @Expose
    public Long userId;
}
