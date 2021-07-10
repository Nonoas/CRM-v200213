package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.dto.VipInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-09-03 21:30
 */
@Repository
public interface VipMapper {
    /**
     * ͨ����Ա���Ų�ѯ��Ա��Ϣ
     *
     * @param id ��Ա����
     * @return VIPBean����
     */
    VipInfoDto getUserById(String id);

    /**
     * ͨ�����Ż�������ȷ��ѯ
     *
     * @param id   ��Ա����
     * @param name ��Ա����
     * @return VipBean
     */
    VipInfoDto getInfoByIdOrName(@Param("id") String id,
                                 @Param("name") String name);

    /**
     * ��ѯ���л�Ա��Ϣ
     *
     * @return VIPBean��ArrayList����
     */
    ArrayList<VipInfoDto> selectAllUser();

    /**
     * ������ɸѡ��Ϣ
     *
     * @param id         ��Ա����
     * @param name       ����
     * @param card_level ��Ա�ȼ�
     * @return VIPBean��ArrayList����, û�в�ѯ���ʱΪnull
     */
    ArrayList<VipInfoDto> selectByFiltrate(@Param("id") String id,
                                           @Param("name") String name,
                                           @Param("card_level") String card_level);

    /**
     * 进价��
     *
     * @param id       ����
     * @param name     ����
     * @param level    ��Ա�ȼ�
     * @param dateFrom ����ʱ�䷶Χ����ʼ��
     * @param dateTo   ����ʱ�䷶Χ进价
     * @return VIPBean��ArrayList����, û�в�ѯ���ʱΪnull
     */
    ArrayList<VipInfoDto> selectByDateFiltrate(@Param("id") String id,
                                               @Param("name") String name,
                                               @Param("level") String level,
                                               @Param("dateFrom") String dateFrom,
                                               @Param("dateTo") String dateTo);


    /**
     * �����Ա��Ϣ
     *
     * @param vipBean VIPBean����
     */
    void insertInfo(VipInfoDto vipBean);

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
    void updateInfo(VipInfoDto vipBean);

}
