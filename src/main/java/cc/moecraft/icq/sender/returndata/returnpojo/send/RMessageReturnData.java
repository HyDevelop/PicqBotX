package cc.moecraft.icq.sender.returndata.returnpojo.send;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 所有消息响应数据POJO
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RMessageReturnData extends ReturnPojoBase
{
    @SerializedName("message_id")
    @Expose
    public Long messageId;
}
