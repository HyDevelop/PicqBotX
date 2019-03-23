package cc.moecraft.icq.receiver;

import cc.moecraft.icq.exceptions.HttpServerException;

/**
 * The class {@code HttpServerInterface} is a http server to receive and
 * parse the package sent from the http plugin of CoolQ.
 * <p>
 * Class created by the HyDEV Team on 2019-03-23!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-23 12:55
 */
public abstract class HttpServer
{


    /**
     * 在当前线程启动HTTP服务器
     *
     * @throws HttpServerException 启动失败
     */
    public abstract void start() throws HttpServerException;
}
