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
     * @param str 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String toHex(String str)
    {
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
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
     * @param hexStr Byte字符串(Byte之间无分隔符 如:[616C6B])
     * @return String 对应的字符串
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

    public static String toBase32(String original)
    {
        return new Base32().encodeAsString(original.getBytes());
    }

    public static String fromBase32(String base32)
    {
        return new String(new Base32().decode(base32));
    }
}
