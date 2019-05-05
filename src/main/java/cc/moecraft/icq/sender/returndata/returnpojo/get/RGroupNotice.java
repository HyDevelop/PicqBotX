package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 群通知POJO
 * <p>
 * Class created by the HyDEV Team on 2019-05-05!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-05-05 12:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter(AccessLevel.NONE)
public class RGroupNotice implements ReturnPojoBase
{
    @SerializedName("cn")
    @Expose
    public Long cn;

    @SerializedName("fid")
    @Expose
    public String fid;

    @SerializedName("fn")
    @Expose
    public Long fn;

    /** 消息 */
    @SerializedName("msg")
    @Expose
    public NoticeMessage message;

    /** 发布时间 (单位是秒) */
    @SerializedName("pubt")
    @Expose
    public Long publishTimeSeconds;

    @SerializedName("read_num")
    @Expose
    public Long readNum;

    @SerializedName("settings")
    @Expose
    public NoticeSettings settings;

    /** 发送群通知的管理员ID */
    @SerializedName("u")
    @Expose
    public Long sender;

    @SerializedName("vn")
    @Expose
    public Long vn;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter(AccessLevel.NONE)
    public class NoticeMessage
    {
        /** 内容 */
        @SerializedName("text")
        @Expose
        public String content;

        @SerializedName("text_face")
        @Expose
        public String textFace;

        /** 标题 */
        @SerializedName("title")
        @Expose
        public String title;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter(AccessLevel.NONE)
    public class NoticeSettings
    {
        @SerializedName("is_show_edit_card")
        @Expose
        public Long isShowEditCard;

        @SerializedName("remind_ts")
        @Expose
        public Long remindTs;
    }
}
