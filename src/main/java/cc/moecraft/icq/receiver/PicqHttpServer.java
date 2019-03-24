package cc.moecraft.icq.receiver;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.events.local.EventLocalHttpFail;
import cc.moecraft.icq.event.events.local.EventLocalHttpFail.Reason;
import cc.moecraft.icq.exceptions.HttpServerException;
import cc.moecraft.logger.HyLogger;
import com.sun.net.httpserver.HttpServer;
import lombok.Getter;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;
import java.net.InetSocketAddress;

import static cc.moecraft.icq.PicqConstants.HTTP_API_VERSION_DETECTION;

/**
 * The class {@code PicqHttpServer} is a http server to receive and
 * parse the package sent from the http plugin of CoolQ.
 * <p>
 * Class created by the HyDEV Team on 2019-03-23!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-23 12:55
 */
@Getter
public class PicqHttpServer
{
    /**
     * 端口号 (0~65535)
     */
    private final int port;

    /**
     * 机器人对象
     */
    private final PicqBotX bot;

    /**
     * 日志对象
     */
    protected final HyLogger logger;

    /**
     * 构造一个Http服务器
     *
     * @param port 端口
     * @param bot 机器人
     */
    public PicqHttpServer(int port, PicqBotX bot)
    {
        this.port = port;
        this.bot = bot;

        logger = bot.getLogger();
    }

    /**
     * 启动 Http 服务器
     */
    public void start()
    {
        try
        {
            // 使用 Java SE 6 内置的 HttpServer
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            // 添加监听器
            server.createContext("/", httpExchange -> new PicqHttpHandler());

            // 启动
            server.start();
        }
        catch (IOException e)
        {
            throw new HttpServerException(logger, e);
        }
    }

    /**
     * Http 监听器
     */
    private class PicqHttpHandler extends HttpHandler
    {
        @Override
        public void handle(HttpExchange exchange) throws IOException
        {
            System.out.println("Hi");
        }
    }
}
