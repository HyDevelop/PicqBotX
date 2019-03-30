package cc.moecraft.icq.sender.returndata.returnpojo.send;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 所有消息响应数据POJO
 *
 * @author Hykilpikonna
 */
@Data
@Setter(AccessLevel.NONE)
public class RMessageReturnData implements ReturnPojoBase
{
    @SerializedName("message_id")
    @Expose
    private Long messageId;
}
