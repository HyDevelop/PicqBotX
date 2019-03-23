package cc.moecraft.icq.event.events.local;

/**
 * 机器人发送群消息事件
 *
 * @author Hykilpikonna
 */
public class EventLocalSendGroupMessage extends EventLocalSendMessage
{
    public EventLocalSendGroupMessage(long id, String message, boolean auto_escape)
    {
        super(id, message, auto_escape);
    }
}
