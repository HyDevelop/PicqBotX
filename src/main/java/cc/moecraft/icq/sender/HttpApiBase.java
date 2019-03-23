package cc.moecraft.icq.sender;

import cc.moecraft.icq.event.EventManager;
import cc.moecraft.icq.event.events.local.EventLocalSendDiscussMessage;
import cc.moecraft.icq.event.events.local.EventLocalSendGroupMessage;
import cc.moecraft.icq.event.events.local.EventLocalSendPrivateMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage.Anonymous;
import cc.moecraft.icq.sender.returndata.RawReturnData;
import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import cc.moecraft.utils.MapBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/19/24 创建!
 * Created by Hykilpikonna on 2018/19/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@SuppressWarnings("UnusedReturnValue")
public abstract class HttpApiBase
{
    // 发送区
    public static final String SEND_PRIVATE_MSG = "send_private_msg";
    public static final String SEND_GROUP_MSG = "send_group_msg";
    public static final String SEND_DISCUSS_MSG = "send_discuss_msg";
    public static final String SEND_LIKE = "send_like";

    // 撤回消息单独一类哈哈哈哈
    public static final String DELETE_MSG = "delete_msg";

    // 应用设置区
    public static final String SET_GROUP_KICK = "set_group_kick";
    public static final String SET_GROUP_BAN = "set_group_ban";
    public static final String SET_GROUP_ANONYMOUS_BAN = "set_group_anonymous_ban";
    public static final String SET_GROUP_WHOLE_BAN = "set_group_whole_ban";
    public static final String SET_GROUP_ADMIN = "set_group_admin";
    public static final String SET_GROUP_ANONYMOUS = "set_group_anonymous";
    public static final String SET_GROUP_CARD = "set_group_card";
    public static final String SET_GROUP_LEAVE = "set_group_leave";
    public static final String SET_GROUP_SPECIAL_TITLE = "set_group_special_title";
    public static final String SET_DISCUSS_LEAVE = "set_discuss_leave";
    public static final String SET_FRIEND_ADD_REQUEST = "set_friend_add_request";
    public static final String SET_GROUP_ADD_REQUEST = "set_group_add_request";

    // ICQ(酷Q, 以及HTTP插件)设置区
    public static final String SET_RESTART = "set_restart";
    public static final String SET_RESTART_PLUGIN = "set_restart_plugin";
    public static final String CLEAN_DATA_DIR = "clean_data_dir";
    public static final String CLEAN_PLUGIN_LOG = "clean_plugin_log";

    // 应用内获取区
    public static final String GET_LOGIN_INFO = "get_login_info";
    public static final String GET_STRANGER_INFO = "get_stranger_info";
    public static final String GET_GROUP_LIST = "get_group_list";
    public static final String GET_GROUP_MEMBER_INFO = "get_group_member_info";
    public static final String GET_GROUP_MEMBER_LIST = "get_group_member_list";
    public static final String GET_FRIEND_LIST = "_get_friend_list";
    public static final String GET_GROUP_INFO = "_get_group_info";
    public static final String GET_VIP_INFO = "_get_vip_info";
    public static final String GET_RECORD = "get_record";
    public static final String GET_IMAGE = "get_image";

    // 条件判断区
    public static final String CAN_SEND_IMAGE = "can_send_image";
    public static final String CAN_SEND_RECORD = "can_send_record";

    // ICQ(酷Q, 以及HTTP插件)获取区
    public static final String GET_VERSION_INFO = "get_version_info";
    public static final String GET_STATUS = "get_status";
    public static final String GET_COOKIES = "get_cookies";
    public static final String GET_CSRF_TOKEN = "get_csrf_token";
    public static final String GET_CREDENTIALS = "get_credentials";

    @Deprecated
    public static final String SEND_MSG = "send_msg";  // 这个不需要, 因为最后也要指定类型

    final String baseURL;
    final EventManager eventManager;

    @Getter
    long selfId;

    public HttpApiBase(EventManager eventManager, String baseUrl, int port)
    {
        this.eventManager = eventManager;

        baseUrl = baseUrl.toLowerCase();

        if (!baseUrl.contains("http://")) baseUrl = "http://" + baseUrl;
        this.baseURL = baseUrl + ":" + port + "/";
        if (this instanceof IcqHttpApi) selfId = ((IcqHttpApi) this).getLoginInfo().getData().getUserId();
    }

