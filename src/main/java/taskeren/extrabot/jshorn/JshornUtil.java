package taskeren.extrabot.jshorn;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.*;
import cc.moecraft.icq.event.Event;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.moecraft.icq.user.User;
import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JshornUtil {

	public static String getLastClassName(Class<?> cls) {
		List<String> list = StrUtil.split(cls.getCanonicalName(), '.');
		return list.get(list.size()-1);
	}

	/**
	 * @return 所有继承Event的类
	 */
	public static Set<Class<? extends Event>> getAllEventClasses() {
		final Set<Class<? extends Event>> s = new HashSet<>();
		final Set<Class<?>> s0 = ClassScanner.scanPackageBySuper("cc.moecraft.icq.event.events", Event.class);
		s0.forEach(c -> {
			if(Event.class.isAssignableFrom(c)) {
				s.add((Class<? extends Event>) c);
			}
		});
		return s;
	}

	public static IcqCommand createCommand(String clazz, String property, String funname) {
		IcqCommand cmd = null;

		switch (clazz) {
			case "EverywhereCommand":
				cmd = new EverywhereCommand() {
					@Override
					public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
						return JavaScriptManager.getInstance().invokeFunction(funname, event, sender, command, args).toString();
					}

					@Override
					public CommandProperties properties() {
						return CommandProperties.name(property);
					}
				};break;

			case "GroupCommand":
				cmd = new GroupCommand() {
					@Override
					public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args) {
						return JavaScriptManager.getInstance().invokeFunction(funname, event, sender, group, command, args).toString();
					}

					@Override
					public CommandProperties properties() {
						return CommandProperties.name(property);
					}
				};break;

			case "DiscussCommand":
				cmd = new DiscussCommand() {
					@Override
					public String discussMessage(EventDiscussMessage event, GroupUser sender, Group discuss, String command, ArrayList<String> args) {
						return JavaScriptManager.getInstance().invokeFunction(funname, event, sender, discuss, command, args).toString();
					}

					@Override
					public CommandProperties properties() {
						return CommandProperties.name(property);
					}
				};break;

			case "PrivateCommand":
				cmd = new PrivateCommand() {
					@Override
					public String privateMessage(EventPrivateMessage event, User sender, String command, ArrayList<String> args) {
						return JavaScriptManager.getInstance().invokeFunction(funname, event, sender, command, args).toString();
					}

					@Override
					public CommandProperties properties() {
						return CommandProperties.name(property);
					}
				};break;
		}

		return cmd;
	}

}
