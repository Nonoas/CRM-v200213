package indi.nonoas.crm.utils;

public class Log {
	/**
	 * �����Ϣ��־
	 * @param tag ��־
	 * @param message ��־����
	 */
	public static void i(Object tag,String message) {
		System.out.println(tag.getClass().getSimpleName()+":"+message);
	}
}
