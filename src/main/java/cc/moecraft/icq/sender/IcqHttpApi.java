package cc.moecraft.icq.sender;

import cc.moecraft.icq.event.EventManager;
import cc.moecraft.icq.event.events.local.EventLocalSendDiscussMessage;
import cc.moecraft.icq.event.events.local.EventLocalSendGroupMessage;
import cc.moecraft.icq.event.events.local.EventLocalSendPrivateMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.returndata.RawReturnData;
import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import cc.moecraft.icq.sender.returndata.returnpojo.get.*;
import cc.moecraft.icq.sender.returndata.returnpojo.send.RMessageReturnData;
import cc.moecraft.utils.MapBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/19/24 创建!
 * Created by Hykilpikonna on 2018/19/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class IcqHttpApi extends HttpApiBase
{
    public IcqHttpAsyncApi async;

    public IcqHttpApi(EventManager eventManager, String baseUrl, int port)
    {
        super(eventManager, baseUrl, port);
        async = new IcqHttpAsyncApi(eventManager, baseUrl, port);
    }

    /**
     * 发送请求
     * @param request 请求
     * @param parameters 参数
     * @return 响应
     */
    public JsonElement send(String request, Map<String, Object> parameters)
    {
        return new JsonParser().parse(HttpUtil.post(baseURL + request, new JSONObject(parameters).toString(), 5000));
    }
}
