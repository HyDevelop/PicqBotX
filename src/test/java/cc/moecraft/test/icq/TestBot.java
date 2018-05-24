package cc.moecraft.test.icq;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.exceptions.HttpServerStartFailedException;

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
        PicqBotX bot = new PicqBotX(31092, true);
        try
        {
            bot.startBot();
        }
        catch (HttpServerStartFailedException e)
        {
            e.printStackTrace();
        }
    }
}
