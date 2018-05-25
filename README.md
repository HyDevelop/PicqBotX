<h1 align="center">
  <br>
  <br>
  PicqBotX
  <h4 align="center">
  一个基于酷Q HTTP插件的Java QQ机器人类库
  </h4>
  <h5 align="center">
<a href="https://circleci.com/gh/hykilpikonna/PicqBotX">CircleCI</a>&nbsp;&nbsp;
<a href="#maven">Maven导入</a>&nbsp;&nbsp;
<a href="#environment">环境</a>&nbsp;&nbsp;
<a href="#development">开发</a>&nbsp;&nbsp;
<a href="#license">开源条款</a>
</h5>
  <br>
  <br>
  <br>
</h1>



<a name="maven"></a>
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


<br>

<a name="environment"></a>
配置环境:
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


把发送端口和接收端口改成你的机器人程序里用的端口 (测试机器人的接收为31091, 发送31092)<br>
如果酷Q要和你的机器人程序分开运行的话, 127.0.0.1改成你的机器人部署的服务器的地址<br>
保存配置文件<br>

#### 4. 启动酷Q, 配置完成!


<br>

<a name="development"></a>
开发:
--------

#### 启动机器人 (Main类):

	public class 类名
	{
	    // 这里其实不一定要用psvm, 任何其他方式只要能启动就行
	    public static void main(String[] args) throws HttpServerStartFailedException
	    {
		PicqBotX bot = new PicqBotX(发送到的URL, 发送到的端口号, 接收的端口号, 后台是否显示debug消息);
		
		bot.getEventManager().registerListener(new 监听器类()); // 注册事件监听器
		
		bot.startBot(); // 启动 (会占用主线程, 如果要同时运行其他东西的话, 需要异步)
	    }
	}
	
##### 例子:

	public class TestBot
	{
	    public static void main(String[] args)
	    {
		PicqBotX bot = new PicqBotX("127.0.0.1", 31091, 31092, true);
		try
		{
		    bot.getEventManager().registerListener(new TestListener());
		    bot.startBot();
		}
		catch (HttpServerStartFailedException e)
		{
		    e.printStackTrace();
		}
	    }
	}

#### 监听事件:

	public class 类名随意 extends IcqListener // 继承监听器类
	{
	    @EventHandler // 这个注解必须加, 用于反射时判断哪些方法是事件方法的, 因为是反射就不用@Override了
	    public void 方法名随意(事件类名 event) // 想监听什么方法就写在这里, 一个方法只能有一个事件对象
	    {
		// 处理
	    }
	    
	    @EventHandler
	    public void 方法名随意(事件类名 event) // 同一个类下可以添加无限个监听器方法
	    ...
	}
	
嗯... 创建一个类, 写成上面那个样子就行了_(:з」∠)_

##### 例子:

	public class TestListener extends IcqListener
	{
	    @EventHandler
	    public void onPMEvent(EventPrivateMessage event)
	    {
		System.out.println("接到消息");

		if (event.getMessage().equals("你以为这是yangjinhe/maintain-robot?"))
		    event.respond("其实是我Hykilpikonna/PicqBotX哒!");
	    }
	}
	
#### 发送信息:

需要一个bot对象, **请不要使用全局变量存bot对象**<br>
其实监听器里的话直接用 `event.getBot()` 就行了, 不是监听器的话也很少会直接用到bot对象...<br>

##### 如果已经封装过了的话, 这样发送:

	JsonElement response = event.getBot().getHttpApi().封装方法名(参数); // response就是响应数据

##### 例子:

	JsonElement response = event.getBot().getHttpApi().sendPrivateMsg(871674895, "hi"); // 给871674895发送hi
	
##### 如果没有封装过的话, 或者想手动添加参数对的话, 这样发送:

	JsonElement response = event.getBot().getHttpApi().send(请求目标, 参数); // 请求目标在IcqHttpApi里面有常量

##### 例子:

	JsonElement response = event.getBot().getHttpApi().send(IcqHttpApi.SEND_PRIVATE_MSG, 
                "user_id", 871674895,
                "message", "hi",
                "auto_escape", false); // 这个参数因为不常用就没有封装, 所以要用的话这样发送

#### 如果有Bug的话, 联系我QQ: 871674895哦!


<br>

<a name="license"></a>
[开源条款](https://choosealicense.com/licenses/gpl-3.0/): GNU / GPL
--------

