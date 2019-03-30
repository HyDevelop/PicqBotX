package cc.moecraft.icq.exceptions;

import cc.moecraft.logger.HyLogger;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HttpServerException extends RuntimeException
{
    private HyLogger logger;

    public HttpServerException(HyLogger logger, Exception cause)
    {
        super(cause);
        this.logger = logger;
    }

    @Override
    public void printStackTrace()
    {
        if (getCause().getMessage() != null && getCause().getMessage().toLowerCase().contains("address already in use:"))
        {
            logger.error("端口已经被占用了... 是不是写错了?");
            logger.error("默认的31091是发送, 31092是接收哦!");
        }
        else
        {
            super.printStackTrace();
            getCause().printStackTrace();
        }
    }
}
