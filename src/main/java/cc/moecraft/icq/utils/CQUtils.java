package cc.moecraft.icq.utils;

/**
 * 此类由 Hykilpikonna 在 2018/06/13 创建!
 * Created by Hykilpikonna on 2018/06/13!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CQUtils
{
    public static String decodeMessage(String rawMessage)
    {
        return rawMessage
                .replace("&amp;", "&")
                .replace("&#91;", "[")
                .replace("&#93;", "]")
                .replace("&#44;", ",");
    }

    public static String removeCqCode(String original)
    {
        return original.replaceAll("\\[CQ:.*?]", "");
    }
}
