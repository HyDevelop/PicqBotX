package cc.moecraft.icq.utils;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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

    /**
     * 用主机地址和端口创建URL
     *
     * @param host 主机地址
     * @param port 端口
     * @return URL
     */
    public static String url(String host, int port)
    {
        try
        {
            return new URI("http", null, host, port, "/", null, null).toURL().toString();
        }
        catch (MalformedURLException | URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }
}
