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
        UNKNOWN, REQUEST_IS_EMPTY,
        INCORRECT_VERSION, INCORRECT_CHARSET, INCORRECT_APPLICATION_TYPE,
        INCORRECT_REQUEST_METHOD,
        SOCKET_CLOSE_FAILED
    }
}
