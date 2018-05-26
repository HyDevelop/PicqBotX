package cc.moecraft.icq.event.events.local;

import cc.moecraft.icq.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * 从机器人发送消息事件
 * 这里的消息可以改的, 所有监听器执行完之后才会发送
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EventLocalSendMessage extends Event
{
    public long id;
    public String message;
    public boolean autoEscape = false;

    public EventLocalSendMessage(long id, String message)
    {
        this(id, message, false);
    }

    public EventLocalSendMessage(long id, String message, boolean autoEscape)
    {
        setId(id); setMessage(message); setAutoEscape(autoEscape);
    }
}
