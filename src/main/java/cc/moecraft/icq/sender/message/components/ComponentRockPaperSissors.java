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
public class ComponentRockPaperSissors extends MessageComponent
{
	/**
	 * 猜拳结果<br>
	 * 但是发送时不能自定义，所以仅仅用于接收
	 */
	public int type;
	
    @Override
    public String toString()
    {
        return "[CQ:rps,type=1]";
    }
}
