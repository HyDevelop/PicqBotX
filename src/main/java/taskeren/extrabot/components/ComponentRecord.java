package taskeren.extrabot.components;

import lombok.Getter;
import lombok.ToString;

/**
 * 语音组件。
 *
 * @author Taskeren
 */
@ToString
@Getter
public class ComponentRecord extends ComponentSendable
{
    /**
     * 语音储存在本地的位置
     */
    final String file;

    /**
     * 是否使用变声器
     */
    final boolean magic;

    /**
     * 构建一个内部语音组件
     *
     * @param file 语音本地地址
     * @param magic 是否启用变声器
     */
    protected ComponentRecord(String file, boolean magic)
    {
        this.file = file;
        this.magic = magic;
    }

    /**
     * 构造一个语音组件
     *
     * @param fileOrUrl 语音地址
     */
    public ComponentRecord(String fileOrUrl)
    {
        this(fileOrUrl, false);
    }

    @Override
    public String toCQCode()
    {
        return "[CQ:record,file=" + file + "]";
    }
}
