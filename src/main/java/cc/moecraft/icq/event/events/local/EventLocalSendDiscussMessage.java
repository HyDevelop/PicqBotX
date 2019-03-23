package cc.moecraft.icq.event.events.local;

/**
 * 机器人发送讨论组消息事件
 *
 * @author Hykilpikonna
 */
public class EventLocalSendDiscussMessage extends EventLocalSendMessage
{
    public EventLocalSendDiscussMessage(long id, String message, boolean auto_escape)
    {
        super(id, message, auto_escape);
    }
}
