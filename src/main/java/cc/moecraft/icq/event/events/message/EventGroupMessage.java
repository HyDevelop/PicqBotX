package cc.moecraft.icq.event.events.message;

import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.moecraft.icq.user.User;
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
public class EventGroupMessage extends EventGroupOrDiscussMessage
{
    @SerializedName("anonymous")
    @Expose
    public Anonymous anonymous;

    @SerializedName("group_id")
    @Expose
    public Long groupId;

    @SerializedName("sub_type")
    @Expose
    public String subType;

    @Data
    public class Anonymous
    {
        @SerializedName("flag")
        @Expose
        public String flag;

        @SerializedName("id")
        @Expose
        public Long id;

        @SerializedName("name")
        @Expose
        public String name;
    }

    @Override
    public ReturnData<RMessageReturnData> respond(String message, boolean raw)
    {
        return getBot().getHttpApi().sendGroupMsg(groupId, message, raw);
    }
}
