package taskeren.extrabot.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 这是一个神奇的组件，富文本组件。
 * 他包含了很多功能，包括网易云音乐、QQ音乐、Bilibili的分享，
 * 还有其他群投票等群功能，都使用的是富文本组件，请自行选用。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor
@Getter
public class ComponentRich extends Component {

	final String title;
	final String text;
	final String content;

}
