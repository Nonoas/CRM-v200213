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
     * 通过商品id查询
     *
     * @param id 商品id
     * @return GoodsDto 可null
     */
    GoodsDto selectById(String id);

    /**
     * 查询所有商品
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
     * 通过id删除商品
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
     *
     * @return ������Ʒ�������ƣ�����Ϊnull
     */
    LinkedList<String> selectGoodsTypes();

    /**
     * 进价�޸���Ʒ��Ϣ
     *
     * @return Ӱ�������
     */
    long updateGoodsAmount(@Param("goodsBeans") List<GoodsDto> goodsBeans);

}
