package cc.moecraft.test.general;

import cc.moecraft.icq.command.CommandArgs;
import cc.moecraft.icq.command.CommandManager;
import cc.moecraft.icq.command.exceptions.CommandNotFoundException;
import cc.moecraft.icq.command.exceptions.NotACommandException;
import cc.moecraft.icq.command.interfaces.IcqCommand;
import cc.moecraft.icq.user.GroupManager;
import cc.moecraft.test.icq.commands.CommandTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/08/10 创建!
 * Created by Hykilpikonna on 2018/08/10!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class StaticDebug
{
    public static void main(String[] args) throws NotACommandException, CommandNotFoundException
    {
        String[] prefixes = new String[]{"!", "bot -"};
        Map<String, ArrayList<IcqCommand>> registeredCommands = new HashMap<>();
        CommandTest command = new CommandTest();
        registeredCommands.put(command.properties().getName().toLowerCase(), new ArrayList<>(Collections.singletonList(command)));

        CommandArgs commandArgs = CommandArgs.parse(prefixes, registeredCommands, "   !  test a b  cc d   ", true);
        System.out.println(commandArgs);
    }
}
