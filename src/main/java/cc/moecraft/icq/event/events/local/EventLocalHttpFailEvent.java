package cc.moecraft.icq.event.events.local;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

/**
 * HTTP接受失败
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class EventLocalHttpFailEvent extends EventLocal
{
    public FailType fail; // 怎样失败的

    public enum FailType
    {
        unknown, requestIsEmpty,
        incorrectVersion, incorrectCharset, incorrectApplicationType,
        incorrectRequestMethod,
    }
}
