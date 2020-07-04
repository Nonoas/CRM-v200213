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
     * 查询一条数据
     *
     * @param sql    执行的SQL语句
     * @param params SQL语句占位符值
     * @return Class &lt;T> 的实例对象,没有查询结果时返回null
     */
    final protected T selectOne(String sql, Object... params) {
        T t = null;
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getSql(sql)); // 将#{}占位符替换为?
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            rs = ps.executeQuery();
            if (rs.next())
                t = mapToBean(rs, getBeanClass());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (rs != null)
                    rs.close();
                conn.close();
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
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getSql(sql)); // 将#{}占位符替换为?
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
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
     * 向数据库插入数据
     *
     * @param sql 传入的带#{}通配符的SQL语句
     * @param t   传入的泛型Bean类
     */
    final protected void insert(String sql, T t) {
        try {
            beanToDataBase(sql, t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向数据库更新内容
     *
     * @param sql 传入的带#{}通配符的SQL语句
     * @param t   传入的泛型Bean类
     */
    final protected void update(String sql, T t) {
        try {
            beanToDataBase(sql, t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向数据库删除内容
     *
     * @param sql 传入的带#{}通配符的SQL语句
     * @param t   传入的泛型Bean类
     */
    final protected void delete(String sql, T t) {
        try {
            beanToDataBase(sql, t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将传入的泛型Bean中的字段赋值给SQL语句中的占位符，并执行他们
     *
     * @param sql 带#{}通配符的SQL语句
     * @param t   泛型Bean对象
     * @throws SQLException 数据库异常
     */
    private void beanToDataBase(String sql, T t) throws SQLException {
        Class<?> beanClass = t.getClass();
        List<String> list = getParms(sql);
        sql = getSql(sql); // 将SQL中的#{}占位符改为?
        Connection con = getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < list.size(); i++) {
                String colnName = list.get(i);
                String methodName = "get" + firstUpperCase(colnName); // 方法名
                Method method = beanClass.getDeclaredMethod(methodName);
                Object value = method.invoke(t);
                ps.setObject(i + 1, value);
            }
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (ps != null)
                ps.close();
            con.close();
        }
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
    private List<String> getParms(String sql) {

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
