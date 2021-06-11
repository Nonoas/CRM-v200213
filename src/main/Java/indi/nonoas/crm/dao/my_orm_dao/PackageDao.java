package indi.nonoas.crm.dao.my_orm_dao;

import java.util.ArrayList;

import indi.nonoas.crm.pojo.PackageDto;

/**
 * �ײ���Ŀ���ݿ������
 *
 * @author Nonoas
 */
@Deprecated
public class PackageDao extends SqliteDao<PackageDto> {

    private static final String SELECT_ALL = "select * from package_info";

    private static final String SELECT_BY_ID = "select * from package_info where id=#{id}";

    private static final String FIND_BY_FILTER = "select * from package_info " +

            "where (id like #{id} or name like #{name}) and (money_cost between #{money1} and #{money2})";

    private static final String INSERT_ONE = "insert into " +

            "package_info (id,name,integral_cost,money_cost,min_discount,type,other) " +

            "values (#{id},#{name},#{integral_cost},#{money_cost},#{min_discount},#{type},#{other})";

    private static final String UPDATE_ONE = "update package_info " +
            "set name=#{name},integral_cost=#{integral_cost},money_cost=#{money_cost},min_discount=#{min_discount}," +
            "type=#{type},other=#{other} where id=#{id}";

    private PackageDao() {

    }

    private static final PackageDao INSTANCE = new PackageDao();

    public static PackageDao getInstance() {
        return INSTANCE;
    }

    /**
     * ��ѯ������Ŀ��Ϣ
     *
     * @return ������Ŀ��Ϣ
     */
    public ArrayList<PackageDto> selectAll() {
        return (ArrayList<PackageDto>) select(SELECT_ALL);
    }

    /**
     * ͨ��id������Ŀ��Ϣ
     *
     * @param id ��Ŀid
     * @return ��Ӧ����Ŀ��Ϣbean�����
     */
    public PackageDto selectById(String id) {
        return selectOne(SELECT_BY_ID, id);
    }

    /**
     * ͨ�����˲�����Ŀ��Ϣ
     *
     * @param id     ���
     * @param name   ����
     * @param money1 ��Ǯ����
     * @param money2 ��Ǯ����
     * @return �������������������Ŀ��Ϣ, ����Ϊnull
     */
    public ArrayList<PackageDto> findByFilter(String id, String name, double money1, double money2) {
        return (ArrayList<PackageDto>) select(FIND_BY_FILTER, id, name, money1, money2);
    }

    /**
     * ����һ����Ŀ��Ϣ
     *
     * @param bean PackageBean����
     */
    public void insert(PackageDto bean) {
        insert(INSERT_ONE, bean);
    }

    /**
     * ����һ����Ŀ��Ϣ
     *
     * @param bean PackageBean����
     */
    public void update(PackageDto bean) {
        update(UPDATE_ONE, bean);
    }

    @Override
    protected Class<PackageDto> getBeanClass() {
        return PackageDto.class;
    }

}
