package cc.moecraft.icq;

import cc.moecraft.logger.environments.ColorSupportLevel;
import lombok.Builder;
import lombok.Data;

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
@Builder(toBuilder = true)
public class PicqConfig
{
    /** 接收端口 */
    private final int socketPort;

    /** 是否输出Debug消息 */
    private boolean debug;

    /** 是否跳过酷Q版本验证 (不推荐) */
    private boolean noVerify;

    /** 是否启用异步 */
    private boolean useAsync;

    /** 是否启用维护模式 */
    private boolean maintenanceMode;

    /** 是否开启多账号优化 */
    private boolean multiAccountOptimizations;

    /** 是否暂停事件 */
    private boolean eventPaused;

    /** 是否暂停HTTP接收 */
    private boolean httpPaused;

    // 一次配置项 | Unchangeable config fields
    // 这里的配置init之后就不能变了w

    /** Logger颜色支持级别 */
    private ColorSupportLevel colorSupportLevel;

    /** Logger日志路径 */
    private String logPath;

    /** Logger日志文件名 */
    private String logFileName;

    /**
     * 全参数构造器
     *
     * @param socketPort 接收端口
     * @param debug 是否输出Debug消息
     * @param noVerify 是否跳过酷Q版本验证 (不推荐)
     * @param useAsync 是否启用异步
     * @param maintenanceMode 是否启用维护模式
     * @param multiAccountOptimizations 是否开启多账号优化
     * @param eventPaused 是否暂停事件
     * @param httpPaused 是否暂停HTTP接收
     * @param colorSupportLevel Logger颜色支持级别
     * @param logPath Logger日志路径
     * @param logFileName Logger日志文件名
     */
    public PicqConfig(int socketPort, boolean debug, boolean noVerify, boolean useAsync, boolean maintenanceMode,
                      boolean multiAccountOptimizations, boolean eventPaused, boolean httpPaused,
                      ColorSupportLevel colorSupportLevel, String logPath, String logFileName)
    {
        this.socketPort = socketPort;
        this.debug = debug;
        this.noVerify = noVerify;
        this.useAsync = useAsync;
        this.maintenanceMode = maintenanceMode;
        this.multiAccountOptimizations = multiAccountOptimizations;
        this.eventPaused = eventPaused;
        this.httpPaused = httpPaused;

        this.colorSupportLevel = colorSupportLevel;
        this.logPath = logPath;
        this.logFileName = logFileName;
    }

    /**
     * 必要参数构造器
     *
     * @param socketPort 接收端口
     */
    public PicqConfig(int socketPort)
    {
        this.socketPort = socketPort;
        this.debug = true; //TODO: change this to false
        this.noVerify = false;
        this.useAsync = false;
        this.maintenanceMode = false;
        this.multiAccountOptimizations = true;
        this.eventPaused = false;
        this.httpPaused = false;
        
        this.colorSupportLevel = FORCED;
        this.logPath = "logs";
        this.logFileName = "PicqBotX-Log";
    }
}
