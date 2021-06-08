package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.dto.GoodsDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-01 19:47
 */
@Repository
public interface GoodsMapper {

    /**
     * ͨ����Ʒid��ѯ��Ӧ��Ʒ��������Ϣ
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
    ArrayList<GoodsDto> selectByFiltrate(@Param("id") String id,
                                         @Param("name") String name,
                                         @Param("type") String type);

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
     * ��ѯ������Ʒ��������
     *
     * @return ������Ʒ�������ƣ�����Ϊnull
     */
    LinkedList<String> selectGoodsTypes();

    /**
     * ���������޸���Ʒ��Ϣ
     *
     * @return Ӱ�������
     */
    long updateGoodsAmount(@Param("goodsBeans") List<GoodsDto> goodsBeans);

}
