package indi.nonoas.crm.dao;

import java.util.ArrayList;

import indi.nonoas.crm.beans.GoodsBean;

/**
 * ��Ʒ��Ϣ��DAO
 *
 * @author Nonoas
 */
public class GoodsDao extends SqliteDao<GoodsBean> {

    private static final String SELECT_ALL = "select * from goods_info";

    private static final String SELECT_BY_FILTRATE = "select * from goods_info " +

            "where (id like #{id} or name like #{name}) and type like #{type}";

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
     * ͨ����Ʒid��ѯ��Ӧ��Ʒ��������Ϣ
     *
     * @param id ��Ʒid
     * @return id��Ӧ����Ʒ��Ϣbean��
     */
    public GoodsBean selectById(String id) {
        return selectOne(SELECT_BY_ID, id);
    }

    /**
     * ��ѯ���е���Ʒ��Ϣ
     *
     * @return GoodsBean��ArrayList����, ��Ϊnull
     */
    public ArrayList<GoodsBean> selectAll() {
        ArrayList<GoodsBean> list = null;
        list = (ArrayList<GoodsBean>) select(SELECT_ALL);
        return list;
    }

    /**
     * ������ɸѡģ����ѯ
     *
     * @param id   ��Ʒ���
     * @param name ��Ʒ����
     * @param type ��Ʒ����
     * @return GoodsBean��ArrayList����, ����Ϊnull
     */
    public ArrayList<GoodsBean> selectByFiltrate(String id, String name, String type) {
        ArrayList<GoodsBean> list;
        list = (ArrayList<GoodsBean>) select(SELECT_BY_FILTRATE, id, name, type);
        return list;
    }

    /**
     * ɾ��һ����Ʒ��Ϣ
     *
     * @param bean GoodsBean����
     */
    public void deleteByID(GoodsBean bean) {
        delete(DELETE_BY_ID, bean);
    }

    /**
     * ����һ����Ʒ��Ϣ
     *
     * @param bean GoodsBean����
     */
    public void insertInfo(GoodsBean bean) {
        insert(INSERT_INFO, bean);
    }

    /**
     * ������Ʒ��Ϣ
     *
     * @param goodsBean GoodsBean����
     */
    public void update(GoodsBean goodsBean) {
        update(UPDATE, goodsBean);
    }

    @Override
    protected Class<GoodsBean> getBeanClass() {
        return GoodsBean.class;
    }

}
