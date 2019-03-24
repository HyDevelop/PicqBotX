package cc.moecraft.icq.receiver;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.events.local.EventLocalHttpFail.Reason;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static cc.moecraft.icq.PicqConstants.HTTP_API_VERSION_DETECTION;
import static cc.moecraft.icq.event.events.local.EventLocalHttpFail.Reason.*;
import static cc.moecraft.icq.utils.NetUtils.read;
import static cc.moecraft.logger.format.AnsiColor.GREEN;
import static java.lang.Integer.parseInt;

/**
 * TODO: No description yet...
 * <p>
 * Class created by the HyDEV Team on 2019-03-23!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-23 13:01
 */
@Getter
public class PicqHttpServer extends HttpServer
{
    /**
     * Socket服务器对象
     */
    private ServerSocket server;

    /**
     * Socket对象
     */
    private Socket socket;

    /**
     * 构造一个Http服务器
     *
     * @param port 端口
     * @param bot 机器人
     */
    public PicqHttpServer(int port, PicqBotX bot)
    {
        super(port, bot);
    }

    @Override
    protected void init() throws Exception
    {
        server = new ServerSocket(getPort());
        logger.log(GREEN + "启动成功! 开始接收消息...");
    }

    @Override
    protected void accept() throws Exception
    {
        // 获取新的请求
        socket = server.accept();
    }

    @Override
    protected boolean validate() throws Exception
    {
        // 读取请求字符
        InputStream in = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        // 读第一行
        String line = reader.readLine();
        if (line == null || line.isEmpty())
        {
            return failed(Reason.REQUEST_IS_EMPTY);
        }

        // 读取请求信息
        String[] info = line.split(" ");

        // 必须是POST
        if (!info[0].equalsIgnoreCase("post"))
        {
            return failed(INCORRECT_REQUEST_METHOD);
        }

        // 内容长度
        int contentLength = 0;

        // 判断Header是不是对的, 顺便获取Content-Length
        while ((line = reader.readLine()) != null && !line.isEmpty())
        {
            if (!validateLine(line))
            {
                return false;
            }
            if (line.contains("Content-Length: "))
            {
                contentLength = parseInt(line.replace("Content-Length: ", ""));
            }
        }

        // 读取内容
        String content = read(reader, contentLength);

        return true;
    }

    /**
     * 验证一行
     *
     * @param line 一行
     * @return 是否正确
     */
    private boolean validateLine(String line)
    {
        if (line.contains("Content-Type: "))
        {
            line = line.toLowerCase();
            if (!line.contains("application/json"))
            {
                return failed(INCORRECT_APPLICATION_TYPE);
            }
            if (!line.contains("charset=utf-8"))
            {
                return failed(INCORRECT_CHARSET);
            }
        }
        else if (line.contains("User-Agent: ") &&
                !line.replace("User-Agent: ", "").matches(HTTP_API_VERSION_DETECTION))
        {
            return failed(INCORRECT_VERSION);
        }
        return true;
    }

    @Override
    protected void close() throws Exception
    {
        if (socket != null && !socket.isClosed())
        {
            socket.close();
        }
    }


}
