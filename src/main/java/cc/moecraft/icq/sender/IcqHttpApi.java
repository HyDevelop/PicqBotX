package cc.moecraft.icq.sender;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.accounts.BotAccount;
import cc.moecraft.icq.event.events.local.EventLocalSendDiscussMessage;
import cc.moecraft.icq.event.events.local.EventLocalSendGroupMessage;
import cc.moecraft.icq.event.events.local.EventLocalSendPrivateMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage.Anonymous;
import cc.moecraft.icq.sender.returndata.RawReturnData;
import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import cc.moecraft.icq.sender.returndata.returnpojo.get.*;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import cc.moecraft.utils.MapBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Getter;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;

import static cc.moecraft.icq.sender.HttpApiNode.*;
import static cc.moecraft.icq.utils.NetUtils.url;

/**
 * 酷Q HTTP插件的API客户端w
 *
 * @author Hykilpikonna
 */
@Getter
@SuppressWarnings("UnusedReturnValue")
public class IcqHttpApi
{
    private final String baseUrl;

    protected final PicqBotX bot;

    protected final BotAccount account;

    /**
     * 构造一个 HttpApi 发送对象
     *
     * @param bot 机器人
     * @param account 账号
     * @param host 主机地址
     * @param port 端口
     */
    public IcqHttpApi(PicqBotX bot, BotAccount account, String host, int port)
    {
        this.bot = bot;
        this.account = account;
        this.baseUrl = url(host, port);
    }

    /**
     * 生成URL
     *
     * @param api API节点
     * @return 完整URL
     */
    public String makeUrl(HttpApiNode api)
    {
        String url = getBaseUrl() + api.getSubUrl();

        // 不能异步的话直接返回
        if (!api.isCanAsync())
        {
            return url;
        }

        // 有没有设置限制速度
        if (bot.getConfig().isApiRateLimited())
        {
            return url + "_rate_limited";
        }

        // 有没有设置异步
        if (bot.getConfig().isApiAsync())
        {
            return url + "_async";
        }

        // 全都没有的话直接返回
        return url;
    }

    /**
     * 发送请求
     *
     * @param api API节点
     * @param params 参数
     * @return 响应
     */
    public JsonElement send(HttpApiNode api, Map<String, Object> params)
    {
        // 创建请求
        HttpRequest request = HttpRequest.post(makeUrl(api)).body(new JSONObject(params)).timeout(5000);

        // 判断有没有 Access Token, 并加到头上w
        if (!bot.getConfig().getAccessToken().isEmpty())
        {
            request.header("Authorization", "Bearer " + bot.getConfig().getAccessToken());
        }

        // 发送并返回
        return new JsonParser().parse(request.execute().body());
    }

    /**
     * 发送请求 封装
     *
     * @param api API节点
     * @param params 参数
     * @return 响应
     */
    public JsonElement send(HttpApiNode api, Object... params)
    {
        return send(api, MapBuilder.build(String.class, Object.class, params));
    }

    /**
     * 发送请求 封装
     *
     * @param api API节点
     * @param params 参数
     * @return 响应
     */
    public RawReturnData sendReturnRaw(HttpApiNode api, Map<String, Object> params)
    {
        return new Gson().fromJson(send(api, params), RawReturnData.class);
    }

    /**
     * 发送请求 封装
     *
     * @param api API节点
     * @param params 参数
     * @return 响应
     */
    public RawReturnData sendReturnRaw(HttpApiNode api, Object... params)
    {
        return sendReturnRaw(api, MapBuilder.build(String.class, Object.class, params));
    }

    /**
     * 发送请求 封装
     *
     * @param type 返回数据类型
     * @param api API节点
     * @param params 参数
     * @param <T> 返回值的类型
     * @return 响应
     */
    public <T extends ReturnPojoBase> ReturnData<T> send(Type type, HttpApiNode api, Map<String, Object> params)
    {
        return sendReturnRaw(api, params).processData(type);
    }

