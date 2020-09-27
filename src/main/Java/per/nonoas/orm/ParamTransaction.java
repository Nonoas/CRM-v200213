package per.nonoas.orm;

/**
 * @author : Nonoas
 * @time : 2020-08-15 12:34
 */
public class ParamTransaction extends AbstractTransaction {

    private final Object[] objects;
    private final String sql;

    public ParamTransaction(String sql, Object... objects) {
        this.sql = sql;
        this.objects = objects;
    }

    @Override
    public Object getParams() {
        return objects;
    }

    @Override
    public String getSQL() {
        return sql;
    }
}
