package cc.moecraft.test.icq.features.codec;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ArrayUtils;

import java.util.ArrayList;

import static cc.moecraft.test.icq.features.codec.CommandEncode.MESSAGE_POSSIBLE_TYPES;
import static cc.moecraft.utils.StringCodecUtils.*;

/**
 * 此类由 Hykilpikonna 在 2018/06/13 创建!
 * Created by Hykilpikonna on 2018/06/13!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandDecode implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        if (args.size() < 2) return "bot -decode [类型] [加密的字符串] || 类型可能为: " + MESSAGE_POSSIBLE_TYPES;

        String text;
        String encoded = ArrayUtils.getTheRestArgsAsString(args, 1);

        switch (args.get(0).toLowerCase())
        {
            case "hex": text = fromHex(encoded); break;
            case "b32": case "base32": text = fromBase32(encoded); break;
            case "ascii": text = fromAscii(encoded); break;
            default: return "不支持的类型, 类型可能为: " + MESSAGE_POSSIBLE_TYPES;
        }

        return sender.getInfo().getNickname() + " >> " + text;
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("decode");
    }
}
