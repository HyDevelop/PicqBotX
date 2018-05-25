package cc.moecraft.icq.sender;

import cc.moecraft.icq.utils.MapBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.xiaoleilu.hutool.http.HttpUtil;
import lombok.Data;

import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/19/24 创建!
 * Created by Hykilpikonna on 2018/19/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@Data
public class IcqHttpApi
{
    public static final String SEND_PRIVATE_MSG = "send_private_msg";
    public static final String SEND_GROUP_MSG = "send_group_msg";
    public static final String SEND_DISCUSS_MSG = "send_discuss_msg";
    public static final String SEND_MSG = "send_msg";
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
        return new Gson().fromJson(HttpUtil.post(baseURL + request, parameters, 5000), JsonElement.class);
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
     * 获取群列表
     */
    public JsonElement getGroupList()
    {
        return send(GET_GROUP_LIST);
    }

    /**
     * 获取群成员信息
     * @param groupId 群号
     * @param qq      QQ 号（不可以是登录号）
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
