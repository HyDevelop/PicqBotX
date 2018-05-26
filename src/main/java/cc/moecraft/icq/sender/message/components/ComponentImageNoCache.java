package cc.moecraft.icq.sender.message.components;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class ComponentImageNoCache extends ComponentImage
{
    public ComponentImageNoCache(String fileOrURL)
    {
        super(fileOrURL);
    }

    @Override
    public String toString()
    {
        return "[CQ:image,cache=0,file=" + super.fileOrURL + "]";
    }
}
