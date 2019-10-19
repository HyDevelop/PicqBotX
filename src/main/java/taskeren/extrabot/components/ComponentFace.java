package taskeren.extrabot.components;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 自带表情组件。
 *
 * @author Taskeren
 */
@ToString
@Getter
public class ComponentFace extends ComponentSendable {

	/**
	 * 表情ID
	 */
	final int id;

	/**
	 * 构造一个表情组件
	 *
	 * @param id 表情ID
	 */
	public ComponentFace(int id) {
		this.id = id;
	}

	@Override
	public String toCQCode() {
		return "[CQ:face,id=" + id + "]";
	}
}
