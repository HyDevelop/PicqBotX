package taskeren.extrabot.components;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * URL组件。
 * 出现条件很骚，只有腾讯认证的网页地址才会出现这个组件。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class ExComponentShare extends ExComponent {

	final String content;

	final String img;

	final String title;

	final String url;

}
