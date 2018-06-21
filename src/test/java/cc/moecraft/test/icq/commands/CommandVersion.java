package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RStatus;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RVersionInfo;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CommandVersion implements EverywhereCommand // 继承EverywhereCommand就是无论私聊还是群聊都能收到的指令
{
    // 指令属性
    @Override
    public CommandProperties properties()
    {
        // 这个括号里填指令名和其他名称, 指令名必须至少有一个
        // 这个的话, 用"!v", "!version", 和"!版本"都能触发指令 (感叹号为你设置的前缀, 不一定必须要感叹号)
        return new CommandProperties("version", "v", "版本");
    }

    // 机器人接到指令后会执行这个方法 ( 实现不同的接口的话方法名不一定一样 )
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        // 处理, 返回值会自动回复回去

        // 获取版本信息
        RVersionInfo versionInfo = event.getHttpApi().getVersionInfo().getData();
        RStatus status = event.getHttpApi().getStatus().getData();

        // 这里因为这个指令是用来查版本的, 所以直接返回字符串了
        return new MessageBuilder()
                .add("TestBot - PicqBotX v1.0.4.7").newLine()
                .add("- 酷Q ").add(versionInfo.getCoolqEdition()).add(" HTTP插件: ")
                    .add(versionInfo.getPluginVersion()).add(" b").add(versionInfo.getPluginBuildNumber()).newLine()
                .add("- 运行状态: Good: ").add(status.getGood()).add(", ").add(status.getPluginsGood())
                .toString();
    }
}
