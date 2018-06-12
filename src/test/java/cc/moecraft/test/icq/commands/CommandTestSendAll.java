package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import cc.moecraft.icq.user.User;
import cc.moecraft.logger.DebugLogger;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CommandTestSendAll implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        DebugLogger logger = event.getBot().getLogger();

        {
            logger.log("测试撤回...");
            RMessageReturnData returnData = event.respond("测试撤回").getData();
            logger.log(returnData.toString());
            event.getBot().getHttpApi().deleteMsg(returnData.getMessageId());
        }
        {
            logger.log("测试赞");
            event.getBot().getHttpApi().sendLike(sender.getId(), 10);
        }
        return null;
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("testsendall");
    }
}
