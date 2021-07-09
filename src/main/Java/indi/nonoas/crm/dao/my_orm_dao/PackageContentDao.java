package indi.nonoas.crm.dao.my_orm_dao;

import java.util.ArrayList;

import indi.nonoas.crm.pojo.PackageContentDto;

/**
 * 套餐项目内容数据库操作类
 *
 * @author Nonoas
 */
@Deprecated
public class PackageContentDao extends SqliteDao<PackageContentDto> {

    private static final String SELECT_BY_ID = "select * from package_content where pkg_id=#{pkg_id}";

    private PackageContentDao() {

    }

    private static final PackageContentDao INSTANCE = new PackageContentDao();

    public static PackageContentDao getInstance() {
        return INSTANCE;
    }


    /**
     * 通过项目id查询项目内容
     *
     * @param pkg_id 项目id
     * @return 项目id包含的商品信息，可以为null
     */
    public ArrayList<PackageContentDto> selectById(String pkg_id) {
        return (ArrayList<PackageContentDto>) select(SELECT_BY_ID, pkg_id);
    }


    @Override
    protected Class<PackageContentDto> getBeanClass() {
        return PackageContentDto.class;
    }
}
