package cc.moecraft.test.icq;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.accounts.BotAccount;
import cc.moecraft.icq.exceptions.HttpServerStartFailedException;
import cc.moecraft.icq.exceptions.InvalidSendingURLException;
import cc.moecraft.icq.exceptions.VersionIncorrectException;
import cc.moecraft.test.icq.features.annoy.AnnoyingListener;
import cc.moecraft.test.icq.features.antirecall.AntiRecallListener;
import cc.moecraft.test.icq.listeners.ExceptionListener;
import cc.moecraft.test.icq.listeners.RequestListener;
import cc.moecraft.test.icq.listeners.SimpleTextLoggingListener;
import cc.moecraft.test.icq.listeners.TestListener;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class TestBot
{
    public static void main(String[] args)
    {
        // 创建机器人对象 ( 信息发送URL, 发送端口, 接收端口, 是否DEBUG )
        PicqBotX bot = new PicqBotX(31092, false);

        bot.getAccountManager().addAccount(
                new BotAccount("One", bot.getEventManager(), "127.0.0.1", 31091),
                new BotAccount("Two", bot.getEventManager(), "127.0.0.1", 31090)
        );

        bot.setMaintenanceMode(false);

        // 设置异步
        bot.setUseAsync(true);

        try
        {
            bot.getEventManager()
                    .registerListener(new TestListener()) // 注册监听器
                    .registerListener(new RequestListener())
                    .registerListener(new AntiRecallListener())
                    .registerListener(new AnnoyingListener())
                    .registerListener(new ExceptionListener()); // 可以注册多个监听器
            if (!bot.isDebug()) bot.getEventManager().registerListener(new SimpleTextLoggingListener()); // 这个只是在不开Debug的时候用来Log消息的

            // 启用指令管理器, 启用的时候会自动注册指令
            // 这些字符串是指令前缀, 比如!help的前缀就是!
            bot.enableCommandManager("bot -", "!", "/", "~", "！", "我以令咒命之，", "我以令咒命之, ");

            bot.startBot(); // 启动机器人
        }
        catch (HttpServerStartFailedException | IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace(); // 启动失败, 结束程序
        }
    }
}
