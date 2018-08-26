package cc.moecraft.icq.utils;

import java.lang.reflect.Field;

/**
 * 此类由 Hykilpikonna 在 2018/08/26 创建!
 * Created by Hykilpikonna on 2018/08/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class ExceptionUtils
{
    public static <T extends Throwable> String getAllVariables(T throwable)
    {
        StringBuilder variables = new StringBuilder("@").append(throwable.getClass().getName()).append("[");

        for (Field field : throwable.getClass().getDeclaredFields())
        {
            field.setAccessible(true);
            try
            {
                String name = field.getName();
                String value = field.get(throwable).toString();
                variables.append(name).append("=").append(value).append(";");
            }
            catch (IllegalAccessException ignored) {}
        }

        return variables.append("]").toString();
    }
}
