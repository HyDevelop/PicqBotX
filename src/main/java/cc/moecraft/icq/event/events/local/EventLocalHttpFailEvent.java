package cc.moecraft.icq.event.events.local;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * HTTP接受失败
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class EventLocalHttpFailEvent extends EventLocal
{
    public Reason fail; // 怎样失败的

    public enum Reason
    {
        unknown, requestIsEmpty,
        incorrectVersion, incorrectCharset, incorrectApplicationType,
        incorrectRequestMethod,
        socketCloseFailed
    }
}
