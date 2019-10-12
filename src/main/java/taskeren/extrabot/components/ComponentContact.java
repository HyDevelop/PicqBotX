package taskeren.extrabot.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 名片组件，包括QQ用户名片和群名片。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor
@Getter
public class ComponentContact extends Component {

	public enum ContactTo {
		USER, GROUP;

		public static ContactTo parse(String str){
			switch(str) {
				case "qq":     return USER;
				case "group":  return GROUP;
				default:       return null;
			}
		}
	}

	/**
	 * 名片指向的QQ号或群号
	 */
	final long id;

	/**
	 * 名片类型（QQ用户或QQ群）
	 *
	 * @see ContactTo
	 */
	final ContactTo to;

}
