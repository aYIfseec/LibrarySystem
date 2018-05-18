package modle;

public class User {
	private int id;
	private String name;
	private String password;
	private int point;
	
	public User() {}
	public User(int id, String name, String password, int point) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.point = point;
	}
	
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}//只允许设置一次，即注册时
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	//管理员可获得密码，以帮助忘记密码的同学；但user不能getPassword
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	
	public int getPoint() {return point;}
	//public void setPoint(int point) {this.point = point;}
	
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", point=" + point
				+ "]";
	}
	
}
