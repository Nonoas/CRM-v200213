package indi.nonoas.crm.dao;

import java.sql.Connection;

import indi.nonoas.crm.utils.DBOpener;
import per.nonoas.orm.MyOrmUtil;

/**
 * Dao�㸸��
 *
 * @param <T> Bean�෺��
 * @author Nonoas
 */
public abstract class SqliteDao<T> extends MyOrmUtil<T> {

    @Override
    protected Connection getConnection() {
        return DBOpener.getConnection();
    }

    @Override
    protected abstract Class<T> getBeanClass();

}
