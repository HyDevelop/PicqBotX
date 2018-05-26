package cc.moecraft.icq.event.events.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EventPrivateMessage extends EventMessage
{
    @SerializedName("sub_type")
    @Expose
    public String subType;

    @Override
    public void respond(String message)
    {
        respondPrivateMessage(message);
    }
}
