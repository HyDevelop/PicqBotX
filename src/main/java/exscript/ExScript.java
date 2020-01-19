package exscript;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.accounts.BotAccount;
import cc.moecraft.icq.event.Event;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.logger.HyLogger;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ClassUtil;
import exscript.core.ExCommand;
import exscript.core.ExEngine;
import exscript.core.ExFunction;

import javax.script.ScriptException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExScript {

	protected static HyLogger logger;

	protected final PicqBotX bot;
	protected final ExEngine engine;
	protected final ExScriptListener listener;

	protected final Map<Class<? extends Event>, List<ExFunction>> eventFunctions = new HashMap<>();

	public ExScript(PicqBotX bot) {
		if(logger == null) logger = bot.getLogger().getInstanceManager().getLoggerInstance("ExScript", bot.getConfig().isDebug());

		this.bot = bot;
		this.engine = new ExEngine(this);
		this.listener = new ExScriptListener(this);

		this.bot.getEventManager().registerListener(listener);
	}

	/**
	 * 获取事件监听器列表
	 * @param evtCls 事件类型
	 * @return 事件监听器列表
	 */
	protected List<ExFunction> getExFunctionList(Class<? extends Event> evtCls) {
		if(!eventFunctions.containsKey(evtCls)) eventFunctions.put(evtCls, new ArrayList<>());
		return eventFunctions.get(evtCls);
	}

	/**
	 * 注册事件监听器
	 * @JSOnly
	 * @param evtClassName 事件类型
	 * @param functionName 函数名称
	 */
	public void addEvent(String evtClassName, String functionName) {
		final ExFunction ef = new ExFunction(engine, functionName);
		getExFunctionList(ExScriptUtil.getEventClass(evtClassName)).add(ef);
		logger.debug("将函数["+functionName+"]注册为事件["+ ClassUtil.getShortClassName(ExScriptUtil.getEventClass(evtClassName).getCanonicalName())+"]监听器");
	}

	/**
	 * 注册指令执行器
	 * @JSOnly
	 * @param cmdClassName 指令类型
	 * @param commandName  指令名称
	 * @param functionName 函数名称
	 */
	public void addCommand(String cmdClassName, String commandName, String functionName) {
		final ExCommand ec = new ExCommand(engine, ExScriptUtil.getCommandClass(cmdClassName), commandName, functionName);
		bot.getCommandManager().registerCommand(ec.toIcqCommand());
		logger.debug("将函数["+functionName+"]注册为指令["+ ClassUtil.getShortClassName(ExScriptUtil.getCommandClass(cmdClassName).getCanonicalName())+"]执行器");
	}

	/**
	 * 获取IcqHttpApi。
	 * @JSOnly
	 * @return IcqHttpApi
	 */
	public IcqHttpApi api() {
		return bot.getAccountManager().getNonAccountSpecifiedApi();
	}

	/**
	 * 获取指定账号的IcqHttpApi。可能返回null。
	 * @JSOnly
	 * @param id 账号
	 * @return 指定账号的IcqHttpApi
	 */
	public IcqHttpApi api(long id) {
		final BotAccount account = bot.getAccountManager().getAccounts().stream().filter(ac -> ac.getId() == id ).findFirst().orElse(null);
		return account == null ? null : account.getHttpApi();
	}

	/**
	 * 调用JS事件监听器
	 * @param cls 事件类型
	 * @param evt 事件实例
	 */
	protected void callFunction(Class<? extends Event> cls, Event evt) {
		getExFunctionList(cls).forEach(ef -> ef.execute(evt));
	}

	public void eval(String content) {
		try {
			engine.eval(content);
		} catch (ScriptException ex) {
			ex.printStackTrace();
		}
	}

	public void loadJS(File file) {
		eval(FileUtil.readUtf8String(file));
	}

	public void loadJS(String path) {
		eval(ResourceUtil.readUtf8Str(path));
	}

}
