package indi.nonoas.crm.bean;

/**
 * ��¼��Ϣ
 * 
 * @author Nonoas
 *
 */
public class LoginBean {

	/** �˺� */
	private String id;
	/** ���� */
	private String name;
	/** ���� */
	private String password;
	/** Ȩ�� */
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
