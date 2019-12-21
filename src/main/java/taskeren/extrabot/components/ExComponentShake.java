package taskeren.extrabot.components;

import lombok.Getter;
import lombok.ToString;

/**
 * 戳一戳组件。
 *
 * @author Taskeren
 */
@ToString
@Getter
public class ExComponentShake extends ExComponentSendable
{
    public ExComponentShake()
    {
    }

    @Override
    public String toCQCode()
    {
        return "[CQ:shake]";
    }
}
