package cc.moecraft.icq.receiver;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.events.local.EventLocalHttpFail;
import cc.moecraft.icq.event.events.local.EventLocalHttpFail.Reason;
import cc.moecraft.icq.exceptions.HttpServerException;
import cc.moecraft.logger.HyLogger;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.Getter;

import java.io.IOException;
import java.io.OutputStream;
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
            server.createContext("/", new PicqHttpHandler());

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
    private class PicqHttpHandler implements HttpHandler
    {
        @Override
        public void handle(HttpExchange exchange) throws IOException
        {
            if (!validate(exchange)) return;

            System.out.println("Hi");

            byte response[]="Hello, World!".getBytes("UTF-8");

            exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.length);

            OutputStream out=exchange.getResponseBody();
            out.write(response);
            out.close();
        }
    }

    /**
     * 验证一个请求
     *
     * @param exchange 请求
     * @return 是否为 CoolQ Http 请求
     */
    private boolean validate(HttpExchange exchange)
    {
        // 必须是 POST
        if (!exchange.getRequestMethod().toLowerCase().equals("post"))
        {
            return failed(Reason.INCORRECT_REQUEST_METHOD, exchange);
        }

        // 获取头
        Headers headers = exchange.getRequestHeaders();
        String contentType = headers.getFirst("content-type");
        String userAgent = headers.getFirst("user-agent");

        // 必须是 UTF-8
        if (!contentType.toLowerCase().contains("charset=utf-8"))
        {
            return failed(Reason.INCORRECT_CHARSET, exchange);
        }

        // 必须是 JSON
        if (!contentType.contains("application/json"))
        {
            return failed(Reason.INCORRECT_APPLICATION_TYPE, exchange);
        }

        // 判断版本
        if (!userAgent.matches(HTTP_API_VERSION_DETECTION))
        {
            reportIncorrectVersion(userAgent);
            return failed(Reason.INCORRECT_VERSION, exchange);
        }

        // TODO: validate SHA
        return true;
    }

    /**
     * 报告失败
     *
     * @param reason 失败原因
     */
    private boolean failed(Reason reason, HttpExchange exchange)
    {
        getBot().getEventManager().call(new EventLocalHttpFail(reason));
        logger.debug("Http Failed: {}: {}", reason, exchange);
        return false;
    }

    /**
     * 报告版本错误
     *
     * @param currentVersion 当前版本
     */
    private void reportIncorrectVersion(String currentVersion)
    {
        logger.error("HTTP API请求版本不正确, 设置的兼容版本为: " + HTTP_API_VERSION_DETECTION);
        logger.error("当前版本为: " + currentVersion);
        logger.error("推荐更新这个类库或者HTTP API的版本");
        logger.error("如果要无视版本检查, 请修改 HTTP_API_VERSION_DETECTION");
    }
}
