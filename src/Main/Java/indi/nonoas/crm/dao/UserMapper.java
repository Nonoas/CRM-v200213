package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.UserBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-09-03 21:30
 */
@Repository
public interface UserMapper {
    /**
     * ͨ����Ա���Ų�ѯ��Ա��Ϣ
     *
     * @param id ��Ա����
     * @return VIPBean����
     */
    UserBean getUserById(String id);

    /**
     * ͨ�����Ż�������ȷ��ѯ
     *
     * @param id   ��Ա����
     * @param name ��Ա����
     * @return VipBean
     */
    UserBean getInfoByIdOrName(@Param("id") String id,
                               @Param("name") String name);

    /**
     * ��ѯ���л�Ա��Ϣ
     *
     * @return VIPBean��ArrayList����
     */
    ArrayList<UserBean> selectAllUser();

    /**
     * ������ɸѡ��Ϣ
     *
     * @param id         ��Ա����
     * @param name       ����
     * @param card_level ��Ա�ȼ�
     * @return VIPBean��ArrayList����, û�в�ѯ���ʱΪnull
     */
    ArrayList<UserBean> selectByFiltrate(@Param("id") String id,
                                         @Param("name") String name,
                                         @Param("card_level") String card_level);

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
    ArrayList<UserBean> selectByDateFiltrate(@Param("id") String id,
                                             @Param("name") String name,
                                             @Param("level") String level,
                                             @Param("dateFrom") String dateFrom,
                                             @Param("dateTo") String dateTo);


    /**
     * �����Ա��Ϣ
     *
     * @param vipBean VIPBean����
     */
    void insertInfo(UserBean vipBean);

    /**
     * ͨ������ɾ����Ա��Ϣ
     *
     * @param id ��Ա����
     */
    void deleteByID(String id);

    /**
     * �����û���Ϣ
     *
     * @param vipBean VIPBean����
     */
    void updateInfo(UserBean vipBean);

}
