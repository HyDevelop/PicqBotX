package cc.moecraft.icq.event;

/**
 * 此类由 Hykilpikonna 在 2018/08/26 创建!
 * Created by Hykilpikonna on 2018/08/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public interface ContentComparable
{
    /**
     * 内容是否和另一个相同
     *
     * @param other 另一个
     * @return 是否相同
     */
    boolean contentEquals(Object other);
}
