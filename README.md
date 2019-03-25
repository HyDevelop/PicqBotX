<h1 align="center">
  <br>
  <br>
  PicqBotX
  <h4 align="center">
  一个基于 酷Q HTTP 插件 的 Java QQ 机器人类库
  </h4>
  <h5 align="center">
<a href="#maven">Maven 导入</a>&nbsp;&nbsp;
<a href="#environment">环境</a>&nbsp;&nbsp;
<a href="#development">开发</a>&nbsp;&nbsp;
<a href="#qa">常见问题</a>&nbsp;&nbsp;
<a href="#license">开源条款</a>
</h5>
  <br>
  <br>
  <br>
</h1>



<a name="maven"></a>
Maven 导入:
--------

没有添加 JitPack 的 Repo 的话首先添加 Repo, 在 pom 里面把这些粘贴进去:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

然后添加这个库:

```xml
<dependency>
    <groupId>com.github.hydevelop</groupId>
    <artifactId>PicqBotX</artifactId>
    <version>3.0.4.810</version>
</dependency>
```

然后 Reimport 之后就导入好了!

<br>

<a name="gradle"></a>
Gradle 导入:
--------

没有添加 JitPack 的 Repo 的话首先添加 Repo, 在 pom 里面把这些粘贴进去:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

然后添加这个库:

```gradle
dependencies {
    implementation 'com.github.hydevelop:PicqBotX:3.0.4.810'
}
```

<!-- 每次更新都要手动改这些版本号好烦的_(:з」∠)_... -->

#### [其他导入(SBT / Leiningen)](https://jitpack.io/#hydevelop/PicqBotX/3.0.4.810)

<br>

<a name="environment"></a>
配置环境:
--------

##### 注意: 下面的教程适用于 Windows `酷Q v5.13.2`, Linux 教程以后可能会进行补充<br>还有, HTTP 插件支持的版本是`v4.8.0`, `2019/03/25`刚更新

#### 1. 下载 [酷Q](https://cqp.cc/)... (如果有 酷Q Pro 的话效果更好哦!)

* 下载完后解压到你想安装的目录下
* 首次启动请运行 `cqa.exe` 或 `cqp.exe`, 并登陆机器人的 QQ 号
* 然后退出 酷Q (右键悬浮窗点退出)

#### 2. 添加 [酷Q HTTP 插件](https://github.com/richardchien/coolq-http-api/releases):

* 把 `.cpk` 文件下载下来, 放进 `酷Q安装目录\app` 文件夹里
* 启动 酷Q
* 右键悬浮窗, 然后点击 `应用 -> 应用管理`
* 列表里现在应该有 `[未启用] HTTP API`, 点击它, 点击启用
* 启用的时候会提示需要一些敏感权限, 选择继续
* 启用之后在 `酷Q安装目录\app` 文件夹里会出现 `io.github.richardchien.coolqhttpapi` 文件夹
* 退出 酷Q<br>

#### 3. 配置 酷Q HTTP 插件:

* 在 `io.github.richardchien.coolqhttpapi` 文件夹里创建一个文件名为 `config.cfg` 的配置文件
* 并在其中写入以下配置代码

```
[general]
host=0.0.0.0
port=酷Q端口
post_url=http://127.0.0.1:Picq端口
```

* 把`酷Q端口`和`Picq端口`改成你的机器人程序里用的端口 (测试机器人的`酷Q端口`是`31091`, `Picq端口`是`31092`)
* 如果 酷Q 要和你的机器人程序分开运行的话, 请把`127.0.0.1`改成你的机器人部署的服务器的地址
* 保存配置文件

#### 4. 配置完成! 启动 酷Q!


<br>

<a name="development"></a>
开发:
--------

##### [开发文档 (JavaDocs)](https://hydevelop.github.io/picqbotx/javadocs/)

#### 启动机器人 (Main类):

在 main 方法中, 先创建一个机器人配置对象: <br>

```java
// 创建机器人配置 ( 传入Picq端口 )
PicqConfig config = new PicqConfig(31092);
```

