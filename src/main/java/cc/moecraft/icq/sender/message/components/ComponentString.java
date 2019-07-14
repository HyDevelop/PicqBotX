package cc.moecraft.icq.sender.message.components;

import cc.moecraft.icq.sender.message.MessageComponent;
import lombok.AllArgsConstructor;

/**
 * 封装非CQ码的消息，一般用不上
 *
 * @author Taskeren
 */
@AllArgsConstructor
public class ComponentString extends MessageComponent {

	public String message;
	
	@Override
	public String toString() {
		return String.valueOf(message);
	}
	
}
