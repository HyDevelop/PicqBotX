package cc.moecraft.test.icq;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.test.icq.features.annoy.AnnoyingListener;
import cc.moecraft.test.icq.features.antirecall.AntiRecallListener;
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
    public static void main(String[] args)
    {
        // 创建机器人对象 ( 接收端口 )
        PicqBotX bot = new PicqBotX(31092);

        // 添加一个机器人账户 ( 名字, 发送URL, 发送端口 )
        bot.addAccount("Bot01", "127.0.0.1", 31091);

        // 启用HyExp ( 非必要 )
        bot.setUniversalHyExpSupport(true);

        // 设置异步
        bot.getConfig().setUseAsync(true);

        // 注册事件
        bot.getEventManager().registerListeners(
                new TestListener(),
                new RequestListener(),
                new AntiRecallListener(),
                new AnnoyingListener(),
                new ExceptionListener()); // 可以注册多个监听器

        // 在没有Debug的时候加上这个消息日志监听器
        if (!bot.getConfig().isDebug())
            bot.getEventManager().registerListener(new SimpleTextLoggingListener());

        // 启用指令管理器, 启用的时候会自动注册指令
        // 这些字符串是指令前缀, 比如指令"!help"的前缀就是"!"
        bot.enableCommandManager("bot -", "!", "/", "~", "！", "我以令咒命之，", "我以令咒命之, ", "test -");

        // Debug输出所有已注册的指令
        bot.getLogger().debug(bot.getCommandManager().getCommands().toString());

        // 启动机器人
        bot.startBot();
    }
}
