package indi.nonoas.crm.utils;

import java.sql.Connection;
import java.util.LinkedList;

public class MyDataSource {
    //链表 --- 实现栈结构
    private final LinkedList<Connection> dataSources = new LinkedList<>();

    private final int MAX_SIZE = 10;

    //初始化连接数量
    public MyDataSource() {
        //一次性创建10个连接
        for (int i = 0; i < MAX_SIZE; i++) {
            try {
                Connection con = DBOpener.getConnection();
                dataSources.add(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        //取出连接池中一个连接
        if (dataSources.size() == 0)
            return DBOpener.getConnection();
        return dataSources.removeFirst();
    }

    //将连接放回连接池
    public void releaseConnection(Connection conn) {
        if (dataSources.size() >= MAX_SIZE)
            return;
        dataSources.add(conn);
    }
}