package cc.moecraft.icq.command;

import cc.moecraft.icq.command.exceptions.CommandNotFoundException;
import cc.moecraft.icq.command.exceptions.NotACommandException;
import cc.moecraft.icq.command.interfaces.IcqCommand;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 此类由 Hykilpikonna 在 2018/05/05 创建!
 * Created by Hykilpikonna on 2018/05/05!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
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
     * @param fullCommand 完整字符串消息
     * @param isChannel 是不是从频道发出来的
     * @return CommandArgs指令
     */
    public static CommandArgs parse(CommandManager commandManager, String fullCommand, boolean isChannel) throws NotACommandException, CommandNotFoundException
    {
        String prefix = getPrefix(commandManager, fullCommand);

        if (prefix.equals("") && isChannel) throw new NotACommandException(); // 不是指令

        // 例子: !ecHO hi there

        fullCommand = fullCommand.replace(prefix, ""); // "ecHO hi there"

        ArrayList<String> args = new ArrayList<>(Arrays.asList(fullCommand.split(" "))); // String "!ecHO hi there" -> ArrayList ["!ecHO", "hi", "there"]

        String command = args.get(0).toLowerCase(); // "echo"
        args.remove(0); // ["hi", "there"]

        if (!commandManager.getRegisteredCommands().containsKey(command)) throw new CommandNotFoundException(); // 无法找到指令

        ArrayList<IcqCommand> commandToRun = commandManager.getRegisteredCommands().get(command);

        return new CommandArgs(prefix, command, commandToRun, args);
    }

    /**
     * 获取指令前缀
     * @param text 消息
     * @return 是指令的话返回指令前缀, 不是指令的话返回""
     */
    private static String getPrefix(CommandManager commandManager, String text)
    {
        for (String prefix : commandManager.getPrefixes())
            if (text.startsWith(prefix)) return prefix;

        return "";
    }
}
