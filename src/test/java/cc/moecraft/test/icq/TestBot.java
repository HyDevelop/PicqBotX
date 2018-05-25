package cc.moecraft.test.icq;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.exceptions.HttpServerStartFailedException;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class TestBot
{
    public static void main(String[] args)
    {
        PicqBotX bot = new PicqBotX("127.0.0.1", 31091, 31092, true);
        try
        {
            bot.getEventManager().registerListener(new TestListener());
            bot.startBot();
        }
        catch (HttpServerStartFailedException e)
        {
            e.printStackTrace();
        }
    }
}
