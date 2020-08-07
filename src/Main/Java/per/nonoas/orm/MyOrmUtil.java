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
     * 查询一条数据
     *
     * @param sql    执行的SQL语句
     * @param params SQL语句占位符值
     * @return Class &lt;T> 的实例对象,没有查询结果时返回null
     */
    final protected T selectOne(String sql, Object... params) {
        T t = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            ps = getGeneralPreparedStatement(sql, params); // 将#{}占位符替换为?
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
     * 查询多条数据
     *
     * @param sql    执行的SQL语句
     * @param params SQL语句占位符值
     * @return Bean对象的List集合, 无查询结果时返回null
     */
    final protected List<T> select(String sql, Object... params) {
        List<T> list = new ArrayList<>(8);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = getGeneralPreparedStatement(sql, params); // 获取带参数的PreparedStatement对象
            rs = ps.executeQuery();
            while (rs.next()) // 将查询结果加入list
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
     * 向数据库插入内容
     *
     * @param sql 传入的带#{}通配符的SQL语句
     * @param t   传入的泛型Bean类
     * @return 插入的数据条数
     */
    final protected int insert(String sql, T t) {
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
     * 向数据库更新内容
     *
     * @param sql 传入的带#{}通配符的SQL语句
     * @param t   传入的泛型Bean类
     * @return 更新的数据条数
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
     * 向数据库删除内容
     *
     * @param sql 传入的带#{}通配符的SQL语句
     * @param t   传入的泛型Bean类
     * @return 删除的数据条数
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
     * SQL批处理
     *
     * @param sql sql语句
     * @param ts  bean类集合
     */
    final protected void executeBatch(String sql, ArrayList<T> ts) {

        List<String> list = getParams(sql);
        sql = getSql(sql); // 将SQL中的#{}占位符改为?

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (T t : ts) {
                Class<?> beanClass = t.getClass();
                for (int i = 0; i < list.size(); i++) {
                    String colName = list.get(i);
                    String methodName = "get" + firstUpperCase(colName); // 方法名
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
     * 执行通用SQL语句
     *
     * @param sql    SQL语句
     * @param params 占位符参数
     * @return 执行成功返回true，否则返回false
     * @throws SQLException SQL异常
     */
    @SuppressWarnings("unused")
    protected boolean execute(String sql, Object... params) throws SQLException {
        PreparedStatement ps = getGeneralPreparedStatement(sql, params);
        return ps.execute();
    }

    /**
     * 获得通用的PrepareStatement对象
     *
     * @param sql    SQL语句
     * @param params 占位符参数
     * @return PrepareStatement对象
     */
    private PreparedStatement getGeneralPreparedStatement(String sql, Object... params) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(getSql(sql)); // 将#{}占位符替换为?
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    /**
     * 通用执行方法
     *
     * @param sql sql语句
     * @param t   对应的bean类
     * @return 执行成功返回true，否则返回false
     * @throws SQLException 数据库操作异常类
     */
    private boolean execute(String sql, T t) throws SQLException {
        PreparedStatement ps = getPrepareStatement(sql, t);
        if (ps != null) {
            return ps.execute();
        }
        return false;
    }

    /**
     * 映射一条数据到Bean中
     *
     * @param rs        ResultSet对象
     * @param beanClass Bean对象
     * @return 返回一个不为空的Bean对象
     */
    private T mapToBean(ResultSet rs, Class<T> beanClass) {
        List<String> list = getColumnNames(rs); // 结果集中所有表名
        T t = null; // 创建泛型实例对象
        try {
            t = beanClass.newInstance();
            for (String colName : list) {
                String methodName = "set" + firstUpperCase(colName); // 获取方法名
                Field field = beanClass.getDeclaredField(colName); // 获取字段名
                Class<?> type = field.getType(); // 获取字段类型
                Object value = rs.getObject(colName); // 获取传入值
                Method method = beanClass.getDeclaredMethod(methodName, type);
                // 类型转换
                if (value != "" && value != null) {
                    if (value.getClass().getSimpleName().equals("BigDecimal") // 值不为空时，转换BigDecimal为Integer类型
                            && type.toString().equals("class java.lang.Integer")) {
                        value = Integer.parseInt(value.toString());
                    }
                } else {
                    field.getType().cast(value);// 如果为空，直接转换类型
                }
                method.invoke(t, value); // 调用set方法
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException | SQLException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将传入的泛型Bean中的字段赋值给SQL语句中的占位符，并执行他们
     *
     * @param sql 带#{}通配符的SQL语句
     * @param t   泛型Bean对象
     */
    private PreparedStatement getPrepareStatement(String sql, T t) {
        Class<?> beanClass = t.getClass();
        List<String> list = getParams(sql);
        sql = getSql(sql); // 将SQL中的#{}占位符改为?
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < list.size(); i++) {
                String colName = list.get(i);
                String methodName = "get" + firstUpperCase(colName); // 方法名
                Method method = beanClass.getDeclaredMethod(methodName);
                Object value = method.invoke(t);
                ps.setObject(i + 1, value);
            }
            return ps;
        } catch (SQLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有查询的列名
     *
     * @param rs ResultSet对象
     * @return 列名的List集合
     */
    private List<String> getColumnNames(ResultSet rs) {
        List<String> list = new ArrayList<>(8);
        ResultSetMetaData rsm;
        int columnCount;
        try {
            rsm = rs.getMetaData();
            columnCount = rsm.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                list.add(rsm.getColumnName(i + 1)); // 获取列名，并添加到list集合
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取所有需要传递的参数列名<br>
     * 例如输入:"insert into user(id,name) value(#{id},#{name})"<br>
     * 返回:"[id,name]"
     *
     * @param sql 传入的SQL语句（带#{}通配符）
     * @return 传递参数列名的List集合，不为null
     */
    private List<String> getParams(String sql) {
        List<String> list = new ArrayList<>(8);
        Pattern p = Pattern.compile("[#]\\{\\w*[}]");
        Matcher m = p.matcher(sql);
        while (m.find()) {
            String str = m.group();
            String column = str.substring(2, m.group().length() - 1); // 获取列名
            list.add(column);
        }
        return list;
    }

    /**
     * 首字母大写
     *
     * @param str 传入的字符串，如“name”
     * @return 首字母大写的字符串，如“Name”
     */
    private String firstUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 将SQL语句中的#{}替换成"?"
     *
     * @param sql 带#{}的SQL语句
     * @return 带?的SQL语句
     */
    private String getSql(String sql) {
        return sql.replaceAll(("[#]\\{\\w*[}]"), "?");
    }

    /**
     * 获取数据库连接对象
     *
     * @return Connection对象
     */
    protected abstract Connection getConnection();

    /**
     * @return T的class属性
     */
    protected abstract Class<T> getBeanClass();
}
