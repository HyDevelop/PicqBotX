package cc.moecraft.test.icq.features.codec;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import cc.moecraft.icq.utils.ArrayUtils;
import cc.moecraft.icq.utils.StringCodecUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/06/13 创建!
 * Created by Hykilpikonna on 2018/06/13!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandEncode implements EverywhereCommand
{
    public static final String MESSAGE_POSSIBLE_TYPES = "hex, b32";

    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        if (args.size() < 2) return "bot -encode [类型] [字符串] || 类型可能为: " + MESSAGE_POSSIBLE_TYPES;

        switch (args.get(0))
        {
            case "hex": return sender.getInfo().getNickname() + " >> " + StringCodecUtils.toHex(ArrayUtils.getTheRestArgsAsString(args, 1));
            case "b32": return sender.getInfo().getNickname() + " >> " + StringCodecUtils.toBase32(ArrayUtils.getTheRestArgsAsString(args, 1));
            default: return "不支持的类型, 类型可能为: " + MESSAGE_POSSIBLE_TYPES;
        }
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("encode");
    }
}
