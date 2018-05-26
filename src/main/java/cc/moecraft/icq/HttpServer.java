package cc.moecraft.icq;

import cc.moecraft.icq.exceptions.HttpServerStartFailedException;
import cc.moecraft.logger.AnsiColor;
import cc.moecraft.logger.DebugLogger;
import lombok.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@Data
public class HttpServer
{
    private final int port;
    private final DebugLogger logger;
    private final PicqBotX bot;

    public HttpServer(int port, PicqBotX bot)
    {
        this.bot = bot;
        this.port = port;
        this.logger = bot.getLogger();
    }

    /**
     * 处理请求
     * @param data JSON
     */
    private void process(String data)
    {
        bot.getEventManager().call(data);
    }

    /**
     * 启动HTTP服务器
     * @throws HttpServerStartFailedException 启动失败
     */
    public void start() throws HttpServerStartFailedException
    {
        ServerSocket serverSocket;
        try
        {
            serverSocket = new ServerSocket(this.port);
            logger.log(AnsiColor.GREEN + "启动成功! 开始接收消息...");
        }
        catch (IOException e)
        {
            throw new HttpServerStartFailedException(logger, e);
        }

        Socket socket = null;
        OutputStream out = null;

        while (true)
        {
            try
            {
                // 关闭上次的Socket, 这样就能直接continue了
                if (out != null) out.close();
                if (socket != null && !socket.isClosed()) socket.close();

                // 获取新的请求
                socket = serverSocket.accept();

                // 读取请求字符
                InputStream inputStream = socket.getInputStream();
                DataInputStream reader = new DataInputStream(inputStream);
                // BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                out = socket.getOutputStream();

                String line = reader.readLine();
                if (line == null || line.isEmpty()) continue;

                // 读取请求信息
                String[] info = line.split(" ");
                String method = info[0];
                String requestUrl = info[1];
                String httpVersion = info[2];

                if (!method.equalsIgnoreCase("post")) continue;

                // 读取信息
                ArrayList<String> otherInfo = readOtherInfo(reader);
                String contentType = "UNINITIALIZED";
                String charset = "UNINITIALIZED";
                String userAgent = "UNINITIALIZED";
                int contentLength = -1;

                for (String oneInfo : otherInfo)
                {
                    if (oneInfo.contains("Content-Type: "))
                    {
                        oneInfo = oneInfo.replace("Content-Type: ", "");
                        if (!oneInfo.contains("application/json")) continue;
                        if (!oneInfo.contains("charset=UTF-8")) continue;

                        String[] split = oneInfo.split("; ");
                        contentType = split[0];
                        charset = split[1];
                    }
                    else if (oneInfo.contains("User-Agent: ")) userAgent = oneInfo.replace("User-Agent: ", "");
                    else if (oneInfo.contains("Content-Length: ")) contentLength = Integer.parseInt(oneInfo.replace("Content-Length: ", ""));
                }

                // 验证信息
                if (contentType.equals("UNINITIALIZED") || !contentType.equals("application/json")) continue;
                if (charset.equals("UNINITIALIZED") || !charset.equals("charset=UTF-8")) continue;
                if (userAgent.equals("UNINITIALIZED") || !userAgent.equals("CQHttp/4.0.0-alpha.4")) continue;

                // 获取Post数据
                String data = "UNINITIALIZED";
                byte[] buffer;
                int size = 0;

                if (contentLength != 0)
                {
                    buffer = new byte[contentLength];
                    while(size < contentLength) buffer[size++] = (byte) reader.read();
                    data = new String(buffer, 0, size);
                }

                // 输出Debug消息
                if (bot.isDebug())
                {
                    logger.debug("收到新请求: " + line);
                    logger.debug("- 请求方法: " + method);
                    logger.debug("- 请求URL : " + requestUrl);
                    logger.debug("- HTTP版本: " + httpVersion);
                    logger.debug("- 其他信息: " + otherInfo);
                    logger.debug("- 数据: " + data);
                }

                process(data);

                sendResponseAndClose(out, "[]");

                // 关闭接收
                socket.close();
            }
            catch (IOException e)
            {
                logger.error("请求接收失败: ");
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取所有行
     * @param reader 读取器
     * @return 所有行的列表
     */
    public static ArrayList<String> readOtherInfo(DataInputStream reader)
    {
        ArrayList<String> result = new ArrayList<>();

        while (true)
        {
            try
            {
                String line = reader.readLine();
                if (line.isEmpty()) break;

                result.add(line);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                break;
            }
        }

        return result;
    }

    /**
     * 回复JSON
     * @param out 输出流
     * @param jsonString JSON字符串
     */
    public OutputStream sendResponseAndClose(OutputStream out, String jsonString) {
        String response = "";
        response += "HTTP/1.1 204 OK\n";
        response += "Content-Type: application/json; charset=UTF-8\n";
        response += "\n";

        try
        {
            out.write(response.getBytes());
            // out.write(jsonString.getBytes());
            out.flush();

            out.close();
        }
        catch (IOException e)
        {
            logger.debug("消息发送失败: " + e.toString());
        }

        return null;
    }
}
