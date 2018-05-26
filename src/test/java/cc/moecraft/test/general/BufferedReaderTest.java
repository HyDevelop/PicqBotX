package cc.moecraft.test.general;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class BufferedReaderTest
{
    public static void main(String[] args) throws IOException
    {
        String original = "123qwer中文";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(original.getBytes()), "UTF-8"));

        int contentLength = 12;
        String data = "UNINITIALIZED";
        byte[] buffer;
        int size = 0;

        if (contentLength != 0)
        {
            buffer = new byte[contentLength];
            while(size < contentLength) buffer[size++] = (byte) reader.read();
            data = new String(buffer, 0, size);
        }

        System.out.println(data);
    }
}
