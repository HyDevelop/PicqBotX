package taskeren.extrabot.components;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 石头剪刀布组件。
 *
 * @author Taskeren
 */
@ToString
@Getter
public class ComponentRPS extends ComponentSendable {

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

	/**
	 * 构造一个内部石头剪刀布组件
	 * @param rps
	 */
	protected ComponentRPS(RPS rps) {
		this.type = rps;
	}

	/**
	 * 构造一个石头剪刀布组件（随机）
	 */
	public ComponentRPS() {
		this(null);
	}

	@Override
	public String toCQCode() {
		return "[CQ:rps,type=1]";
	}
}
