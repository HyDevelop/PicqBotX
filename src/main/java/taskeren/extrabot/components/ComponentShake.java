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
public class ComponentShake extends ComponentSendable
{
    public ComponentShake()
    {
    }

    @Override
    public String toCQCode()
    {
        return "[CQ:shake]";
    }
}
