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
 * Meow!
 *
 * @author Hykilpikonna
 */
@Data
public class RawReturnData
{
    @SerializedName("data")
    @Expose
    public JsonElement data;

    @SerializedName("retcode")
    @Expose
    public Long returnCode;

    @SerializedName("status")
    @Expose
    public ReturnStatus status;

    /**
     * 处理数据
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
}
