package cc.moecraft.icq.event;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.events.local.EventLocalException;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.utils.CQUtils;
import lombok.Getter;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

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
    private ArrayList<IcqListener> registeredListeners = new ArrayList<>();

    private HashMap<String, ArrayList<RegisteredListenerMethod>> registeredListenerMethods = new HashMap<>();
    private final PicqBotX bot;
    private Set<Class<? extends Event>> eventClasses;

    private final EventParser eventParser;

    public EventManager(PicqBotX bot)
    {
        this.bot = bot;
        this.eventParser = new EventParser(this);

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
}
