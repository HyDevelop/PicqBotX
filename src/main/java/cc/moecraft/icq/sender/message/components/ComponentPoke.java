package cc.moecraft.icq.sender.message.components;

import cc.moecraft.icq.sender.message.MessageComponent;
import lombok.AllArgsConstructor;

/**
 * 戳一戳(仅限群聊)
 * 注: 此类适配 go-cqhttp, 与 OneBot 标准略有差异
 *
 * @author CrazyKid (i@crazykid.moe)
 * @since 2020/3/31 10:43
 */
@AllArgsConstructor
public class ComponentPoke extends MessageComponent {

    private final Long qq;

    @Override
    public String toString() {
        return "[CQ:poke,qq=" + qq + "]";
    }
}
