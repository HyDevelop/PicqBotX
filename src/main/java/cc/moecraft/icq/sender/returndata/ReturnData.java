package cc.moecraft.icq.sender.returndata;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import lombok.Data;

/**
 * 此类由 Hykilpikonna 在 2018/05/25 创建!
 * Created by Hykilpikonna on 2018/05/25!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@Data
public class ReturnData<T extends ReturnPojoBase>
{
    private T data;

    private Long returnCode;

    private ReturnStatus status;
}
