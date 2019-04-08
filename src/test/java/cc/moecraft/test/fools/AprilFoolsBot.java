package cc.moecraft.test.fools;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.moecraft.icq.command.interfaces.IcqCommand;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.test.fools.commands.*;
import cc.moecraft.test.icq.features.say.CommandSay;
import cc.moecraft.test.icq.listeners.SimpleTextLoggingListener;

/**
 * TODO: No description yet...
 * <p>
 * Class created by the HyDEV Team on 2019-03-31!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-31 22:24
 */
public class AprilFoolsBot
{
    /**
     * 要注册的指令
     */
    private static IcqCommand[] commands = new IcqCommand[]{
            new CommandSay(),
            new CommandAnnounce(),
            new CommandHelp(),
            new CommandRanking(),
            new CommandRecent(),
            new CommandSetid(),
            new CommandSkills(),
            new CommandStats()
    };

    /**
     * 要注册的监听器
     */
    private static IcqListener[] listeners = new IcqListener[]{

    };

    public static void main(String[] args)
    {
        // 创建机器人对象 ( 传入配置 )
        PicqBotX bot = new PicqBotX(new PicqConfig(31092)
                .setDebug(false)
                .setUseAsyncCommands(true)
                .setSecret("This is secret")
                .setAccessToken("Brq4KSm+3UdaUJnLZ+AJfj**v-vePWL$")
        );

        bot.setUniversalHyExpSupport(true);

        // 添加一个机器人账户 ( 名字, 发送URL, 发送端口 )
        bot.addAccount("Bot01", "127.0.0.1", 31091);

        // 注册事件监听器, 可以注册多个监听器
        bot.getEventManager().registerListeners(listeners);

        // 在没有Debug的时候加上这个消息日志监听器
        if (!bot.getConfig().isDebug())
            bot.getEventManager().registerListener(new SimpleTextLoggingListener());

        // 启用指令管理器
        // 这些字符串是指令前缀, 比如指令"!help"的前缀就是"!"
        bot.enableCommandManager("bot -", "!", "/", "~", "！", "我以令咒命之，", "我以令咒命之, ", "test -", "-");

        // 注册指令
        // 从 v3.0.1.730 之后不会自动注册指令了, 因为效率太低 (≈4000ms), 而且在其他框架上有Bug
        bot.getCommandManager().registerCommands(commands);

        // Debug输出所有已注册的指令
        bot.getLogger().debug(bot.getCommandManager().getCommands().toString());

        // 启动机器人, 不会占用主线程
        bot.startBot();
    }
}
