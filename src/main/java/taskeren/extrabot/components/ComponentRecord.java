package taskeren.extrabot.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 语音组件。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor
@Getter
public class ComponentRecord extends Component {

	/**
	 * 语音储存在本地的位置
	 */
	final String file;

	/**
	 * 是否使用变声器
	 */
	final boolean magic;

}
