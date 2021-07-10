package indi.nonoas.crm.utils;

import java.sql.Connection;
import java.util.LinkedList;

public class MyDataSource {
    //���� --- ʵ��ջ�ṹ
    private final LinkedList<Connection> dataSources = new LinkedList<>();

    private final int MAX_SIZE = 10;

    //��ʼ进价��
    public MyDataSource() {
        //һ���Դ���10������
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
        //ȡ�����ӳ���һ������
        if (dataSources.size() == 0)
            return DBOpener.getConnection();
        return dataSources.removeFirst();
    }

    //�����ӷŻ����ӳ�
    public void releaseConnection(Connection conn) {
        if (dataSources.size() >= MAX_SIZE)
            return;
        dataSources.add(conn);
    }
}