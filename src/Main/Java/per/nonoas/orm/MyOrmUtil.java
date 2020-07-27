package per.nonoas.orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MyOrmUtil<T> {

    /**
     * ��ѯһ������
     *
     * @param sql    ִ�е�SQL���
     * @param params SQL���ռλ��ֵ
     * @return Class &lt;T> ��ʵ������,û�в�ѯ���ʱ����null
     */
    final protected T selectOne(String sql, Object... params) {
        T t = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            ps = getGeneralPreparedStatement(sql, params); // ��#{}ռλ���滻Ϊ?
            rs = ps.executeQuery();
            if (rs.next())
                t = mapToBean(rs, getBeanClass());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                Connection c = null;
                if (ps != null) {
                    c = ps.getConnection();
                    ps.close();
                }
                if (c != null)
                    c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    /**
     * ��ѯ��������
     *
     * @param sql    ִ�е�SQL���
     * @param params SQL���ռλ��ֵ
     * @return Bean�����List����, �޲�ѯ���ʱ����null
     */
    final protected List<T> select(String sql, Object... params) {
        List<T> list = new ArrayList<>(8);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = getGeneralPreparedStatement(sql, params); // ��ȡ��������PreparedStatement����
            rs = ps.executeQuery();
            while (rs.next()) // ����ѯ�������list
                list.add(mapToBean(rs, getBeanClass()));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (list.size() == 0)
            return null;
        return list;
    }

    /**
     * �����ݿ��������
     *
     * @param sql ����Ĵ�#{}ͨ�����SQL���
     * @param t   ����ķ���Bean��
     */
    final protected void insert(String sql, T t) {
        beanToDataBase(sql, t);
    }

    /**
     * �����ݿ��������
     *
     * @param sql ����Ĵ�#{}ͨ�����SQL���
     * @param t   ����ķ���Bean��
     */
    final protected void update(String sql, T t) {
        beanToDataBase(sql, t);
    }

    /**
     * �����ݿ�ɾ������
     *
     * @param sql ����Ĵ�#{}ͨ�����SQL���
     * @param t   ����ķ���Bean��
     */
    final protected void delete(String sql, T t) {
        beanToDataBase(sql, t);
    }

    /**
     * SQL������
     *
     * @param sql sql���
     * @param ts  bean�༯��
     */
    final protected void executeBatch(String sql, ArrayList<T> ts) {

        List<String> list = getParams(sql);
        sql = getSql(sql); // ��SQL�е�#{}ռλ����Ϊ?

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (T t : ts) {
                Class<?> beanClass = t.getClass();
                for (int i = 0; i < list.size(); i++) {
                    String colnName = list.get(i);
                    String methodName = "get" + firstUpperCase(colnName); // ������
                    Method method = beanClass.getDeclaredMethod(methodName);
                    Object value = method.invoke(t);
                    ps.setObject(i + 1, value);
                }
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * ִ��ͨ��SQL���
     * @param sql SQL���
     * @param params ռλ������
     * @return ִ�гɹ�����true�����򷵻�false
     * @throws SQLException SQL�쳣
     */
    protected boolean execute(String sql, Object... params) throws SQLException {
        PreparedStatement ps = getGeneralPreparedStatement(sql, params);
        return ps.execute();
    }

    /**
     * ���ͨ�õ�PrepareStatement����
     *
     * @param sql    SQL���
     * @param params ռλ������
     * @return PrepareStatement����
     */
    private PreparedStatement getGeneralPreparedStatement(String sql, Object... params) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(getSql(sql)); // ��#{}ռλ���滻Ϊ?
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    /**
     * ӳ��һ�����ݵ�Bean��
     *
     * @param rs        ResultSet����
     * @param beanClass Bean����
     * @return ����һ����Ϊ�յ�Bean����
     */
    private T mapToBean(ResultSet rs, Class<T> beanClass) {
        List<String> list = getColumnNames(rs); // ����������б���
        T t = null; // ��������ʵ������
        try {
            t = beanClass.newInstance();
            for (String colName : list) {
                String methodName = "set" + firstUpperCase(colName); // ��ȡ������
                Field field = beanClass.getDeclaredField(colName); // ��ȡ�ֶ���
                Class<?> type = field.getType(); // ��ȡ�ֶ�����
                Object value = rs.getObject(colName); // ��ȡ����ֵ
                Method method = beanClass.getDeclaredMethod(methodName, type);
                // ����ת��
                if (value != "" && value != null) {
                    if (value.getClass().getSimpleName().equals("BigDecimal") // ֵ��Ϊ��ʱ��ת��BigDecimalΪInteger����
                            && type.toString().equals("class java.lang.Integer")) {
                        value = Integer.parseInt(value.toString());
                    }
                } else {
                    field.getType().cast(value);// ���Ϊ�գ�ֱ��ת������
                }
                method.invoke(t, value); // ����set����
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException | SQLException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * ������ķ���Bean�е��ֶθ�ֵ��SQL����е�ռλ������ִ������
     *
     * @param sql ��#{}ͨ�����SQL���
     * @param t   ����Bean����
     */
    private void beanToDataBase(String sql, T t) {
        Class<?> beanClass = t.getClass();
        List<String> list = getParams(sql);
        sql = getSql(sql); // ��SQL�е�#{}ռλ����Ϊ?
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < list.size(); i++) {
                String colnName = list.get(i);
                String methodName = "get" + firstUpperCase(colnName); // ������
                Method method = beanClass.getDeclaredMethod(methodName);
                Object value = method.invoke(t);
                ps.setObject(i + 1, value);
            }
            ps.execute();
        } catch (SQLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ȡ���в�ѯ������
     *
     * @param rs ResultSet����
     * @return ������List����
     */
    private List<String> getColumnNames(ResultSet rs) {
        List<String> list = new ArrayList<>(8);
        ResultSetMetaData rsm;
        int columnCount;
        try {
            rsm = rs.getMetaData();
            columnCount = rsm.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                list.add(rsm.getColumnName(i + 1)); // ��ȡ�����������ӵ�list����
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ��ȡ������Ҫ���ݵĲ�������<br>
     * ��������:"insert into user(id,name) value(#{id},#{name})"<br>
     * ����:"[id,name]"
     *
     * @param sql �����SQL��䣨��#{}ͨ�����
     * @return ���ݲ���������List���ϣ���Ϊnull
     */
    private List<String> getParams(String sql) {

        List<String> list = new ArrayList<>(8);
        Pattern p = Pattern.compile("[#]\\{\\w*[}]");
        Matcher m = p.matcher(sql);
        while (m.find()) {
            String str = m.group();
            String column = str.substring(2, m.group().length() - 1); // ��ȡ����
            list.add(column);
        }
        return list;
    }

    /**
     * ����ĸ��д
     *
     * @param str ������ַ������硰name��
     * @return ����ĸ��д���ַ������硰Name��
     */
    private String firstUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * ��SQL����е�#{}�滻��"?"
     *
     * @param sql ��#{}��SQL���
     * @return ��?��SQL���
     */
    private String getSql(String sql) {
        return sql.replaceAll(("[#]\\{\\w*[}]"), "?");
    }

    /**
     * ��ȡ���ݿ����Ӷ���
     *
     * @return Connection����
     */
    protected abstract Connection getConnection();

    /**
     * @return T��class����
     */
    protected abstract Class<T> getBeanClass();
}