    /**
     * 发送请求
     * @param request 请求
     * @param parameters 参数
     * @return 响应
     */
    public abstract JsonElement send(String request, Map<String, Object> parameters);

    /**
     * 发送请求 封装x1
     * @param request 请求
     * @param parameters 参数
     * @return 响应
     */
    public JsonElement send(String request, Object... parameters)
    {
        return send(request, MapBuilder.build(String.class, Object.class, parameters));
    }

    /**
     * 发送请求 封装
     * @param request 请求
     * @param parameters 参数
     * @return 响应
     */
    public RawReturnData sendReturnRaw(String request, Map<String, Object> parameters)
    {
        return new Gson().fromJson(send(request, parameters), RawReturnData.class);
    }

    /**
     * 发送请求 封装
     * @param request 请求
     * @param parameters 参数
     * @return 响应
     */
    public RawReturnData sendReturnRaw(String request, Object... parameters)
    {
        return sendReturnRaw(request, MapBuilder.build(String.class, Object.class, parameters));
    }

    /**
     * 发送请求 封装
     * @param typeOfT 返回数据类型
     * @param request 请求
     * @param parameters 参数
     * @param <T> 返回值的类型
     * @return 响应
     */
    public <T extends ReturnPojoBase> ReturnData<T> send(Type typeOfT, String request, Map<String, Object> parameters)
    {
        return sendReturnRaw(request, parameters).processData(typeOfT);
    }

    /**
     * 发送请求 封装
     * @param typeOfT 返回数据类型
     * @param request 请求
     * @param parameters 参数
     * @param <T> 返回值的类型
     * @return 响应
     */
    public <T extends ReturnPojoBase> ReturnData<T> send(Class<T> typeOfT, String request, Object... parameters)
    {
        return send(typeOfT, request, MapBuilder.build(String.class, Object.class, parameters));
    }

    /**
     * 发送请求 封装
     * @param typeOfT 返回数据类型
     * @param request 请求
     * @param parameters 参数
     * @param <T> 返回值的类型
     * @return 响应
     */
    public <T extends ReturnPojoBase> ReturnListData<T> sendReturnList(Type typeOfT, String request, Map<String, Object> parameters)
    {
        return sendReturnRaw(request, parameters).processDataAsList(typeOfT);
    }

    /**
     * 发送请求 封装
     * @param typeOfT 返回数据类型
     * @param request 请求
     * @param parameters 参数
     * @param <T> 返回值的类型
     * @return 响应
     */
    public <T extends ReturnPojoBase> ReturnListData<T> sendReturnList(Class<T> typeOfT, String request, Object... parameters)
    {
        return sendReturnList(typeOfT, request, MapBuilder.build(String.class, Object.class, parameters));
    }

    /**
     * 发送私聊消息
     * @param qq      QQ号
     * @param message 消息
     * @return 发送消息结果
     */
    public ReturnData<RMessageReturnData> sendPrivateMsg(long qq, String message)
    {
        return sendPrivateMsg(qq, message, false);
    }

    /**
     * 发送私聊消息
     * @param qq      QQ号
     * @param message 消息
     * @param autoEscape 是否纯文本发送
     * @return 发送消息结果
     */
    public ReturnData<RMessageReturnData> sendPrivateMsg(long qq, String message, boolean autoEscape)
    {
        EventLocalSendPrivateMessage event = new EventLocalSendPrivateMessage(qq, message, autoEscape);
        event.selfId = selfId;
        eventManager.call(event);
        if (event.isCancelled()) return null;
        return send(RMessageReturnData.class, SEND_PRIVATE_MSG, "user_id", event.getId(), "message", event.getMessage(), "auto_escape", event.isAutoEscape());
    }

    /**
     * 发送群聊消息
     * @param groupId 群ID
     * @param message 消息
     * @return 发送消息结果
     */
    public ReturnData<RMessageReturnData> sendGroupMsg(long groupId, String message)
    {
        return sendGroupMsg(groupId, message, false);
    }

