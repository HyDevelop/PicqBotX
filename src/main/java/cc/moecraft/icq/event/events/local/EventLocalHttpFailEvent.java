package cc.moecraft.icq.event.events.local;


import lombok.AllArgsConstructor;

import java.util.ArrayList;

/**
 * HTTP接受失败
 */
@AllArgsConstructor
public class EventLocalHttpFailEvent extends EventLocalHttpEvent{
    public String fail;//怎样失败的
    public String head;
    public ArrayList<String> otherInfo;
    public String contentType;
    public String charset;
    public String userAgent;
    public String data;

    public EventLocalHttpFailEvent(String fail) {
        this.fail = fail;
    }
}
