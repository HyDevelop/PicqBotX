package cc.moecraft.icq.event;

import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.event.events.meta.EventMetaHeartbeat;
import cc.moecraft.icq.event.events.meta.EventMetaLifecycle;
import cc.moecraft.icq.event.events.notice.EventNoticeFriendAdd;
import cc.moecraft.icq.event.events.notice.EventNoticeGroupUpload;
import cc.moecraft.icq.event.events.notice.groupadmin.EventNoticeGroupAdminRemove;
import cc.moecraft.icq.event.events.notice.groupadmin.EventNoticeGroupAdminSet;
import cc.moecraft.icq.event.events.notice.groupmember.decrease.EventNoticeGroupMemberKick;
import cc.moecraft.icq.event.events.notice.groupmember.decrease.EventNoticeGroupMemberKickBot;
import cc.moecraft.icq.event.events.notice.groupmember.decrease.EventNoticeGroupMemberLeave;
import cc.moecraft.icq.event.events.notice.groupmember.increase.EventNoticeGroupMemberApprove;
import cc.moecraft.icq.event.events.notice.groupmember.increase.EventNoticeGroupMemberInvite;
import cc.moecraft.icq.event.events.request.EventFriendRequest;
import cc.moecraft.icq.event.events.request.EventGroupAddRequest;
import cc.moecraft.icq.event.events.request.EventGroupInviteRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static cc.moecraft.icq.PicqConstants.*;

/**
 * The class {@code EventParser} has methods to decide which event
 * class or methods should be invoked when calling a event.
 * <p>
 * Class created by the HyDEV Team on 2019-03-21!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-21 19:13
 */
@RequiredArgsConstructor
public class EventParser
{
    private final EventManager manager;

    private final Map<Class<? extends Event>, Map<String, ArrayList<ContentComparable>>> cache = new HashMap<>();

    /**
     * 判断一个事件是不是新的
     * 是新的代表这个事件在其他账号上有没有判定为是新的过.
     *
     * @param event 事件
     * @param identifier 标记 (比如群消息的标记就是群号)
     * @param <T> 实现了内容比较方法的事件类
     * @return 是不是新的
     */
    @SuppressWarnings("unchecked")
    private <T extends Event & ContentComparable> boolean isNew(T event, String identifier)
    {
        if (!manager.getBot().getConfig().isMultiAccountOptimizations(manager.getBot()))
        {
            return true;
        }
        Class<? extends Event> eventClass = event.getClass();

        if (!cache.containsKey(eventClass))
        {
            cache.put(eventClass, new HashMap<>());
        }
        Map<String, ArrayList<ContentComparable>> cachedEventMap = cache.get(eventClass);

        if (!cachedEventMap.containsKey(identifier))
        {
            cachedEventMap.put(identifier, new ArrayList<>());
        }
        ArrayList<ContentComparable> cachedEvents = cachedEventMap.get(identifier);

        for (ContentComparable comparable : cachedEvents)
        {
            if (comparable.contentEquals(event))
            {
                return false;
            }
        }

        cachedEvents.add(event);

        if (cachedEvents.size() > 10)
        {
            cachedEvents.remove(0);
        }
        return true;
    }

    /**
     * 封装manager的call()
     *
     * @param event 事件对象
     */
    private void call(Event event)
    {
        manager.call(event);
    }

    /**
     * 执行事件
     *
     * @param inputJsonString ICQ发来的Json字符串
     */
    public void call(String inputJsonString)
    {
        if (manager.getBot().getConfig().isEventPaused())
        {
            return; // 判断暂停
        }

        JsonObject json = new JsonParser().parse(inputJsonString).getAsJsonObject();
        String postType = json.get(EVENT_KEY_POST_TYPE).getAsString();

        switch (postType)
        {
            case EVENT_KEY_POST_TYPE_MESSAGE: // 消息事件
            {
                callMessage(json);
                break;
            }
            case EVENT_KEY_POST_TYPE_NOTICE: // 通知事件
            {
                callNotice(json);
                break;
            }
            case EVENT_KEY_POST_TYPE_REQUEST: // 请求事件
            {
                callRequest(json);
                break;
            }
            case EVENT_KEY_POST_TYPE_META: // Meta事件
            {
                callMeta(json);
                break;
            }
            default: // 未识别
            {
                reportUnrecognized(EVENT_KEY_POST_TYPE, postType, json);
                break;
            }
        }
    }

