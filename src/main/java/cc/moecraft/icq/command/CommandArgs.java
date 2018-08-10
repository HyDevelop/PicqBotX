package cc.moecraft.icq.command;

import cc.moecraft.icq.command.exceptions.CommandNotFoundException;
import cc.moecraft.icq.command.exceptions.NotACommandException;
import cc.moecraft.icq.command.interfaces.IcqCommand;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/05/05 创建!
 * Created by Hykilpikonna on 2018/05/05!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@Data @AllArgsConstructor
public class CommandArgs
{
    private String prefix;
    private String commandName;
    private ArrayList<IcqCommand> commandRunners;
    private ArrayList<String> args;

    /**
     * 从字符串消息转换为CommandArgs
     *
     * @param prefixes 启用的前缀
     * @param registeredCommands 已注册的指令
     * @param fullCommand 完整字符串消息
     * @param isGroup 是不是群消息
     * @return CommandArgs指令
     */
    public static CommandArgs parse(String[] prefixes,
            Map<String, ArrayList<IcqCommand>> registeredCommands, String fullCommand,
            boolean isGroup) throws NotACommandException, CommandNotFoundException
    {
        // 获取前缀
        String prefix = getPrefix(prefixes, fullCommand);

        // 判断有没有前缀, 私聊不需要前缀
        if (prefix.equals("") && isGroup) throw new NotACommandException();

        // 移除前缀
        fullCommand = fullCommand.substring(prefix.length());

        // 移除前缀和指令第一项之间的空格
        while (fullCommand.startsWith(" "))
            fullCommand = fullCommand.substring(1);

        // 分解指令args
        ArrayList<String> args = new ArrayList<>(Arrays.asList(fullCommand.split(" ")));

        // 获取指令名
        String command = args.get(0).toLowerCase();
        args.remove(0);

        // 确认指令存在
        if (!registeredCommands.containsKey(command)) throw new CommandNotFoundException();

        // 获取执行器
        ArrayList<IcqCommand> commandsToRun = registeredCommands.get(command);

        return new CommandArgs(prefix, command, commandToRun, args);
    }

    /**
     * 获取指令前缀
     * @param prefixes 可用前缀
     * @param text 消息
     * @return 是指令的话返回指令前缀, 不是指令的话返回""
     */
    private static String getPrefix(String[] prefixes, String text)
    {
        for (String prefix : prefixes)
            if (text.startsWith(prefix)) return prefix;

        return "";
    }
}
