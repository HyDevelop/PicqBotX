package cc.moecraft.icq.sender;

import cc.moecraft.icq.sender.returndata.RawReturnData;
import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import cc.moecraft.icq.utils.MapBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.xiaoleilu.hutool.http.HttpUtil;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/19/24 创建!
 * Created by Hykilpikonna on 2018/19/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class IcqHttpApi
{
    public static final String SEND_PRIVATE_MSG = "send_private_msg";
    public static final String SEND_GROUP_MSG = "send_group_msg";
    public static final String SEND_DISCUSS_MSG = "send_discuss_msg";
    public static final String SEND_LIKE = "send_like";

    public static final String DELETE_MSG = "delete_msg";

    public static final String SET_GROUP_KICK = "set_group_kick";
    public static final String SET_GROUP_BAN = "set_group_ban";
    public static final String SET_GROUP_ANONYMOUS_BAN = "set_group_anonymous_ban";
    public static final String SET_GROUP_WHOLE_BAN = "set_group_whole_ban";
    public static final String SET_RESTART = "set_restart";
    public static final String SET_RESTART_PLUGIN = "set_restart_plugin";
    public static final String SET_GROUP_ADMIN = "set_group_admin";
    public static final String SET_GROUP_ANONYMOUS = "set_group_anonymous";
    public static final String SET_GROUP_CARD = "set_group_card";
    public static final String SET_GROUP_LEAVE = "set_group_leave";
    public static final String SET_GROUP_SPECIAL_TITLE = "set_group_special_title";
    public static final String SET_DISCUSS_LEAVE = "set_discuss_leave";
    public static final String SET_FRIEND_ADD_REQUEST = "set_friend_add_request";
    public static final String SET_GROUP_ADD_REQUEST = "set_group_add_request";

    public static final String GET_LOGIN_INFO = "get_login_info";
    public static final String GET_STRANGER_INFO = "get_stranger_info";
    public static final String GET_GROUP_LIST = "get_group_list";
    public static final String GET_GROUP_MEMBER_INFO = "get_group_member_info";
    public static final String GET_GROUP_MEMBER_LIST = "get_group_member_list";
    public static final String GET_VERSION_INFO = "get_version_info";

    @Deprecated
    public static final String SEND_MSG = "send_msg";  // 这个不需要, 因为最后也要指定类型

    private final String baseURL;

    public IcqHttpApi(String baseUrl, int port)
    {
        baseUrl = baseUrl.toLowerCase();
        if (!baseUrl.contains("http://")) baseUrl = "http://" + baseUrl;

        baseURL = baseUrl + ":" + port + "/";
    }

    /**
     * 发送请求
     * @param request 请求
     * @param parameters 参数
     * @return 响应
     */
    public JsonElement send(String request, Map<String, Object> parameters)
    {
        return new JsonParser().parse(HttpUtil.post(baseURL + request, parameters, 5000));
    }

    /**
     * 发送请求 封装
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
     * @param typeOfT 返回数据类型
     * @param request 请求
     * @param parameters 参数
     * @return 响应
     */
    public <T extends ReturnPojoBase> ReturnData<T> send(Type typeOfT, String request, Map<String, Object> parameters)
    {
        return new Gson().fromJson(send(request, parameters), RawReturnData.class).processData(typeOfT);
    }

    /**
     * 发送请求 封装
     * @param typeOfT 返回数据类型
     * @param request 请求
     * @param parameters 参数
     * @return 响应
     */
    public <T extends ReturnPojoBase> ReturnData<T> send(Class<T> typeOfT, String request, Object... parameters)
    {
        return send(typeOfT, request, MapBuilder.build(String.class, Object.class, parameters));
    }
    /**
     * 发送私聊消息
     * @param qq      QQ号
     * @param message 消息
     */
    public JsonElement sendPrivateMsg(long qq, String message)
    {
        return send(SEND_PRIVATE_MSG, "user_id", qq, "message", message);
    }

    /**
     * 发送私聊消息
     * @param qq      QQ号
     * @param message 消息
     * @param autoEscape 是否纯文本发送
     */
    public JsonElement sendPrivateMsg(long qq, String message, boolean autoEscape)
    {
        return send(SEND_PRIVATE_MSG, "user_id", qq, "message", message, "auto_escape", autoEscape);
    }

    /**
     * 发送群聊消息
     * @param groupId 群ID
     * @param message 消息
     */
    public JsonElement sendGroupMsg(long groupId, String message)
    {
        return send(SEND_GROUP_MSG, "group_id", groupId, "message", message);
    }

    /**
     * 发送群聊消息
     * @param groupId 群ID
     * @param message 消息
     * @param autoEscape 是否纯文本发送
     */
    public JsonElement sendGroupMsg(long groupId, String message, boolean autoEscape)
    {
        return send(SEND_GROUP_MSG, "group_id", groupId, "message", message, "auto_escape", autoEscape);
    }

    /**
     * 发送讨论组消息
     * @param groupId 讨论组ID
     * @param message 消息
     */
    public JsonElement sendDiscussMsg(long groupId, String message)
    {
        return send(SEND_DISCUSS_MSG, "discuss_id", groupId, "message", message);
    }

    /**
     * 发送讨论组消息
     * @param groupId 讨论组ID
     * @param message 消息
     * @param autoEscape 是否纯文本发送
     */
    public JsonElement sendDiscussMsg(long groupId, String message, boolean autoEscape)
    {
        return send(SEND_DISCUSS_MSG, "discuss_id", groupId, "message", message, "auto_escape", autoEscape);
    }

    /**
     * 撤回消息
     * @param messageId 消息ID
     */
    public JsonElement deleteMsg(long messageId)
    {
        return send(DELETE_MSG, "message_id", messageId);
    }

    /**
     * 发送好友赞
     * @param qq    QQ号
     * @param times 赞的次数，每个好友每天最多 10 次
     */
    public JsonElement sendLike(long qq, long times)
    {
        return send(SEND_LIKE, "user_id", qq, "times", times);
    }

    /**
     * 群组踢人
     * @param qq      QQ
     * @param groupId 群号
     */
    public JsonElement setGroupKick(long qq, long groupId)
    {
        return send(SET_GROUP_KICK, "user_id", qq, "group_id", groupId);
    }

    /**
     * 群组单人禁言
     * @param qq       QQ
     * @param groupId  群号
     * @param duration 禁言时长，单位秒，0 表示取消禁言
     */

    public JsonElement setGroupBan(long qq, long groupId, long duration)
    {
        return send(SET_GROUP_BAN, "user_id", qq, "group_id", groupId, "duration", duration);
    }

    /**
     * 群组匿名用户禁言
     * @param flag     要禁言的匿名用户的 flag（需从群消息上报的数据中获得）
     * @param groupId  群号
     * @param duration 禁言时长，单位秒，无法取消匿名用户禁言
     */
    public JsonElement setGroupAnonymousBan(String flag, long groupId, long duration)
    {
        return send(SET_GROUP_ANONYMOUS_BAN, "flag", flag, "group_id", groupId, "duration", duration);
    }

    /**
     * 群组全员禁言
     * @param groupId 群号
     * @param enable  是否禁言
     */
    public JsonElement setGroupWholeBan(long groupId, boolean enable)
    {
        return send(SET_GROUP_WHOLE_BAN, "group_id", groupId, "enable", enable);
    }

    /**
     * 群组设置管理员
     * @param groupId 群号
     * @param qq      要设置管理员的 QQ 号
     * @param enable  true 为设置，false 为取消
     */
    public JsonElement setGroupAdmin(long groupId, long qq, boolean enable)
    {
        return send(SET_GROUP_ADMIN, "group_id", groupId, "user_id", qq, "enable", enable);
    }

    /**
     * 群组设置匿名
     * @param groupId 群号
     * @param enable  是否允许匿名聊天
     */
    public JsonElement setGroupAnonymous(long groupId, boolean enable)
    {
        return send(SET_GROUP_ANONYMOUS, "group_id", groupId, "enable", enable);
    }

    /**
     * 设置群名片（群备注）
     * @param groupId 群号
     * @param qq      要设置的 QQ 号
     * @param card  群名片内容，不填或空字符串表示删除群名片
     */
    public JsonElement setGroupCard(long groupId, long qq, String card)
    {
        return send(SET_GROUP_CARD, "group_id", groupId, "user_id", qq, "card", card);
    }

    /**
     * 退出群组
     * @param groupId 群号
     * @param dismiss 是否解散，如果登录号是群主，则仅在此项为 true 时能够解散
     */
    public JsonElement setGroupLeave(long groupId, boolean dismiss)
    {
        return send(SET_GROUP_LEAVE, "group_id", groupId, "is_dismiss", dismiss);
    }

    /**
     * 设置群组专属头衔
     * @param groupId 群号
     * @param qq 要设置的QQ号
     * @param specialTitle 专属头衔，不填或空字符串表示删除专属头衔
     */
    public JsonElement setGroupSpecialTitle(long groupId, long qq, String specialTitle)
    {
        return send(SET_GROUP_SPECIAL_TITLE, "group_id", groupId, "user_id", qq, "special_title", specialTitle);
    }

    /**
     * 设置群组专属头衔
     * @param groupId 群号
     * @param qq 要设置的QQ号
     * @param specialTitle 专属头衔，不填或空字符串表示删除专属头衔
     * @param duration 专属头衔有效期，单位秒，-1 表示永久，不过此项似乎没有效果，可能是只有某些特殊的时间长度有效，有待测试
     */
    public JsonElement setGroupSpecialTitle(long groupId, long qq, String specialTitle, long duration)
    {
        return send(SET_GROUP_SPECIAL_TITLE, "group_id", groupId, "user_id", qq, "special_title", specialTitle, "duration", duration);
    }

    /**
     * 退出讨论组
     * @param discussId 讨论组 ID（正常情况下看不到，需要从讨论组消息上报的数据中获得）
     */
    public JsonElement setDiscussLeave(long discussId)
    {
        return send(SET_DISCUSS_LEAVE, "discuss_id", discussId);
    }

    /**
     * 处理加好友请求
     * @param flag 加好友请求的 flag（需从上报的数据中获得）
     * @param approve 是否同意请求
     */
    public JsonElement setFriendAndRequest(String flag, boolean approve)
    {
        return send(SET_FRIEND_ADD_REQUEST, "flag", flag, "approve", approve);
    }

    /**
     * 处理加好友请求
     * @param flag 加好友请求的 flag（需从上报的数据中获得）
     * @param approve 是否同意请求
     * @param remark 添加后的好友备注（仅在同意时有效）
     */
    public JsonElement setFriendAndRequest(String flag, boolean approve, String remark)
    {
        return send(SET_FRIEND_ADD_REQUEST, "flag", flag, "approve", approve, "remark", remark);
    }

    /**
     * 处理加群请求／邀请
     * @param flag 加好友请求的 flag（需从上报的数据中获得）
     * @param type add 或 invite，请求类型（需要和上报消息中的 sub_type 字段相符）
     * @param approve 是否同意请求／邀请
     * @param reason 拒绝理由（仅在拒绝时有效）
     */
    public JsonElement setGroupAndRequest(String flag, String type, boolean approve, String reason)
    {
        return send(SET_GROUP_ADD_REQUEST, "flag", flag, "type", type, "approve", approve, "reason", reason);
    }

    /**
     * 同意加群请求／邀请
     * @param flag 加好友请求的 flag（需从上报的数据中获得）
     * @param type add 或 invite，请求类型（需要和上报消息中的 sub_type 字段相符）
     */
    public JsonElement approveGroupRequest(String flag, String type)
    {
        return setGroupAndRequest(flag, type, true, "");
    }

    /**
     * 拒绝加群请求／邀请
     * @param flag 加好友请求的 flag（需从上报的数据中获得）
     * @param type add 或 invite，请求类型（需要和上报消息中的 sub_type 字段相符）
     * @param reason 拒绝理由
     */
    public JsonElement rejectGroupRequest(String flag, String type, String reason)
    {
        return setGroupAndRequest(flag, type, false, reason);
    }

    /**
     * 获取登录号信息
     */
    public JsonElement getLoginInfo()
    {
        return send(GET_LOGIN_INFO);
    }

    /**
     * 获取陌生人信息
     * @param qq QQ号
     * @param noCache 是否不使用缓存（使用缓存可能更新不及时，但响应更快）
     */
    public JsonElement getStrangerInfo(long qq, boolean noCache)
    {
        return send(GET_STRANGER_INFO, "user_id", qq, "no_cache", noCache);
    }

    /**
     * 获取陌生人信息, 默认使用缓存
     * @param qq QQ号
     */
    public JsonElement getStrangerInfo(long qq)
    {
        return getStrangerInfo(qq, true);
    }

    /**
     * 获取群列表
     */
    public JsonElement getGroupList()
    {
        return send(GET_GROUP_LIST);
    }

    /**
     * 获取群成员信息
     * @param groupId 群号
     * @param qq QQ 号（不可以是登录号）
     */
    public JsonElement getGroupMemberInfo(long groupId, long qq)
    {
        return send(GET_GROUP_MEMBER_INFO, "group_id", groupId, "user_id", qq);
    }

    /**
     * 获取群成员列表
     * @param groupId 群号
     */
    public JsonElement getGroupMemberList(long groupId)
    {
        return send(GET_GROUP_MEMBER_LIST, "group_id", groupId);
    }

    /**
     * 获取酷 Q 及 HTTP API 插件的版本信息
     */
    public JsonElement getVersionInfo()
    {
        return send(GET_VERSION_INFO);
    }

    /**
     * 重启酷 Q，并以当前登录号自动登录（需勾选快速登录）
     */
    public JsonElement setRestart()
    {
        return send(SET_RESTART);
    }

    /**
     * 重启 HTTP API 插件
     */
    public JsonElement setRestartPlugin()
    {
        return send(SET_RESTART_PLUGIN);
    }
}
