package cc.moecraft.icq;

import cc.moecraft.icq.command.CommandListener;
import cc.moecraft.icq.command.CommandManager;
import cc.moecraft.icq.event.EventManager;
import cc.moecraft.icq.exceptions.HttpServerStartFailedException;
import cc.moecraft.icq.exceptions.InvalidSendingURLException;
import cc.moecraft.icq.exceptions.VersionIncorrectException;
import cc.moecraft.icq.exceptions.VersionRecommendException;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RVersionInfo;
import cc.moecraft.icq.user.GroupManager;
import cc.moecraft.icq.user.GroupUserManager;
import cc.moecraft.icq.user.UserManager;
import cc.moecraft.icq.utils.ResourceUtils;
import cc.moecraft.logger.HyLogger;
import cc.moecraft.logger.LoggerInstanceManager;
import cc.moecraft.logger.environments.ColorSupportLevel;
import cc.moecraft.logger.environments.ConsoleColoredEnv;
import cc.moecraft.logger.environments.ConsoleEnv;
import cc.moecraft.logger.environments.FileEnv;
import cc.moecraft.logger.format.AnsiColor;
import cn.hutool.http.HttpException;
import lombok.Getter;
import lombok.Setter;

import static cc.moecraft.logger.format.AnsiColor.*;

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
    private HttpServer httpServer; // HTTP监听服务器

    @Getter
    private boolean debug; // 是否输出DEBUG消息

    @Getter @Setter
    private boolean noVerify = false; // 这个为true是跳过版本验证, 需要手动设置

    @Getter
    private EventManager eventManager; // 事件管理器

    @Getter
    private AccountManager accountManager; // 机器人账号管理器

    @Getter
    private UserManager userManager; // 用户对象缓存管理器

    @Getter
    private GroupManager groupManager; // 群对象缓存管理器

    @Getter
    private GroupUserManager groupUserManager; // 群用户对象缓存管理器

    @Getter
    private CommandManager commandManager; // 指令管理器

    @Getter @Setter
    private String httpApiVersionDetection = ".*4.4.*"; // 兼容版本检测

    @Getter
    private LoggerInstanceManager loggerInstanceManager; // Logger实例管理器

    @Getter
    private HyLogger logger; // Logger

    @Getter @Setter
    private boolean useAsync = false; // 是否异步

    @Getter @Setter
    private boolean maintenanceMode = false; // 维护模式

    @Getter @Setter
    private boolean multiAccountOptimizations = true; // 多账号优化

    public static final String VERSION = "1.4.0.475";

    public PicqBotX(String postUrl, int postPort, int socketPort, boolean debug)
    {
        this(postUrl, postPort, socketPort, debug,
                ColorSupportLevel.PASSTHROUGH, "logs", "PicqBotX-Log");
    }

    public PicqBotX(String postUrl, int postPort, int socketPort, boolean debug, ColorSupportLevel colorSupportLevel, String logPath, String logFileName)
    {
        this.debug = debug;

        loggerInstanceManager = new LoggerInstanceManager(new FileEnv(logPath, logFileName));

        if (colorSupportLevel == null) loggerInstanceManager.addEnvironment(new ConsoleEnv());
        else loggerInstanceManager.addEnvironment(new ConsoleColoredEnv(colorSupportLevel));

        logger = loggerInstanceManager.getLoggerInstance("PicqBotX", debug);
        logger.timing.init();

        if (colorSupportLevel == null) ResourceUtils.logResource(this.getClass(), logger, "splash", "version", VERSION);
        else ResourceUtils.logResource(this.getClass(), logger, "splash-precolored", "version", VERSION);

        logInit("日志管理器     ", 0, 6);

        userManager = new UserManager(this);
        groupUserManager = new GroupUserManager(this);
        groupManager = new GroupManager(this);
        logInit("缓存管理器     ", 1, 5);

        // setDebug(debug);
        logInit("DEBUG设置      ", 2, 4);

        eventManager = new EventManager(this);
        logInit("事件管理器     ", 3, 3);

        accountManager = new AccountManager();
        logInit("账号管理器     ", 4, 2);

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
     */
    public void startBot() throws HttpServerStartFailedException
    {
        if (!verifyHttpPluginVersion())
        {
            logger.error("验证失败, 请检查上面的错误信息再重试启动服务器.");
            System.exit(1);
        }

        logger.log(GREEN + "正在启动...");
        httpServer.start();
    }

    /**
     * 启用指令系统
     * @param prefixes 前缀
     * @throws InstantiationException 反射失败
     * @throws IllegalAccessException 反射失败
     */
    public void enableCommandManager(String ... prefixes) throws InstantiationException, IllegalAccessException
    {
        enableCommandManager(true, prefixes);
    }

    /**
     * 启用指令系统
     * @param registerAllCommands 是否自动注册所有指令
     * @param prefixes 前缀
     * @throws InstantiationException 反射失败
     * @throws IllegalAccessException 反射失败
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
     * @return 是否通过验证
     */
    public boolean verifyHttpPluginVersion()
    {
        if (noVerify) return true;

        for (BotAccount botAccount : accountManager.getAccounts())
        {
            String prefix =  "账号 " + botAccount.getName() + ": ";

            try
            {
                RVersionInfo versionInfo = botAccount.getHttpApi().getVersionInfo().getData();

                if (!versionInfo.getPluginVersion().matches(httpApiVersionDetection))
                {
                    logger.error(prefix + "HTTP插件版本不正确, 已停止启动");
                    logger.error("- 当前版本: " + versionInfo.getPluginVersion());
                    logger.error("- 兼容的版本: " + httpApiVersionDetection);
                    return false;
                }

                if (!versionInfo.getCoolqEdition().equalsIgnoreCase("pro"))
                    logger.error(prefix + "版本正确, 不过用酷Q Pro的话效果更好哦!");
            }
            catch (HttpException e)
            {
                if (e.getMessage().toLowerCase().contains("connection refused"))
                {
                    logger.error("HTTP发送地址验证失败, 已停止启动");
                    logger.error("- 请检查酷Q是否已经启动");
                    logger.error("- 请检查酷Q的接收端口是否和Picq的发送端口一样");
                    logger.error("- 请检查你的发送IP是不是写错了");
                    logger.error("- 如果是向外, 请检查这个主机有没有网络连接");
                }
                else
                {
                    logger.error("验证失败, HTTP发送错误: ");
                }
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 暂停机器人HTTP接收
     */
    public void httpPause()
    {
        httpSetPauseState(true);
    }

    /**
     * 继续机器人HTTP接收
     */
    public void httpResume()
    {
        httpSetPauseState(false);
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
     * 暂停机器人事件
     */
    public void eventsPause()
    {
        eventsSetPauseState(true);
    }

    /**
     * 继续机器人事件
     */
    public void eventsResume()
    {
        eventsSetPauseState(false);
    }

    /**
     * 设置机器人事件管理器暂停状态
     * @param isPaused 是否暂停
     */
    public void eventsSetPauseState(boolean isPaused)
    {
        eventManager.setPaused(isPaused);
    }

    /**
     * 启用指令系统
     * @param prefixes 前缀
     * @throws InstantiationException 反射失败
     * @throws IllegalAccessException 反射失败
     */
    public void enableCommandManager(String ... prefixes) throws InstantiationException, IllegalAccessException
    {
        enableCommandManager(true, prefixes);
    }

    /**
     * 启用指令系统
     * @param registerAllCommands 是否自动注册所有指令
     * @param prefixes 前缀
     * @throws InstantiationException 反射失败
     * @throws IllegalAccessException 反射失败
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
     * @throws VersionRecommendException 推荐版本
     * @throws InvalidSendingURLException 发送URL错误
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
