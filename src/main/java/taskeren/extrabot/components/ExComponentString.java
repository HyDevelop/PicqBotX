package taskeren.extrabot.components;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 文本消息，不是组件。
 *
 * @author Taskeren
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class ExComponentString extends ExComponent
{
    final String message;

    public String toString() {
        return message;
    }
}
