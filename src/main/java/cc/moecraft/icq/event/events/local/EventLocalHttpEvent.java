package cc.moecraft.icq.event.events.local;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * HTTP获取 事件
 */
@AllArgsConstructor
@NoArgsConstructor
public class EventLocalHttpEvent extends EventLocal {
    public String head;
    public ArrayList<String> otherInfo;
    public String contentType;
    public String charset;
    public String userAgent;
    public String data;
}
