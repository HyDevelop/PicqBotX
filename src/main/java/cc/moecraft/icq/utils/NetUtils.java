package cc.moecraft.icq.utils;

import java.io.InputStream;
import java.util.Scanner;

/**
 * The class {@code NetUtils} is an utility class for Socket servers.
 * <p>
 * Class created by the HyDEV Team on 2019-03-23!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-23 13:54
 */
public class NetUtils
{
    /**
     * 读取字符串数据
     *
     * @param in 输入流
     * @return 字符串
     */
    public static String read(InputStream in)
    {
        Scanner scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
