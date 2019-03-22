package cc.moecraft.icq;

import cc.moecraft.logger.environments.ColorSupportLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import static cc.moecraft.logger.environments.ColorSupportLevel.FORCED;

/**
 * The class {@code PicqConfig} stores the configuration for
 * {@code PicqBotX} in an object oriented way.
 * <p>
 * Class created by the HyDEV Team on 2019-03-21!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-21 18:40
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PicqConfig
{
    // 主要配置项 | Main config fields

    /**
     * 是否输出Debug消息
     */
    private boolean debug;

    /**
     * 是否跳过酷Q版本验证 (不推荐)
     */
    private boolean noVerify = false;

    /**
     * 是否启用异步
     */
    private boolean useAsync = false;


    // 一次配置项 | Unchangeable config fields
    // 这里的配置init之后就不能变了w

    /**
     * 接收端口
     */
    private final int socketPort;

    /**
     * Logger颜色支持级别
     */
    private ColorSupportLevel colorSupportLevel = FORCED;

    /**
     * Logger日志路径
     */
    private String logPath = "logs";

    /**
     * Logger日志文件名
     */
    private String logFileName = "PicqBotX-Log";


    // 事件管理器配置 | Event manager config fields

    /**
     * 是否暂停事件
     */
    private boolean eventPaused = false;

    // HTTP服务器配置 | Http server config fields

    /**
     * 是否暂停HTTP接收
     */
    private boolean httpPaused = false;

}
