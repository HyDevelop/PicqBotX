package taskeren.extrabot.jshorn;

import cc.moecraft.icq.event.Event;
import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.util.StrUtil;

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

}