    /**
     * 发送请求 封装
     *
     * @param type 返回数据类型
     * @param api API节点
     * @param params 参数
     * @param <T> 返回值的类型
     * @return 响应
     */
    public <T extends ReturnPojoBase> ReturnData<T> send(Class<T> type, HttpApiNode api, Object... params)
    {
        return send(type, api, MapBuilder.build(String.class, Object.class, params));
    }

    /**
     * 发送请求 封装
     *
     * @param type 返回数据类型
     * @param api API节点
     * @param params 参数
     * @param <T> 返回值的类型
     * @return 响应
     */
    public <T extends ReturnPojoBase> ReturnListData<T> sendReturnList(Type type, HttpApiNode api, Map<String, Object> params)
    {
        return sendReturnRaw(api, params).processDataAsList(type);
    }

    /**
     * 发送请求 封装
     *
     * @param type 返回数据类型
     * @param api API节点
     * @param params 参数
     * @param <T> 返回值的类型
     * @return 响应
     */
    public <T extends ReturnPojoBase> ReturnListData<T> sendReturnList(Class<T> type, HttpApiNode api, Object... params)
    {
        return sendReturnList(type, api, MapBuilder.build(String.class, Object.class, params));
    }

    /**
     * 发送私聊消息
     *
     * @param qq QQ号
     * @param message 消息
     * @return 发送消息结果
     */
    public ReturnData<RMessageReturnData> sendPrivateMsg(long qq, String message)
    {
        return sendPrivateMsg(qq, message, false);
    }

    /**
     * 发送私聊消息
     *
     * @param qq QQ号
     * @param message 消息
     * @param autoEscape 是否纯文本发送
     * @return 发送消息结果
     */
    public ReturnData<RMessageReturnData> sendPrivateMsg(long qq, String message, boolean autoEscape)
    {
        if (message == null || message.isEmpty())
        {
            return null;
        }

        EventLocalSendPrivateMessage event = new EventLocalSendPrivateMessage(qq, message, autoEscape);
        event.selfId = account.getId();
        bot.getEventManager().call(event);
        if (event.isCancelled())
        {
            return null;
        }
        return send(RMessageReturnData.class, SEND_PRIVATE_MSG, "user_id", event.getId(), "message", event.getMessage(), "auto_escape", event.isAutoEscape());
    }

    /**
     * 发送群聊消息
     *
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
     *
     * @param groupId 群ID
     * @param message 消息
     * @param autoEscape 是否纯文本发送
     * @return 发送消息结果
     */
    public ReturnData<RMessageReturnData> sendGroupMsg(long groupId, String message, boolean autoEscape)
    {
        if (message == null || message.isEmpty())
        {
            return null;
        }

        EventLocalSendGroupMessage event = new EventLocalSendGroupMessage(groupId, message, autoEscape);
        event.selfId = account.getId();
        bot.getEventManager().call(event);
        if (event.isCancelled())
        {
            return null;
        }
        return send(RMessageReturnData.class, SEND_GROUP_MSG, "group_id", event.getId(), "message", event.getMessage(), "auto_escape", event.isAutoEscape());
    }

    /**
     * 发送讨论组消息
     *
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
     *
     * @param groupId 讨论组ID
     * @param message 消息
     * @param autoEscape 是否纯文本发送
     * @return 发送消息结果
     */
    public ReturnData<RMessageReturnData> sendDiscussMsg(long groupId, String message, boolean autoEscape)
    {
        if (message == null || message.isEmpty())
        {
            return null;
        }

        EventLocalSendDiscussMessage event = new EventLocalSendDiscussMessage(groupId, message, autoEscape);
        event.selfId = account.getId();
        bot.getEventManager().call(event);
        if (event.isCancelled())
        {
            return null;
        }
        return send(RMessageReturnData.class, SEND_DISCUSS_MSG, "discuss_id", event.getId(), "message", event.getMessage(), "auto_escape", event.isAutoEscape());
    }

    /**
     * 撤回消息
     *
     * @param messageId 消息ID
     * @return 执行结果
     */
    public RawReturnData deleteMsg(long messageId)
    {
        return sendReturnRaw(DELETE_MSG, "message_id", messageId);
    }

