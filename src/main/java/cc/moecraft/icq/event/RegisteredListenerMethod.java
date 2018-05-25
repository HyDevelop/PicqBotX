package cc.moecraft.icq.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@Data @AllArgsConstructor
public class RegisteredListenerMethod
{
    private Method method;
    private IcqListener listener;

    /**
     * 反射执行监听方法
     * @param event 事件
     * @return Json响应
     * @throws InvocationTargetException 反射失败
     * @throws IllegalAccessException 无访问权限 (不可能发生)
     */
    public void call(Event event) throws InvocationTargetException, IllegalAccessException
    {
        method.setAccessible(true);
        method.invoke(listener, event);
    }
}
