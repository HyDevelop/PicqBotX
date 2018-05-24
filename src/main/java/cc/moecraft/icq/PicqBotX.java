package cc.moecraft.icq;

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
    private static DebugLogger logger = new DebugLogger("PicqBotX", true);

    public PicqBotX(int port)
    {
        httpServer = new HttpServer(port, logger);
    }

    public void startBot() throws Exception
    {
        httpServer.start();
    }
}
