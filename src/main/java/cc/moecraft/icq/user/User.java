package cc.moecraft.icq.user;

import cc.moecraft.icq.PicqBotX;
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
public class User
{
    private final PicqBotX bot;
    @Getter
    public final long id;

    private RStrangerInfo info;

    /**
     * 获取数据
     * @return 数据 (不一定是最新)
     */
    public RStrangerInfo getInfo()
    {
        if (info != null) return info;
        return refreshInfo(false);
    }

    /**
     * 更新数据
     * @param noCache 是否不用缓存
     * @return 更新的数据
     */
    public RStrangerInfo refreshInfo(boolean noCache)
    {
        return info = bot.getAccountManager().getNonAccountSpecifiedApi().getStrangerInfo(id, noCache).getData();
    }
}
