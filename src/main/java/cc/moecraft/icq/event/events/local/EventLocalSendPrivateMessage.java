package cc.moecraft.icq.event.events.local;

/**
 * 机器人发送私聊消息事件
 *
 * @author Hykilpikonna
 */
public class EventLocalSendPrivateMessage extends EventLocalSendMessage
{
    public EventLocalSendPrivateMessage(long id, String message, boolean auto_escape)
    {
        super(id, message, auto_escape);
    }
}
