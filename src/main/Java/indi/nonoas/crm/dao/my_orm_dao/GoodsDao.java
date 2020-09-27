package indi.nonoas.crm.dao.my_orm_dao;

import indi.nonoas.crm.beans.GoodsBean;

/**
 * 商品信息的DAO
 *
 * @author Nonoas
 */
@Deprecated
public class GoodsDao extends SqliteDao<GoodsBean> {

    private static final String SELECT_BY_ID = "select * from goods_info where id=#{id}";

    private static final String INSERT_INFO = "insert into goods_info(id,name,sell_price,purchase_price,quantity,min_discount,deduction,deduction_rate,base_unit,type,photo) "

            + "values(#{id},#{name},#{sell_price},#{purchase_price},#{quantity},#{min_discount},#{deduction},#{deduction_rate},#{base_unit},#{type},#{photo})";

    private static final String DELETE_BY_ID = "delete from goods_info where id=#{id}";

    private static final String UPDATE = "update goods_info " +

            "set name=#{name},sell_price=#{sell_price},purchase_price=#{purchase_price},quantity=#{quantity},min_discount=#{min_discount}," +

            "deduction=#{deduction},deduction_rate=#{deduction_rate},base_unit=#{base_unit},type=#{type},photo=#{photo} where id=#{id}";


    private static final GoodsDao INSTANCE = new GoodsDao();

    private GoodsDao() {
    }

    public static GoodsDao getInstance() {
        return INSTANCE;
    }

    /**
     * 通过商品id查询对应商品的所有信息
     *
     * @param id 商品id
     * @return id对应的商品信息bean类
     */
    public GoodsBean selectById(String id) {
        return selectOne(SELECT_BY_ID, id);
    }


    /**
     * 删除一条商品信息
     *
     * @param bean GoodsBean对象
     */
    public void deleteByID(GoodsBean bean) {
        delete(DELETE_BY_ID, bean);
    }

    /**
     * 插入一条商品信息
     *
     * @param bean GoodsBean对象
     */
    public void insertInfo(GoodsBean bean) {
        insert(INSERT_INFO, bean);
    }

    /**
     * 更新商品信息
     *
     * @param goodsBean GoodsBean对象
     */
    public void update(GoodsBean goodsBean) {
        update(UPDATE, goodsBean);
    }

    @Override
    protected Class<GoodsBean> getBeanClass() {
        return GoodsBean.class;
    }

}