    /**
     * 执行消息事件
     *
     * @param json JSON输入
     */
    private void callMessage(JsonObject json)
    {
        // 获取消息类型
        String messageType = json.get(EVENT_KEY_MESSAGE_TYPE).getAsString();
        switch (messageType)
        {
            case EVENT_KEY_MESSAGE_TYPE_PRIVATE: // 私聊消息
            {
                call(gsonRead.fromJson(json, EventPrivateMessage.class));
                break;
            }
            case EVENT_KEY_MESSAGE_TYPE_GROUP: // 群消息
            {
                EventGroupMessage event = gsonRead.fromJson(json, EventGroupMessage.class);

                // 判断事件是不是新的
                if (isNew(event, event.getGroupId().toString()))
                {
                    call(event);
                }
                break;
            }
            case EVENT_KEY_MESSAGE_TYPE_DISCUSS: // 讨论组消息
            {
                EventDiscussMessage event = gsonRead.fromJson(json, EventDiscussMessage.class);
                if (isNew(event, event.getDiscussId().toString()))
                {
                    call(event);
                }
                break;
            }
            default: // 未识别
            {
                reportUnrecognized(EVENT_KEY_MESSAGE_TYPE, messageType, json);
                break;
            }
        }
    }

    /**
     * 执行请求事件
     *
     * @param json JSON输入
     */
    private void callRequest(JsonObject json)
    {
        String requestType = json.get(EVENT_KEY_REQUEST_TYPE).getAsString();
        switch (requestType)
        {
            case EVENT_KEY_REQUEST_TYPE_FRIEND: // 好友请求
            {
                call(gsonRead.fromJson(json, EventFriendRequest.class));
                break;
            }
            case EVENT_KEY_REQUEST_TYPE_GROUP: // 群请求
            {
                String subtype = json.get(EVENT_KEY_SUBTYPE).getAsString();
                switch (subtype)
                {
                    case EVENT_KEY_REQUEST_TYPE_GROUP_ADD: // 加群
                    {
                        EventGroupAddRequest event = gsonRead.fromJson(json, EventGroupAddRequest.class);
                        if (isNew(event, event.getGroupId().toString()))
                        {
                            call(event);
                        }
                        break;
                    }
                    case EVENT_KEY_REQUEST_TYPE_GROUP_INVITE: // 邀请入群
                    {
                        call(gsonRead.fromJson(json, EventGroupInviteRequest.class));
                        break;
                    }
                    default: // 未识别
                    {
                        reportUnrecognized(EVENT_KEY_SUBTYPE, subtype, json);
                        break;
                    }
                }
                break;
            }
            default: // 未识别
            {
                reportUnrecognized(EVENT_KEY_REQUEST_TYPE, requestType, json);
                break;
            }
        }
    }

