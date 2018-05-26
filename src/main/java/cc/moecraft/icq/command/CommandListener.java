package cc.moecraft.icq.command;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import lombok.AllArgsConstructor;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@AllArgsConstructor
public class CommandListener extends IcqListener
{
    private CommandManager commandManager;

    @EventHandler
    public void onPrivateMessage(EventPrivateMessage event)
    {
        commandManager.runCommand(event);
    }

    @EventHandler
    public void onGroupMessage(EventGroupMessage event)
    {
        commandManager.runCommand(event);
    }

    @EventHandler
    public void onDiscussMessage(EventDiscussMessage event)
    {
        commandManager.runCommand(event);
    }
}
