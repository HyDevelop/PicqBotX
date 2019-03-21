package cc.moecraft.icq.event;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.events.local.EventLocalException;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
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
import cc.moecraft.icq.utils.CQUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static cc.moecraft.icq.PicqConstants.*;

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
    private static final Gson gson = new Gson();
    
    @Getter
    private ArrayList<IcqListener> registeredListeners = new ArrayList<>();

    @Getter
    private HashMap<String, ArrayList<RegisteredListenerMethod>> registeredListenerMethods = new HashMap<>();
    private final PicqBotX bot;
    private Set<Class<? extends Event>> eventClasses;

    @Getter @Setter
    private boolean paused = false;

    public EventManager(PicqBotX bot)
    {
        this.bot = bot;

        // 为了性能, 暂时不支持自己添加事件
        // 如果不加包名, 平均耗时4000ms, 加上包名, 平均耗时300ms
        Reflections reflections = new Reflections("cc.moecraft.icq.event");
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

        if (event instanceof EventMessage)
            ((EventMessage) event).message = CQUtils.decodeMessage(((EventMessage) event).getMessage());

        String mapKey = event.getClass().getName();

        if (!registeredListenerMethods.containsKey(mapKey)) return;

        registeredListenerMethods.get(mapKey).forEach(registeredListenerMethod ->
        {
            try
            {
                registeredListenerMethod.call(event);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace(); // 这些理论上绝对不会出现
            }
            catch (Throwable e)
            {
                callError(event, e);
            }
        });
    }

    public void callError(Event event, Throwable throwable)
    {
        if (event instanceof EventLocalException && ((EventLocalException) event).getParentEvent() instanceof EventLocalException)
            throwable.printStackTrace(); // 如果这个事件是报错事件, 而且这个事件报的错也是报错事件的话, 怎么办呢....
        else call(new EventLocalException(throwable instanceof InvocationTargetException ? throwable.getCause() : throwable, event));
    }

    private final Map<Class<? extends Event>, Map<String, ArrayList<ContentComparable<?>>>> cache = new HashMap<>();

    /**
     * 判断一个事件是不是新的
     * 是新的代表这个事件在其他账号上有没有判定为是新的过.
     * @param event 事件
     * @param identifier 标记 (比如群消息的标记就是群号)
     * @param <T> 实现了内容比较方法的事件类
     * @return 是不是新的
     */
    @SuppressWarnings("unchecked")
    private <T extends Event & ContentComparable<T>> boolean isNew(T event, String identifier)
    {
        if (!bot.isMultiAccountOptimizations()) return true;
        Class<? extends Event> eventClass = event.getClass();

        if (!cache.containsKey(eventClass)) cache.put(eventClass, new HashMap<>());
        Map<String, ArrayList<ContentComparable<?>>> cachedEventMap = cache.get(eventClass);

        if (!cachedEventMap.containsKey(identifier)) cachedEventMap.put(identifier, new ArrayList<>());
        ArrayList<ContentComparable<?>> cachedEvents = cachedEventMap.get(identifier);

        for (ContentComparable comparable : cachedEvents)
            if (comparable.contentEquals(event)) return false;

        cachedEvents.add(event);

        if (cachedEvents.size() > 10) cachedEvents.remove(0);
        return true;
    }

    /**
     * 执行事件
     *
     * @param inputJsonString ICQ发来的Json字符串
     */
    public void call(String inputJsonString)
    {
        if (paused) return; // 判断暂停

        JsonObject json = new JsonParser().parse(inputJsonString).getAsJsonObject();
        String postType = json.get(EVENT_KEY_POST_TYPE).getAsString();

        switch (postType)
        {
            case EVENT_KEY_POST_TYPE_MESSAGE:
            {
                callMessage(json);
                break;
            }
            case EVENT_KEY_POST_TYPE_NOTICE:
            {
                callNotice(json);
                break;
            }
            case EVENT_KEY_POST_TYPE_REQUEST:
            {
                callRequest(json);
                break;
            }
            default:
            {
                bot.getLogger().error("Unrecognized Key (未识别的字段): {}={}", EVENT_KEY_POST_TYPE, postType);
                break;
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
                call(gson.fromJson(json, EventPrivateMessage.class));
                break;
            }
            case "group":
            {
                EventGroupMessage event = gson.fromJson(json, EventGroupMessage.class);
                if (isNew(event, event.getGroupId().toString())) call(event);
                break;
            }
            case "discuss":
            {
                EventDiscussMessage event = gson.fromJson(json, EventDiscussMessage.class);
                if (isNew(event, event.getDiscussId().toString())) call(event);
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
                call(gson.fromJson(json, EventFriendRequest.class));
                break;
            }
            case "group":
            {
                switch (json.get("sub_type").getAsString())
                {
                    case "add":
                    {
                        EventGroupAddRequest event = gson.fromJson(json, EventGroupAddRequest.class);
                        if (isNew(event, event.getGroupId().toString())) call(event);
                        break;
                    }
                    case "invite":
                    {
                        call(gson.fromJson(json, EventGroupInviteRequest.class));
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
                EventNoticeGroupUpload event = gson.fromJson(json, EventNoticeGroupUpload.class);
                if (isNew(event, event.getGroupId().toString())) call(event);
                break;
            }
            case "friend_add":
            {
                call(gson.fromJson(json, EventNoticeFriendAdd.class));
                break;
            }
            case "group_admin":
            {
                switch (json.get("sub_type").getAsString())
                {
                    case "set":
                    {
                        EventNoticeGroupAdminSet event = gson.fromJson(json, EventNoticeGroupAdminSet.class);
                        if (isNew(event, event.getGroupId().toString())) call(event);
                        break;
                    }
                    case "unset":
                    {
                        EventNoticeGroupAdminRemove event = gson.fromJson(json, EventNoticeGroupAdminRemove.class);
                        if (isNew(event, event.getGroupId().toString())) call(event);
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
                        EventNoticeGroupMemberLeave event = gson.fromJson(json, EventNoticeGroupMemberLeave.class);
                        if (isNew(event, event.getGroupId().toString())) call(event);
                        break;
                    }
                    case "kick":
                    {
                        EventNoticeGroupMemberKick event = gson.fromJson(json, EventNoticeGroupMemberKick.class);
                        if (isNew(event, event.getGroupId().toString())) call(event);
                        break;
                    }
                    case "kick_me":
                    {
                        call(gson.fromJson(json, EventNoticeGroupMemberKickBot.class));
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
                        EventNoticeGroupMemberApprove event = gson.fromJson(json, EventNoticeGroupMemberApprove.class);
                        if (isNew(event, event.getGroupId().toString())) call(event);
                        break;
                    }
                    case "invite":
                    {
                        EventNoticeGroupMemberInvite event = gson.fromJson(json, EventNoticeGroupMemberInvite.class);
                        if (isNew(event, event.getGroupId().toString())) call(event);
                        break;
                    }
                }
                break;
            }
        }
    }
}