    /**
     * 发送群聊消息
     * @param groupId 群ID
     * @param message 消息
     * @param autoEscape 是否纯文本发送
     * @return 发送消息结果
     */
    public ReturnData<RMessageReturnData> sendGroupMsg(long groupId, String message, boolean autoEscape)
    {
        EventLocalSendGroupMessage event = new EventLocalSendGroupMessage(groupId, message, autoEscape);
        event.selfId = selfId;
        eventManager.call(event);
        if (event.isCancelled()) return null;
        return send(RMessageReturnData.class, SEND_GROUP_MSG, "group_id", event.getId(), "message", event.getMessage(), "auto_escape", event.isAutoEscape());
    }

    /**
     * 发送讨论组消息
     * @param groupId 讨论组ID
     * @param message 消息
     * @return 发送消息结果
     */
    public ReturnData<RMessageReturnData> sendDiscussMsg(long groupId, String message)
    {
        return sendDiscussMsg(groupId, message, false);
    }

    /**
     * 发送讨论组消息
     * @param groupId 讨论组ID
     * @param message 消息
     * @param autoEscape 是否纯文本发送
     * @return 发送消息结果
     */
    public ReturnData<RMessageReturnData> sendDiscussMsg(long groupId, String message, boolean autoEscape)
    {
        EventLocalSendDiscussMessage event = new EventLocalSendDiscussMessage(groupId, message, autoEscape);
        event.selfId = selfId;
        eventManager.call(event);
        if (event.isCancelled()) return null;
        return send(RMessageReturnData.class, SEND_DISCUSS_MSG, "discuss_id", event.getId(), "message", event.getMessage(), "auto_escape", event.isAutoEscape());
    }

    /**
     * 撤回消息
     * @param messageId 消息ID
     * @return 执行结果
     */
    public RawReturnData deleteMsg(long messageId)
    {
        return sendReturnRaw(DELETE_MSG, "message_id", messageId);
    }

    /**
     * 发送好友赞
     * @param qq    QQ号
     * @param times 赞的次数，每个好友每天最多 10 次
     * @return 执行结果
     */
    public RawReturnData sendLike(long qq, long times)
    {
        return sendReturnRaw(SEND_LIKE, "user_id", qq, "times", times);
    }

    /**
     * 群组踢人
     * @param groupId 群号
     * @param qq      QQ
     * @return 执行结果
     */
    public RawReturnData setGroupKick(long groupId, long qq)
    {
        return sendReturnRaw(SET_GROUP_KICK, "user_id", qq, "group_id", groupId);
    }

    /**
     * 群组踢人
     * @param groupId 群号
     * @param qq QQ
     * @param rejectFurtherRequest 拒绝这个人的加群请求
     * @return 执行结果
     */
    public RawReturnData setGroupKick(long groupId, long qq, boolean rejectFurtherRequest)
    {
        return sendReturnRaw(SET_GROUP_KICK, "user_id", qq, "group_id", groupId, "reject_and_request", rejectFurtherRequest);
    }

    /**
     * 群组单人禁言
     * @param groupId 群号
     * @param qq QQ
     * @param duration 禁言时长，单位秒，0 表示取消禁言
     * @return 执行结果
     */

    public RawReturnData setGroupBan(long groupId, long qq, long duration)
    {
        return sendReturnRaw(SET_GROUP_BAN, "user_id", qq, "group_id", groupId, "duration", duration);
    }

    /**
     * 群组匿名用户禁言
     * @param flag 要禁言的匿名用户的 flag（需从群消息上报的数据中获得）
     * @param groupId 群号
     * @param duration 禁言时长，单位秒，无法取消匿名用户禁言
     * @return 执行结果
     */
    public RawReturnData setGroupAnonymousBan(String flag, long groupId, long duration)
    {
        return sendReturnRaw(SET_GROUP_ANONYMOUS_BAN, "flag", flag, "group_id", groupId, "duration", duration);
    }

    /**
     * 群组匿名用户禁言
     * @param anonymous 要禁言的匿名用户的 Anonymous对象（需从群消息上报的数据中获得）
     * @param groupId 群号
     * @param duration 禁言时长，单位秒，无法取消匿名用户禁言
     * @return 执行结果
     */
    public RawReturnData setGroupAnonymousBan(Anonymous anonymous, long groupId, long duration)
    {
        return sendReturnRaw(SET_GROUP_ANONYMOUS_BAN, "anonymous", anonymous, "group_id", groupId, "duration", duration);
    }

