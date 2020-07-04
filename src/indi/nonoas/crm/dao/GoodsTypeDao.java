package indi.nonoas.crm.dao;

import java.util.ArrayList;
import java.util.LinkedList;

import indi.nonoas.crm.bean.GoodsTypeBean;

/**
 * ��Ʒ�������ݿ������
 * 
 * @author Nonoas
 *
 */
public class GoodsTypeDao extends MyDao<GoodsTypeBean> {

	private static final String SELECT_ALL_NAMES = "select name from goods_type";

	/**
	 * ��ѯ������Ʒ��������
	 * @return ������Ʒ�������ƣ�����Ϊnull
	 */
	public LinkedList<String> selectAllNames() {
		ArrayList<GoodsTypeBean> listBeans = (ArrayList<GoodsTypeBean>) select(SELECT_ALL_NAMES);
		if (listBeans == null)
			return null;
		LinkedList<String> names=new LinkedList<String>();
		for(GoodsTypeBean str:listBeans) {
			names.add(str.getName());
		}
		return names;
	}

	@Override
	protected Class<GoodsTypeBean> getBeanClass() {
		return GoodsTypeBean.class;
	}

}
