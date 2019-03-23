package cc.moecraft.icq.command.interfaces;

import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;

/**
 * 群聊指令
 *
 * @author Hykilpikonna
 */
public interface GroupCommand extends IcqCommand
{
    /**
     * 执行指令
     *
     * @param event 事件
     * @param sender 发送者的用户
     * @param group 群
     * @param command 指令名 ( 不包含指令参数 )
     * @param args 指令参数 ( 不包含指令名 )
     * @return 发送回去的消息 ( 当然也可以手动发送然后返回空 )
     */
    String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args);
}
