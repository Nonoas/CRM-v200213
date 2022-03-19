package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.dto.GoodsDto;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
     * 通过 编号，名称和类型筛选查找
     *
     * @param id   编号
     * @param name 名称
     * @param type 类型
     * @return 商品集合，可null
     */
    ArrayList<GoodsDto> selectByFiltrate(@Param("id") String id,
                                         @Param("name") String name,
                                         @Param("type") String type);

    /**
     * 通过id删除商品
     *
     * @param id 商品id
     */
    void deleteByID(String id);

    /**
     * 插入商品信息
     *
     * @param bean 商品信息
     */
    void insertInfo(GoodsDto bean);

    /**
     * 更新商品信息
     *
     * @param goodsBean GoodsBean
     */
    void update(GoodsDto goodsBean);

    /**
     * 查询商品类型
     *
     * @return 商品类型集合
     */
    LinkedList<String> selectGoodsTypes();

    /**
     * 更新商品数量
     *
     * @param goodsBeans 商品信息
     * @return 更新成功的条目数
     */
    long updateGoodsAmount(@Param("goodsBeans") List<GoodsDto> goodsBeans);

}
