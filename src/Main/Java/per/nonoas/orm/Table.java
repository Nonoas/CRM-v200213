package per.nonoas.orm;

/**
 * 表名注解
 * 
 * @author Nonoas
 *
 */
public @interface Table {
	/**
	 * @return 表名
	 */
	String value();
}
