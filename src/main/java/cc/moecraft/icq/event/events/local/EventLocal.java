package cc.moecraft.icq.event.events.local;

import cc.moecraft.icq.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 本地事件
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public abstract class EventLocal extends Event
{
    public boolean cancelled = false;
}
