package cc.moecraft.icq.user;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroupMemberInfo;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RStrangerInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@RequiredArgsConstructor
public class GroupUser
{
    private final PicqBotX bot;
    @Getter
    public final long id;
    @Getter
    public final Group group;

    private RGroupMemberInfo info;

    /**
     * 获取数据
     * @return 数据 (不一定是最新)
     */
    public RGroupMemberInfo getInfo()
    {
        if (info != null) return info;
        return refreshInfo();
    }

    /**
     * 更新数据
     * @return 更新的数据
     */
    public RGroupMemberInfo refreshInfo()
    {
        return info = bot.getHttpApi().getGroupMemberInfo(group.getId(), id).getData();
    }
}
