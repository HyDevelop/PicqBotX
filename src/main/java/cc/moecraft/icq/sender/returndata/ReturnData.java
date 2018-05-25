package cc.moecraft.icq.sender.returndata;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import lombok.Data;

/**
 * 此类由 Hykilpikonna 在 2018/05/25 创建!
 * Created by Hykilpikonna on 2018/05/25!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@Data
public class ReturnData<T extends ReturnPojoBase>
{
    public T data;
    public Long returnCode;
    public ReturnStatus status;
}
