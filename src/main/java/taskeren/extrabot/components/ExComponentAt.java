package taskeren.extrabot.components;

import lombok.Getter;
import lombok.ToString;

/**
 * 群内AT组件。
 *
 * @author Taskeren
 */
@ToString
@Getter
public class ExComponentAt extends ExComponentSendable
{
    /**
     * 被AT的QQ号
     */
    protected final long at;

    /**
     * 构建一个AT组件
     *
     * @param at 被AT的QQ号
     */
    public ExComponentAt(long at)
    {
        this.at = at;
    }

    @Override
    public String toCQCode()
    {
        return "[CQ:at,qq=" + at + "]";
    }
}
