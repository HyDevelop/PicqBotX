package cc.moecraft.icq.event;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.events.local.*;
import cc.moecraft.icq.event.events.message.*;
import cc.moecraft.icq.event.events.notice.EventNotice;
import cc.moecraft.icq.event.events.notice.EventNoticeFriendAdd;
import cc.moecraft.icq.event.events.notice.EventNoticeGroupUpload;
import cc.moecraft.icq.event.events.notice.groupadmin.EventNoticeGroupAdminChange;
import cc.moecraft.icq.event.events.notice.groupadmin.EventNoticeGroupAdminRemove;
import cc.moecraft.icq.event.events.notice.groupadmin.EventNoticeGroupAdminSet;
import cc.moecraft.icq.event.events.notice.groupmember.EventNoticeGroupMemberChange;
import cc.moecraft.icq.event.events.notice.groupmember.decrease.EventNoticeGroupMemberDecrease;
import cc.moecraft.icq.event.events.notice.groupmember.decrease.EventNoticeGroupMemberKick;
import cc.moecraft.icq.event.events.notice.groupmember.decrease.EventNoticeGroupMemberKickBot;
import cc.moecraft.icq.event.events.notice.groupmember.decrease.EventNoticeGroupMemberLeave;
import cc.moecraft.icq.event.events.notice.groupmember.increase.EventNoticeGroupMemberApprove;
import cc.moecraft.icq.event.events.notice.groupmember.increase.EventNoticeGroupMemberIncrease;
import cc.moecraft.icq.event.events.notice.groupmember.increase.EventNoticeGroupMemberInvite;
import cc.moecraft.icq.event.events.request.EventFriendRequest;
import cc.moecraft.icq.event.events.request.EventGroupAddRequest;
import cc.moecraft.icq.event.events.request.EventGroupInviteRequest;
import cc.moecraft.icq.event.events.request.EventRequest;
import cc.moecraft.icq.utils.CQUtils;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static java.util.Arrays.asList;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@Getter
public class EventManager
{
    private final PicqBotX bot;

    private final EventParser eventParser;

    private ArrayList<IcqListener> registeredListeners = new ArrayList<>();

    private HashMap<String, ArrayList<RegisteredListenerMethod>> registeredMethods = new HashMap<>();

    /**
     * 所有可以注册的事件的类
     * 版本 v3.0.0.611 后, 反射注册类改为手动注册类
     * 因为这样可以减少 ≈600ms 的启动延迟
     */
    private static final List<Class<? extends Event>> eventClasses = asList(
            EventLocal.class,
            EventLocalException.class,
            EventLocalHttpFail.class,
            EventLocalHttpReceive.class,
            EventLocalSendDiscussMessage.class,
            EventLocalSendGroupMessage.class,
            EventLocalSendPrivateMessage.class,
            EventLocalSendMessage.class,

            EventDiscussMessage.class,
            EventGroupMessage.class,
            EventGroupOrDiscussMessage.class,
            EventPrivateMessage.class,
            EventMessage.class,

            EventNoticeGroupAdminChange.class,
            EventNoticeGroupAdminRemove.class,
            EventNoticeGroupAdminSet.class,
            EventNoticeGroupMemberDecrease.class,
            EventNoticeGroupMemberLeave.class,
            EventNoticeGroupMemberKick.class,
            EventNoticeGroupMemberKickBot.class,
            EventNoticeGroupMemberApprove.class,
            EventNoticeGroupMemberInvite.class,
            EventNoticeGroupMemberIncrease.class,
            EventNoticeGroupMemberChange.class,
            EventNoticeFriendAdd.class,
            EventNoticeGroupUpload.class,
            EventNotice.class,

            EventFriendRequest.class,
            EventGroupAddRequest.class,
            EventGroupInviteRequest.class,
            EventRequest.class
    );

    public EventManager(PicqBotX bot)
    {
        this.bot = bot;
        this.eventParser = new EventParser(this);
    }

    /**
     * 注册一个事件监听器
     *
     * @param listener 监听器
     */
    public void registerListener(IcqListener listener)
    {
        registeredListeners.add(listener);

        for (Method method : listener.getClass().getMethods())
        {
            if (method.getParameterCount() != 1)
            {
                continue;
            }

            Class<?> event = method.getParameterTypes()[0];

            if (!Event.class.isAssignableFrom(event))
            {
                continue;
            }
            if (!method.isAnnotationPresent(EventHandler.class))
            {
                continue;
            }

            for (Class<? extends Event> eventClass : eventClasses) // 向下注册所有子类
            {
                if (!event.isAssignableFrom(eventClass))
                {
                    continue;
                }

                String mapKey = eventClass.getName();

                if (registeredMethods.containsKey(mapKey))
                {
                    registeredMethods.get(mapKey).add(new RegisteredListenerMethod(method, listener));
                }
                else
                {
                    registeredMethods.put(mapKey, new ArrayList<>(Collections.singletonList(new RegisteredListenerMethod(method, listener))));
                }
            }
        }
    }

    /**
     * 注册很多个事件监听器
     *
     * @param listeners 很多个监听器
     */
    public void registerListeners(IcqListener... listeners)
    {
        for (IcqListener listener : listeners)
        {
            registerListener(listener);
        }
    }

    /**
     * 执行事件
     *
     * @param event 事件对象
     */
    public void call(Event event)
    {
        event.setBot(bot);

        if (event instanceof EventMessage)
        {
            ((EventMessage) event).message = CQUtils.decodeMessage(((EventMessage) event).getMessage());
        }

        String mapKey = event.getClass().getName();

        if (!registeredMethods.containsKey(mapKey))
        {
            return;
        }

        registeredMethods.get(mapKey).forEach(registeredListenerMethod ->
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
        {
            throwable.printStackTrace(); // 如果这个事件是报错事件, 而且这个事件报的错也是报错事件的话, 怎么办呢....
        }
        else
        {
            call(new EventLocalException(throwable instanceof InvocationTargetException ? throwable.getCause() : throwable, event));
        }
    }
}
