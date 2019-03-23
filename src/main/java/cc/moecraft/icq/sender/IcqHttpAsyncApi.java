package cc.moecraft.icq.sender;

import cc.moecraft.icq.event.EventManager;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/19/24 创建!
 * Created by Hykilpikonna on 2018/19/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class IcqHttpAsyncApi extends HttpApiBase
{
    public IcqHttpAsyncApi(IcqHttpApi api, EventManager eventManager, String baseUrl, int port)
    {
        super(eventManager, baseUrl, port);
        super.selfId = api.selfId;
    }

    /**
     * 发送请求
     *
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
