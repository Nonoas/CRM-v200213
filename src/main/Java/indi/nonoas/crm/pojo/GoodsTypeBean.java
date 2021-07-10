package indi.nonoas.crm.pojo;

/**
 * ��Ʒ���Bean
 * @author Nonoas
 *
 */
public class GoodsTypeBean {
	/**���*/
	private String id;
	/**����*/
	private String name;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "GoodsTypeBean [id=" + id + ", name=" + name + "]";
	}
	
	
}