    /**
     * 发送好友赞
     *
     * @param qq QQ号
     * @param times 赞的次数，每个好友每天最多 10 次
     * @return 执行结果
     */
    public RawReturnData sendLike(long qq, long times)
    {
        return sendReturnRaw(SEND_LIKE, "user_id", qq, "times", times);
    }

    /**
     * 发布群公告
     *
     * @param groupId 群号
     * @param title 标题
     * @param content 内容
     * @return 执行结果
     */
    public RawReturnData sendGroupNotice(long groupId, String title, String content)
    {
        return sendReturnRaw(SEND_GROUP_NOTICE, "group_id", groupId, "title", title, "content", content);
    }

    /**
     * 群组踢人
     *
     * @param groupId 群号
     * @param qq QQ
     * @return 执行结果
     */
    public RawReturnData setGroupKick(long groupId, long qq)
    {
        return sendReturnRaw(SET_GROUP_KICK, "user_id", qq, "group_id", groupId);
    }

    /**
     * 群组踢人
     *
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
     *
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
     *
     * @param flag 要禁言的匿名用户的 flag (需从群消息上报的数据中获得)
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
     *
     * @param anonymous 要禁言的匿名用户的 Anonymous对象 (需从群消息上报的数据中获得)
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
     *
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
     *
     * @param groupId 群号
     * @param qq 要设置管理员的 QQ 号
     * @param enable true 为设置，false 为取消
     * @return 执行结果
     */
    public RawReturnData setGroupAdmin(long groupId, long qq, boolean enable)
    {
        return sendReturnRaw(SET_GROUP_ADMIN, "group_id", groupId, "user_id", qq, "enable", enable);
    }

    /**
     * 群组设置匿名
     *
     * @param groupId 群号
     * @param enable 是否允许匿名聊天
     * @return 执行结果
     */
    public RawReturnData setGroupAnonymous(long groupId, boolean enable)
    {
        return sendReturnRaw(SET_GROUP_ANONYMOUS, "group_id", groupId, "enable", enable);
    }

    /**
     * 设置群名片 (群备注)
     *
     * @param groupId 群号
     * @param qq 要设置的 QQ 号
     * @param card 群名片内容，不填或空字符串表示删除群名片
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
     *
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
     *
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
     *
     * @param discussId 讨论组 ID (正常情况下看不到，需要从讨论组消息上报的数据中获得)
     * @return 执行结果
     */
    public RawReturnData setDiscussLeave(long discussId)
    {
        return sendReturnRaw(SET_DISCUSS_LEAVE, "discuss_id", discussId);
    }

    /**
     * 处理加好友请求
     *
     * @param flag 加好友请求的 flag (需从上报的数据中获得)
     * @param approve 是否同意请求
     * @return 执行结果
     */
    public RawReturnData setFriendAndRequest(String flag, boolean approve)
    {
        return sendReturnRaw(SET_FRIEND_ADD_REQUEST, "flag", flag, "approve", approve);
    }

    /**
     * 处理加好友请求
     *
     * @param flag 加好友请求的 flag (需从上报的数据中获得)
     * @param approve 是否同意请求
     * @param remark 添加后的好友备注 (仅在同意时有效)
     * @return 执行结果
     */
    public RawReturnData setFriendAndRequest(String flag, boolean approve, String remark)
    {
        return sendReturnRaw(SET_FRIEND_ADD_REQUEST, "flag", flag, "approve", approve, "remark", remark);
    }

    /**
     * 处理加群请求/邀请
     *
     * @param flag 加好友请求的 flag (需从上报的数据中获得)
     * @param type add 或 invite，请求类型 (需要和上报消息中的 sub_type 字段相符)
     * @param approve 是否同意请求/邀请
     * @param reason 拒绝理由 (仅在拒绝时有效)
     * @return 执行结果
     */
    public RawReturnData setGroupAndRequest(String flag, String type, boolean approve, String reason)
    {
        return sendReturnRaw(SET_GROUP_ADD_REQUEST, "flag", flag, "type", type, "approve", approve, "reason", reason);
    }

