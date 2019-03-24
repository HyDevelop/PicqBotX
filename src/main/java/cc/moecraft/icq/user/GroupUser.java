package cc.moecraft.icq.user;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroupMemberInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@Getter
@RequiredArgsConstructor
public class GroupUser
{
    private final PicqBotX bot;

    private final long id;

    private final Group group;

    private RGroupMemberInfo info;

    /**
     * 获取数据
     *
     * @return 数据 (不一定是最新)
     */
    public RGroupMemberInfo getInfo()
    {
        if (info != null)
        {
            return info;
        }
        return refreshInfo();
    }

    /**
     * 更新数据
     *
     * @return 更新的数据
     */
    public RGroupMemberInfo refreshInfo()
    {
        if (id == 80000000L)
        {
            return info = new RGroupMemberInfo(
                    0L,
                    "CN",
                    "Anonymous",
                    false,
                    group.getId(),
                    System.currentTimeMillis(),
                    1L,
                    "Anonymous",
                    "Anonymous",
                    "member",
                    "male",
                    "Anonymous",
                    0L,
                    false, id);
        }
        return info = bot.getAccountManager().getNonAccountSpecifiedApi().getGroupMemberInfo(group.getId(), id).getData();
    }

    /**
     * 判断是不是管理员
     *
     * @return 是不是管理员
     */
    public boolean isAdmin()
    {
        return !getInfo().getRole().equals("member");
    }
}
