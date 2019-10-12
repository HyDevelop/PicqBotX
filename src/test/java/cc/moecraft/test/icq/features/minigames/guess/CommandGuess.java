package cc.moecraft.test.icq.features.minigames.guess;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 猜数字游戏指令w
 * <p>
 * Class created by the HyDEV Team on 2019-05-11!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-05-11 14:38
 */
public class CommandGuess implements EverywhereCommand
{
    @Getter
    private Map<Long, GuessSession> sessions = new HashMap<>(); // 会话列表w

    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        if (args.size() != 0)
        {
            return "猜数字直接输数字就可以了哦w";
        }

        // 创建会话
        event.respond("[猜数字] 正在生成答案...");

        // 放进会话列表里
        sessions.put(sender.getId(), new GuessSession(sender.getId(), new Random().nextInt(100)));

        // 游戏提示
        return "请猜一个数字w (范围: [0,100])";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("guess", "猜数字");
    }
}
