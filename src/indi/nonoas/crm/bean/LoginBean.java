package indi.nonoas.crm.bean;

/**
 * 登录信息
 * 
 * @author Nonoas
 *
 */
public class LoginBean {

	/** 账号 */
	private String id;
	/** 姓名 */
	private String name;
	/** 密码 */
	private String password;
	/** 权限 */
	private int root;

	public LoginBean() {
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int isRoot() {
		return root;
	}

	public void setRoot(int root) {
		this.root = root;
	}

}
