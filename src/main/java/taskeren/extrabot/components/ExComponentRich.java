package taskeren.extrabot.components;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import taskeren.extrabot.components.richs.ExComponentRichMusic;
import taskeren.extrabot.components.richs.ExComponentRichNews;

/**
 * 这是一个神奇的组件，富文本组件。
 * 他包含了很多功能，包括网易云音乐、QQ音乐、Bilibili的分享，
 * 还有其他群投票等群功能，都使用的是富文本组件，请自行选用。
 *
 * @author Taskeren
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class ExComponentRich extends ExComponent
{
    final String title;

    final String text;

    final String content;

    public class To
    {
        /**
         * 转换为音乐分享富文本组件。
         */
        public ExComponentRichMusic music()
        {
            JSONObject json = JSONUtil.parseObj(content);
            if (json.containsKey("music"))
            {
                JSONObject d = json.getJSONObject("music");
                return new ExComponentRichMusic(
                        d.getStr("title"),
                        d.getStr("desc"),
                        d.getStr("preview"),
                        d.getStr("tag"),
                        d.getStr("musicUrl"),
                        d.getStr("jumpUrl")
                );
            }
            else
            {
                throw new UnsupportedOperationException();
            }
        }

        /**
         * 转换为网页分享富文本组件。
         */
        public ExComponentRichNews news()
        {
            JSONObject json = JSONUtil.parseObj(content);
            if (json.containsKey("news"))
            {
                JSONObject d = json.getJSONObject("news");
                return new ExComponentRichNews(
                        d.getStr("title"),
                        d.getStr("desc"),
                        d.getStr("preview"),
                        d.getStr("tag"),
                        d.getStr("jumpUrl")
                );
            }
            else
            {
                throw new UnsupportedOperationException();
            }
        }
    }
}
