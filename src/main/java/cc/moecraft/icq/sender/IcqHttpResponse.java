package cc.moecraft.icq.sender;

import lombok.Data;

import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/31/24 创建!
 * Created by Hykilpikonna on 2018/31/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@Data @Deprecated
public class IcqHttpResponse
{
    private int returnCode;

    private String status;

    private Map<String, Object> data;
}
