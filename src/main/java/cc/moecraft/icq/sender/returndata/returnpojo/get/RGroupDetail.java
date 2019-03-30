package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

/**
 * 此类由 Hykilpikonna 在 2018/07/20 创建!
 * Created by Hykilpikonna on 2018/07/20!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@Data
@Setter(AccessLevel.NONE)
public class RGroupDetail implements ReturnPojoBase
{
    @SerializedName("admin_count")
    @Expose
    private Long adminCount;

    @SerializedName("admins")
    @Expose
    private List<Admin> admins = null;

    @SerializedName("category")
    @Expose
    private Long category;

    @SerializedName("create_time")
    @Expose
    private Long createTime;

    @SerializedName("group_id")
    @Expose
    private Long groupId;

    @SerializedName("group_name")
    @Expose
    private String groupName;

    @SerializedName("introduction")
    @Expose
    private String introduction;

    @SerializedName("max_admin_count")
    @Expose
    private Long maxAdminCount;

    @SerializedName("max_member_count")
    @Expose
    private Long maxMemberCount;

    @SerializedName("member_count")
    @Expose
    private Long memberCount;

    @SerializedName("owner_id")
    @Expose
    private Long ownerId;

    @Data
    @Setter(AccessLevel.NONE)
    public static class Admin
    {
        @SerializedName("nickname")
        @Expose
        private String nickname;

        @SerializedName("role")
        @Expose
        private String role;

        @SerializedName("user_id")
        @Expose
        private Long userId;
    }
}
