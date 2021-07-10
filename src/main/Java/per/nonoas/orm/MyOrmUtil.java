package per.nonoas.orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
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
     * ��ѯ进价
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
            ps = getGeneralPreparedStatement(sql, params); // ��ȡ进价PreparedStatement����
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
     * �����ݿ进价
     *
     * @param sql ����Ĵ�#{}ͨ�����SQL���
     * @param t   ����ķ���Bean��
     * @return 进价�����
     */
    final protected int insert(String sql, T t) {
        PreparedStatement ps = getPrepareStatement(sql, t);
        if (ps != null) {
            try {
                int res = ps.executeUpdate();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * �����ݿ进价
     *
     * @param sql ����Ĵ�#{}ͨ�����SQL���
     * @param t   ����ķ���Bean��
     * @return ���µ进价�
     */
    final protected int update(String sql, T t) {
        PreparedStatement ps = getPrepareStatement(sql, t);
        if (ps != null) {
            try {
                return ps.executeUpdate();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * �����ݿ�ɾ������
     *
     * @param sql ����Ĵ�#{}ͨ�����SQL���
     * @param t   ����ķ���Bean��
     * @return ɾ进价����
     */
    final protected boolean delete(String sql, T t) {
        try {
            return execute(sql, t);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    /**
     * SQL������
     *
     * @param sql sql���
     * @param ts  bean�༯��
     */
    final protected void executeBatch(String sql, List<T> ts) {

        List<String> list = getParams(sql);
        sql = getSql(sql); // ��SQL�е�#{}ռλ����Ϊ?

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (T t : ts) {
                Class<?> beanClass = t.getClass();
                for (int i = 0; i < list.size(); i++) {
                    String colName = list.get(i);
                    String methodName = "get" + underlineToBigCamel(colName); // ������
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
     * ����ִ�У���һ�����ݿ����ָ��Ϊ����
     *
     * @param transactions �����༯��
     */
    final protected boolean executeTransaction(List<AbstractTransaction> transactions) {
        boolean flag = true;
        Connection conn = getConnection();
        //ȡ���Զ��ύ����
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //进价ִ�����
        try {
            for (AbstractTransaction transaction : transactions) {
                if (transaction instanceof BeanTransaction) {
                    //��ȡ�������
                    Object bean = transaction.getParams();
                    String sql = transaction.getSQL();
                    //����PrepareStatement����
                    Class<?> beanClass = bean.getClass();
                    List<String> paramNames = getParams(sql);
                    sql = getSql(sql);  // ��SQL�е�#{}ռλ����Ϊ?
                    PreparedStatement ps = conn.prepareStatement(sql);
                    for (int i = 0; i < paramNames.size(); i++) {
                        String colName = paramNames.get(i);
                        String methodName = "get" + underlineToBigCamel(colName); // ������
                        Method method = beanClass.getDeclaredMethod(methodName);
                        Object value = method.invoke(bean);
                        ps.setObject(i + 1, value);
                    }
                    ps.execute();
                } else {
                    String sql = getSql(transaction.getSQL());
                    Object[] params = (Object[]) transaction.getParams();
                    PreparedStatement ps = conn.prepareStatement(getSql(sql)); // ��#{}ռλ���滻Ϊ?
                    for (int i = 0; i < params.length; i++) {
                        ps.setObject(i + 1, params[i]);
                    }
                    ps.execute();
                }
            }
        } catch (InvocationTargetException | NoSuchMethodException | SQLException | IllegalAccessException e) {
            e.printStackTrace();
            try {
                conn.rollback();    //�ع�����
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            flag = false;
        } finally {
            try {
                conn.commit();  //�ύ����
                conn.close();   //�ر�����
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * ִ��ͨ��SQL���
     *
     * @param sql    SQL���
     * @param params ռλ������
     * @return ִ�гɹ�����true�����򷵻�false
     * @throws SQLException SQL�쳣
     */
    final protected boolean execute(String sql, Object... params) throws SQLException {
        PreparedStatement ps = getGeneralPreparedStatement(sql, params);
        return ps.execute();
    }

    /**
     * ͨ��ִ�з���
     *
     * @param sql sql���
     * @param t   ��Ӧ��bean��
     * @return ִ�гɹ�����true�����򷵻�false
     * @throws SQLException ���ݿ�����쳣��
     */
    private boolean execute(String sql, T t) throws SQLException {
        PreparedStatement ps = getPrepareStatement(sql, t);
        if (ps != null) {
            return ps.execute();
        }
        return false;
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
     * ������ķ���Bean�е��ֶθ�ֵ��SQL����е�ռλ������ִ������
     *
     * @param sql ��#{}ͨ�����SQL���
     * @param t   ����Bean����
     */
    private PreparedStatement getPrepareStatement(String sql, T t) {
        Class<?> beanClass = t.getClass();
        List<String> list = getParams(sql);
        sql = getSql(sql); // ��SQL�е�#{}ռλ����Ϊ?
        Connection con = getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < list.size(); i++) {
                String colName = list.get(i);
                String methodName = "get" + underlineToBigCamel(colName); // ������
                Method method = beanClass.getDeclaredMethod(methodName);
                Object value = method.invoke(t);
                ps.setObject(i + 1, value);
            }
        } catch (SQLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
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
        List<String> list = getColumnNames(rs); // 进价��б���
        T t = null; // 进价ʵ������
        try {
            t = beanClass.newInstance();
            for (String colName : list) {
                String methodName = "set" + underlineToBigCamel(colName); // ��ȡ������
                Field field = beanClass.getDeclaredField(underlineToCamel(colName)); // ��ȡ�ֶ���
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
                list.add(rsm.getColumnName(i + 1)); // ��ȡ进价��ӵ�list����
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ��ȡ������Ҫ���ݵĲ�������<br>
     * 进价:"insert into user(id,name) value(#{id},#{name})"<br>
     * ����:"[id,name]"
     *
     * @param sql �����SQL��䣨��#{}ͨ�����
     * @return ���ݲ进价�List���ϣ���Ϊnull
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
     * �»�������ת���շ�
     *
     * @param str ������ַ������硰user_name��
     * @return ����ĸ��д���ַ������硰UserName��
     */
    private String underlineToBigCamel(String str) {
        String[] strs = str.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            strs[i] = strs[i].substring(0, 1).toUpperCase() + strs[i].substring(1);
            sb.append(strs[i]);
        }
        return sb.toString();
    }

    /**
     * �»�������תС�շ�
     *
     * @param str ������ַ������硰user_name��
     * @return ����ĸ��д���ַ������硰userName��
     */
    private String underlineToCamel(String str) {
        String[] strs = str.split("_");
        StringBuilder sb = new StringBuilder(strs[0]);
        for (int i = 1; i < strs.length; i++) {
            strs[i] = strs[i].substring(0, 1).toUpperCase() + strs[i].substring(1);
            sb.append(strs[i]);
        }
        return sb.toString();
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


    //===========================================================================
    //                            ���󷽷�
    //===========================================================================


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
