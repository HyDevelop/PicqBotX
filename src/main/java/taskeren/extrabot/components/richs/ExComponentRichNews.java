package taskeren.extrabot.components.richs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import taskeren.extrabot.components.ExComponent;

/**
 * 网页分享富文本组件。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor
@Getter
public class ExComponentRichNews extends ExComponent
{
    /**
     * 页面名称
     */
    final String title;

    /**
     * 页面描述
     */
    final String desc;

    /**
     * 页面预览
     */
    final String preview;

    /**
     * APP标签
     */
    final String tag;

    /**
     * 页面地址
     */
    final String url;
}