    /**
     * 执行Notice事件
     *
     * @param json JSON输入
     */
    private void callNotice(JsonObject json)
    {
        String noticeType = json.get(EVENT_KEY_NOTICE_TYPE).getAsString();
        switch (noticeType)
        {
            // 传群文件
            case EVENT_KEY_NOTICE_TYPE_GROUP_UPLOAD:
            {
                EventNoticeGroupUpload event = gsonRead.fromJson(json, EventNoticeGroupUpload.class);
                if (isNew(event, event.getGroupId().toString()))
                {
                    call(event);
                }
                break;
            }
            // 加好友
            case EVENT_KEY_NOTICE_TYPE_FRIEND_ADD:
            {
                call(gsonRead.fromJson(json, EventNoticeFriendAdd.class));
                break;
            }
            // 群管理
            case EVENT_KEY_NOTICE_TYPE_GROUP_ADMIN:
            {
                String subtype = json.get(EVENT_KEY_SUBTYPE).getAsString();
                switch (subtype)
                {
                    // 设置管理
                    case EVENT_KEY_NOTICE_TYPE_GROUP_ADMIN_SET:
                    {
                        EventNoticeGroupAdminSet event = gsonRead.fromJson(json, EventNoticeGroupAdminSet.class);
                        if (isNew(event, event.getGroupId().toString()))
                        {
                            call(event);
                        }
                        break;
                    }
                    // 取消管理
                    case EVENT_KEY_NOTICE_TYPE_GROUP_ADMIN_UNSET:
                    {
                        EventNoticeGroupAdminRemove event = gsonRead.fromJson(json, EventNoticeGroupAdminRemove.class);
                        if (isNew(event, event.getGroupId().toString()))
                        {
                            call(event);
                        }
                        break;
                    }
                    default: // 未识别
                    {
                        reportUnrecognized(EVENT_KEY_SUBTYPE, subtype, json);
                        break;
                    }
                }
                break;
            }
            // 群成员减少
            case EVENT_KEY_NOTICE_TYPE_GROUP_DECREASE:
            {
                String subtype = json.get(EVENT_KEY_SUBTYPE).getAsString();
                switch (subtype)
                {
                    // 退群
                    case EVENT_KEY_NOTICE_TYPE_GROUP_DECREASE_LEAVE:
                    {
                        EventNoticeGroupMemberLeave event = gsonRead.fromJson(json, EventNoticeGroupMemberLeave.class);
                        if (isNew(event, event.getGroupId().toString()))
                        {
                            call(event);
                        }
                        break;
                    }
                    // 踢出
                    case EVENT_KEY_NOTICE_TYPE_GROUP_DECREASE_KICK:
                    {
                        EventNoticeGroupMemberKick event = gsonRead.fromJson(json, EventNoticeGroupMemberKick.class);
                        if (isNew(event, event.getGroupId().toString()))
                        {
                            call(event);
                        }
                        break;
                    }
                    // 自己被踢出
                    case EVENT_KEY_NOTICE_TYPE_GROUP_DECREASE_KICK_ME:
                    {
                        call(gsonRead.fromJson(json, EventNoticeGroupMemberKickBot.class));
                        break;
                    }
                    default: // 未识别
                    {
                        reportUnrecognized(EVENT_KEY_SUBTYPE, subtype, json);
                        break;
                    }
                }
                break;
            }
            // 群成员增加
            case EVENT_KEY_NOTICE_TYPE_GROUP_INCREASE:
            {
                String subtype = json.get(EVENT_KEY_SUBTYPE).getAsString();
                switch (subtype)
                {
                    // 同意
                    case EVENT_KEY_NOTICE_TYPE_GROUP_INCREASE_APPROVE:
                    {
                        EventNoticeGroupMemberApprove event = gsonRead.fromJson(json, EventNoticeGroupMemberApprove.class);
                        if (isNew(event, event.getGroupId().toString()))
                        {
                            call(event);
                        }
                        break;
                    }
                    // 邀请
                    case EVENT_KEY_NOTICE_TYPE_GROUP_INCREASE_INVITE:
                    {
                        EventNoticeGroupMemberInvite event = gsonRead.fromJson(json, EventNoticeGroupMemberInvite.class);
                        if (isNew(event, event.getGroupId().toString()))
                        {
                            call(event);
                        }
                        break;
                    }
                    default: // 未识别
                    {
                        reportUnrecognized(EVENT_KEY_SUBTYPE, subtype, json);
                        break;
                    }
                }
                break;
            }
            default: // 未识别
            {
                reportUnrecognized(EVENT_KEY_NOTICE_TYPE, noticeType, json);
                break;
            }
        }
    }

    /**
     * 执行Meta事件
     *
     * @param json JSON输入
     */
    private void callMeta(JsonObject json)
    {
        String metaType = json.get(EVENT_KEY_META_TYPE).getAsString();
        switch (metaType)
        {
            // 生命周期
            case EVENT_KEY_META_TYPE_LIFECYCLE:
            {
                call(gsonRead.fromJson(json, EventMetaLifecycle.class));
                break;
            }
            // 心跳
            case EVENT_KEY_META_TYPE_HEARTBEAT:
            {
                call(gsonRead.fromJson(json, EventMetaHeartbeat.class));
                break;
            }
            // 未识别
            default:
            {
                reportUnrecognized(EVENT_KEY_NOTICE_TYPE, metaType, json);
                break;
            }
        }
    }

    /**
     * 报告未识别字段
     *
     * @param key 字段
     * @param val 获取的值
     */
    private void reportUnrecognized(String key, String val, Object json)
    {
        manager.getBot().getLogger().error("Unrecognized Key (未识别的字段): {}={}", key, val);
        manager.getBot().getLogger().error("- {}", json);
    }
}
