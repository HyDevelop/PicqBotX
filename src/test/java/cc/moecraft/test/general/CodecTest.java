package cc.moecraft.test.general;

import cc.moecraft.utils.StringCodecUtils;
import org.junit.Test;

/**
 * 此类由 Hykilpikonna 在 2018/06/15 创建!
 * Created by Hykilpikonna on 2018/06/15!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CodecTest
{
    @Test
    public void testAscii()
    {
        String original = "abc123 45中文";
        String ascii = StringCodecUtils.toAscii(original);
        String restored = StringCodecUtils.fromAscii(ascii);

        System.out.println(ascii);
        System.out.println(restored);

        if (!original.equals(restored)) throw new RuntimeException();
    }
}
