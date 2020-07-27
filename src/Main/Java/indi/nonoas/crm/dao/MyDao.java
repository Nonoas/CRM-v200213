package indi.nonoas.crm.dao;

import java.sql.Connection;

import indi.nonoas.crm.utils.DBOpener;
import per.nonoas.orm.MyOrmUtil;

/**
 * Dao≤„∏∏¿‡
 * 
 * @author Nonoas
 * @param <T> Bean¿‡∑∫–Õ
 */
public abstract class MyDao<T> extends MyOrmUtil<T> {

	@Override
	protected Connection getConnection() {
		return DBOpener.getConnection();
	}

	@Override
	protected abstract Class<T> getBeanClass();

}
