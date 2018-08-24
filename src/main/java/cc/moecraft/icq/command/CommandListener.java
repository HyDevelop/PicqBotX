package cc.moecraft.icq.command;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@RequiredArgsConstructor
public class CommandListener extends IcqListener
{
    private final CommandManager commandManager;

    @Getter
    private Map<String, CommandRunnable> runningThreads = new LinkedHashMap<>();

    @EventHandler
    public void onPrivateMessage(EventPrivateMessage event)
    {
        run(event);
    }

    @EventHandler
    public void onGroupMessage(EventGroupMessage event)
    {
        run(event);
    }

    @EventHandler
    public void onDiscussMessage(EventDiscussMessage event)
    {
        run(event);
    }

    private void run(EventMessage event)
    {
        CommandRunnable runnable = new CommandRunnable(event);

        if (event.getBot().isUseAsync()) runnable.runAsync();
        else runnable.run();
    }

    @RequiredArgsConstructor
    private class CommandRunnable implements Runnable
    {
        private Thread thread;
        private final EventMessage event;

        @Override
        public void run()
        {
            runningThreads.put(thread.getName(), this);
            commandManager.runCommand(event);
            runningThreads.remove(thread.getName());
        }

        public void runAsync()
        {
            thread = new Thread(this, "Thread-" + System.currentTimeMillis());
            thread.start();
        }
    }
}
