package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.GoodsBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author : Nonoas
 * @time : 2020-09-01 19:47
 */
@Repository
public interface GoodsMapper {

    /**
     * 通过商品id查询对应商品的所有信息
     *
     * @param id 商品id
     * @return id对应的商品信息bean类
     */
    GoodsBean selectById(String id);

    /**
     * 查询所有的商品信息
     *
     * @return GoodsBean的ArrayList集合, 可为null
     */
    ArrayList<GoodsBean> selectAll();

    /**
     * 按条件筛选模糊查询
     *
     * @param id   商品编号
     * @param name 商品名称
     * @param type 商品种类
     * @return GoodsBean的ArrayList集合, 可以为null
     */
    ArrayList<GoodsBean> selectByFiltrate(@Param("id") String id,
                                          @Param("name") String name,
                                          @Param("type") String type);

    /**
     * 删除一条商品信息
     *
     * @param id 商品id
     */
    void deleteByID(String id);

    /**
     * 插入一条商品信息
     *
     * @param bean GoodsBean对象
     */
    void insertInfo(GoodsBean bean);

    /**
     * 更新商品信息
     *
     * @param goodsBean GoodsBean对象
     */
    void update(GoodsBean goodsBean);

    /**
     * 查询所有商品分类名称
     *
     * @return 所有商品分类名称，可以为null
     */
    LinkedList<String> selectGoodsTypes();

}
