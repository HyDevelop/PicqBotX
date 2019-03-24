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
    public String[] info;

    public ArrayList<String> otherInfo;

    public String contentType;

    public String charset;

    public String userAgent;

    public String data;
}
