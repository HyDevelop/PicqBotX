package cc.moecraft.icq.utils;

/**
 * The class {@code StringUtils} is an utility class for string
 * processing in PicqBotX.
 * <p>
 * Class created by the HyDEV Team on 2019-03-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-24 13:35
 */
public class StringUtils
{
    /**
     * 移除前面的空格
     *
     * @param original 源字符串
     * @return 移除后的字符串
     */
    public static String removeStartingSpace(String original)
    {
        while (original.startsWith(" "))
        {
            original = original.substring(1);
        }

        return original;
    }
}
