package taskeren.extrabot.components;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 图片组件（包括自定义表情）。
 *
 * @author Taskeren
 */
@ToString
@Getter
public class ComponentImage extends ComponentSendable
{

    /**
     * 图片暂存于腾讯服务器上的地址
     */
    final String url;

    /**
     * 图片下载到本地的地址
     */
    final String file;

    @Setter
    boolean noCache;

    /**
     * 构造一个内部图片组件
     *
     * @param url 图片在线地址
     * @param file 图片离线地址
     */
    protected ComponentImage(String url, String file)
    {
        this.url = url;
        this.file = file;
    }

    /**
     * 构造一个图片组件
     *
     * @param fileOrUrl 图片地址
     */
    public ComponentImage(String fileOrUrl)
    {
        this(fileOrUrl, false);
    }

    /**
     * 构造一个图片组件
     *
     * @param fileOrUrlOrB64 图片或B64编码图片地址
     * @param isBase64 是否是B64编码图片
     */
    public ComponentImage(String fileOrUrlOrB64, boolean isBase64)
    {
        this.file = isBase64 ? "base64://" + fileOrUrlOrB64 : fileOrUrlOrB64;
        this.url = null;
    }

    @Override
    public String toCQCode()
    {
        return "[CQ:image," + (noCache ? "cache=0," : "") + "file=" + file + "]";
    }
}
