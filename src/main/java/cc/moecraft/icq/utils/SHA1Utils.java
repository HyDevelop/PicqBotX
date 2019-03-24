package cc.moecraft.icq.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Formatter;

/**
 * The class {@code SHA1Utils} is a utility class to generate SHA1 hashes
 * and validate them.
 * <p>
 * Class created by the HyDEV Team on 2019-03-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-24 16:38
 */
public class SHA1Utils
{
    /**
     * 生成 HAMC SHA1
     *
     * @param data 数据
     * @param key 秘钥
     * @return HASH
     */
    public static String generateHAMCSHA1(String data, String key)
    {
        try
        {
            // Get an hmac_sha1 key from the raw key bytes
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());

            //  Covert array of Hex bytes to a String
            return toHexString(rawHmac);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把Bytes转换为Hex字符串
     *
     * @param bytes Bytes
     * @return Hex字符串
     */
    private static String toHexString(byte[] bytes)
    {
        Formatter formatter = new Formatter();

        for (byte b : bytes)
        {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }
}
