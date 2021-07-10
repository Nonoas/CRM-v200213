package indi.nonoas.crm.pojo;

/**
 * ��¼��Ϣ
 * 
 * @author Nonoas
 *
 */
public class LoginDto {

	/** �˺� */
	private String userId;
	/** ���� */
	private String userName;
	/** ���� */
	private String password;
	/** Ȩ�� */
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
