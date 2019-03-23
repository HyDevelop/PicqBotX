package cc.moecraft.icq.sender.message;

import lombok.Getter;

import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class MessageBuilder
{
    @Getter
    private ArrayList<Object> components = new ArrayList<>();

    /**
     * 添加一个子消息
     *
     * @param object Component
     * @return 这个实例
     */
    public MessageBuilder add(Object object)
    {
        components.add(object);
        return this;
    }

    /**
     * 换行
     *
     * @return 这个实例
     */
    public MessageBuilder newLine()
    {
        return this.add("\n");
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        components.forEach(stringBuilder::append);

        return stringBuilder.toString();
    }
}