package indi.nonoas.crm.dao;

import java.util.ArrayList;

import indi.nonoas.crm.bean.PackageContentBean;

/**
 * �ײ���Ŀ�������ݿ������
 * 
 * @author Nonoas
 *
 */
public class PackageContentDao extends MyDao<PackageContentBean>{

	private static final String SELECT_BY_ID = "select * from package_content where pkg_id=#{pkg_id}";

	/**
	 * ͨ����Ŀid��ѯ��Ŀ����
	 * @param pkg_id ��Ŀid
	 * @return ��Ŀid��������Ʒ��Ϣ������Ϊnull
	 */
	public ArrayList<PackageContentBean> selectById(String pkg_id) {
		return (ArrayList<PackageContentBean>) select(SELECT_BY_ID,pkg_id);
	}

	@Override
	protected Class<PackageContentBean> getBeanClass() {
		return PackageContentBean.class;
	}
}
