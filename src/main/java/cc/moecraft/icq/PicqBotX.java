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
import cc.moecraft.logger.HyLogger;
import cc.moecraft.logger.LoggerInstanceManager;
import cc.moecraft.logger.environments.ConsoleColoredEnv;
import cc.moecraft.logger.environments.FileEnv;
import cc.moecraft.logger.format.AnsiColor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

import static cc.moecraft.logger.format.AnsiColor.*;

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
    private String httpApiVersionDetection = "CQHttp/4.1.*"; // 兼容版本检测

    @Getter
    private LoggerInstanceManager loggerInstanceManager; // Logger实例管理器

    @Getter
    private HyLogger logger; // Logger

    public PicqBotX(String postUrl, int postPort, int socketPort, boolean debug)
    {
        long startTime = System.currentTimeMillis();

        this.debug = debug;

        loggerInstanceManager = new LoggerInstanceManager(new ConsoleColoredEnv(), new FileEnv("logs", "PicqBotX-Log"));
        logger = loggerInstanceManager.getLoggerInstance("PicqBotX", debug);
        logger.log(YELLOW + "日志管理器     " + GREEN + "初始化完成" + YELLOW + " [" + GREEN + "" + RED + "******" + YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)"); startTime = System.currentTimeMillis();

        userManager = new UserManager(this);
        groupUserManager = new GroupUserManager(this);
        groupManager = new GroupManager(this);
        logger.log(YELLOW + "缓存管理器     " + GREEN + "初始化完成" + YELLOW + " [" + GREEN + "*" + RED + "*****" + YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)"); startTime = System.currentTimeMillis();

        // setDebug(debug);
        logger.log(YELLOW + "DEBUG设置      " + GREEN + "初始化完成" + YELLOW + " [" + GREEN + "**" + RED + "****" + YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)"); startTime = System.currentTimeMillis();

        eventManager = new EventManager(this);
        logger.log(YELLOW + "事件管理器     " + GREEN + "初始化完成" + YELLOW + " [" + GREEN + "***" + RED + "***" + YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)"); startTime = System.currentTimeMillis();

        httpApi = new IcqHttpApi(eventManager, postUrl, postPort);
        logger.log(YELLOW + "HTTP发送器     " + GREEN + "初始化完成" + YELLOW + " [" + GREEN + "****" + RED + "**" + YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)"); startTime = System.currentTimeMillis();

        httpServer = new HttpServer(socketPort, this);
        logger.log(YELLOW + "HTTP监听服务器 " + GREEN + "初始化完成" + YELLOW + " [" + GREEN + "*****" + RED + "*" + YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)");
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
            try
            {
                verifyHttpPluginVersion();
            }
            catch (VersionRecommendException e)
            {
                logger.error("版本正确, 不过用酷Q Pro的话效果更好哦!");
            }

            logger.log(GREEN + "检测版本正确, 正在启动...");
            httpServer.start();
        }
        catch (VersionIncorrectException e)
        {
            logger.error("HTTP插件版本不正确, 已停止启动" +
                    "\n- 当前版本: " + e.getCurrentVersion() +
                    "\n- 需要的版本: " + e.getRequiredVersion());
            throw e;
        }
    }

    /**
     * 启用指令系统
     * @param prefixes 前缀
     */
    public void enableCommandManager(String ... prefixes) throws InstantiationException, IllegalAccessException
    {
        enableCommandManager(true, prefixes);
    }

    /**
     * 启用指令系统
     * @param registerAllCommands 是否自动注册所有指令
     * @param prefixes 前缀
     */
    public void enableCommandManager(boolean registerAllCommands, String ... prefixes) throws InstantiationException, IllegalAccessException
    {
        long startTime = System.currentTimeMillis();
        commandManager = new CommandManager(groupManager, userManager, groupUserManager, prefixes);
        if (registerAllCommands) commandManager.registerAllCommands();
        eventManager.registerListener(new CommandListener(commandManager));
        logger.log(YELLOW + "指令管理器     " + GREEN + "初始化完成" + YELLOW + " [" + GREEN + "******" + RED + "" + YELLOW + "] ...(" + (System.currentTimeMillis() - startTime) + "ms)");
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
