package indi.nonoas.crm.dao.my_orm_dao;

import java.util.ArrayList;

import indi.nonoas.crm.beans.UserBean;

/**
 * ��Ա��Ϣ�����ݿ������
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
     * ����������
     *
     * @param id       ����
     * @param name     ����
     * @param level    ��Ա�ȼ�
     * @param dateFrom ����ʱ�䷶Χ����ʼ��
     * @param dateTo   ����ʱ�䷶Χ��������
     * @return VIPBean��ArrayList����, û�в�ѯ���ʱΪnull
     */
    public ArrayList<UserBean> selectByFiltrate(String id, String name, String level, String dateFrom, String dateTo) {
        return (ArrayList<UserBean>) select(SELECT_BY_FILTRATE_2, id, name, level, dateFrom, dateTo);
    }


    /**
     * �����Ա��Ϣ
     *
     * @param vipBean VIPBean����
     */
    public void insertInfo(UserBean vipBean) {
        insert(INSERT_INFO, vipBean);
    }

    /**
     * ͨ������ɾ����Ա��Ϣ
     *
     * @param bean VIPBean����
     */
    public void deleteByID(UserBean bean) {
        delete(DELETE_BY_ID, bean);
    }

    /**
     * �����û���Ϣ
     *
     * @param vipBean VIPBean����
     */
    public void updateInfo(UserBean vipBean) {
        update(UPDATE_INFO, vipBean);
    }

    @Override
    public Class<UserBean> getBeanClass() {
        return UserBean.class;
    }

}
