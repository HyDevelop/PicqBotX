package cc.moecraft.icq.sender;

import cc.moecraft.icq.utils.MapBuilder;
import com.google.gson.Gson;
import com.xiaoleilu.hutool.http.HttpUtil;
import lombok.Data;

import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/19/24 创建!
 * Created by Hykilpikonna on 2018/19/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
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
    public IcqHttpResponse send(String request, Map<String, Object> parameters)
    {
        return new Gson().fromJson(HttpUtil.post(baseURL + request, parameters, 5000), IcqHttpResponse.class);
    }

    /**
     * 发送请求 封装
     * @param request 请求
     * @param parameters 参数
     * @return 响应
     */
    public IcqHttpResponse send(String request, Object... parameters)
    {
        return send(request, MapBuilder.build(String.class, Object.class, parameters));
    }
}
