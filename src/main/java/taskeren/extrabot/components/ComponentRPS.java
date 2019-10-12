package taskeren.extrabot.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 石头剪刀布组件。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor
@Getter
public class ComponentRPS extends Component {

	public enum RPS {
		ROCK,     // 石头
		PAPER,    // 布
		SCISSORS; // 剪刀

		public static RPS parse(int type){
			switch (type) {
				case 1:  return ROCK;
				case 2:  return SCISSORS;
				case 3:  return PAPER;
				default: return null;
			}
		}
	}

	/**
	 * 石头剪刀布
	 */
	final RPS type;

}
