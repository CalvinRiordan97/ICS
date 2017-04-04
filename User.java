package Model;

public class User {
	private String userName;
	private String password;
	private final String PRODUCT_OWNER = "PO";
	private final String DEVLOPER = "DEV";
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
	public String getPRODUCT_OWNER() {
		return PRODUCT_OWNER;
	}
	public String getDEVLOPER() {
		return DEVLOPER;
	}
	
	
}
