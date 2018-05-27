package cc.moecraft.icq.event.events.message;

import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EventDiscussMessage extends EventMessage
{
    @SerializedName("discuss_id")
    @Expose
    public Long discussId;

    /**
     * 回复一条讨论组消息
     * @param message 消息
     */
    @Override
    public ReturnData<RMessageReturnData> respond(String message)
    {
        return getBot().getHttpApi().sendDiscussMsg(discussId, message);
    }
}
