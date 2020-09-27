package indi.nonoas.crm.dao.my_orm_dao;

import java.util.ArrayList;

import indi.nonoas.crm.beans.UserBean;

/**
 * 会员信息的数据库操作类
 *
 * @author Nonoas
 */
@Deprecated
public class VipInfoDao extends SqliteDao<UserBean> {

    private static final String SELECT_BY_FILTRATE_2 = "select * from user_info where (id like #{id} "
            + "or name like #{name}) and card_level like #{card_level} " + "and admission_date between ? and ?";
    private static final String INSERT_INFO = "insert into "
            + "user_info(id,name,sex,birthday,card_level,balance,cumulative,"
            + "address,integral,telephone,idcard,career,email,other,admission_date,photo,discount) "
            + "values(#{id},#{name},#{sex},#{birthday},#{card_level},#{balance},"
            + "#{cumulative},#{address},#{integral},#{telephone},#{idcard},"
            + "#{career},#{email},#{other},#{admission_date},#{photo},#{discount});";
    private static final String DELETE_BY_ID = "delete from user_info where id=#{id}";
    private static final String UPDATE_INFO = "update user_info set "
            + "name=#{name},sex=#{sex},birthday=#{birthday},card_level=#{card_level},"
            + "balance=#{balance},discount=#{discount},"
            + "cumulative=#{cumulative},address=#{address},integral=#{integral},telephone=#{telephone},"
            + "idcard=#{idcard},career=#{career},email=#{email},other=#{other} " + "where id=#{id}";


    /**
     * 私有构造器
     */
    private VipInfoDao() {
    }

    /**
     * 本类的单例对象
     */
    private static final VipInfoDao INSTANCE = new VipInfoDao();

    /**
     * 获取本类的单例对象
     *
     * @return VipInfoDao的单例对象
     */
    public static VipInfoDao getInstance() {
        return INSTANCE;
    }


    /**
     * 过滤器查找
     *
     * @param id       卡号
     * @param name     姓名
     * @param level    会员等级
     * @param dateFrom 加入时间范围（起始）
     * @param dateTo   加入时间范围（结束）
     * @return VIPBean的ArrayList对象, 没有查询结果时为null
     */
    public ArrayList<UserBean> selectByFiltrate(String id, String name, String level, String dateFrom, String dateTo) {
        return (ArrayList<UserBean>) select(SELECT_BY_FILTRATE_2, id, name, level, dateFrom, dateTo);
    }


    /**
     * 插入会员信息
     *
     * @param vipBean VIPBean对象
     */
    public void insertInfo(UserBean vipBean) {
        insert(INSERT_INFO, vipBean);
    }

    /**
     * 通过卡号删除会员信息
     *
     * @param bean VIPBean对象
     */
    public void deleteByID(UserBean bean) {
        delete(DELETE_BY_ID, bean);
    }

    /**
     * 更新用户信息
     *
     * @param vipBean VIPBean对象
     */
    public void updateInfo(UserBean vipBean) {
        update(UPDATE_INFO, vipBean);
    }

    @Override
    public Class<UserBean> getBeanClass() {
        return UserBean.class;
    }

}