**注意: 这里的`Picq端口`要和酷Q`config.cfg`配置里面设置的`Picq端口`一样!!**<br>
之后可以通过`config.set...`来配置机器人了.<br>
可配置的项目如下:




```java
// 创建机器人对象 ( 传入机器人配置对象 )
PicqBotX bot = new PicqBotX(config);
```
( 这是因为 酷Q 需要发送到 Picq 的接收端口去, 而不是发送到对方的发送端口ww )<br>
重要的事情要加粗说三遍了w<br>
已经有4个人问我为什么端口不对了噗...<br>

例子: 如果你的 酷Q 的 `port` 写的是 `12345`, 然后 `post_url` 写的是 `http://127.0.0.1:54321`,<br>
      那么你的传入 PicqBotX 的 `postPort` 就应该是 `12345`, 然后 `socketPort` 就应该是 `54321`.

注册监听器: <br>
可以注册多个监听器 <br>
监听器的构造器可以有参数

```java
bot.getEventManager().registerListener(new 监听器());
```

启用指令管理器: <br>
如果不想用自带的指令管理器, 想自己写指令管理器, 不写这行就好了. <br>
不过这个指令管理器真的很方便哦! 注册都完全是自动的_(:з」∠)_ <br>
因为完全自动注册, 所以只要这一行就行了!

```java
// 启用指令管理器, 启用的时候会自动注册指令
// 这些字符串是指令前缀, 比如 !help 的前缀就是"!"
bot.enableCommandManager("bot -", "!", "/", "~");
```

启动机器人: 

```java
// 启动机器人, 这个因为会占用线程, 所以必须放到最后
bot.startBot(); 
```

嗯, 然后就没了!<br>
就这么简单方便!!<br>
完整例子代码:

```java
public class TestBot
{
    public static void main(String[] args)
    {
        // 创建机器人对象 ( 信息发送URL, 发送端口, 接收端口, 是否DEBUG )
        PicqBotX bot = new PicqBotX("127.0.0.1", 31091, 31092, false);

        try
        {
            bot.getEventManager()
                    .registerListener(new TestListener()) // 注册监听器
                    .registerListener(new RequestListener()); // 可以注册多个监听器
                    
            if (!bot.isDebug()) bot.getEventManager().registerListener(new SimpleTextLoggingListener()); // 条件下注册监听器

            // 启用指令管理器, 启用的时候会自动注册指令
            // 这些字符串是指令前缀, 比如!help的前缀就是!
            bot.enableCommandManager("bot -", "!", "/", "~");

        // 启动机器人, 这个因为会占用线程, 所以必须放到最后
            bot.startBot(); 
        }
        catch (HttpServerStartFailedException | VersionIncorrectException | IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace(); // 启动失败, 结束程序
        }
    }
}
```

##### 其他例子去看[TestBot](https://github.com/hydevelop/PicqBotX/blob/master/src/test/java/cc/moecraft/test/icq/TestBot.java)!


#### 监听事件:

```java
public class 类名随意 extends IcqListener // 必须继承 IcqListener 监听器类
{
    @EventHandler // 这个注解必须加, 用于反射时判断哪些方法是事件方法的, 不用 @Override
    public void 方法名随意(事件类名 event) // 想监听什么事件就写在事件类名这里, 一个方法只能有一个事件参数
    {
    // 处理
    }

    @EventHandler
    public void 方法名随意(事件类名 event) // 同一个类下可以无限添加监听器方法
    ...
}
```

嗯... 创建一个类, 写成上面那个样子就行了_(:з」∠)_

可用事件:

