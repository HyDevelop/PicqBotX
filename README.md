<h1 align="center">
  <br>
  <br>
  PicqBotX
  <h4 align="center">
  一个基于酷Q HTTP插件的Java QQ机器人类库
  </h4>
  <br>
  <br>
  <br>
</h1>

Maven 导入:
--------

没有添加JitPack的Repo的话首先添加Repo, 在pom里面把这些粘贴进去:

    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

然后添加这个库, 把版本号改成Release下的最新版本号即可:

    <dependency>
        <groupId>com.github.hykilpikonna</groupId>
        <artifactId>PicqBotX</artifactId>
        <version>版本号</version>
    </dependency>

环境 & 使用:
--------

##### 注意: 下面的教程是对于Windows的酷Q v5.11.13的, Linux教程以后可能会补充

#### 1. 下载[酷Q](https://cqp.cc/)... (如果有酷QPro的话效果更好哦!)
下载完之后解压到你想要的安装目录<br>
首次启动运行 `cqa.exe` 或者 `cqp.exe`, 登陆机器人的QQ号<br>
然后退出酷Q (右键悬浮窗点退出)<br>

#### 2. 添加[酷Q HTTP插件](https://cqp.cc/t/30748):
把 `.cpk` 文件下载下来放进 `酷Q安装目录\app` 文件夹里<br>
启动酷Q<br>
右键悬浮窗, 然后点击 `应用 -> 应用管理`<br>
列表里现在应该有 `[未启用] HTTP API`, 点击它, 点击启用<br>
启用的时候会提示需要一些敏感权限, 选择继续<br>
启用之后在 `酷Q安装目录\app` 文件夹里会出现 `io.github.richardchien.coolqhttpapi` 文件夹<br>
退出酷Q<br>

#### 3. 配置酷Q HTTP插件:
在 `io.github.richardchien.coolqhttpapi` 文件夹里创建一个叫做 `config.cfg` 的文件<br>
配置文件内写入以下代码<br>


	[general]
	host=0.0.0.0
	port=接收端口
	post_url=http://127.0.0.1:发送端口


把发送端口和接收端口改成你的机器人程序里用的端口 (默认为31091和31092)<br>
如果酷Q要和你的机器人程序分开运行的话, 127.0.0.1改成你的机器人部署的服务器的地址<br>
保存配置文件<br>

#### 4. 启动酷Q, 配置完成!
