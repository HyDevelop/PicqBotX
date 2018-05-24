package cc.moecraft.icq;

import cc.moecraft.logger.DebugLogger;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@Data @RequiredArgsConstructor
public class HttpServer
{
    private final int port;
    private final DebugLogger logger;

    //处理GET请求
    private void doGet(DataInputStream reader, OutputStream out) throws Exception
    {

    }

    //处理post请求
    private void doPost(DataInputStream reader, OutputStream out) throws Exception
    {

    }

    public void start() throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(this.port);

        while (true)
        {
            // 获取新的请求
            Socket socket = serverSocket.accept();

            // 读取请求字符
            InputStream inputStream = socket.getInputStream();
            String text = IOUtils.toString(inputStream);
            inputStream.close();

            logger.log("Text: " + text);

            Scanner scanner = new Scanner(text);

            // 获取请求方法 (GET, POST, etc...)
            String line = scanner.nextLine();
            String method = line.substring(0, 4).trim();

            logger.log("Line: " + line);
            logger.log("Method: " + method);

            // OutputStream out = socket.getOutputStream(); 这里不需要返回值
            // String requestPath = line.split(" ")[1];

            if ("GET".equalsIgnoreCase(method))
            {
                // GET
            }
            else if ("POST".equalsIgnoreCase(method))
            {
                // POST
            }

            // 关闭接受
            socket.close();
        }
    }
}
