package taskeren.extrabot.components;

import lombok.Getter;
import lombok.ToString;

/**
 * 自带表情组件。
 *
 * @author Taskeren
 */
@ToString
@Getter
public class ExComponentFace extends ExComponentSendable
{
    /**
     * 表情ID
     */
    final int id;

    /**
     * 构造一个表情组件
     *
     * @param id 表情ID
     */
    public ExComponentFace(int id)
    {
        this.id = id;
    }

    @Override
    public String toCQCode()
    {
        return "[CQ:face,id=" + id + "]";
    }
}
