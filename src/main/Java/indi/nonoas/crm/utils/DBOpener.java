package indi.nonoas.crm.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ��ȡ���ݿ����ӵ���
 *
 * @author Nonoas
 */
public class DBOpener {

    private static final String url = "jdbc:sqlite:data/mycrm.db";

    /**
     * ˽�й�����
     */
    private DBOpener() {
    }

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ȡ���ݿ����Ӷ���
     *
     * @return Connection����
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
