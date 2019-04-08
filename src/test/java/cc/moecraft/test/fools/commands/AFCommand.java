package cc.moecraft.test.fools.commands;

import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;
import java.util.Random;

/**
 * TODO: No description yet...
 * <p>
 * Class created by the HyDEV Team on 2019-03-31!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-31 22:29
 */
public abstract class AFCommand implements GroupCommand
{
    public static ArrayList<Long> groups = new ArrayList<>();

    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args)
    {
        // 一个群只发一次
        if (groups.contains(group.getId())) return null;
        groups.add(group.getId());

        // 随机防封号嗯x
        int rand = new Random().nextInt(6);
        switch (rand)
        {
            case 0:
                return "噗哈哈愚人节快乐ww";
            case 1:
                return "噗哈哈愚人节快乐w!";
            case 2:
                return "噗哈哈哈哈哈哈w\n愚人节快乐ww!";
            case 3:
                return "愚人节快乐ww";
            case 4:
                return "噗哈哈哈哈哈哈w\n愚人节快乐ww!";
            case 5:
                return "愚人节快乐哈哈哈w!";
            case 6:
                return "愚人节快乐哈哈哈w";
        }
        return "愚人节快乐w!";
    }
}
