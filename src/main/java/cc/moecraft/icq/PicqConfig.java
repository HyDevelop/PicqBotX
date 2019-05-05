package cc.moecraft.icq;

import cc.moecraft.logger.environments.ColorSupportLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import static cc.moecraft.logger.environments.ColorSupportLevel.FORCED;

/**
 * The class {@code PicqConfig} stores the configuration for
 * {@code PicqBotX} in an object oriented way.
 * <p>
 * Construct an object with the toBuilder() method:
 * {@code new PicqConfig().toBuilder().build();}
 * ( https://www.baeldung.com/lombok-builder-default-value )
 * <p>
 * Class created by the HyDEV Team on 2019-03-21!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-21 18:40
 */
@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class PicqConfig
{
    /** 接收端口 */
    private final int socketPort;

    /** 是否输出Debug消息 */
    private boolean debug = false;

    /** 是否跳过酷Q版本验证 (不推荐) */
    private boolean noVerify = false;

    /** 是否异步执行指令 */
    private boolean useAsyncCommands = true;

    /** 是否启用维护模式 */
    private boolean maintenanceMode = false;

    /** 维护模式回复 (设为空就不会回复了) */
    private String maintenanceResponse = "- 机器人正在维护 -";

    /** 是否自动判断是否开启多账号优化 */
    private boolean autoMultiAccountOptimizations = true;

    /** 上面那个是false的时候是否开启多账号优化 */
    private boolean multiAccountOptimizations = true;

    /** 是否暂停事件 */
    private boolean eventPaused = false;

    /** 是否暂停HTTP接收 */
    private boolean httpPaused = false;

    /** X-Signature SHA1 验证秘钥 (设置为空就是不用秘钥w) */
    private String secret = "";

    /** Access Token 访问令牌 (设置为空就是不用令牌) */
    private String accessToken = "";

    /** 是否启用限速调用API (需要enable_rate_limited_actions=true) */
    private boolean apiRateLimited = false;

    /** 是否异步调用API */
    private boolean apiAsync = false;

    /** 解析指令的时候用来分割参数的正则 */
    private String commandArgsSplitRegex = " ";

    // 一次配置项 | Unchangeable config fields
    // 这里的配置init之后就不能变了w

    /** Logger颜色支持级别 */
    private ColorSupportLevel colorSupportLevel = FORCED;

    /** Logger日志路径 (设为空就不输出文件了) */
    private String logPath = "logs";

    /** Logger日志文件名 */
    private String logFileName = "PicqBotX-Log";

    // 方法 | Methods

    /**
     * @param bot 机器人对象
     * @return 是否开启多账号优化
     */
    public boolean isMultiAccountOptimizations(PicqBotX bot)
    {
        return !autoMultiAccountOptimizations ? multiAccountOptimizations :
                bot.getAccountManager().getAccounts().size() > 1;
    }
}
