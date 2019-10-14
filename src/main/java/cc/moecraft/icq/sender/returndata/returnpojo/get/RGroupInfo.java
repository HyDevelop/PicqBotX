package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 获取群信息
 *
 * @author Hykilpikonna
 */
@Data
@Setter(AccessLevel.NONE)
public class RGroupInfo implements ReturnPojoBase
{
    @SerializedName("group_id")
    @Expose
    private Long groupId;

    @SerializedName("group_name")
    @Expose
    private String groupName;

    @SerializedName("member_count")
    @Expose
    private Integer memberCount;

    @SerializedName("max_member_count")
    @Expose
    private Integer maxMemberCount;
}
