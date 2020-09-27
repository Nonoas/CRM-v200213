package indi.nonoas.crm.utils;

public class Log {
	/**
	 * 输出消息日志
	 * @param tag 标志
	 * @param message 日志内容
	 */
	public static void i(Object tag,String message) {
		System.out.println(tag.getClass().getSimpleName()+":"+message);
	}
}