| 事件类名                 | 事件介绍 |
| :------------ | :------------ |
| Event                 | 所有事件(不推荐监听) |
| EventMessage          | 所有消息事件 |
| EventDiscussMessage     | 讨论组消息事件 |
| EventGroupMessage     | 群聊消息事件 |
| EventPrivateMessage     | 私聊消息事件 |
| EventNotice | 所有提醒事件 |
| EventNoticeFriendAdd | 加好友提醒 |
| EventNoticeGroupAdminChange | 所有群管理员数量更改事件 |
| EventNoticeGroupAdminSet | 群员被设为管理员事件 |
| EventNoticeGroupAdminRemove | 群员被取消管理员事件 |
| EventNoticeGroupMemberChange | 所有群员数量更改事件 |
| EventNoticeGroupMemberDecrease | 所有群员数量减少事件 |
| EventNoticeGroupMemberKick | 群员被踢事件 |
| EventNoticeGroupMemberKickBot | 自己被踢事件 |
| EventNoticeGroupMemberLeave | 群员主动退出事件 |
| EventNoticeGroupMemberIncrease | 所有群员数量增加事件 |
| EventNoticeGroupMemberApprove | 群员被同意进群事件 |
| EventNoticeGroupMemberInvite | 群员被邀请进群事件 |
| EventNoticeGroupUpload | 上传群文件事件 |
| EventRequest | 所有请求事件 |
| EventFriendRequest | 加好友请求事件 |
| EventGroupAddRequest | 加群请求事件 |
| EventGroupInviteRequest | 拉你入群请求事件 |
| EventLocal | 所有本地事件 |
| EventLocalSendMessage | 所有本地向外发送的事件 |
| EventLocalSendDiscussMessage | 发送讨论组消息事件 |
| EventLocalSendGroupMessage | 发送群聊消息事件 |
| EventLocalSendPrivateMessage | 发送私聊消息事件 |
| EventLocalHttpRecieveEvent | 接收HTTP请求事件 |
| EventLocalHttpFailEvent | 接收HTTP请求失败事件 |

##### 例子:

```java
public class TestListener extends IcqListener
{
    @EventHandler
    public void onPMEvent(EventPrivateMessage event)
    {
    System.out.println("接到消息");

    // 判断消息是不是这段文字, 如果是就回复那段文字, 很简单的测试_(:з」∠)_
    if (event.getMessage().equals("你以为这是 yangjinhe/maintain-robot?"))
        event.respond("其实是我 HyDevelop/PicqBotX 哒!");
    }
}
```

##### 用事件监听实现消息替换过滤:

```java
public class TestFilter extends IcqListener
{
    @EventHandler
    public void onAllLocalMessageEvent(EventLocalSendMessage event) // 监听所有发送消息的事件
    {
        // 获取消息
        String message = event.getMessage();
        
        // 这里可以做任何处理
        // 我把所有"%prefix%"变量替换成了"!"
        message = message.replace("%prefix%", "!");
        
        // 设置消息, 因为这个事件是在发送之前执行的, 所以这样设置的消息能生效
    // 设置为 null 就能拦截了
        event.setMessage(message);
    }
}
```

<br>

#### 发送信息:

需要一个 bot 对象, **请不要使用全局变量存 bot 对象**<br>
其实监听器里的话直接用 `event.getBot()` 就行了, 不是监听器的话也很少会直接用到 bot 对象...<br>
返回数据为 `ReturnData<Pojo 数据类>` 形式, 获取数据的话用 `response.getData()` 就行了.<br>

##### 如果已经封装过了的话, 这样发送:
```java
ReturnData<RMessageReturnData> response = event.getHttpApi().封装方法名(参数); // response就是响应数据
```
##### 例子:
```java
ReturnData<RMessageReturnData> response = event.getHttpApi().sendPrivateMsg(565656, "hi"); // 给565656发送hi
```
##### 如果没有封装过的话, 或者想手动添加参数对的话, 这样发送:
```java
ReturnData<RMessageReturnData> response = event.getHttpApi().send(请求目标, 参数); // 请求目标在IcqHttpApi里面有常量
```
##### 例子:
```java
ReturnData<RMessageReturnData> response = event.getHttpApi().send(IcqHttpApi.SEND_PRIVATE_MSG, 
    "user_id", 565656,
    "message", "hi",
    "auto_escape", false); // 这个参数因为不常用就没有封装, 所以要用的话这样发送
```

##### 复杂的消息建造 (比如图片什么的) 用 MessageBuilder 类:

注意: .add(object) 方法对于所有类型的对象都有效, 只要能 toString 就行

