package exscript;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.*;
import cc.moecraft.icq.event.Event;
import cc.moecraft.icq.event.events.message.*;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.moecraft.icq.user.User;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

import java.util.*;

public class ExScriptUtil {


	protected static final String PKG_EVENT = "cc.moecraft.icq.event.events";
	protected static final String PKG_COMMAND = "cc.moecraft.icq.command.interfaces";

	protected static final Map<String, Class<? extends Event>> eventIndex = MapUtil.newHashMap();
	protected static final Map<String, Class<? extends IcqCommand>> commandIndex = MapUtil.newHashMap();

	static {
		initEventIndex();
		initCommandIndex();
	}

	private static void initEventIndex() {
		Set<Class<?>> var0 = new HashSet<>(ClassScanner.scanPackageBySuper(PKG_EVENT, Event.class));
		var0.add(Event.class);
		var0.forEach(cls -> {
			if(Event.class.isAssignableFrom(cls)) {
				eventIndex.put(cls.getCanonicalName(), (Class<? extends Event>) cls);
				eventIndex.put(getSuperShortedName(cls.getCanonicalName()), (Class<? extends Event>) cls);
			}
			else {
				System.err.println(cls.getCanonicalName()+" isn't assignable to Event.");
			}
		});
	}

	private static void initCommandIndex() {
		Set<Class<?>> var0 = ClassScanner.scanPackageBySuper(PKG_COMMAND, IcqCommand.class);
		var0.forEach(cls -> {
			if(IcqCommand.class.isAssignableFrom(cls)) {
				commandIndex.put(cls.getCanonicalName(), (Class<? extends IcqCommand>) cls);
				commandIndex.put(getSuperShortedName(cls.getCanonicalName()), (Class<? extends IcqCommand>) cls);
			}
			else {
				System.err.println(cls.getCanonicalName()+" isn't assignable to IcqCommand.");
			}
		});
	}

	public static String getSuperShortedName(String className) {
		return CollectionUtil.getLast(StrUtil.split(className, '.'));
	}

	public static Class<? extends Event> getEventClass(String className) {
		return eventIndex.get(className);
	}

	public static Class<? extends IcqCommand> getCommandClass(String className) {
		return commandIndex.get(className);
	}
}
