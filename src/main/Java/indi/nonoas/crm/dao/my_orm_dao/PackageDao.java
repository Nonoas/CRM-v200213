package indi.nonoas.crm.dao.my_orm_dao;

import java.util.ArrayList;

import indi.nonoas.crm.pojo.PackageBean;

/**
 * 套餐项目数据库操作类
 *
 * @author Nonoas
 */
@Deprecated
public class PackageDao extends SqliteDao<PackageBean> {

    private static final String SELECT_ALL = "select * from package_info";

    private static final String SELECT_BY_ID = "select * from package_info where id=#{id}";

    private static final String FIND_BY_FILTER = "select * from package_info " +

            "where (id like #{id} or name like #{name}) and (money_cost between #{money1} and #{money2})";

    private static final String DELETE_BY_ID = "delete from package_info where id=#{id}";

    private static final String INSERT_ONE = "insert into " +

            "package_info (id,name,integral_cost,money_cost,min_discount,type,other) " +

            "values (#{id},#{name},#{integral_cost},#{money_cost},#{min_discount},#{type},#{other})";

    private static final String UPDATE_ONE = "update package_info " +
            "set name=#{name},integral_cost=#{integral_cost},money_cost=#{money_cost},min_discount=#{min_discount}," +
            "type=#{type},other=#{other} where id=#{id}";

    private PackageDao() {

    }

    private static final PackageDao INSTANCE = new PackageDao();

    public static PackageDao getInstance() {
        return INSTANCE;
    }

    /**
     * 查询所有项目信息
     *
     * @return 所有项目信息
     */
    public ArrayList<PackageBean> selectAll() {
        return (ArrayList<PackageBean>) select(SELECT_ALL);
    }

    /**
     * 通过id查找项目信息
     *
     * @param id 项目id
     * @return 对应的项目信息bean类对象
     */
    public PackageBean selectById(String id) {
        return selectOne(SELECT_BY_ID, id);
    }

    /**
     * 通过过滤查找项目信息
     *
     * @param id     编号
     * @param name   名称
     * @param money1 金钱下限
     * @param money2 金钱上限
     * @return 所有满足过滤条件的项目信息, 可以为null
     */
    public ArrayList<PackageBean> findByFilter(String id, String name, double money1, double money2) {
        return (ArrayList<PackageBean>) select(FIND_BY_FILTER, id, name, money1, money2);
    }

    /**
     * 删除一条项目信息
     *
     * @param bean PackageBean对象
     */
    public void deleteByID(PackageBean bean) {
        delete(DELETE_BY_ID, bean);
    }

    /**
     * 插入一条项目信息
     *
     * @param bean PackageBean对象
     */
    public void insert(PackageBean bean) {
        insert(INSERT_ONE, bean);
    }

    /**
     * 更新一条项目信息
     *
     * @param bean PackageBean对象
     */
    public void update(PackageBean bean) {
        update(UPDATE_ONE, bean);
    }

    @Override
    protected Class<PackageBean> getBeanClass() {
        return PackageBean.class;
    }

}
