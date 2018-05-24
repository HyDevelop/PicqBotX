package cc.moecraft.icq;

import cc.moecraft.icq.exceptions.HttpServerStartFailedException;
import cc.moecraft.logger.DebugLogger;
import lombok.Getter;

import java.io.PipedReader;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class PicqBotX
{
    @Getter
    private HttpServer httpServer;

    @Getter
    private boolean debug;

    @Getter
    private DebugLogger logger = new DebugLogger("PicqBotX", true);

    public PicqBotX(int port, boolean debug)
    {
        httpServer = new HttpServer(port, this);
        setDebug(debug);
    }

    public void startBot() throws HttpServerStartFailedException
    {
        httpServer.start();
    }

    /**
     * 设置是否debug
     * @param debug 是否debug
     */
    public void setDebug(boolean debug)
    {
        this.debug = debug;
        logger.setDebug(debug);
    }
}
