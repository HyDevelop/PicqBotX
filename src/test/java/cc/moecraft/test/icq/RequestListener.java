package cc.moecraft.test.icq;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.request.EventFriendRequest;
import cc.moecraft.icq.event.events.request.EventGroupAddRequest;
import cc.moecraft.icq.event.events.request.EventGroupInviteRequest;

/**
 * 此类由 Hykilpikonna 在 2018/05/25 创建!
 * Created by Hykilpikonna on 2018/05/25!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class RequestListener extends IcqListener
{
    // 接受好友请求的字符 (只要contains就行) 虽然知道有只要输一个字母就能过的bug但是还是这样写了...
    private static final String acceptedFriendRequestMessage = "hykilpikonna 小桂 admin@moecraft.cc hydev";
    // 接受加群申请的字符
    private static final String acceptedGroupRequestMessage = "hyosuircbot iosu hyconfiglib hylogger osu机器人";

    @EventHandler
    public void onFriendRequest(EventFriendRequest event)
    {
        // 因为我这里设置的问题是 "开发我的人叫什么?" 所以答案这样判断...
        String answer = event.getMessage().replace("问题1:开发我的人叫什么?\n回答:", "");
        if (acceptedFriendRequestMessage.contains(answer.toLowerCase()))
        {
            event.accept();
        }
        else
        {
            event.reject("开发我的人不叫\"" + answer + "\"哦! 再试试吧!"); // TODO: 把这些输错的人记录下来
        }
    }

    @EventHandler
    public void onGroupInvite(EventGroupInviteRequest event)
    {
        // 接受所有群邀请
        event.accept();
    }
}
