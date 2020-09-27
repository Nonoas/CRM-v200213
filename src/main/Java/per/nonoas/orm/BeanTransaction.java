package per.nonoas.orm;

/**
 * @author : Nonoas
 * @time : 2020-08-15 12:38
 */
public class BeanTransaction extends AbstractTransaction {

    private final Object bean;
    private final String sql;

    public BeanTransaction(String sql, Object bean) {
        this.sql = sql;
        this.bean = bean;
    }

    @Override
    public Object getParams() {
        return bean;
    }

    @Override
    public String getSQL() {
        return sql;
    }
}
