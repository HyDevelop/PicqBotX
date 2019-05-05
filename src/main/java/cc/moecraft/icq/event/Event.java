package cc.moecraft.icq.event;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.accounts.BotAccount;
import cc.moecraft.icq.sender.IcqHttpApi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@Data
@Setter(AccessLevel.NONE)
public abstract class Event implements ContentComparable
{
    @SerializedName("post_type")
    @Expose
    public String postType;

    @SerializedName("self_id")
    @Expose
    public Long selfId;

    @SerializedName("time")
    @Expose
    public Long time;

    public PicqBotX bot;

    public Event setBot(PicqBotX bot)
    {
        this.bot = bot;
        return this;
    }

    public IcqHttpApi getHttpApi()
    {
        return getBotAccount().getHttpApi();
    }

    public BotAccount getBotAccount()
    {
        return getBot().getAccountManager().getIdIndex().get(selfId);
    }

    @Override
    public boolean contentEquals(Object o)
    {
        if (!(o instanceof Event)) return false;
        Event other = (Event) o;

        // ID 不能用来判断是不是相等...
        // 因为不同酷Q端发来的的ID不一样啦w
        return other.getBot() == this.getBot() && other.getTime().equals(this.getTime());
    }
}
