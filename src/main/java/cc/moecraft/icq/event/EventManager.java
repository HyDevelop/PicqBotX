package cc.moecraft.icq.event;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class EventManager
{
    @Getter
    private ArrayList<IcqListener> registeredListeners = new ArrayList<>();
    @Getter
    private HashMap<String, ArrayList<RegisteredListenerMethod>> registeredListenerMethods = new HashMap<>();
    private final PicqBotX bot;
    private Set<Class<? extends Event>> eventClasses;

    public EventManager(PicqBotX bot)
    {
        this.bot = bot;

        Reflections reflections = new Reflections();
        eventClasses = reflections.getSubTypesOf(Event.class);
    }

    /**
     * 注册一个事件监听器
     * @param listener 监听器
     * @return 这个实例
     */
    public EventManager registerListener(IcqListener listener)
    {
        registeredListeners.add(listener);

        for (Method method : listener.getClass().getMethods())
        {
            if (method.getParameterCount() != 1) continue;

            Class<?> event = method.getParameterTypes()[0];

            if (!Event.class.isAssignableFrom(event)) continue;
            if (!method.isAnnotationPresent(EventHandler.class)) continue;

            for (Class<? extends Event> eventClass : eventClasses) // 向下注册所有子类
            {
                if (!event.isAssignableFrom(eventClass)) continue;

                String mapKey = eventClass.getName();

                if (registeredListenerMethods.containsKey(mapKey))
                    registeredListenerMethods.get(mapKey).add(new RegisteredListenerMethod(method, listener));
                else registeredListenerMethods.put(mapKey, new ArrayList<>(Collections.singletonList(new RegisteredListenerMethod(method, listener))));
            }
        }

        return this;
    }

    /**
     * 执行事件
     * @param event 事件对象
     */
    public void call(Event event)
    {
        event.setBot(bot);

        String mapKey = event.getClass().getName();

        if (!registeredListenerMethods.containsKey(mapKey)) return;

        registeredListenerMethods.get(mapKey).forEach(registeredListenerMethod ->
        {
            try
            {
                registeredListenerMethod.call(event);
            }
            catch (InvocationTargetException | IllegalAccessException e)
            {
                e.printStackTrace(); // 这些理论上绝对不会出现
            }
        });
    }

    /**
     * 执行事件
     * @param inputJsonString ICQ发来的Json字符串
     */
    public void call(String inputJsonString)
    {
        JsonObject json = new JsonParser().parse(inputJsonString).getAsJsonObject();

        switch (json.get("post_type").getAsString())
        {
            case "message":
            {
                callMessage(json);
                break;
            }
            case "notice": // 这里写成这样就只支持4.0了
            {
                callNotice(json);
                break;
            }
            case "request":
            {
                callRequest(json);
                break;
            }
            default:
            {

            }
        }
    }

    /**
     * 执行消息事件
     * @param json JSON输入
     */
    public void callMessage(JsonObject json)
    {
        switch (json.get("message_type").getAsString())
        {
            case "private":
            {
                call(new Gson().fromJson(json, EventPrivateMessage.class));
                break;
            }
            case "group":
            {
                call(new Gson().fromJson(json, EventGroupMessage.class));
                break;
            }
            case "discuss":
            {
                call(new Gson().fromJson(json, EventDiscussMessage.class));
                break;
            }
        }
    }

    /**
     * 执行请求事件
     * @param json JSON输入
     */
    public void callRequest(JsonObject json)
    {
        switch (json.get("request_type").getAsString())
        {
            case "friend":
            {
                call(new Gson().fromJson(json, EventFriendRequest.class));
                break;
            }
            case "group":
            {
                switch (json.get("sub_type").getAsString())
                {
                    case "add":
                    {
                        call(new Gson().fromJson(json, EventGroupAddRequest.class));
                        break;
                    }
                    case "invite":
                    {
                        call(new Gson().fromJson(json, EventGroupInviteRequest.class));
                        break;
                    }
                }
                break;
            }
        }
    }

    /**
     * 执行Notice事件
     * @param json JSON输入
     */
    public void callNotice(JsonObject json)
    {
        switch (json.get("notice_type").getAsString())
        {
            case "group_upload":
            {
                call(new Gson().fromJson(json, EventNoticeGroupUpload.class));
                break;
            }
            case "friend_add":
            {
                call(new Gson().fromJson(json, EventNoticeFriendAdd.class));
                break;
            }
            case "group_admin":
            {
                switch (json.get("sub_type").getAsString())
                {
                    case "set":
                    {
                        call(new Gson().fromJson(json, EventNoticeGroupAdminSet.class));
                        break;
                    }
                    case "unset":
                    {
                        call(new Gson().fromJson(json, EventNoticeGroupAdminRemove.class));
                        break;
                    }
                }
                break;
            }
            case "group_decrease":
            {
                switch (json.get("sub_type").getAsString())
                {
                    case "leave":
                    {
                        call(new Gson().fromJson(json, EventNoticeGroupMemberLeave.class));
                        break;
                    }
                    case "kick":
                    {
                        call(new Gson().fromJson(json, EventNoticeGroupMemberKick.class));
                        break;
                    }
                    case "kick_me":
                    {
                        call(new Gson().fromJson(json, EventNoticeGroupMemberKickBot.class));
                        break;
                    }
                }
                break;
            }
            case "group_increase":
            {
                switch (json.get("sub_type").getAsString())
                {
                    case "approve":
                    {
                        call(new Gson().fromJson(json, EventNoticeGroupMemberApprove.class));
                        break;
                    }
                    case "invite":
                    {
                        call(new Gson().fromJson(json, EventNoticeGroupMemberInvite.class));
                        break;
                    }
                }
                break;
            }
        }
    }
}
