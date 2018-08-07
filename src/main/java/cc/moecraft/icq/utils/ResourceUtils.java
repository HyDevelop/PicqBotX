package cc.moecraft.icq.utils;

import cc.moecraft.logger.HyLogger;
import cc.moecraft.logger.format.AnsiFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 此类由 Hykilpikonna 在 2018/07/27 创建!
 * Created by Hykilpikonna on 2018/07/27!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class ResourceUtils
{
    public static void logResource(Class resourceClass, HyLogger logger, String name, String... variablesAndReplacements)
    {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceClass.getClassLoader().getResourceAsStream(name))))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                for (int i = 0; i < variablesAndReplacements.length; i += 2)
                {
                    line = line.replace("%{" + variablesAndReplacements[i] + "}", variablesAndReplacements[i + 1]);
                }

                line = AnsiFormat.replaceAllFormatWithANSI(line);

                logger.log(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
