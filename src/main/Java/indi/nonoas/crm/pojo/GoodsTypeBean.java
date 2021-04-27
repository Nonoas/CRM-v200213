package indi.nonoas.crm.pojo;

/**
 * 商品类别Bean
 * @author Nonoas
 *
 */
public class GoodsTypeBean {
	/**编号*/
	private String id;
	/**名称*/
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
