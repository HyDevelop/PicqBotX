package cc.moecraft.icq.utils;

/**
 * 安全地解析数据<br>
 * 别问我为什么没有 Boolean，因为它根本不抛异常啊！
 * @author Taskeren
 *
 */
public class Parser {

	/**
	 * 解析Integer
	 * @param s 数据
	 * @return 返回值，抛出异常时返回<code>null</code>
	 */
	public static Integer Int(String s) {
		try {
			return Integer.parseInt(s);
		} catch(Exception ex) {
			return null;
		}
	}
	
	/**
	 * 解析Byte
	 * @param s 数据
	 * @return 返回值，抛出异常时返回<code>null</code>
	 */
	public static Byte Byte(String s) {
		try {
			return Byte.parseByte(s);
		} catch(Exception ex) {
			return null;
		}
	}
	
	/**
	 * 解析Short
	 * @param s 数据
	 * @return 返回值，抛出异常时返回<code>null</code>
	 */
	public static Short Short(String s) {
		try {
			return Short.parseShort(s);
		} catch(Exception ex) {
			return null;
		}
	}
	
	/**
	 * 解析Long
	 * @param s 数据
	 * @return 返回值，抛出异常时返回<code>null</code>
	 */
	public static Long Long(String s) {
		try {
			return Long.parseLong(s);
		} catch(Exception ex) {
			return null;
		}
	}
	
	/**
	 * 解析Float
	 * @param s 数据
	 * @return 返回值，抛出异常时返回<code>null</code>
	 */
	public static Float Float(String s) {
		try {
			return Float.parseFloat(s);
		} catch(Exception ex) {
			return null;
		}
	}
	
	/**
	 * 解析Double
	 * @param s 数据
	 * @return 返回值，抛出异常时返回<code>null</code>
	 */
	public static Double Double(String s) {
		try {
			return Double.parseDouble(s);
		} catch(Exception ex) {
			return null;
		}
	}
	
}
