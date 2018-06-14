package cc.moecraft.test.icq.features.codec;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import cc.moecraft.icq.utils.ArrayUtils;
import cc.moecraft.icq.utils.StringCodecUtils;

import java.util.ArrayList;

import static cc.moecraft.test.icq.features.codec.CommandEncode.MESSAGE_POSSIBLE_TYPES;

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

        switch (args.get(0))
        {
            case "hex": return sender.getInfo().getNickname() + " >> " + StringCodecUtils.fromHex(ArrayUtils.getTheRestArgsAsString(args, 1));
            case "b32": return sender.getInfo().getNickname() + " >> " + StringCodecUtils.fromBase32(ArrayUtils.getTheRestArgsAsString(args, 1));
            default: return "不支持的类型, 类型可能为: " + MESSAGE_POSSIBLE_TYPES;
        }
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("decode");
    }
}
