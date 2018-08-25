package cc.moecraft.icq.event.events.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 加好友请求事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class EventFriendRequest extends EventRequest
{
    @Override
    public void accept()
    {
        getHttpApi().setFriendAndRequest(flag, true);
    }

    @Override
    public void reject(String reason)
    {
        getHttpApi().setFriendAndRequest(flag, false);
    }
}
