package indi.nonoas.crm.pojo;

/**
 * 登录信息
 * 
 * @author Nonoas
 *
 */
public class LoginDto {

	/** 账号 */
	private String userId;
	/** 姓名 */
	private String userName;
	/** 密码 */
	private String password;
	/** 权限 */
	private int root;

	public LoginDto() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
				"id='" + userId + '\'' +
				", name='" + userName + '\'' +
				", password='" + password + '\'' +
				", root=" + root +
				'}';
	}
}
