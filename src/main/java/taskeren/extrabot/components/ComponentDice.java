package taskeren.extrabot.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 骰子组件。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor
@Getter
public class ComponentDice extends Component {

	/**
	 * 投到的点数
	 */
	final int type;

}
