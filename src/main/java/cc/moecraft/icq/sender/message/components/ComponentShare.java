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
public class ComponentShare extends MessageComponent
{
    public String url;

    public String title;

    public String content;

    public String image;

    @Override
    public String toString()
    {
        return String.format("[CQ:share,url=%s,title=%s,content=%s,image=%s]", url, title, content, image);
    }
}
