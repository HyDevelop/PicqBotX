package cc.moecraft.icq.utils;

import org.apache.commons.codec.binary.Base32;

import java.io.UnsupportedEncodingException;

/**
 * 此类由 Hykilpikonna 在 2018/06/13 创建!
 * Created by Hykilpikonna on 2018/06/13!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class StringCodecUtils
{
    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    /**
     * 字符串转换成十六进制字符串
     * @param original 源字符串
     * @return HEX字符串
     */
    public static String toHex(String original)
    {
        StringBuilder sb = new StringBuilder("");
        byte[] bs = original.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++)
        {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(HEX_CHARS[bit]);
            bit = bs[i] & 0x0f;
            sb.append(HEX_CHARS[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 十六进制转换字符串
     * @param hexStr HEX字符串
     * @return 还原字符串
     */
    public static String fromHex(String hexStr)
    {
        hexStr = hexStr.replace(" ", "");

        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++)
        {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * 字符串转换成Base32
     * @param original 源字符串
     * @return Base32字符串
     */
    public static String toBase32(String original)
    {
        return new Base32().encodeAsString(original.getBytes());
    }

    /**
     * Base32转换回字符串
     * @param base32 Base32字符串
     * @return 还原字符串
     */
    public static String fromBase32(String base32)
    {
        return new String(new Base32().decode(base32));
    }

    /**
     * 字符串转换成ASCII串
     * @param original 源字符串
     * @return ASCII串
     */
    public static String toAscii(String original)
    {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = original.toCharArray();
        for (int i = 0; i < chars.length - 1; i++)
        {
            stringBuilder.append((int) chars[i]).append(" ");
        }
        stringBuilder.append((int) chars[chars.length - 1]);
        return stringBuilder.toString();
    }

    /**
     * ASCII串转字符串
     * @param ascii ASCII串 (空格分割)
     * @return 还原字符串
     */
    public static String fromAscii(String ascii)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String[] chars = ascii.split(" ");
        for (String aChar : chars)
        {
            stringBuilder.append((char) Integer.parseInt(aChar));
        }
        return stringBuilder.toString();
    }
}
