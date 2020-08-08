package indi.nonoas.crm.dao;

import java.util.ArrayList;

import indi.nonoas.crm.beans.PackageContentBean;

/**
 * �ײ���Ŀ�������ݿ������
 *
 * @author Nonoas
 */
public class PackageContentDao extends MySqlDao<PackageContentBean> {

    private static final String SELECT_BY_ID = "select * from package_content where pkg_id=#{pkg_id}";

    private static final String INSERT_INFO = "insert into " +

            "package_content (pkg_id,goods_id,goods_amount)" +

            "values (#{pkg_id},#{goods_id},#{goods_amount})";

    private static final String DELETE_BY_ID = "delete from package_content where pkg_id=#{pkg_id}";

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
    public ArrayList<PackageContentBean> selectById(String pkg_id) {
        return (ArrayList<PackageContentBean>) select(SELECT_BY_ID, pkg_id);
    }

    /**
     * ���������ײ�����Ʒ��Ϣ
     *
     * @param beans ��Ʒ��Ϣ����
     */
    public void insertInfos(ArrayList<PackageContentBean> beans) {
        executeBatch(INSERT_INFO, beans);
    }

    /**
     * ����idɾ����Ϣ
     *
     * @param id ���
     */
    public void deleteById(String id) {
        PackageContentBean packageContentBean = new PackageContentBean();
        packageContentBean.setPkgId(id);
        delete(DELETE_BY_ID, packageContentBean);
    }

    @Override
    protected Class<PackageContentBean> getBeanClass() {
        return PackageContentBean.class;
    }
}