    /**
     * 群组全员禁言
     * @param groupId 群号
     * @param enable 是否禁言
     * @return 执行结果
     */
    public RawReturnData setGroupWholeBan(long groupId, boolean enable)
    {
        return sendReturnRaw(SET_GROUP_WHOLE_BAN, "group_id", groupId, "enable", enable);
    }

    /**
     * 群组设置管理员
     * @param groupId 群号
     * @param qq      要设置管理员的 QQ 号
     * @param enable  true 为设置，false 为取消
     * @return 执行结果
     */
    public RawReturnData setGroupAdmin(long groupId, long qq, boolean enable)
    {
        return sendReturnRaw(SET_GROUP_ADMIN, "group_id", groupId, "user_id", qq, "enable", enable);
    }

    /**
     * 群组设置匿名
     * @param groupId 群号
     * @param enable  是否允许匿名聊天
     * @return 执行结果
     */
    public RawReturnData setGroupAnonymous(long groupId, boolean enable)
    {
        return sendReturnRaw(SET_GROUP_ANONYMOUS, "group_id", groupId, "enable", enable);
    }

    /**
     * 设置群名片（群备注）
     * @param groupId 群号
     * @param qq      要设置的 QQ 号
     * @param card  群名片内容，不填或空字符串表示删除群名片
     * @return 执行结果
     */
    public RawReturnData setGroupCard(long groupId, long qq, String card)
    {
        return sendReturnRaw(SET_GROUP_CARD, "group_id", groupId, "user_id", qq, "card", card);
    }

    /**
     * 退出群组
     *
     * @param groupId 群号
     * @return 执行结果
     */
    public RawReturnData setGroupLeave(long groupId)
    {
        return setGroupLeave(groupId, false);
    }

    /**
     * 退出群组
     *
     * @param groupId 群号
     * @param dismiss 是否解散, 如果登录号是群主, 则仅在此项为 true 时能够解散
     * @return 执行结果
     */
    public RawReturnData setGroupLeave(long groupId, boolean dismiss)
    {
        return sendReturnRaw(SET_GROUP_LEAVE, "group_id", groupId, "is_dismiss", dismiss);
    }

    /**
     * 设置群组专属头衔
     * @param groupId 群号
     * @param qq 要设置的QQ号
     * @param specialTitle 专属头衔，不填或空字符串表示删除专属头衔
     * @return 执行结果
     */
    public RawReturnData setGroupSpecialTitle(long groupId, long qq, String specialTitle)
    {
        return sendReturnRaw(SET_GROUP_SPECIAL_TITLE, "group_id", groupId, "user_id", qq, "special_title", specialTitle);
    }

    /**
     * 设置群组专属头衔
     * @param groupId 群号
     * @param qq 要设置的QQ号
     * @param specialTitle 专属头衔，不填或空字符串表示删除专属头衔
     * @param duration 专属头衔有效期，单位秒，-1 表示永久，不过此项似乎没有效果，可能是只有某些特殊的时间长度有效，有待测试
     * @return 执行结果
     */
    public RawReturnData setGroupSpecialTitle(long groupId, long qq, String specialTitle, long duration)
    {
        return sendReturnRaw(SET_GROUP_SPECIAL_TITLE, "group_id", groupId, "user_id", qq, "special_title", specialTitle, "duration", duration);
    }

    /**
     * 退出讨论组
     * @param discussId 讨论组 ID（正常情况下看不到，需要从讨论组消息上报的数据中获得）
     * @return 执行结果
     */
    public RawReturnData setDiscussLeave(long discussId)
    {
        return sendReturnRaw(SET_DISCUSS_LEAVE, "discuss_id", discussId);
    }

    /**
     * 处理加好友请求
     * @param flag 加好友请求的 flag（需从上报的数据中获得）
     * @param approve 是否同意请求
     * @return 执行结果
     */
    public RawReturnData setFriendAndRequest(String flag, boolean approve)
    {
        return sendReturnRaw(SET_FRIEND_ADD_REQUEST, "flag", flag, "approve", approve);
    }

