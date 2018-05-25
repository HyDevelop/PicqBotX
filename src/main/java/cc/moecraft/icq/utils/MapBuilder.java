package cc.moecraft.icq.utils;

import java.util.HashMap;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@SuppressWarnings("ALL")
public class MapBuilder
{
    /**
     * 把KV对换成Map
     * @param classOne 第一个类的类型
     * @param classTwo 第二个类的类型
     * @param map KV对
     * @return Map
     */
    public static <T1, T2> HashMap<T1, T2> build(Class<T1> classOne, Class<T2> classTwo, Object... map)
    {
        HashMap<T1, T2> result = new HashMap<>();

        for (int i = 0; i < map.length; i += 2)
        {
            T1 key = (T1) map[i];
            T2 value = (T2) map[i + 1];

            result.put(key, value);
        }

        return result;
    }
}
