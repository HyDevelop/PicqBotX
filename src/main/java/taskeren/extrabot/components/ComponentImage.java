package taskeren.extrabot.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 图片组件（包括自定义表情）。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor
@Getter
public class ComponentImage extends Component {

	/**
	 * 图片暂存于腾讯服务器上的地址
	 */
	final String url;

	/**
	 * 图片下载到本地的地址
	 */
	final String file;

}
