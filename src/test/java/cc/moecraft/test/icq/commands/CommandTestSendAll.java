package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import cc.moecraft.icq.user.User;
import cc.moecraft.logger.HyLogger;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CommandTestSendAll implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        HyLogger logger = event.getBot().getLogger();

        {
            logger.log("测试撤回...");
            RMessageReturnData returnData = event.respond("测试撤回").getData();
            logger.log(returnData.toString());
            event.getHttpApi().deleteMsg(returnData.getMessageId());
        }
        {
            logger.log("测试赞");
            event.getHttpApi().sendLike(sender.getId(), 10);
        }
        return null;
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("testsendall");
    }
}
