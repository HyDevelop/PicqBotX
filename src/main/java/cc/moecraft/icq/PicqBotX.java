package cc.moecraft.icq;

import cc.moecraft.icq.command.CommandListener;
import cc.moecraft.icq.command.CommandManager;
import cc.moecraft.icq.event.EventManager;
import cc.moecraft.icq.exceptions.HttpServerStartFailedException;
import cc.moecraft.icq.exceptions.VersionIncorrectException;
import cc.moecraft.icq.exceptions.VersionRecommendException;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RVersionInfo;
import cc.moecraft.icq.user.GroupManager;
import cc.moecraft.icq.user.GroupUserManager;
import cc.moecraft.icq.user.UserManager;
import cc.moecraft.logger.AnsiColor;
import cc.moecraft.logger.DebugLogger;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class PicqBotX
{
    @Getter
    private HttpServer httpServer; // HTTP监听服务器

    @Getter
    private boolean debug; // 是否输出DEBUG消息

    @Getter @Setter
    private boolean noVerify = false; // 这个为true是跳过版本验证, 需要手动设置

    @Getter
    private EventManager eventManager; // 事件管理器

    @Getter
    private IcqHttpApi httpApi; // HTTP发送器

    @Getter
    private UserManager userManager; // 用户对象缓存管理器

    @Getter
    private GroupManager groupManager; // 群对象缓存管理器

    @Getter
    private GroupUserManager groupUserManager; // 群用户对象缓存管理器

    @Getter
    private CommandManager commandManager; // 指令管理器

    @Getter @Setter
    private String httpApiVersionDetection = "CQHttp/4.0.3"; // 兼容版本检测

    @Getter
    private DebugLogger logger = new DebugLogger("PicqBotX", true, "logs", "PicqBotX-Log"); // Logger

    public PicqBotX(String postUrl, int postPort, int socketPort, boolean debug)
    {
        long startTime = System.currentTimeMillis();

        userManager = new UserManager(this);
        groupUserManager = new GroupUserManager(this);
        logger.log(AnsiColor.YELLOW + "用户缓存管理器 " + AnsiColor.GREEN + "初始化完成" + AnsiColor.YELLOW + " [" + AnsiColor.GREEN + "" + AnsiColor.RED + "******" + AnsiColor.YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)"); startTime = System.currentTimeMillis();
        groupManager = new GroupManager(this);
        logger.log(AnsiColor.YELLOW + "群缓存管理器   " + AnsiColor.GREEN + "初始化完成" + AnsiColor.YELLOW + " [" + AnsiColor.GREEN + "*" + AnsiColor.RED + "*****" + AnsiColor.YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)"); startTime = System.currentTimeMillis();
        setDebug(debug);
        logger.log(AnsiColor.YELLOW + "DEBUG设置      " + AnsiColor.GREEN + "初始化完成" + AnsiColor.YELLOW + " [" + AnsiColor.GREEN + "**" + AnsiColor.RED + "****" + AnsiColor.YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)"); startTime = System.currentTimeMillis();
        eventManager = new EventManager(this);
        logger.log(AnsiColor.YELLOW + "事件管理器     " + AnsiColor.GREEN + "初始化完成" + AnsiColor.YELLOW + " [" + AnsiColor.GREEN + "***" + AnsiColor.RED + "***" + AnsiColor.YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)"); startTime = System.currentTimeMillis();
        httpApi = new IcqHttpApi(eventManager, postUrl, postPort);
        logger.log(AnsiColor.YELLOW + "HTTP发送器     " + AnsiColor.GREEN + "初始化完成" + AnsiColor.YELLOW + " [" + AnsiColor.GREEN + "****" + AnsiColor.RED + "**" + AnsiColor.YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)"); startTime = System.currentTimeMillis();
        httpServer = new HttpServer(socketPort, this);
        logger.log(AnsiColor.YELLOW + "HTTP监听服务器 " + AnsiColor.GREEN + "初始化完成" + AnsiColor.YELLOW + " [" + AnsiColor.GREEN + "*****" + AnsiColor.RED + "*" + AnsiColor.YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)");
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
     * 启用指令系统
     * @param prefixes 前缀
     */
    public void enableCommandManager(String ... prefixes) throws InstantiationException, IllegalAccessException
    {
        long startTime = System.currentTimeMillis();
        commandManager = new CommandManager(groupManager, userManager, groupUserManager, prefixes);
        commandManager.registerAllCommands();
        eventManager.registerListener(new CommandListener(commandManager));
        logger.log(AnsiColor.YELLOW + "指令管理器     " + AnsiColor.GREEN + "初始化完成" + AnsiColor.YELLOW + " [" + AnsiColor.GREEN + "******" + AnsiColor.RED + "" + AnsiColor.YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)");
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
