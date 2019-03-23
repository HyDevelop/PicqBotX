package cc.moecraft.icq.receiver;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.exceptions.HttpServerException;
import cc.moecraft.logger.HyLogger;
import lombok.Getter;

/**
 * The class {@code HttpServerInterface} is a http server to receive and
 * parse the package sent from the http plugin of CoolQ.
 * <p>
 * Class created by the HyDEV Team on 2019-03-23!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-23 12:55
 */
public abstract class HttpServer
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
    protected HttpServer(int port, PicqBotX bot)
    {
        this.port = port;
        this.bot = bot;

        logger = bot.getLogger();
    }

    /**
     * 在当前线程启动HTTP服务器
     *
     * @throws HttpServerException 启动失败
     */
    public abstract void start() throws HttpServerException;
}
