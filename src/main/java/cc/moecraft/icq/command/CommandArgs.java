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

    public static CommandArgs parse(CommandManager commandManager, String fullCommand, boolean isGroup)
            throws NotACommandException, CommandNotFoundException
    {
        return parse(commandManager.getPrefixes(), commandManager.getRegisteredCommands(), fullCommand, isGroup);
    }

    /**
     * 从字符串消息转换为CommandArgs
     *
     * @param prefixes 启用的前缀
     * @param registeredCommands 已注册的指令
     * @param fullCommand 完整字符串消息
     * @param isGroup 是不是群消息
     * @return CommandArgs指令
     * @throws NotACommandException 不是指令
     * @throws CommandNotFoundException 指令执行器未找到
     */
    public static CommandArgs parse(String[] prefixes,
            Map<String, ArrayList<IcqCommand>> registeredCommands, String fullCommand,
            boolean isGroup) throws NotACommandException, CommandNotFoundException
    {
        // 移除前缀前面的空格
        fullCommand = removeStartingSpace(fullCommand);

        // 获取前缀
        String prefix = getPrefix(prefixes, fullCommand);

        // 判断有没有前缀, 私聊不需要前缀
        if (prefix.equals("") && isGroup) throw new NotACommandException();

        // 移除前缀, 和前缀与指令第一项之间的空格
        fullCommand = fullCommand.substring(prefix.length());
        fullCommand = removeStartingSpace(fullCommand);

        // 因为如果最后全是空格的话split会忽略这些空格, 所以要先在结尾添加一个字符
        fullCommand += " ;";

        // 分解指令args
        ArrayList<String> args = new ArrayList<>(Arrays.asList(fullCommand.split(" ")));

        // 移除结尾添加的字符
        args.remove(args.size() - 1);

        // 获取指令名
        String command = args.get(0).toLowerCase();
        args.remove(0);

        // 确认指令存在
        if (!registeredCommands.containsKey(command)) throw new CommandNotFoundException();

        // 获取执行器
        ArrayList<IcqCommand> commandsToRun = registeredCommands.get(command);

        return new CommandArgs(prefix, command, commandsToRun, args);
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

    /**
     * 移除前面的空格
     * @param original 源字符串
     * @return 移除后的字符串
     */
    private static String removeStartingSpace(String original)
    {
        while (original.startsWith(" "))
            original = original.substring(1);

        return original;
    }
}
