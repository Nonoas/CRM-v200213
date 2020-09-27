package per.nonoas.orm;

/**
 * @author : Nonoas
 * @time : 2020-08-15 12:29
 */
public abstract class AbstractTransaction {
    public abstract Object getParams();
    public abstract String getSQL();
}
