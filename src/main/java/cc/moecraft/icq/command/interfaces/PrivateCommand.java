package cc.moecraft.icq.command.interfaces;

import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

/**
 * 私聊指令
 *
 * @author Hykilpikonna
 */
public interface PrivateCommand extends IcqCommand
{
    /**
     * 执行指令
     *
     * @param event 事件
     * @param sender 发送者的用户
     * @param command 指令名 ( 不包含指令参数 )
     * @param args 指令参数 ( 不包含指令名 )
     * @return 发送回去的消息 ( 当然也可以手动发送然后返回空 )
     */
    String privateMessage(EventPrivateMessage event, User sender, String command, ArrayList<String> args);
}
