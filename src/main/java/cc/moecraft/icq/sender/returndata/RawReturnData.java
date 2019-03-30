package cc.moecraft.icq.sender.returndata;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/05/25 创建!
 * Created by Hykilpikonna on 2018/05/25!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@Data
public class RawReturnData
{
    @SerializedName("data")
    @Expose
    private JsonElement data;

    @SerializedName("retcode")
    @Expose
    private Long returnCode;

    @SerializedName("status")
    @Expose
    private ReturnStatus status;

    /**
     * 处理数据
     *
     * @param typeOfT Data的POJO数据类型 例子: RLoginInfo.class
     * @param <T> 数据类型
     * @return 处理后的数据
     */
    public <T extends ReturnPojoBase> ReturnData<T> processData(Type typeOfT)
    {
        ReturnData<T> returnData = new ReturnData<>();

        returnData.setReturnCode(returnCode);
        returnData.setStatus(status);
        returnData.setData(new Gson().fromJson(data, typeOfT));

        return returnData;
    }

    /**
     * 处理数据为列表格式
     *
     * @param typeOfT Data的POJO数据类型 例子: RGroupList.class
     * @param <T> 数据类型
     * @return 处理后的数据
     */
    public <T extends ReturnPojoBase> ReturnListData<T> processDataAsList(Type typeOfT)
    {
        ReturnListData<T> returnData = new ReturnListData<>();

        returnData.setReturnCode(returnCode);
        returnData.setStatus(status);

        ArrayList<T> returnList = new ArrayList<>();

        for (JsonElement element : data.getAsJsonArray())
        {
            returnList.add(new Gson().fromJson(element, typeOfT));
        }

        returnData.setData(returnList);

        return returnData;
    }
}
