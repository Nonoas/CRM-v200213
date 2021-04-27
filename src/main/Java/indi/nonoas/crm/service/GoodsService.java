package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.GoodsBean;
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
    ArrayList<GoodsBean> selectByFiltrate(String id, String name, String type);

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
     * @return 所有商品分类名称，可以为null
     */
    LinkedList<String> selectGoodsTypes();
}
