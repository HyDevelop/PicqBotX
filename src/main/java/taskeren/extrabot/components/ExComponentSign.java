package taskeren.extrabot.components;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 签到组件。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class ExComponentSign extends ExComponent
{
    /**
     * 签到地点
     */
    final String location;

    /**
     * 签到内容
     */
    final String title;

    /**
     * 签到图片
     */
    final String image;

}
