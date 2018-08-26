package cc.moecraft.test.general;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 此类由 Hykilpikonna 在 2018/08/26 创建!
 * Created by Hykilpikonna on 2018/08/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class InvokeTest
{
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        int loopTimes = 100000000;
        long startTime;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < loopTimes; i++) doStuff(i);
        System.out.println("无反射直接调用, 耗时: " + (System.currentTimeMillis() - startTime) + "ms");

        Method doStuffMethod = InvokeTest.class.getDeclaredMethod("doStuff", int.class);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < loopTimes; i++) doStuffMethod.invoke(null, i);
        System.out.println("反射Invoke调用, 耗时: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    public static boolean doStuff(int i)
    {
        return i % 2 == 1;
    }
}
