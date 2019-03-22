package cc.moecraft.icq.accounts;

import cc.moecraft.icq.event.EventManager;
import cc.moecraft.icq.sender.IcqHttpApi;
import lombok.Data;
import lombok.Getter;

/**
 * 此类由 Hykilpikonna 在 2018/08/25 创建!
 * Created by Hykilpikonna on 2018/08/25!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@Data
@Getter
public class BotAccount
{
    private final String name;
    private final String postUrl;
    private final int postPort;

    private long id;
    private IcqHttpApi httpApi;

    public BotAccount(String name, EventManager eventManager, String postUrl, int postPort)
    {
        this.name = name;
        this.postUrl = postUrl;
        this.postPort = postPort;

        this.httpApi = new IcqHttpApi(eventManager, postUrl, postPort);
        this.id = httpApi.getSelfId();
    }
}
