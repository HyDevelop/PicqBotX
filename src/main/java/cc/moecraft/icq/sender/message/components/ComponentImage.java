package cc.moecraft.icq.sender.message.components;

import cc.moecraft.icq.sender.message.MessageComponent;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class ComponentImage extends MessageComponent
{
    public String fileOrURL;
    public boolean isLocalFile;

    public ComponentImage(String fileOrURL) {
        this.fileOrURL = fileOrURL;
    }

    public ComponentImage(String fileOrURL, boolean isLocalFile) {
        this.fileOrURL = fileOrURL;
        this.isLocalFile = isLocalFile;
    }

    @Override
    public String toString()
    {
        if (isLocalFile) {
            return "[CQ:image,file=file:///" + fileOrURL + "]";
        } else {
            return "[CQ:image,file=" + fileOrURL + "]";
        }
    }
}
