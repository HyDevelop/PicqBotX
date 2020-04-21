package cc.moecraft.icq.command;

import cc.moecraft.icq.command.exceptions.CommandNotFoundException;
import cc.moecraft.icq.command.exceptions.NotACommandException;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

import static cc.moecraft.icq.command.CommandArgsParser.parse;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@RequiredArgsConstructor
public class CommandListener
{
    private final CommandManager commandManager;

    @Getter
    private Map<String, Thread> runningAsyncThreads = new LinkedHashMap<>();

    /**
     * 检查并运行指令
     *
     * @param event 事件
     * @return 是否继续执行后续事件
     */
    public boolean check(EventMessage event)
    {
        try
        {
            // 获取Args
            CommandArgs args = parse(event.getBot().getCommandManager(), event.getMessage(),
                event instanceof EventGroupMessage || event instanceof EventDiscussMessage);
            CommandRunnable runnable = new CommandRunnable(event, args);

            // 取消后续事件
            if (!event.getBot().getConfig().isCommandsAlsoCallEvents())
            {
                return false;
            }

            // 运行指令
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
        catch (NotACommandException | CommandNotFoundException e)
        {
            // 不是指令
        }

        return true;
    }

    @Setter
    @Getter
    @RequiredArgsConstructor
    private class CommandRunnable implements Runnable
    {
        private final EventMessage event;
        private final CommandArgs args;
        private Runnable callback;

        @Override
        public void run()
        {
            try
            {
                commandManager.runCommand(event, args);
            }
            catch (Throwable e)
            {
                event.getBot().getEventManager().callError(event, e);
                event.getBot().getConfig().getCommandErrorHandler().accept(e);
            }

            if (callback != null)
            {
                callback.run();
            }
        }
    }
}