    /**
     * 处理加好友请求
     * @param flag 加好友请求的 flag（需从上报的数据中获得）
     * @param approve 是否同意请求
     * @param remark 添加后的好友备注（仅在同意时有效）
     * @return 执行结果
     */
    public RawReturnData setFriendAndRequest(String flag, boolean approve, String remark)
    {
        return sendReturnRaw(SET_FRIEND_ADD_REQUEST, "flag", flag, "approve", approve, "remark", remark);
    }

    /**
     * 处理加群请求/邀请
     *
     * @param flag 加好友请求的 flag（需从上报的数据中获得）
     * @param type add 或 invite，请求类型（需要和上报消息中的 sub_type 字段相符）
     * @param approve 是否同意请求/邀请
     * @param reason 拒绝理由（仅在拒绝时有效）
     * @return 执行结果
     */
    public RawReturnData setGroupAndRequest(String flag, String type, boolean approve, String reason)
    {
        return sendReturnRaw(SET_GROUP_ADD_REQUEST, "flag", flag, "type", type, "approve", approve, "reason", reason);
    }

    /**
     * 同意加群请求／邀请
     * @param flag 加好友请求的 flag（需从上报的数据中获得）
     * @param type add 或 invite，请求类型（需要和上报消息中的 sub_type 字段相符）
     * @return 执行结果
     */
    public RawReturnData approveGroupRequest(String flag, String type)
    {
        return setGroupAndRequest(flag, type, true, "");
    }

    /**
     * 拒绝加群请求／邀请
     * @param flag 加好友请求的 flag（需从上报的数据中获得）
     * @param type add 或 invite，请求类型（需要和上报消息中的 sub_type 字段相符）
     * @param reason 拒绝理由
     * @return 执行结果
     */
    public RawReturnData rejectGroupRequest(String flag, String type, String reason)
    {
        return setGroupAndRequest(flag, type, false, reason);
    }

    /**
     * 重启 HTTP API 插件
     *
     * @return 执行结果
     */
    public RawReturnData setRestartPlugin()
    {
        return sendReturnRaw(SET_RESTART_PLUGIN);
    }

    /**
     * 重启 HTTP API 插件
     *
     * @param delay 要延迟的毫秒数, 如果默认情况下无法重启, 可以尝试设置延迟为 2000 左右
     * @return 执行结果
     */
    public RawReturnData setRestartPlugin(int delay)
    {
        return sendReturnRaw(SET_RESTART_PLUGIN, "delay", delay);
    }

    /**
     * 重启酷Q, 并以当前登录号自动登陆 (需要勾选快速登录)
     *
     * @return 执行结果
     */
    public RawReturnData setRestart()
    {
        return sendReturnRaw(SET_RESTART);
    }

    /**
     * 重启酷Q, 并以当前登录号自动登陆 (需要勾选快速登录)
     *
     * @param cleanLog 是否在重启时清空酷Q的日志数据库 (logv1.db)
     * @param cleanCache 是否在重启时清空酷Q的缓存数据库 (cache.db)
     * @param cleanEvent 是否在重启时清空酷Q的事件数据库 (eventv2.db)
     * @return 执行结果
     */
    public RawReturnData setRestart(boolean cleanLog, boolean cleanCache, boolean cleanEvent)
    {
        return sendReturnRaw(SET_RESTART, "clean_log", cleanLog,
                "clean_cache", cleanCache, "clean_event", cleanEvent);
    }

    /**
     * 清空数据文件夹
     *
     * @return 执行结果
     */
    public RawReturnData cleanDataDir()
    {
        return sendReturnRaw(CLEAN_DATA_DIR);
    }

    /**
     * 清空数据文件夹
     *
     * @param dataDir 要清理的目录名, 支持 image, record, show, bface
     * @return 执行结果
     */
    public RawReturnData cleanDataDir(String dataDir)
    {
        return sendReturnRaw(CLEAN_DATA_DIR, "data_dir", dataDir);
    }

    /**
     * 清空插件日志
     * @return 执行结果
     */
    public RawReturnData cleanPluginLog()
    {
        return sendReturnRaw(CLEAN_PLUGIN_LOG);
    }
}
