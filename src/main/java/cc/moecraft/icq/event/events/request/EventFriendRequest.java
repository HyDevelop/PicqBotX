package cc.moecraft.icq.event.events.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 加好友请求事件
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
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
