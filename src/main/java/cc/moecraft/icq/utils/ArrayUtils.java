package cc.moecraft.icq.utils;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/04/26 创建!
 * Created by Hykilpikonna on 2018/04/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 */
public class ArrayUtils
{
    /**
     * 获取一个指定index后面的所有args
     *
     * 例子:
     *   处理前:
     *   - args = ["say", "hello", "world"]
     *   - index = 1
     *   处理后:
     *     "hello world"
     *
     * @param args args
     * @param index 位置
     */
    public static String getTheRestArgsAsString(ArrayList<String> args, int index)
    {
        StringBuilder result = new StringBuilder();

        for (int i = index; i < args.size() - 1; i++)
        {
            result.append(args.get(i)).append(" ");
        }

        return result.append(args.get(args.size() - 1)).toString();
    }
}
