package cc.moecraft.test.icq.features.minigames.guess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 猜数字游戏会话w
 * <p>
 * Class created by the HyDEV Team on 2019-05-11!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-05-11 14:38
 */
@Data @AllArgsConstructor
@RequiredArgsConstructor
public class GuessSession
{
    private final long id; // 用户QQ号
    private long timestamp = System.currentTimeMillis(); // 时间戳 (ms)

    private final long guessTarget; // 猜数字的答案
    private long guessCount; // 猜数字的尝试数

    /**
     * 判断是否在有效时间内
     *
     * @return 是否有效
     */
    public boolean isValid()
    {
        return System.currentTimeMillis() - timestamp < 5 * 60 * 1000;
    }

    /**
     * +1 次猜测
     */
    public void updateCount()
    {
        timestamp = System.currentTimeMillis();
        guessCount ++;
    }
}
