package cc.moecraft.icq.event.events.local;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * HTTP获取 事件
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventLocalHttpReceive extends EventLocal
{
    protected String[] info;

    protected ArrayList<String> otherInfo;

    protected String contentType;

    protected String charset;

    protected String userAgent;

    protected String data;
}
