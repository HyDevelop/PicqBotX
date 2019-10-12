package cc.moecraft.test.icq.features.minigames.guess;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.utils.StringUtils;
import lombok.AllArgsConstructor;

/**
 * TODO: No description yet...
 * <p>
 * Class created by the HyDEV Team on 2019-05-11!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-05-11 15:00
 */
@AllArgsConstructor
public class GuessListener extends IcqListener
{
    private final CommandGuess guess;

    @EventHandler
    public void onMessage(EventMessage event)
    {
        // TODO: 忽略指令

        // 忽略不是数字的输入
        if (!StringUtils.isNumeric(event.getMessage()))
        {
            return;
        }

        // 忽略没有会话的情况
        if (!guess.getSessions().containsKey(event.getSenderId()))
        {
            return;
        }

        // 用户会话已经在列表里面了
        GuessSession session = guess.getSessions().get(event.getSenderId());

        // 如果不在有效时间内, 移除会话
        if (!session.isValid())
        {
            guess.getSessions().remove(event.getSenderId());
            return;
        }

        // 游戏逻辑
        int num = Integer.parseInt(event.getMessage());

        if (num > session.getGuessTarget())
        {
            event.respond("[猜数字] 太大了x 继续猜w");
        }
        else if (num < session.getGuessTarget())
        {
            event.respond("[猜数字] 太小了x 继续猜w");
        }
        else
        {
            event.respond("[猜数字] 正确w! 次数: " + session.getGuessCount());
            guess.getSessions().remove(session.getId());
        }

        session.updateCount();
    }
}
