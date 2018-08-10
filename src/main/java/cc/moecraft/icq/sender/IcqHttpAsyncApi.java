package cc.moecraft.icq.sender;

import cc.moecraft.icq.event.EventManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.json.JSONObject;

import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/19/24 创建!
 * Created by Hykilpikonna on 2018/19/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class IcqHttpAsyncApi extends HttpApiBase
{
    public IcqHttpAsyncApi(EventManager eventManager, String baseUrl, int port)
    {
        super(eventManager, baseUrl, port);
    }

    /**
     * 发送请求
     * @param request 请求
     * @param parameters 参数
     * @return 响应
     */
    @Override
    public JsonElement send(String request, Map<String, Object> parameters)
    {
        return new JsonParser().parse(HttpUtil.post(baseURL + request + "_async", new JSONObject(parameters).toString(), 5000));
    }
}
