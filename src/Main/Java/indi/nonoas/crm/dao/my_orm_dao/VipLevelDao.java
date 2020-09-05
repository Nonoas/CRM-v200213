package indi.nonoas.crm.dao.my_orm_dao;

import java.util.ArrayList;
import java.util.LinkedList;

import indi.nonoas.crm.beans.VipLevelBean;
import indi.nonoas.crm.dao.my_orm_dao.SqliteDao;

/**
 * ��Ա�ȼ����ݿ������
 *
 * @author Nonoas
 */
public class VipLevelDao extends SqliteDao<VipLevelBean> {

    private static final String SELECT_ALL_NAMES = "select name from vip_level";

    /**
     * ��ѯ���еĻ�Ա�ȼ�����
     *
     * @return ���еĻ�Ա�ȼ����ƣ�����Ϊnull
     */
    public LinkedList<String> selectAllNames() {
        ArrayList<VipLevelBean> a_listBean = (ArrayList<VipLevelBean>) select(SELECT_ALL_NAMES);
        if (a_listBean == null)
            return null;
        LinkedList<String> l_listName = new LinkedList<String>();
        for (VipLevelBean bean : a_listBean) {
            l_listName.add(bean.getName());
        }
        return l_listName;
    }

    @Override
    protected Class<VipLevelBean> getBeanClass() {
        return VipLevelBean.class;
    }

}