    /**
     * 同意加群请求／邀请
     *
     * @param flag 加好友请求的 flag (需从上报的数据中获得)
     * @param type add 或 invite，请求类型 (需要和上报消息中的 sub_type 字段相符)
     * @return 执行结果
     */
    public RawReturnData approveGroupRequest(String flag, String type)
    {
        return setGroupAndRequest(flag, type, true, "");
    }

    /**
     * 拒绝加群请求／邀请
     *
     * @param flag 加好友请求的 flag (需从上报的数据中获得)
     * @param type add 或 invite，请求类型 (需要和上报消息中的 sub_type 字段相符)
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
    public RawReturnData setRestartUnsafe()
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
    public RawReturnData setRestartUnsafe(boolean cleanLog, boolean cleanCache, boolean cleanEvent)
    {
        return sendReturnRaw(SET_RESTART, "clean_log", cleanLog,
                "clean_cache", cleanCache, "clean_event", cleanEvent);
    }

    /**
     * 清空所有数据文件夹
     */
    public void cleanDataDir()
    {
        cleanDataDir("image");
        cleanDataDir("record");
        cleanDataDir("show");
        cleanDataDir("bface");
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
     *
     * @return 执行结果
     */
    public RawReturnData cleanPluginLog()
    {
        return sendReturnRaw(CLEAN_PLUGIN_LOG);
    }

    /**
     * 获取登录号信息
     *
     * @return 登录号信息
     */
    public ReturnData<RLoginInfo> getLoginInfo()
    {
        return send(RLoginInfo.class, GET_LOGIN_INFO);
    }

    /**
     * 获取陌生人信息
     *
     * @param qq QQ号
     * @param noCache 是否不使用缓存 (使用缓存可能更新不及时，但响应更快)
     * @return 陌生人信息
     */
    public ReturnData<RStrangerInfo> getStrangerInfo(long qq, boolean noCache)
    {
        return send(RStrangerInfo.class, GET_STRANGER_INFO, "user_id", qq, "no_cache", noCache);
    }

    /**
     * 获取陌生人信息, 默认不使用缓存
     *
     * @param qq QQ号
     * @return 陌生人信息
     */
    public ReturnData<RStrangerInfo> getStrangerInfo(long qq)
    {
        return getStrangerInfo(qq, true);
    }

    /**
     * 获取用户VIP信息
     *
     * @param qq QQ号
     * @return VIP信息
     */
    public ReturnData<RVipInfo> getVIPInfo(long qq)
    {
        return send(RVipInfo.class, GET_VIP_INFO, "user_id", qq);
    }

    /**
     * 获取群列表
     *
     * @return 群列表
     */
    public ReturnListData<RGroup> getGroupList()
    {
        return sendReturnList(RGroup.class, GET_GROUP_LIST);
    }

    /**
     * 获取群成员信息
     *
     * @param groupId 群号
     * @param qq QQ 号 (不可以是登录号)
     * @param noCache 是否不使用缓存 (使用缓存可能更新不及时, 但响应更快)
     * @return 群成员信息
     */
    public ReturnData<RGroupMemberInfo> getGroupMemberInfo(long groupId, long qq, boolean noCache)
    {
        return send(RGroupMemberInfo.class, GET_GROUP_MEMBER_INFO, "group_id", groupId,
                "user_id", qq, "no_cache", noCache);
    }

    /**
     * 获取群成员信息, 默认不使用缓存
     *
     * @param groupId 群号
     * @param qq QQ 号 (不可以是登录号)
     * @return 群成员信息
     */
    public ReturnData<RGroupMemberInfo> getGroupMemberInfo(long groupId, long qq)
    {
        return getGroupMemberInfo(groupId, qq, true);
    }

    /**
     * 获取群信息
     *
     * @param groupId 群号
     * @return 群信息
     */
    public ReturnData<RGroupDetail> getGroupInfo(long groupId)
    {
        return send(RGroupDetail.class, GET_GROUP_INFO, "group_id", groupId);
    }

    /**
     * 获取群成员列表
     *
     * @param groupId 群号
     * @return 群成员列表
     */
    public ReturnListData<RGroupMemberInfo> getGroupMemberList(long groupId)
    {
        return sendReturnList(RGroupMemberInfo.class, GET_GROUP_MEMBER_LIST, "group_id", groupId);
    }

    /**
     * 获取好友列表
     *
     * @return 好友列表
     */
    public ReturnListData<RFriendList> getFriendList()
    {
        return sendReturnList(RFriendList.class, GET_FRIEND_LIST);
    }

    /**
     * 获取语音文件
     *
     * @param file 收到的语音文件名 (CQ码的file参数), 如 0B38145AA44505000B38145AA4450500.silk
     * @param format 要转换到的格式, 目前支持 mp3, amr, wma, m4a, spx, ogg, wav, flac
     * @param fullPath 是否返回完整路径 (Windows环境下建议使用, Docker中不建议)
     * @return 语音文件
     */
    public ReturnData<RFile> getRecord(String file, String format, boolean fullPath)
    {
        return send(RFile.class, GET_RECORD, "file", file, "out_format", format, "full_path", fullPath);
    }

    /**
     * 获取图片文件完整路径
     *
     * @param file 收到的图片文件名 (CQ码的file参数), 如 6B4DE3DFD1BD271E3297859D41C530F5.jpg
     * @return 下载后的图片文件路径
     */
    public ReturnData<RFile> getImage(String file)
    {
        return send(RFile.class, GET_IMAGE, "file", file);
    }

    /**
     * 获取图片文件 (封装)
     *
     * @param file 收到的图片文件名 (CQ码的file参数), 如 6B4DE3DFD1BD271E3297859D41C530F5.jpg
     * @return 图片文件对象
     */
    public File getImageFile(String file)
    {
        return new File(getImage(file).getData().getFile());
    }

    /**
     * 获取一个群的所有公告
     *
     * @param groupId 群ID
     * @return 所有公告
     */
    public ReturnListData<RGroupNotice> getGroupNotices(long groupId)
    {
        return sendReturnList(RGroupNotice.class, GET_GROUP_NOTICE, "group_id", groupId);
    }

    /**
     * 检查是否可以发送图片
     *
     * @return 是否可以发送图片
     */
    public boolean canSendImage()
    {
        return send(RBoolean.class, CAN_SEND_IMAGE).getData().getYes();
    }

    /**
     * 检查是否可以发送语音
     *
     * @return 是否可以发送语音
     */
    public boolean canSendRecord()
    {
        return send(RBoolean.class, CAN_SEND_RECORD).getData().getYes();
    }

    /**
     * 获取酷 Q 及 HTTP API 插件的版本信息
     *
     * @return 酷 Q 及 HTTP API 插件的版本信息
     */
    public ReturnData<RVersionInfo> getVersionInfo()
    {
        return send(RVersionInfo.class, GET_VERSION_INFO);
    }

    /**
     * 抢走酷Q的曲奇饼w
     *
     * @return Cookies
     */
    public ReturnData<RCookies> getCookies()
    {
        return send(RCookies.class, GET_COOKIES);
    }

    /**
     * 获取CSRF Token
     *
     * @return CSRF Token
     */
    public ReturnData<RCsrfToken> getCsrfToken()
    {
        return send(RCsrfToken.class, GET_CSRF_TOKEN);
    }

    /**
     * 抢走酷Q的曲奇饼并获取CSRF Token
     *
     * @return QQ相关接口凭证
     */
    public ReturnData<RCredentials> getCredentials()
    {
        return send(RCredentials.class, GET_CREDENTIALS);
    }

    /**
     * 获取插件运行状态
     *
     * @return 插件运行状态
     */
    public ReturnData<RStatus> getStatus()
    {
        return send(RStatus.class, GET_STATUS);
    }
}
