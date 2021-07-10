package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.dto.VipInfoDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-04 1:34
 */
@Transactional
public interface VipService {
    /**
     * �Զ����ɻ�Ա����
     *
     * @return 32������ ��Ա����
     */
    String generateVipID();

    /**
     * ͨ�����Ż�������ȷ��ѯ
     *
     * @param id   ��Ա����
     * @param name ��Ա����
     * @return VipBean
     */
    VipInfoDto getInfoByIdOrName(String id, String name);

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
    ArrayList<VipInfoDto> selectByFiltrate(String id, String name, String card_level);

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
    List<VipInfoDto> selectByDateFiltrate(String id, String name, String level, String dateFrom, String dateTo);

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