```java
new MessageBuilder()
    .add("添加一条字符串消息")
    .add(123)
    .add(16.5f)
    .newLine() // 换行
    .add(new ComponentImage("此处填图片文件路径或者 URL")) // 图片组件
    .add(new ComponentImageBase64("此处填图片 Base64 码")) // Base64 图片组件
    .add(new ComponentRecord("此处填语音文件路径或者 URL")) // 语音组件
    .toString();
```

可用组件 (需要 酷Q Pro 才能用, 详细介绍请看[CQ码](https://d.cqp.me/Pro/CQ%E7%A0%81)):

| 组件类名                 | 组件介绍 |
| :------------ | :------------ |
| ComponentAt | @组件 |
| ComponentBFace | 原创表情组件 |
| ComponentEmoji | Emoji 表情组件 |
| ComponentFace | QQ 表情组件 |
| ComponentImage | 图片组件 |
| ComponentImageBase64 | Base64 编码图片组件 |
| ComponentSFace | 小表情组件 |
| ComponentShare | 分享链接组件 |
| ComponentDice | 掷骰子组件 (只能单独发送) |
| ComponentMusic | 音乐组件 (只能单独发送) |
| ComponentRecord | 语音组件 (只能单独发送) |
| ComponentRockPaperSissors | 猜拳组件 (只能单独发送) |
| ComponentShake | 抖一抖组件 (只能单独发送) |

<br>

#### 指令

每一个指令需要单独创建一个类, 实现一个指令接口<br>
如果这个指令在所有消息环境内都能执行的话, 实现 `EverywhereCommand` 类<br>
如果这个指令只需要在 群 或者 讨论组 或者 私聊 执行的话, <br>
实现 `GroupCommand` 或者 `DiscussCommand` 或者 `PrivateCommand` 就可以了<br>
因为启动的时候自动注册指令, 不需要做任何其他事情, 只创建类就能用了!

例子:

```java
public class CommandVersion implements EverywhereCommand // 实现EverywhereCommand就是无论私聊群聊还是讨论组都能收到的指令
{
    // 指令属性
    @Override
    public CommandProperties properties()
    {
        // 这个括号里填指令名和其他名称, 指令名必须至少有一个
        // 这个的话, 用"!v", "!version", 和"!版本"都能触发指令 (感叹号为你设置的前缀, 不一定必须要感叹号)
        return new CommandProperties("version", "v", "版本");
    }
    
    // 机器人接到指令后会执行这个方法 ( 实现不同的接口的话方法名不一定一样 )
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args)
    {
        // 处理, 返回值会自动回复回去
        // 这里因为这个指令是用来查版本的, 所以直接返回字符串了
        return "TestBot - PicqBotX v3.0.4.810";
    }
}
```

<a name="qa"></a>
常见问题:
--------

#### Q: 为什么刚加的群不能回复消息?

因为用户信息在账号管理器里缓存了啦...<br>
不会自动刷新的...<br>
监听加群的事件然后<br>
```event.getBot().getAccountManager().refreshCache();``` 就行啦w

<br>

#### Q: 如何在事件外获取`IcqHttpApi`对象用来发消息?

因为有多账号管理,<br>
每个账号都有单独的`IcqHttpApi`对象...<br>
如果要用一个不指定账号的API对象的话,<br>
`bot.getAccountManager().getNonAccountSpecifiedApi()`<br>
有多个账号的话不推荐用这个.<br>

如果要用所有账号的话,<br>
`bot.getAccountManager().getAccounts()` 获取个列表,<br>
然后循环过去就好啦w<br>

如果要用一个指定账号的话,<br>
还是像上面那样循环过去,<br>
然后每个账号判断`getId()`好啦w<br>

<br>
<br>

#### 如果发现 Bug 的话, 请开一个 [issue](https://github.com/HyDevelop/PicqBotX/issues) 说明问题
#### 或者联系我 QQ: 565656 哦!
#### 或者加开发群 498386389

<br>

<a name="license"></a>
[开源条款](https://choosealicense.com/licenses/gpl-3.0/): GNU / GPL
--------

