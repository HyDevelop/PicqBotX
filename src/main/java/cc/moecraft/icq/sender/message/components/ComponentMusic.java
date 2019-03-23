package cc.moecraft.icq.sender.message.components;

import cc.moecraft.icq.sender.message.MessageComponent;
import lombok.AllArgsConstructor;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@AllArgsConstructor
public class ComponentMusic extends MessageComponent
{
    public int musicId;

    public MusicSourceType type;

    @Override
    public String toString()
    {
        return "[CQ:music,type=" + type.toString() + ",id=" + musicId + "]";
    }

    @AllArgsConstructor
    public enum MusicSourceType
    {
        qq("qq"), netease("163");

        public String type;

        @Override
        public String toString()
        {
            return type;
        }
    }
}
