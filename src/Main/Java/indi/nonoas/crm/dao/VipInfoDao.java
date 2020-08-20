package indi.nonoas.crm.dao;

import java.util.ArrayList;

import indi.nonoas.crm.beans.VipBean;

/**
 * ��Ա��Ϣ�����ݿ������
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
     * ˽�й�����
     */
    private VipInfoDao() {
    }

    /**
     * ����ĵ�������
     */
    private static final VipInfoDao INSTANCE = new VipInfoDao();

    /**
     * ��ȡ����ĵ�������
     *
     * @return VipInfoDao�ĵ�������
     */
    public static VipInfoDao getInstance() {
        return INSTANCE;
    }


    /**
     * ͨ����Ա���Ų�ѯ��Ա��Ϣ
     *
     * @param id ��Ա����
     * @return VIPBean����
     */
    public VipBean getInfoById(String id) {
        return selectOne(GET_INFO_BY_ID, id);
    }

    /**
     * ͨ�����Ż�������ȷ��ѯ
     *
     * @param id   ��Ա����
     * @param name ��Ա����
     * @return VipBean
     */
    public VipBean getInfoByIdOrName(String id, String name) {
        return selectOne(GET_INFO_BY_ID_OR_NAME, id, name);
    }

    /**
     * ��ѯ���л�Ա��Ϣ
     *
     * @return VIPBean��ArrayList����
     */
    public ArrayList<VipBean> selectAll() {
        ArrayList<VipBean> list;
        list = (ArrayList<VipBean>) select(SELECT_ALL);
        return list;
    }

    /**
     * ������ɸѡ��Ϣ
     *
     * @param id         ��Ա����
     * @param name       ����
     * @param card_level ��Ա�ȼ�
     * @return VIPBean��ArrayList����, û�в�ѯ���ʱΪnull
     */
    public ArrayList<VipBean> selectByFiltrate(String id, String name, String card_level) {
        return (ArrayList<VipBean>) select(SELECT_BY_FILTRATE_1, id, name, card_level);
    }

    /**
     * ����������
     *
     * @param id       ����
     * @param name     ����
     * @param level    ��Ա�ȼ�
     * @param dateFrom ����ʱ�䷶Χ����ʼ��
     * @param dateTo   ����ʱ�䷶Χ��������
     * @return VIPBean��ArrayList����, û�в�ѯ���ʱΪnull
     */
    public ArrayList<VipBean> selectByFiltrate(String id, String name, String level, String dateFrom, String dateTo) {
        return (ArrayList<VipBean>) select(SELECT_BY_FILTRATE_2, id, name, level, dateFrom, dateTo);
    }

    /**
     * ��������idֵ
     *
     * @return ���idֵ
     */
    public String selectMaxId() {
        VipBean bean = selectOne(SELECT_MAX_ID);
        return bean.getId();
    }

    /**
     * �����Ա��Ϣ
     *
     * @param vipBean VIPBean����
     */
    public void insertInfo(VipBean vipBean) {
        insert(INSERT_INFO, vipBean);
    }

    /**
     * ͨ������ɾ����Ա��Ϣ
     *
     * @param bean VIPBean����
     */
    public void deleteByID(VipBean bean) {
        delete(DELETE_BY_ID, bean);
    }

    /**
     * �����û���Ϣ
     *
     * @param vipBean VIPBean����
     */
    public void updateInfo(VipBean vipBean) {
        update(UPDATE_INFO, vipBean);
    }

    @Override
    public Class<VipBean> getBeanClass() {
        return VipBean.class;
    }

}
