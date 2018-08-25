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
        getBot().getHttpApi().setFriendAndRequest(flag, true);
    }

    @Override
    public void reject(String reason)
    {
        getBot().getHttpApi().setFriendAndRequest(flag, false);
    }
}
