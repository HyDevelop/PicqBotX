package cc.moecraft.icq.event;

import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@NoArgsConstructor
public class EventManager
{
    @Getter
    private ArrayList<IcqListener> registeredListeners = new ArrayList<>();
    @Getter
    private HashMap<String, ArrayList<RegisteredListenerMethod>> registeredListenerMethods = new HashMap<>();

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

            String mapKey = event.getName();

            if (registeredListenerMethods.containsKey(mapKey))
                registeredListenerMethods.get(mapKey).add(new RegisteredListenerMethod(method, listener));
            else
                registeredListenerMethods.put(mapKey, new ArrayList<>(Collections.singletonList(new RegisteredListenerMethod(method, listener))));
        }

        return this;
    }

    /**
     * 执行事件
     * @param event 事件对象
     */
    public void call(Event event)
    {
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
            case "event":
            {
                // TODO
                break;
            }
            case "request":
            {
                // TODO
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
}
