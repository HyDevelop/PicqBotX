package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 群列表返回值
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RGroupList extends ReturnPojoBase
{
    @SerializedName("group_id")
    @Expose
    public Long groupId;

    @SerializedName("group_name")
    @Expose
    public String groupName;
}
