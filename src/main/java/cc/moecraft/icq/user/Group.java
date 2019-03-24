package cc.moecraft.icq.user;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroup;
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
public class Group
{
    private final PicqBotX bot;

    private final long id;

    private RGroup info;

    /**
     * 获取数据
     *
     * @return 数据 (不一定是最新)
     */
    public RGroup getInfo()
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
    public RGroup refreshInfo()
    {
        ReturnListData<RGroup> groupList = bot.getAccountManager().getNonAccountSpecifiedApi().getGroupList();
        for (RGroup group : groupList.getData())
        {
            if (group.getGroupId().equals(id))
            {
                return info = group;
            }
        }
        return info = null;
    }
}
