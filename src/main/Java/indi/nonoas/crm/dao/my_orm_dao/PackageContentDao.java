package indi.nonoas.crm.dao.my_orm_dao;

import java.util.ArrayList;

import indi.nonoas.crm.pojo.PackageContentDto;

/**
 * �ײ���Ŀ�������ݿ������
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
     * ͨ����Ŀid��ѯ��Ŀ����
     *
     * @param pkg_id ��Ŀid
     * @return ��Ŀid��������Ʒ��Ϣ������Ϊnull
     */
    public ArrayList<PackageContentDto> selectById(String pkg_id) {
        return (ArrayList<PackageContentDto>) select(SELECT_BY_ID, pkg_id);
    }


    @Override
    protected Class<PackageContentDto> getBeanClass() {
        return PackageContentDto.class;
    }
}
