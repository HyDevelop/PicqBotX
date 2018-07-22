package cc.moecraft.icq;

import cc.moecraft.icq.command.CommandListener;
import cc.moecraft.icq.command.CommandManager;
import cc.moecraft.icq.event.EventManager;
import cc.moecraft.icq.exceptions.HttpServerStartFailedException;
import cc.moecraft.icq.exceptions.InvalidSendingURLException;
import cc.moecraft.icq.exceptions.VersionIncorrectException;
import cc.moecraft.icq.exceptions.VersionRecommendException;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RVersionInfo;
import cc.moecraft.icq.user.GroupManager;
import cc.moecraft.icq.user.GroupUserManager;
import cc.moecraft.icq.user.UserManager;
import cc.moecraft.logger.HyLogger;
import cc.moecraft.logger.LoggerInstanceManager;
import cc.moecraft.logger.environments.ColorSupportLevel;
import cc.moecraft.logger.environments.ConsoleColoredEnv;
import cc.moecraft.logger.environments.FileEnv;
import com.xiaoleilu.hutool.http.HttpException;
import lombok.Getter;
import lombok.Setter;

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
    private String httpApiVersionDetection = ".*4.3.*"; // 兼容版本检测

    @Getter
    private LoggerInstanceManager loggerInstanceManager; // Logger实例管理器

    @Getter
    private HyLogger logger; // Logger

    public PicqBotX(String postUrl, int postPort, int socketPort, boolean debug)
    {
        this(postUrl, postPort, socketPort, debug,
                ColorSupportLevel.PASSTHROUGH, "logs", "PicqBotX-Log");
    }

    public PicqBotX(String postUrl, int postPort, int socketPort, boolean debug, ColorSupportLevel colorSupportLevel, String logPath, String logFileName)
    {
        this.debug = debug;

        loggerInstanceManager = new LoggerInstanceManager(
                new ConsoleColoredEnv(colorSupportLevel),
                new FileEnv(logPath, logFileName));
        logger = loggerInstanceManager.getLoggerInstance("PicqBotX", debug);
        logger.timing.init();
        logInit("日志管理器     ", 0, 6);

        userManager = new UserManager(this);
        groupUserManager = new GroupUserManager(this);
        groupManager = new GroupManager(this);
        logInit("缓存管理器     ", 1, 5);

        // setDebug(debug);
        logInit("DEBUG设置      ", 2, 4);

        eventManager = new EventManager(this);
        logInit("事件管理器     ", 3, 3);

        httpApi = new IcqHttpApi(eventManager, postUrl, postPort);
        logInit("HTTP发送器     ", 4, 2);

        httpServer = new HttpServer(socketPort, this);
        logInit("HTTP监听服务器 ", 5, 1);

        logger.timing.clear();
    }

    private void logInit(String name, int greens, int reds)
    {
        StringBuilder greenStars = new StringBuilder();
        StringBuilder redStars = new StringBuilder();

        for (int i = 0; i < greens; i++) greenStars.append("*");
        for (int i = 0; i < reds; i++) redStars.append("*");

        logger.log(String.format("%s%s%s初始化完成%s [%s%s%s%s%s] ...(%s ms)",
                YELLOW, name, GREEN,
                YELLOW,
                GREEN, greenStars.toString(), RED, redStars.toString(), YELLOW,
                Math.round(logger.timing.getMilliseconds() * 100d) / 100d));

        logger.timing.reset();
    }

    /**
     * 启动机器人
     * @throws HttpServerStartFailedException HTTP服务器启动失败
     * @throws VersionIncorrectException 版本错误
     */
    public void startBot() throws HttpServerStartFailedException, VersionIncorrectException, InvalidSendingURLException
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
            logger.error("HTTP插件版本不正确, 已停止启动");
            logger.error("- 当前版本: " + e.getCurrentVersion());
            logger.error("- 兼容的版本: " + e.getRequiredVersion());

            throw e;
        }
        catch (InvalidSendingURLException e)
        {
            logger.error("HTTP发送地址验证失败, 已停止启动");
            logger.error("- 请检查酷Q是否已经启动");
            logger.error("- 请检查酷Q的接收端口是否和Picq的发送端口一样");
            logger.error("- 请检查你的发送IP是不是写错了");
            logger.error("- 如果是向外, 请检查这个主机有没有网络连接");
            throw e;
        }
    }


    /**
     * 设置机器人HTTP暂停状态
     * @param isPaused 是否暂停
     */
    public void httpSetPauseState(boolean isPaused)
    {
        httpServer.setPaused(isPaused);
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
        logger.timing.init();

        commandManager = new CommandManager(groupManager, userManager, groupUserManager, prefixes);
        if (registerAllCommands) commandManager.registerAllCommands();
        eventManager.registerListener(new CommandListener(commandManager));
        logInit("指令管理器     ", 6, 0);

        logger.timing.clear();
    }

    /**
     * 验证HTTP插件版本
     * @throws VersionIncorrectException 版本不对
     */
    public void verifyHttpPluginVersion() throws
            VersionIncorrectException,
            VersionRecommendException,
            InvalidSendingURLException
    {
        if (noVerify) return;

        try
        {
            RVersionInfo versionInfo = httpApi.getVersionInfo().getData();

            if (!versionInfo.getPluginVersion().matches(httpApiVersionDetection))
                throw new VersionIncorrectException(httpApiVersionDetection, versionInfo.getPluginVersion());

            if (!versionInfo.getCoolqEdition().equalsIgnoreCase("pro"))
                throw new VersionRecommendException();
        }
        catch (HttpException e)
        {
            if (e.getMessage().toLowerCase().contains("connection refused"))
                throw new InvalidSendingURLException();
            e.printStackTrace();
        }
    }
}
