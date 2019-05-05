package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.moecraft.utils.ArrayUtils;
import cn.hutool.core.util.ArrayUtil;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.ArrayList;

/**
 * TODO: No description yet...
 * <p>
 * Class created by the HyDEV Team on 2019-05-05!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-05-05 13:16
 */
public class CommandJS implements GroupCommand
{
    private static final Long[] ALLOWED_IDS =
    {
            871674895L,
            1339638753L,
            714026292L
    };

    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args)
    {
        if (!ArrayUtil.contains(ALLOWED_IDS, sender.getId()))
        {
            return "啊... 你好w";
        }

        NashornScriptEngineFactory factory =  new NashornScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine();

        // 环境变量w
        engine.put("event", event);
        engine.put("sender", sender);
        engine.put("group", group);
        engine.put("command", command);
        engine.put("args", args);

        engine.put("bot", event.getBot());
        engine.put("account", event.getBotAccount());
        engine.put("api", event.getBotAccount().getHttpApi());

        try
        {
            Object result = engine.eval(ArrayUtils.getTheRestArgsAsString(args, 0));
            return result == null ? "执行成功!" : result.toString();
        }
        catch (ScriptException e)
        {
            return e.getLocalizedMessage();
        }
    }

    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("jsexec");
    }
}
