package taskeren.extrabot.jshorn;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.Event;
import cn.hutool.script.JavaScriptEngine;
import cn.hutool.script.ScriptUtil;
import lombok.Getter;

import javax.script.ScriptException;
import java.util.*;

public class JavaScriptManager {

	@Getter
	protected final PicqBotX bot;
	protected final JavaScriptEngine engine;

	protected final JavaScriptEventListener listener;

	public JavaScriptManager(PicqBotX bot) {
		this.bot = bot;
		this.engine = ScriptUtil.getJavaScriptEngine();
		this.listener = new JavaScriptEventListener(this);

		this.bot.getEventManager().registerListener(listener);
	}

	// Event类索引
	public static final Map<String, Class<? extends Event>> EVENT_MAP = new HashMap<>();

	static {
		/* 初始化Event类索引 */
		final Set<Class<? extends Event>> s = JshornUtil.getAllEventClasses();
		s.add(Event.class); // Set不包含Event自身
		s.forEach(c->EVENT_MAP.put(JshornUtil.getLastClassName(c), c));
	}

	// 函数注册表
	public static final Map<Class<? extends Event>, List<String>> FUNCTIONS = new HashMap<>();

	/**
	 * 获取事件对应的函数列表
	 * @param cls 事件类
	 * @return 事件对应函数列表
	 */
	public static List<String> getFunctionList(Class<? extends Event> cls) {
		if(!FUNCTIONS.containsKey(cls)) FUNCTIONS.put(cls, new ArrayList<>());
		return FUNCTIONS.get(cls);
	}

	/**
	 * 函数注册方法（用于JavaScript调用）
	 * <pre>Java.type("taskeren.extrabot.jshorn.JavaScriptManager").addFunction("EventMessage(事件类短名称或全称)", "msg0(函数名!注意不是函数本身)");</pre>
	 * @param clazz 事件类短名称或全称
	 * @param name  函数名称
	 */
	public static void addFunction(String clazz, String name) throws ClassNotFoundException {
		Class<? extends Event> clsEvt = null;
		if (EVENT_MAP.containsKey(clazz)) {
			clsEvt = EVENT_MAP.get(clazz);
		} else {
			Class<?> cls = Class.forName(clazz);
			if (Event.class.isAssignableFrom(cls)) {
				clsEvt = (Class<? extends Event>) cls;
			} else {
				System.err.println("Cannot cast '" + clazz + "' to Event");
			}
		}
		getFunctionList(clsEvt).add(name);
		System.out.println("Registered Function(" + name + ") as Event(" + JshornUtil.getLastClassName(clsEvt) + ") Listener");
	}

	/**
	 * 调用所有列表中的函数
	 * @param names   函数名列表
	 * @param objects 函数参数
	 */
	void invokeFunctions(List<String> names, Object...objects) {
		for(String name : names) {
			try {
				engine.invokeFunction(name, objects);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 调用函数
	 * @param cls 事件类
	 * @param evt 事件实例
	 */
	public void callFunction(Class<? extends Event> cls, Event evt) {
		System.out.println(cls + " called");
		invokeFunctions(getFunctionList(cls), evt);
	}

	/**
	 * 执行函数
	 * @param js 函数内容
	 */
	public void eval(String js) {
		try {
			eval0(js);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 执行函数
	 * @param js 函数内容
	 * @throws ScriptException 函数报错
	 */
	public void eval0(String js) throws ScriptException {
		engine.eval(js);
	}

}
