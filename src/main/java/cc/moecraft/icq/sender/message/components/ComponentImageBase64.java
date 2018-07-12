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
public class ComponentImageBase64 extends MessageComponent
{
    public String base64;

    @Override
    public String toString()
    {
        return "[CQ:image,file=base64://" + base64 + "]";
    }
}
