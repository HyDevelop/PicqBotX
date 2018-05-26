package cc.moecraft.icq.event;

import cc.moecraft.icq.PicqBotX;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@Data
public abstract class Event
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
}
