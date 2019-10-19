package taskeren.extrabot.components;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 位置组件。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class ComponentLocation extends Component
{

    /**
     * 经度
     */
    final String lat;

    /**
     * 纬度
     */
    final String lon;

    /**
     * 粗略地名
     */
    final String title;

    /**
     * 详细地址
     */
    final String content;

    /**
     * 样式代码（未知）
     */
    final int style;

}
