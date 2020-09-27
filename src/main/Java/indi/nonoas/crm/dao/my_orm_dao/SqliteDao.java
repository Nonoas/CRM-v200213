package indi.nonoas.crm.dao.my_orm_dao;

import java.sql.Connection;

import indi.nonoas.crm.utils.DBOpener;
import indi.nonoas.crm.utils.MyDataSource;
import per.nonoas.orm.MyOrmUtil;

/**
 * Dao≤„∏∏¿‡
 *
 * @param <T> Bean¿‡∑∫–Õ
 * @author Nonoas
 */
@Deprecated
public abstract class SqliteDao<T> extends MyOrmUtil<T> {

    private static final MyDataSource dataSource = new MyDataSource();

    @Override
    protected Connection getConnection() {
        return dataSource.getConnection();
    }

    @Override
    protected abstract Class<T> getBeanClass();

}
