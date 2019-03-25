package cc.moecraft.icq.sender;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.accounts.BotAccount;
import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.get.*;

/**
 * 此类由 Hykilpikonna 在 2018/19/24 创建!
 * Created by Hykilpikonna on 2018/19/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class IcqHttpApi extends HttpApiBase
{
    /**
     * 构造一个 IcqHttpApi 发送对象
     *
     * @param bot 机器人
     * @param account 账号
     * @param baseUrl URL
     * @param port 端口
     */
    public IcqHttpApi(PicqBotX bot, BotAccount account, String baseUrl, int port)
    {
        super(bot, account, baseUrl, port);
    }

    @Override
    public String makeUrl(String api)
    {
        return getBaseUrl() + api;
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
