package cc.moecraft.icq.event.events.notice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * 群文件上传事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
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
}
