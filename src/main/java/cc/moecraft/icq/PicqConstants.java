package cc.moecraft.icq;

import com.google.gson.Gson;

/**
 * The class {@code PicqConstants} is a class with static and final
 * constant fields. They can be changed with reflection.
 * <p>
 * Class created by the HyDEV Team on 2019-03-21!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-21 18:45
 */
public class PicqConstants
{
    // 版本常量 | Version constant

    /** 当前版本 */
    public static final String VERSION = "4.10.1.928";

    /** 兼容版本检测Regex */
    public static String HTTP_API_VERSION_DETECTION = ".*4.10.*";


    // 可变常量 | Variable constant

    /** 多账号优化用来判断一个事件是不是新的的时间间隔 */
    public static int MAO_JUDGEMENT_TIME_INTERVAL_SEC = 2;


    // 事件字段名 | Event key names
    public static final String EVENT_KEY_SUBTYPE = "sub_type";
    public static final String EVENT_KEY_POST_TYPE = "post_type";

    public static final String EVENT_KEY_POST_TYPE_MESSAGE = "message";
    public static final String EVENT_KEY_POST_TYPE_NOTICE = "notice";
    public static final String EVENT_KEY_POST_TYPE_REQUEST = "request";
    public static final String EVENT_KEY_POST_TYPE_META = "meta_event";

    public static final String EVENT_KEY_MESSAGE_TYPE = "message_type";
    public static final String EVENT_KEY_MESSAGE_TYPE_PRIVATE = "private";
    public static final String EVENT_KEY_MESSAGE_TYPE_GROUP = "group";
    public static final String EVENT_KEY_MESSAGE_TYPE_DISCUSS = "discuss";

    public static final String EVENT_KEY_REQUEST_TYPE = "request_type";
    public static final String EVENT_KEY_REQUEST_TYPE_FRIEND = "friend";
    public static final String EVENT_KEY_REQUEST_TYPE_GROUP = "group";
    public static final String EVENT_KEY_REQUEST_TYPE_GROUP_ADD = "add";
    public static final String EVENT_KEY_REQUEST_TYPE_GROUP_INVITE = "invite";

    public static final String EVENT_KEY_NOTICE_TYPE = "notice_type";
    public static final String EVENT_KEY_NOTICE_TYPE_GROUP_UPLOAD = "group_upload";
    public static final String EVENT_KEY_NOTICE_TYPE_FRIEND_ADD = "friend_add";
    public static final String EVENT_KEY_NOTICE_TYPE_GROUP_ADMIN = "group_admin";
    public static final String EVENT_KEY_NOTICE_TYPE_GROUP_ADMIN_SET = "set";
    public static final String EVENT_KEY_NOTICE_TYPE_GROUP_ADMIN_UNSET = "unset";
    public static final String EVENT_KEY_NOTICE_TYPE_GROUP_DECREASE = "group_decrease";
    public static final String EVENT_KEY_NOTICE_TYPE_GROUP_DECREASE_LEAVE = "leave";
    public static final String EVENT_KEY_NOTICE_TYPE_GROUP_DECREASE_KICK = "kick";
    public static final String EVENT_KEY_NOTICE_TYPE_GROUP_DECREASE_KICK_ME = "kick_me";
    public static final String EVENT_KEY_NOTICE_TYPE_GROUP_INCREASE = "group_increase";
    public static final String EVENT_KEY_NOTICE_TYPE_GROUP_INCREASE_APPROVE = "approve";
    public static final String EVENT_KEY_NOTICE_TYPE_GROUP_INCREASE_INVITE = "invite";

    public static final String EVENT_KEY_META_TYPE = "meta_event_type";
    public static final String EVENT_KEY_META_TYPE_HEARTBEAT = "heartbeat";
    public static final String EVENT_KEY_META_TYPE_LIFECYCLE = "lifecycle";


    // GSON 常量
    public static Gson gsonRead = new Gson();
    public static Gson gsonWrite = new Gson();
}
