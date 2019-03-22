package cc.moecraft.icq;

import cc.moecraft.icq.accounts.AccountManager;
import cc.moecraft.icq.accounts.AccountManagerListener;
import cc.moecraft.icq.accounts.BotAccount;
import cc.moecraft.icq.command.CommandListener;
import cc.moecraft.icq.command.CommandManager;
import cc.moecraft.icq.event.EventManager;
import cc.moecraft.icq.exceptions.VerifyFailedException;
import cc.moecraft.icq.listeners.HyExpressionListener;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RVersionInfo;
import cc.moecraft.icq.user.GroupManager;
import cc.moecraft.icq.user.GroupUserManager;
import cc.moecraft.icq.user.UserManager;
import cc.moecraft.logger.HyLogger;
import cc.moecraft.logger.LoggerInstanceManager;
import cc.moecraft.logger.environments.ConsoleColoredEnv;
import cc.moecraft.logger.environments.ConsoleEnv;
import cc.moecraft.logger.environments.FileEnv;
import cc.moecraft.logger.format.AnsiColor;
import cc.moecraft.utils.HyExpressionResolver;
import cc.moecraft.utils.ThreadUtils;
import cc.moecraft.utils.cli.ResourceUtils;
import cn.hutool.http.HttpException;
import lombok.Getter;
import lombok.Setter;

import static cc.moecraft.icq.PicqConstants.HTTP_API_VERSION_DETECTION;
import static cc.moecraft.icq.PicqConstants.VERSION;
import static cc.moecraft.icq.utils.MiscUtils.logInitDone;
import static cc.moecraft.logger.format.AnsiColor.GREEN;
import static cc.moecraft.logger.format.AnsiFormat.replaceAllFormatWithANSI;

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
    /**
     * HTTP监听服务器
     */
    @Getter
    private HttpServer httpServer;

    /**
     * 事件管理器
     */
    @Getter
    private EventManager eventManager;

    /**
     * 机器人账号管理器
     */
    @Getter
    private AccountManager accountManager;

    /**
     * 用户对象缓存管理器
     */
    @Getter
    private UserManager userManager;

    /**
     * 群对象缓存管理器
     */
    @Getter
    private GroupManager groupManager;

    /**
     * 群用户对象缓存管理器
     */
    @Getter
    private GroupUserManager groupUserManager;

    /**
     * 指令管理器
     */
    @Getter
    private CommandManager commandManager;

    /**
     * Logger实例管理器
     */
    @Getter
    private LoggerInstanceManager loggerInstanceManager;

    /**
     * Logger
     */
    @Getter
    private HyLogger logger;

    /**
     * 全局替换HyExp表达式 (如果是null就不替换)
     */
    @Getter
    private HyExpressionResolver hyExpressionResolver = null;

    /**
     * 是否启用维护模式
     */
    @Getter @Setter
    private boolean maintenanceMode = false;

    /**
     * 是否开启多账号优化
     */
    @Getter @Setter
    private boolean multiAccountOptimizations = true; // 多账号优化

    /**
     * Picq配置 | Picq configuration
     */
    @Getter
    private final PicqConfig config;

    /**
     * 构造器
     *
     * @param config Picq配置
     */
    public PicqBotX(PicqConfig config)
    {
        this.config = config;
    }

    private void init()
    {
        loggerInstanceManager = new LoggerInstanceManager(new FileEnv(config.getLogPath(), config.getLogFileName()));

        if (config.getColorSupportLevel() == null) loggerInstanceManager.addEnvironment(new ConsoleEnv());
        else loggerInstanceManager.addEnvironment(new ConsoleColoredEnv(config.getColorSupportLevel()));

        logger = loggerInstanceManager.getLoggerInstance("PicqBotX", config.isDebug());
        logger.timing.init();

        logResource(logger, config.getColorSupportLevel() == null ? "splash" : "splash-precolored", "version", VERSION);

        logInitDone(logger, "日志管理器     ", 0, 6);

        userManager = new UserManager(this);
        groupUserManager = new GroupUserManager(this);
        groupManager = new GroupManager(this);
        logInitDone(logger, "缓存管理器     ", 1, 5);

        // setDebug(debug);
        logInitDone(logger, "DEBUG设置      ", 2, 4);

        eventManager = new EventManager(this);
        eventManager.registerListener(new HyExpressionListener());
        logInitDone(logger, "事件管理器     ", 3, 3);

        accountManager = new AccountManager();
        eventManager.registerListener(new AccountManagerListener(accountManager));
        logInitDone(logger, "账号管理器     ", 4, 2);

        httpServer = new HttpServer(config.getSocketPort(), this);
        logInitDone(logger, "HTTP监听服务器 ", 5, 1);

        logger.timing.clear();
    }

    /**
     * 添加机器人账号
     *
     * @param postUrl 发送URL (Eg. 127.0.0.1)
     * @param postPort 发送端口 (Eg. 31091)
     */
    public void addAccount(String postUrl, int postPort)
    {
        try
        {
            this.accountManager.addAccount(new BotAccount("Main", eventManager, postUrl, postPort));
        }
        catch (HttpException e)
        {
            logger.error("HTTP发送错误: " + e.getLocalizedMessage());
            logger.error("- 检查一下是不是忘记开酷Q了, 或者写错地址了");
            ThreadUtils.safeSleep(5);
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动机器人
     */
    public void startBot()
    {
        if (!verifyHttpPluginVersion())
        {
            logger.error("验证失败, 请检查上面的错误信息再重试启动服务器.");
            throw new VerifyFailedException();
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
        logInitDone(logger, "指令管理器     ", 6, 0);

        logger.timing.clear();
    }

    /**
     * 验证HTTP插件版本
     * @return 是否通过验证
     */
    public boolean verifyHttpPluginVersion()
    {
        if (config.isNoVerify()) return true;

        for (BotAccount botAccount : accountManager.getAccounts())
        {
            String prefix =  "账号 " + botAccount.getName() + ": ";

            try
            {
                RVersionInfo versionInfo = botAccount.getHttpApi().getVersionInfo().getData();

                if (!versionInfo.getPluginVersion().matches(HTTP_API_VERSION_DETECTION))
                {
                    logger.error(prefix + "HTTP插件版本不正确, 已停止启动");
                    logger.error("- 当前版本: " + versionInfo.getPluginVersion());
                    logger.error("- 兼容的版本: " + HTTP_API_VERSION_DETECTION);
                    return false;
                }

                if (!versionInfo.getCoolqEdition().equalsIgnoreCase("pro"))
                    logger.error(prefix + "版本正确, 不过用酷Q Pro的话效果更好哦!");
            }
            catch (HttpException e)
            {
                if (e.getMessage().toLowerCase().contains("connection"))
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
            logger.log(AnsiColor.YELLOW + prefix + AnsiColor.GREEN + "  版本验证完成!");
        }
        return true;
    }

    /**
     * 设置是否替换HyExp表达式
     * @param value 是否替换
     */
    public void setUniversalHyExpSupport(boolean value)
    {
        setUniversalHyExpSupport(value, true);
    }

    /**
     * 设置是否替换HyExp表达式
     * @param value 是否替换
     * @param safeMode 是否安全模式 (推荐是)
     */
    public void setUniversalHyExpSupport(boolean value, boolean safeMode)
    {
        hyExpressionResolver = value ? new HyExpressionResolver(safeMode) : null;
    }

    private void logResource(HyLogger logger, String name, Object... variablesAndReplacements)
    {
        ResourceUtils.printResource(getClass().getClassLoader(), s -> logger.log(replaceAllFormatWithANSI(s)),
                name, variablesAndReplacements);
    }
}
