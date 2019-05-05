package cc.moecraft.icq.event.events.notice;

import cc.moecraft.icq.accounts.BotAccount;
import cc.moecraft.icq.event.methodsets.GroupEventMethods;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 群文件上传事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
@ToString(callSuper = true)
public class EventNoticeGroupUpload extends EventNotice
{
    @SerializedName("file")
    @Expose
    public File file;

    @SerializedName("group_id")
    @Expose
    public Long groupId;

    @Data
    @Setter(AccessLevel.NONE)
    public class File
    {
        @SerializedName("busid")
        @Expose
        public Long busid;

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("size")
        @Expose
        public Long size;
    }

    private GroupEventMethods groupMethods = null;

    @Override
    public boolean contentEquals(Object o)
    {
        if (!(o instanceof EventNoticeGroupUpload)) return false;
        EventNoticeGroupUpload other = (EventNoticeGroupUpload) o;

        return super.contentEquals(o) &&
                other.getGroupId().equals(getGroupId()) &&
                other.getFile().equals(getFile());
    }

    public GroupEventMethods getGroupMethods()
    {
        if (groupMethods != null)
        {
            return groupMethods;
        }
        return groupMethods = new GroupEventMethods(this, groupId);
    }

    @Override
    public BotAccount getBotAccount()
    {
        return getGroupMethods().getBotAccount();
    }
}
