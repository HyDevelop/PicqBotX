package taskeren.extrabot.components;

import lombok.Getter;
import lombok.ToString;

/**
 * 骰子组件。
 *
 * @author Taskeren
 */
@ToString
@Getter
public class ExComponentDice extends ExComponentSendable
{
    /**
     * 投到的点数
     */
    final int type;

    /**
     * 构造一个骰子组件。（数字随机）
     */
    public ExComponentDice()
    {
        this.type = 0;
    }

    /**
     * 构造一个内部骰子组件。
     *
     * @param type 数字
     */
    protected ExComponentDice(int type)
    {
        this.type = type;
    }

    @Override
    public String toCQCode()
    {
        return "[CQ:dice,type=1]";
    }
}
