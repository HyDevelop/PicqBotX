package cc.moecraft.icq.command;

import cc.moecraft.icq.command.exceptions.CommandNotFoundException;
import cc.moecraft.icq.command.exceptions.NotACommandException;
import cc.moecraft.icq.command.interfaces.IcqCommand;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;

import static cc.moecraft.icq.utils.StringUtils.removeStartingSpace;

/**
 * 此类由 Hykilpikonna 在 2018/05/05 创建!
 * Created by Hykilpikonna on 2018/05/05!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@Data
@AllArgsConstructor
public class CommandArgs
{
    private String prefix;

    private String commandName;

    private IcqCommand commandRunners;

    private ArrayList<String> args;

    /**
     * 从字符串消息转换为CommandArgs
     *
     * @param manager 指令管理器
     * @param fullCommand 完整字符串消息
     * @param isGM 是不是群消息
     * @return CommandArgs 指令参数
     * @throws NotACommandException 不是指令
     * @throws CommandNotFoundException 指令执行器未找到
     */
    public static CommandArgs parse(CommandManager manager, String fullCommand, boolean isGM)
            throws NotACommandException, CommandNotFoundException
    {
        // 移除前缀前面的空格
        fullCommand = removeStartingSpace(fullCommand);

        // 获取前缀
        String prefix = getPrefix(manager.getPrefixes(), fullCommand);

        // 判断有没有前缀, 私聊不需要前缀
        if (prefix.equals("") && isGM)
        {
            throw new NotACommandException();
        }

        // 移除前缀, 和前缀与指令第一项之间的空格
        fullCommand = removeStartingSpace(fullCommand.substring(prefix.length()));

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
        if (!manager.getCommands().containsKey(command))
        {
            throw new CommandNotFoundException();
        }

        // 获取执行器, 返回
        return new CommandArgs(prefix, command, manager.getCommands().get(command), args);
    }

    /**
     * 获取指令前缀
     *
     * @param prefixes 可用前缀
     * @param text 消息
     * @return 是指令的话返回指令前缀, 不是指令的话返回""
     */
    private static String getPrefix(String[] prefixes, String text)
    {
        for (String prefix : prefixes)
        {
            if (text.startsWith(prefix))
            {
                return prefix;
            }
        }

        return "";
    }
}
