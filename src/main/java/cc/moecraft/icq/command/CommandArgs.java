package cc.moecraft.icq.command;

import cc.moecraft.icq.command.interfaces.IcqCommand;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

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

    private IcqCommand commandRunner;

    private ArrayList<String> args;
}
