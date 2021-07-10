package indi.nonoas.crm.pojo;

/**
 * 登录信息
 *
 * @author Nonoas
 *
 */
public class LoginDto {

	/** 账号 */
	private String id;
	/** 姓名 */
	private String name;
	/** 密码 */
	private String password;
	/** 权限 */
	private int root;

	public LoginDto() {
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

	@Override
	public String toString() {
		return "LoginBean{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", root=" + root +
				'}';
	}
}
