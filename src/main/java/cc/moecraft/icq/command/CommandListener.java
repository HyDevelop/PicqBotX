package cc.moecraft.icq.command;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
    private Map<String, Thread> runningAsyncThreads = new LinkedHashMap<>();

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

        if (event.getBot().getConfig().isUseAsyncCommands())
        {
            Thread thread = new Thread(runnable, "Thread-" + System.currentTimeMillis());
            thread.start();
            runningAsyncThreads.put(thread.getName(), thread);
            runnable.setCallback(() -> runningAsyncThreads.remove(thread.getName()));
        }
        else
        {
            runnable.run();
        }
    }

    @Setter
    @Getter
    @RequiredArgsConstructor
    private class CommandRunnable implements Runnable
    {
        private final EventMessage event;
        private Runnable callback;

        @Override
        public void run()
        {
            try
            {
                commandManager.runCommand(event);
            }
            catch (Throwable e)
            {
                event.getBot().getEventManager().callError(event, e);
            }

            if (callback != null)
            {
                callback.run();
            }
        }
    }
}
