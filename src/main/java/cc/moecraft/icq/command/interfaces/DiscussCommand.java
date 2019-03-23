package cc.moecraft.icq.command.interfaces;

import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;

/**
 * 讨论组指令
 *
 * @author Hykilpikonna
 */
public interface DiscussCommand extends IcqCommand
{
    /**
     * 执行指令
     *
     * @param event 事件
     * @param sender 发送者的用户
     * @param discuss 讨论组
     * @param command 指令名 ( 不包含指令参数 )
     * @param args 指令参数 ( 不包含指令名 )
     * @return 发送回去的消息 ( 当然也可以手动发送然后返回空 )
     */
    String discussMessage(EventDiscussMessage event, GroupUser sender, Group discuss, String command, ArrayList<String> args);
}
