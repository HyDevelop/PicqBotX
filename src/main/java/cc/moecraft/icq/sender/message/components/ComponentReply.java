package cc.moecraft.icq.sender.message.components;

import cc.moecraft.icq.sender.message.MessageComponent;
import lombok.AllArgsConstructor;

/**
 * @author CrazyKid (i@crazykid.moe)
 * @since 2020/12/27 17:18
 */
@AllArgsConstructor
public class ComponentReply extends MessageComponent {

    private final Long messageId;

    @Override
    public String toString() {
        return "[CQ:reply,id=" + messageId + "]";
    }
}
