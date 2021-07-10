package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.dto.GoodsDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author : Nonoas
 * @time : 2020-08-19 22:05
 */
@Transactional
public interface GoodsService{

    /**
     * ͨ����Ʒid��ѯ��Ӧ��Ʒ进价Ϣ
     *
     * @param id ��Ʒid
     * @return id��Ӧ����Ʒ��Ϣbean��
     */
    GoodsDto selectById(String id);

    /**
     * ��ѯ���е���Ʒ��Ϣ
     *
     * @return GoodsBean��ArrayList����, ��Ϊnull
     */
    ArrayList<GoodsDto> selectAll();

    /**
     * ������ɸѡģ����ѯ
     *
     * @param id   ��Ʒ���
     * @param name ��Ʒ����
     * @param type ��Ʒ����
     * @return GoodsBean��ArrayList����, ����Ϊnull
     */
    ArrayList<GoodsDto> selectByFiltrate(String id, String name, String type);

    /**
     * ɾ��һ����Ʒ��Ϣ
     *
     * @param id ��Ʒid
     */
    void deleteByID(String id);

    /**
     * ����һ����Ʒ��Ϣ
     *
     * @param bean GoodsBean����
     */
    void insertInfo(GoodsDto bean);

    /**
     * ������Ʒ��Ϣ
     *
     * @param goodsBean GoodsBean����
     */
    void update(GoodsDto goodsBean);

    /**
     * ��ѯ������Ʒ进价
     * @return ������Ʒ�������ƣ�����Ϊnull
     */
    LinkedList<String> selectGoodsTypes();
}
