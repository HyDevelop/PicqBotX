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
}
