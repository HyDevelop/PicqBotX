package cc.moecraft.test.icq.listeners;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.local.EventLocalSendGroupMessage;
import cc.moecraft.icq.event.events.local.EventLocalSendMessage;
import cc.moecraft.icq.event.events.local.EventLocalSendPrivateMessage;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import static cc.moecraft.logger.format.AnsiColor.*;

/**
 * 此类由 Hykilpikonna 在 2018/05/25 创建!
 * Created by Hykilpikonna on 2018/05/25!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor @Data
public class SimpleTextLoggingListener extends IcqListener
{
    private static final int nicknameLength = 16;

    public static String getGroupName(PicqBotX bot, long groupId)
    {
        ReturnListData<RGroup> returnListData = bot.getAccountManager().getNonAccountSpecifiedApi().getGroupList();
        for (RGroup group : returnListData.getData())
        {
            if (group.getGroupId().equals(groupId)) return group.getGroupName();
        }
        return "未知群聊";
    }

    public static String getNickname(EventMessage event)
    {
        return event.getHttpApi().getStrangerInfo(event.getSenderId()).getData().getNickname();
    }

    public static String getNickname(EventLocalSendMessage event)
    {
        return event.getHttpApi().getStrangerInfo(event.getId()).getData().getNickname();
    }

    public static String getSelfNickname(EventMessage event)
    {
        return event.getHttpApi().getLoginInfo().getData().getNickname();
    }

    public static String getFixedLengthNickname(String nickname, boolean space, boolean center)
    {
        int nicknameLength = SimpleTextLoggingListener.nicknameLength;

        for (char c : nickname.toCharArray())
        {
            if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HAN) nicknameLength --;
        }

        if (nickname.length() == nicknameLength) return nickname;

        StringBuilder result = new StringBuilder();
        boolean dots = nickname.length() > nicknameLength;

        for (int i = 0; i < nicknameLength; i++)
        {
            if (nicknameLength - i < 4 && dots) result.append(".");
            else if (i < nickname.length())
            {
                result.append(nickname.charAt(i));
            }
            else if (space)
            {
                result.append(" ");
                if (center)
                {
                    i++;
                    if (i < nicknameLength) result.insert(0, " ");
                }
            }
        }

        return result.toString();
    }

    @EventHandler
    public void onPMEvent(EventPrivateMessage event)
    {
        if (StringUtils.countMatches(event.getMessage(), "\n") > 10) return;
        event.getBot().getLogger().log(String.format("%s[%sPM%s] [%s%s%s]%s %s%s >> %s%s", WHITE, YELLOW, WHITE, YELLOW,
                getFixedLengthNickname(getSelfNickname(event), true, true), WHITE, CYAN.getBright(),
                getFixedLengthNickname(getNickname(event), true, false), RED, RESET,
                event.getMessage()));
    }

    @EventHandler
    public void onGMEvent(EventGroupMessage event)
    {
        if (StringUtils.countMatches(event.getMessage(), "\n") > 10) return;
        event.getBot().getLogger().log(String.format("%s[%sGM%s] [%s%s%s]%s %s%s >> %s%s", WHITE, RED, WHITE, RED,
                getFixedLengthNickname(getGroupName(event.getBot(), event.getGroupId()), true, true), WHITE, CYAN.getBright(),
                getFixedLengthNickname(getNickname(event), true, false), RED, RESET,
                event.getMessage()));
    }

    @EventHandler
    public void onDMEvent(EventDiscussMessage event)
    {
        if (StringUtils.countMatches(event.getMessage(), "\n") > 10) return;
        event.getBot().getLogger().log(String.format("%s[%sDM%s] [%s%s%s]%s %s%s >> %s%s", WHITE, WHITE, WHITE, WHITE,
                getFixedLengthNickname(String.valueOf(event.getDiscussId()), true, true), WHITE, CYAN.getBright(),
                getFixedLengthNickname(getNickname(event), true, false), RED, RESET,
                event.getMessage()));
    }

    @EventHandler
    public void onSendPMEvent(EventLocalSendPrivateMessage event)
    {
        if (StringUtils.countMatches(event.getMessage(), "\n") > 10) return;
        event.getBot().getLogger().log(String.format("%s[%sSO%s] [%s%s%s]%s %s%s >> %s%s", WHITE, GREEN, WHITE, GREEN,
                getFixedLengthNickname("这个机器人 ", true, true), WHITE, CYAN.getBright(),
                getFixedLengthNickname(getNickname(event), true, false), RED, RESET,
                event.getMessage()));
    }

    @EventHandler
    public void onSendEvent(EventLocalSendGroupMessage event)
    {
        if (StringUtils.countMatches(event.getMessage(), "\n") > 10) return;
        event.getBot().getLogger().log(String.format("%s[%sSO%s] [%s%s%s]%s %s%s >> %s%s", WHITE, GREEN, WHITE, GREEN,
                getFixedLengthNickname("这个机器人 ", true, true), WHITE, CYAN.getBright(),
                getFixedLengthNickname(getGroupName(event.getBot(), event.getId()), true, false), RED, RESET,
                event.getMessage()));
    }
}
