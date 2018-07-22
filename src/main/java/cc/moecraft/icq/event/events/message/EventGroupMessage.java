package cc.moecraft.icq.event.events.message;

import cc.moecraft.icq.sender.returndata.RawReturnData;
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

    /**
     * 禁言这个用户
     * @param duration 时长(秒)
     */
    public void ban(long duration)
    {
        if (anonymous != null)
        {
            getHttpApi().setGroupAnonymousBan(anonymous, groupId, duration);
        }
        else
        {
            getHttpApi().setGroupBan(groupId, senderId, duration);
        }
    }

    @Override
    public User getSender()
    {
        return getBot().getUserManager().getUserFromID(senderId);
    }

    @Override
    public Group getGroup()
    {
        return getBot().getGroupManager().getGroupFromID(groupId);
    }

    @Override
    public void kick()
    {
        getHttpApi().setGroupKick(groupId, senderId);
    }

    /**
     * 获取发送者是不是匿名状态
     * @return 是不是匿名
     */
    public boolean isSenderAnonymous()
    {
        return anonymous != null;
    }

    /**
     * 获取一个用户为GroupUser对象
     * @param userId 用户的QQ号
     * @return GroupUser对象
     */
    public GroupUser getGroupUser(long userId)
    {
        return getBot().getGroupUserManager().getUserFromID(userId, getGroup());
    }

    /**
     * 一个用户是不是管理员
     * @param userId 用户的QQ号
     * @return 是不是管理员
     */
    public boolean isAdmin(long userId)
    {
        return getGroupUser(userId).isAdmin();
    }

    /**
     * 自己是不是管理员
     * @return 是不是管理员
     */
    public boolean isAdmin()
    {
        return isAdmin(selfId);
    }

    /**
     * 撤回消息 (我知道是recall但是酷Q叫他delete那我就封装成delete啦!
     */
    public RawReturnData delete()
    {
        return getHttpApi().deleteMsg(getMessageId());
    }

    public RawReturnData recall()
    {
        return delete();
    }
}
