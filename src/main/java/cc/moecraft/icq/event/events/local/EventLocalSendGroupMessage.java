package cc.moecraft.icq.event.events.local;

import cc.moecraft.icq.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机器人发送群消息事件
 *
 * @author Hykilpikonna
 */
public class EventLocalSendGroupMessage extends EventLocalSendMessage
{
    public EventLocalSendGroupMessage(long id, String message, boolean auto_escape) {
        super(id, message, auto_escape);
    }
}
