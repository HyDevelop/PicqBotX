package cc.moecraft.test.icq;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import exscript.ExScript;
import org.junit.Ignore;
import org.junit.Test;

/**
 * ExScript测试类
 *
 * @author Taskeren
 */
public class TestExScript {

	// Bot Configuration
	public static final int listenPort = 31092;
	public static final String postUrl = "localhost";
	public static final int postPort   = 31090;

	@Test
	@Ignore
	public void testExScript() {

		final PicqBotX bot = new PicqBotX(new PicqConfig(listenPort).setDebug(true));
		bot.addAccount("ExBot", postUrl, postPort);

		// 必须先启用指令管理器才能使用ExScript指令功能
		bot.enableCommandManager("/");

		// 先实例一个ExScript
		final ExScript es = new ExScript(bot);

		// 在Resource里直接放JS就会读取（包括test）
		// 当然可以读取外部JS
		es.loadJS("test.js");

		// 除了直接运行JS以外，还可以直接执行JS脚本
		es.eval("function test() { return 0; }");
	}

}
