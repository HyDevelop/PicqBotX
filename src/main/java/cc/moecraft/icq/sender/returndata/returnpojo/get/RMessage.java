package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import cc.moecraft.icq.utils.CQUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * 根据消息id获取消息接口(/get_msg)返回的内容
 * 字段取自 OneBot 标准
 * 实际可获取的字段根据使用的机器人框架不同而有差异。
 * https://github.com/howmanybots/onebot/blob/master/v11/specs/api/public.md#get_msg-%E8%8E%B7%E5%8F%96%E6%B6%88%E6%81%AF
 *
 * @author CrazyKid (i@crazykid.moe)
 * @since 2020/12/30 19:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter(AccessLevel.NONE)
public class RMessage implements ReturnPojoBase {
    /**
     * 发送时间
     */
    @SerializedName("time")
    @Expose
    private Long time;

    /**
     * 消息类型
     *
     * 可能的值: private/私聊信息 group/群聊信息
     */
    @SerializedName("message_type")
    @Expose
    private String messageType;

    /**
     * 消息 ID
     */
    @SerializedName("message_id")
    @Expose
    private Long messageId;

    /**
     * 消息真实 ID
     */
    @SerializedName("real_id")
    @Expose
    private Long realId;

    /**
     * 发送人信息
     */
    @SerializedName("sender")
    @Expose
    private Sender sender;

    /**
     * 消息内容
     */
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * 未经处理的消息内容 (这个字段 OneBot 标准里没有, go-cqhttp里面有)
     */
    @SerializedName("message_raw")
    @Expose
    private String messageRaw;

    public String getMessage() {
        return CQUtils.decodeMessage(message);
    }

    public String getMessageRaw() {
        if (messageRaw == null) {
            return null;
        }
        return CQUtils.decodeMessage(messageRaw);
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class Sender {
        /**
         * 发送者 QQ 号
         */
        @SerializedName("user_id")
        @Expose
        private Long userId;

        /**
         * 昵称
         */
        @SerializedName("nickname")
        @Expose
        private String nickName;

        /**
         * 性别，male 或 female 或 unknown
         */
        @SerializedName("sex")
        @Expose
        private String sex;

        /**
         * 年龄
         */
        @SerializedName("age")
        @Expose
        private Integer age;

        /**
         * 群名片/备注 (仅群消息才有)
         */
        @SerializedName("card")
        @Expose
        private String card;

        /**
         * 地区 (仅群消息才有)
         */
        @SerializedName("area")
        @Expose
        private String area;

        /**
         * 成员等级 (仅群消息才有)
         */
        @SerializedName("level")
        @Expose
        private String level;

        /**
         * 角色 (仅群消息才有)
         *
         * 可能的值: owner/群主 admin/群管 member/成员
         */
        @SerializedName("role")
        @Expose
        private String role;

        /**
         * 专属头衔 (仅群消息才有)
         */
        @SerializedName("title")
        @Expose
        private String title;
    }
}
