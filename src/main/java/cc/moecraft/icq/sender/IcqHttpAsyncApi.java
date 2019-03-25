package cc.moecraft.icq.sender;

import cc.moecraft.icq.PicqBotX;

/**
 * 此类由 Hykilpikonna 在 2018/19/24 创建!
 * Created by Hykilpikonna on 2018/19/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class IcqHttpAsyncApi extends HttpApiBase
{
    public IcqHttpAsyncApi(IcqHttpApi api, PicqBotX bot, String baseUrl, int port)
    {
        super(bot, baseUrl, port);
        super.selfId = api.selfId;
    }

    @Override
    public String makeUrl(String api)
    {
        return getBaseURL() + api + "_async";
    }
}
