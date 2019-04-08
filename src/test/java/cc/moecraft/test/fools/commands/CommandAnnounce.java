package cc.moecraft.test.fools.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroup;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import cc.moecraft.icq.user.User;
import cc.moecraft.utils.ArrayUtils;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/08/18 创建!
 * Created by Hykilpikonna on 2018/08/18!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CommandAnnounce implements EverywhereCommand
{
    @Override
    public String run(EventMessage event, User user, String command, ArrayList<String> args)
    {
        if (args.size() < 2) return "广播给所有群: /announce [延迟(ms)] [消息...]";
        // TODO: 权限模块
        if (user.getId() != 871674895) return null;

        int delay;
        try
        {
            delay = Integer.parseInt(args.get(0));
        }
        catch (Exception e)
        {
            return "请指定延迟, 单位为毫秒";
        }

        new Thread(() -> {
            ReturnListData<RGroup> groups = event.getHttpApi().getGroupList();
            String message = ArrayUtils.getTheRestArgsAsString(args, 1);

            int successCount = 0;
            int failCount = 0;
            int asyncCount = 0;

            outer:
            for (RGroup group : groups.getData())
            {
                // TODO: Remove this
                Long[] list = new Long[]{175473823L, 62847742L, 139289187L, 68368719L, 138014904L, 201865589L, 204228752L, 211957490L, 213894622L};
                for (Long aLong : list)
                {
                    if (group.getGroupId().equals(aLong)) continue outer;
                }

                ReturnData<RMessageReturnData> returnData = event.getHttpApi().sendGroupMsg(group.getGroupId(), message);
                event.getBot().getLogger().error("发送了: " + group.getGroupId() + " " + group.getGroupName());
                switch (returnData.getStatus())
                {
                    case ok:
                        successCount ++;
                        break;
                    case async:
                        asyncCount ++;
                        break;
                    case failed:
                        failCount ++;
                        break;
                }
                try
                {
                    Thread.sleep(delay);
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }

            event.getBot().getLogger().error("Done!");

        }).start();

        // TODO: Fix async
        return "已开始";
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("announce");
    }
}
