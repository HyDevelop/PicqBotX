package cc.moecraft.icq;

import cc.moecraft.icq.event.EventManager;
import cc.moecraft.icq.exceptions.HttpServerStartFailedException;
import cc.moecraft.icq.exceptions.VersionIncorrectException;
import cc.moecraft.icq.exceptions.VersionRecommendException;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RVersionInfo;
import cc.moecraft.logger.AnsiColor;
import cc.moecraft.logger.DebugLogger;
import lombok.Getter;
import lombok.Setter;

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

    @Getter @Setter
    private boolean noVerify = false; // 这个为true是跳过版本验证, 需要手动设置

    @Getter
    private EventManager eventManager;

    @Getter
    private IcqHttpApi httpApi;

    @Getter
    private DebugLogger logger = new DebugLogger("PicqBotX", true);

    public PicqBotX(String postUrl, int postPort, int socketPort, boolean debug)
    {
        setDebug(debug);
        httpApi = new IcqHttpApi(eventManager, postUrl, postPort);
        httpServer = new HttpServer(socketPort, this);
        eventManager = new EventManager(this);
    }

    /**
     * 启动机器人
     * @throws HttpServerStartFailedException HTTP服务器启动失败
     * @throws VersionIncorrectException 版本错误
     */
    public void startBot() throws HttpServerStartFailedException, VersionIncorrectException
    {
        try
        {
            verifyHttpPluginVersion();
            logger.log(AnsiColor.GREEN + "检测版本正确, 正在启动...");
            httpServer.start();
        }
        catch (VersionIncorrectException e)
        {
            logger.error("HTTP插件版本不正确, 已停止启动" +
                    "\n- 当前版本: " + e.getCurrentVersion() +
                    "\n- 需要的版本: " + e.getRequiredVersion());
            throw e;
        }
        catch (VersionRecommendException e)
        {
            logger.error("版本正确, 不过用酷Q Pro的话效果更好哦!");
        }
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

    /**
     * 验证HTTP插件版本
     * @throws VersionIncorrectException 版本不对
     */
    public void verifyHttpPluginVersion() throws VersionIncorrectException, VersionRecommendException
    {
        if (noVerify) return;

        RVersionInfo versionInfo = httpApi.getVersionInfo().getData();

        if (!versionInfo.getPluginVersion().startsWith("4."))
            throw new VersionIncorrectException("4.*", versionInfo.getPluginVersion());

        if (!versionInfo.getCoolqEdition().equalsIgnoreCase("pro"))
            throw new VersionRecommendException();
    }
}
