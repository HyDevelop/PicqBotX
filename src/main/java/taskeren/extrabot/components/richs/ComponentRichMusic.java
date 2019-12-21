package taskeren.extrabot.components.richs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import taskeren.extrabot.components.Component;

/**
 * 音乐分享富文本组件。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor
@Getter
public class ComponentRichMusic extends Component
{
    /**
     * 歌曲名称
     */
    final String title;

    /**
     * 歌曲描述 （网易云音乐和QQ音乐则显示作者）
     */
    final String desc;

    /**
     * 歌曲封面
     */
    final String preview;

    /**
     * APP标签 （例如：“QQ音乐”、“网易云音乐”）
     */
    final String tag;

    /**
     * 音乐文件地址
     */
    final String urlMusic;

    /**
     * 音乐网页
     */
    final String urlPage;
}
