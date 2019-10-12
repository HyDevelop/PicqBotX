package cc.moecraft.icq.command;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@Data
public class CommandProperties
{
    public String name;

    public ArrayList<String> alias;

    /**
     * 指令属性构造器
     *
     * @param name 指令名
     * @param alias 其他指令名
     */
    public CommandProperties(String name, ArrayList<String> alias)
    {
        this.name = name;
        this.alias = alias;
    }

    /**
     * 指令属性构造器封装
     *
     * @param name 指令名
     */
    public CommandProperties(String name)
    {
        this(name, new ArrayList<>());
    }

    /**
     * 指令属性构造器封装
     *
     * @param name 指令名
     * @param alias 其他指令名
     */
    public CommandProperties(String name, String... alias)
    {
        this(name, new ArrayList<>(Arrays.asList(alias)));
    }

    /**
     * 构造CommandProperty
     * @param name 指令名称
     */
    public static CommandProperties name(String name) {
        return new CommandProperties(name);
    }

    /**
     * 添加指令别名
     * @param alias 指令别名
     */
    public CommandProperties alias(String alias) {
        this.alias.add(alias);
        return this;
    }

}
