package taskeren.extrabot.components;

import lombok.Getter;
import lombok.ToString;

/**
 * 原创表情组件。
 *
 * @author Taskeren
 */
@ToString
@Getter
public class ComponentBFace extends ComponentSendable
{

    final int p;

    final String id;

    /**
     * 构造表情
     *
     * @param id 表情ID
     */
    public ComponentBFace(int id)
    {
        this(-1, Integer.toString(id));
    }

    /**
     * 构造内部表情
     *
     * @param p 未知
     * @param id 表情ID
     */
    protected ComponentBFace(int p, String id)
    {
        this.p = p;
        this.id = id;
    }

    @Override
    public String toCQCode()
    {
        return "[CQ:bface,id=" + id + "]";
    }
}
