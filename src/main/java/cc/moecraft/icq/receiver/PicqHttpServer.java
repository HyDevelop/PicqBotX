package cc.moecraft.icq.receiver;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.events.local.EventLocalHttpFail;
import cc.moecraft.icq.event.events.local.EventLocalHttpFail.Reason;
import cc.moecraft.icq.exceptions.HttpServerException;
import cc.moecraft.icq.utils.SHA1Utils;
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
import static cc.moecraft.icq.event.events.local.EventLocalHttpFail.Reason.*;
import static cc.moecraft.icq.utils.NetUtils.read;

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
            // 验证
            if (!validateHeader(exchange))
            {
                respondAndClose(exchange, 200, "Oh hi there! How are you?");
                return;
            }

            // 获取请求数据
            String data = read(exchange.getRequestBody());

            // 输出Debug
            printDebug(exchange, data);

            // 调用事件
            bot.getEventManager().getEventParser().call(data);

            // 回复成功
            respondAndClose(exchange, 204, "");
        }
    }

    /**
     * 验证一个请求
     *
     * @param exchange 请求
     * @return 是否为 CoolQ Http 请求
     */
    private boolean validateHeader(HttpExchange exchange)
    {
        // 必须是 POST
        if (!exchange.getRequestMethod().toLowerCase().equals("post"))
        {
            return failed(INCORRECT_REQUEST_METHOD, "Not POST");
        }

        // 获取头
        Headers headers = exchange.getRequestHeaders();
        String contentType = headers.getFirst("content-type");
        String userAgent = headers.getFirst("user-agent");

        // 必须是 UTF-8
        if (!contentType.toLowerCase().contains("charset=utf-8"))
        {
            return failed(INCORRECT_CHARSET, "Not UTF-8");
        }

        // 必须是 JSON
        if (!contentType.contains("application/json"))
        {
            return failed(INCORRECT_APPLICATION_TYPE, "Not JSON");
        }

        // 判断版本
        if (!userAgent.matches(HTTP_API_VERSION_DETECTION))
        {
            reportIncorrectVersion(userAgent);
            return failed(INCORRECT_VERSION, "Supported Version: " + HTTP_API_VERSION_DETECTION);
        }

        return true;
    }

    /**
     * 验证 HAMC SHA1 (如果有的话)
     *
     * @param exchange 请求
     * @param data 数据
     * @return 如果有, 是否验证成功
     */
    private boolean validateSHA1(HttpExchange exchange, String data)
    {
        // 是否启用验证
        if (bot.getConfig().getSecret().isEmpty())
        {
            return true;
        }

        // 获取 SHA1
        String signature = exchange.getRequestHeaders().getFirst("x-signature");

        // CoolQ HTTP 是否有发送 SHA1
        if (signature == null || signature.isEmpty())
        {
            return failed(INCORRECT_SHA1, "Signature Empty");
        }

        // 获取 SHA1 里面的 SHA1 嗯x
        signature = signature.replace("sha1=", "");

        // 生成 SHA1
        String generatedSignature = SHA1Utils.generateHAMCSHA1(data, bot.getConfig().getSecret());

        // 判断生成的和获取的是不是一样的
        if (!signature.equals(generatedSignature))
        {
            return failed(INCORRECT_SHA1, "Signature Mismatch: \n" +
                    "- Sent: " + signature + "\n- Generated: " + generatedSignature);
        }
        return true;
    }

    /**
     * 报告失败
     *
     * @param reason 失败原因
     */
    private boolean failed(Reason reason, String text)
    {
        getBot().getEventManager().call(new EventLocalHttpFail(reason));
        logger.debug("Http Failed: {}: {}", reason, text);
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

    /**
     * 回复输出
     *
     * @param exchange 请求
     * @param code HTTP返回码 (204 = CoolQ 处理成功)
     * @param response 回复
     */
    private void respondAndClose(HttpExchange exchange, int code, String response) throws IOException
    {
        byte[] bytes = response.getBytes();

        exchange.sendResponseHeaders(code, bytes.length == 0 ? -1 : bytes.length);

        OutputStream out = exchange.getResponseBody();
        out.write(bytes);
        out.close();
    }

    /**
     * 输出Debug消息
     *
     * @param exchange 请求
     * @param data 数据
     */
    private void printDebug(HttpExchange exchange, String data)
    {
        if (!bot.getConfig().isDebug()) return;

        logger.debug("收到新请求: {}", exchange.getRequestHeaders().getFirst("user-agent"));
        logger.debug("- 数据: {}", data);
    }
}
