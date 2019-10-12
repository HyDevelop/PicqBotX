package taskeren.extrabot.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 群内AT组件。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor
@Getter
public class ComponentAt extends Component {

	/**
	 * 被AT的QQ号
	 */
	protected final long at;

}
