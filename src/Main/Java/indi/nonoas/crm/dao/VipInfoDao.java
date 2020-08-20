package indi.nonoas.crm.dao;

import java.util.ArrayList;

import indi.nonoas.crm.beans.VipBean;

/**
 * 会员信息的数据库操作类
 *
 * @author Nonoas
 */
@SuppressWarnings("unused")
public class VipInfoDao extends SqliteDao<VipBean> {

    private static final String SELECT_ALL = "select * from user_info";
    private static final String GET_INFO_BY_ID = "select * from user_info where id=#{id}";
    private static final String GET_INFO_BY_ID_OR_NAME = "select * from user_info where id=#{id} or name=#{name}";
    private static final String SELECT_BY_FILTRATE_1 = "select * from user_info where (id like #{id} or name like #{name}) and card_level like #{card_level}";
    private static final String SELECT_BY_FILTRATE_2 = "select * from user_info where (id like #{id} "
            + "or name like #{name}) and card_level like #{card_level} " + "and admission_date between ? and ?";
    private static final String INSERT_INFO = "insert into "
            + "user_info(id,name,sex,birthday,card_level,balance,cumulative,"
            + "address,integral,telephone,idcard,career,email,other,admission_date,photo) "
            + "values(#{id},#{name},#{sex},#{birthday},#{card_level},#{balance},"
            + "#{cumulative},#{address},#{integral},#{telephone},#{idcard},"
            + "#{career},#{email},#{other},#{admission_date},#{photo});";
    private static final String DELETE_BY_ID = "delete from user_info where id=#{id}";
    private static final String UPDATE_INFO = "update user_info set "
            + "name=#{name},sex=#{sex},birthday=#{birthday},card_level=#{card_level},"
            + "balance=#{balance},"
            + "cumulative=#{cumulative},address=#{address},integral=#{integral},telephone=#{telephone},"
            + "idcard=#{idcard},career=#{career},email=#{email},other=#{other} " + "where id=#{id}";
    private static final String SELECT_MAX_ID = "select max(id) as id from user_info";


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
     * 通过会员卡号查询会员信息
     *
     * @param id 会员卡号
     * @return VIPBean对象
     */
    public VipBean getInfoById(String id) {
        return selectOne(GET_INFO_BY_ID, id);
    }

    /**
     * 通过卡号或姓名精确查询
     *
     * @param id   会员卡号
     * @param name 会员姓名
     * @return VipBean
     */
    public VipBean getInfoByIdOrName(String id, String name) {
        return selectOne(GET_INFO_BY_ID_OR_NAME, id, name);
    }

    /**
     * 查询所有会员信息
     *
     * @return VIPBean的ArrayList集合
     */
    public ArrayList<VipBean> selectAll() {
        ArrayList<VipBean> list;
        list = (ArrayList<VipBean>) select(SELECT_ALL);
        return list;
    }

    /**
     * 多条件筛选信息
     *
     * @param id         会员卡号
     * @param name       姓名
     * @param card_level 会员等级
     * @return VIPBean的ArrayList对象, 没有查询结果时为null
     */
    public ArrayList<VipBean> selectByFiltrate(String id, String name, String card_level) {
        return (ArrayList<VipBean>) select(SELECT_BY_FILTRATE_1, id, name, card_level);
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
    public ArrayList<VipBean> selectByFiltrate(String id, String name, String level, String dateFrom, String dateTo) {
        return (ArrayList<VipBean>) select(SELECT_BY_FILTRATE_2, id, name, level, dateFrom, dateTo);
    }

    /**
     * 查找最大的id值
     *
     * @return 最大id值
     */
    public String selectMaxId() {
        VipBean bean = selectOne(SELECT_MAX_ID);
        return bean.getId();
    }

    /**
     * 插入会员信息
     *
     * @param vipBean VIPBean对象
     */
    public void insertInfo(VipBean vipBean) {
        insert(INSERT_INFO, vipBean);
    }

    /**
     * 通过卡号删除会员信息
     *
     * @param bean VIPBean对象
     */
    public void deleteByID(VipBean bean) {
        delete(DELETE_BY_ID, bean);
    }

    /**
     * 更新用户信息
     *
     * @param vipBean VIPBean对象
     */
    public void updateInfo(VipBean vipBean) {
        update(UPDATE_INFO, vipBean);
    }

    @Override
    public Class<VipBean> getBeanClass() {
        return VipBean.class;
    }

}
