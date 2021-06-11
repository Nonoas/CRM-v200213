package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.PackageDto;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2021-06-11 15:15
 */
public interface PackageService {

    /**
     * ��ѯ������Ŀ��Ϣ
     *
     * @return ������Ŀ��Ϣ
     */
    List<PackageDto> selectAll();

    /**
     * ͨ��id������Ŀ��Ϣ
     *
     * @param id ��Ŀid
     * @return ��Ӧ����Ŀ��Ϣbean�����
     */
    PackageDto selectById(String id);

    /**
     * ����һ����Ŀ��Ϣ
     *
     * @param bean PackageBean����
     */
    void insert(PackageDto bean);

    /**
     * ����һ����Ŀ��Ϣ
     *
     * @param bean PackageBean����
     */
    void update(PackageDto bean);

    void deleteById(String id);

    /**
     * ͨ�����˲�����Ŀ��Ϣ
     *
     * @param id     ���
     * @param name   ����
     * @param money1 ��Ǯ����
     * @param money2 ��Ǯ����
     * @return �������������������Ŀ��Ϣ, ����Ϊnull
     */
    List<PackageDto> findByFilter(String id, String name, double money1, double money2);

}
