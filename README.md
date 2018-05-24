<h1 align="center">
  <br>
  <br>
  PicqBotX
  <h4 align="center">
  一个基于酷Q HTTP插件的QQ机器人类库
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
