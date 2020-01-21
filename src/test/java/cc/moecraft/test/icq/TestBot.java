package cc.moecraft.test.icq;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.moecraft.icq.command.interfaces.IcqCommand;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.test.icq.commands.*;
import cc.moecraft.test.icq.features.annoy.AnnoyingListener;
import cc.moecraft.test.icq.features.annoy.CommandAnnoy;
import cc.moecraft.test.icq.features.antirecall.AntiRecallListener;
import cc.moecraft.test.icq.features.antirecall.CommandAntiRecall;
import cc.moecraft.test.icq.features.antirecall.CommandAntiRecallOut;
import cc.moecraft.test.icq.features.codec.CommandDecode;
import cc.moecraft.test.icq.features.codec.CommandEncode;
import cc.moecraft.test.icq.features.say.CommandSay;
import cc.moecraft.test.icq.features.say.CommandSayRaw;
import cc.moecraft.test.icq.listeners.ExceptionListener;
import cc.moecraft.test.icq.listeners.RequestListener;
import cc.moecraft.test.icq.listeners.SimpleTextLoggingListener;
import cc.moecraft.test.icq.listeners.TestListener;

/**
 * 功能测试机器人!
 * <p>
 * Class created by the HyDEV Team on 2019-03-21!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-21 20:26
 */
public class TestBot
{
    /**
     * 要注册的指令
     */
    private static IcqCommand[] commands = new IcqCommand[]{
            new CommandBanSelf(),
            new CommandCls(),
            new CommandCmdList(),
            new CommandKickSelf(),
            new CommandRecallThis(),
            new CommandTest(),
            new CommandVersion(),
            new CommandAnnoy(),
            new CommandAntiRecall(),
            new CommandAntiRecallOut(),
            new CommandEncode(),
            new CommandDecode(),
            new CommandSay(),
            new CommandSayRaw(),

            new CommandJS() // TODO: 做权限管理

            , new CommandTestSleep()
    };

    /**
     * 要注册的监听器
     */
    private static IcqListener[] listeners = new IcqListener[]{
            new TestListener(),
            new RequestListener(),
            new AntiRecallListener(),
            new AnnoyingListener(),
            new ExceptionListener()
    };

    public static void main(String[] args)
    {
        // 创建机器人对象 ( 传入配置 )
        PicqBotX bot = new PicqBotX(new PicqConfig(31092)
                .setDebug(true)
                .setSecret("This is secret")
                .setAccessToken("Brq4KSm+3UdaUJnLZ+AJfj**v-vePWL$")
        );

        // 添加一个机器人账户 ( 名字, 发送URL, 发送端口 )
        bot.addAccount("Bot00", "127.0.0.1", 31090);
        bot.addAccount("Bot01", "127.0.0.1", 31091);

        // 启用HyExp ( 非必要 )
        bot.setUniversalHyExpSupport(true);

        // 设置异步
        bot.getConfig().setUseAsyncCommands(true);

        // 注册事件监听器, 可以注册多个监听器
        bot.getEventManager().registerListeners(listeners);

        // 在没有Debug的时候加上这个消息日志监听器
        if (!bot.getConfig().isDebug())
            bot.getEventManager().registerListener(new SimpleTextLoggingListener());

        // 启用指令管理器
        // 这些字符串是指令前缀, 比如指令"!help"的前缀就是"!"
        bot.enableCommandManager("bot -", "!", "/", "~", "！", "我以令咒命之，", "我以令咒命之, ", "test -");

        // 注册指令
        // 从 v3.0.1.730 之后不会自动注册指令了, 因为效率太低 (≈4000ms), 而且在其他框架上有Bug
        bot.getCommandManager().registerCommands(commands);

        // Debug输出所有已注册的指令
        bot.getLogger().debug(bot.getCommandManager().getCommands().toString());

        // 启动机器人, 不会占用主线程
        bot.startBot();
    }
}