package indi.nonoas.crm.dao;

import java.util.ArrayList;

import indi.nonoas.crm.bean.PackageContentBean;

/**
 * 套餐项目内容数据库操作类
 *
 * @author Nonoas
 */
public class PackageContentDao extends MyDao<PackageContentBean> {

    private static final String SELECT_BY_ID = "select * from package_content where pkg_id=#{pkg_id}";

    private static final String INSERT_INFO = "insert into " +

            "package_content (pkg_id,goods_id,goods_amount)" +

            "values (#{pkg_id},#{goods_id},#{goods_amount})";

    private PackageContentDao(){

    }

    private static final PackageContentDao INSTANCE=new PackageContentDao();

    public static PackageContentDao getInstance(){
        return INSTANCE;
    }


    /**
     * 通过项目id查询项目内容
     *
     * @param pkg_id 项目id
     * @return 项目id包含的商品信息，可以为null
     */
    public ArrayList<PackageContentBean> selectById(String pkg_id) {
        return (ArrayList<PackageContentBean>) select(SELECT_BY_ID, pkg_id);
    }

    /**
     * 批量插入套餐内商品信息
     * @param beans 商品信息集合
     */
    public void insertInfos(ArrayList<PackageContentBean> beans){
        executeBatch(INSERT_INFO,beans);
    }

    @Override
    protected Class<PackageContentBean> getBeanClass() {
        return PackageContentBean.class;
    }
}
