package cc.moecraft.icq.sender;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum {@code HttpApiNode} contains the api nodes and their info.
 * <p>
 * Class created by the HyDEV Team on 2019-03-25!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-25 07:25
 */
@Getter
@AllArgsConstructor
public enum HttpApiNode
{
    // 发送区
    SEND_PRIVATE_MSG        ("send_private_msg",        true),
    SEND_GROUP_MSG          ("send_group_msg",          true),
    SEND_DISCUSS_MSG        ("send_discuss_msg",        true),
    SEND_LIKE               ("send_like",               true),
    DELETE_MSG              ("delete_msg",              true),
    SEND_GROUP_NOTICE       ("_send_group_notice",      true),

    // 应用设置区
    SET_GROUP_KICK          ("set_group_kick",          true),
    SET_GROUP_BAN           ("set_group_ban",           true),
    SET_GROUP_ANONYMOUS_BAN ("set_group_anonymous_ban", true),
    SET_GROUP_WHOLE_BAN     ("set_group_whole_ban",     true),
    SET_GROUP_ADMIN         ("set_group_admin",         true),
    SET_GROUP_ANONYMOUS     ("set_group_anonymous",     true),
    SET_GROUP_CARD          ("set_group_card",          true),
    SET_GROUP_LEAVE         ("set_group_leave",         true),
    SET_GROUP_SPECIAL_TITLE ("set_group_special_title", true),
    SET_DISCUSS_LEAVE       ("set_discuss_leave",       true),
    SET_FRIEND_ADD_REQUEST  ("set_friend_add_request",  true),
    SET_GROUP_ADD_REQUEST   ("set_group_add_request",   true),

    // ICQ(酷Q, 以及HTTP插件)设置区
    SET_RESTART             ("_set_restart",            true),
    SET_RESTART_PLUGIN      ("set_restart_plugin",      true),
    CLEAN_DATA_DIR          ("clean_data_dir",          true),
    CLEAN_PLUGIN_LOG        ("clean_plugin_log",        true),

    // 应用内获取区
    GET_LOGIN_INFO          ("get_login_info",          false),
    GET_STRANGER_INFO       ("get_stranger_info",       false),
    GET_GROUP_LIST          ("get_group_list",          false),
    GET_GROUP_MEMBER_INFO   ("get_group_member_info",   false),
    GET_GROUP_MEMBER_LIST   ("get_group_member_list",   false),
    GET_FRIEND_LIST         ("_get_friend_list",        false),
    GET_GROUP_INFO          ("_get_group_info",         false),
    GET_VIP_INFO            ("_get_vip_info",           false),
    GET_RECORD              ("get_record",              false),
    GET_IMAGE               ("get_image",               false),
    GET_GROUP_NOTICE        ("_get_group_notice",       false),

    // 条件判断区
    CAN_SEND_IMAGE          ("can_send_image",          false),
    CAN_SEND_RECORD         ("can_send_record",         false),

    // ICQ(酷Q, 以及HTTP插件)获取区
    GET_VERSION_INFO        ("get_version_info",        false),
    GET_STATUS              ("get_status",              false),
    GET_COOKIES             ("get_cookies",             false),
    GET_CSRF_TOKEN          ("get_csrf_token",          false),
    GET_CREDENTIALS         ("get_credentials",         false);

    /**
     * 子URL
     */
    private String subUrl;

    /**
     * 能不能异步调用
     */
    private boolean canAsync;
}
