import java.io.Serializable;

class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String user;
	private String password;
	
	public User(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}
	
	public static User[] sampleUser = {
	                                            new User("0987654321","q2w2e3"),
	                                            new User("0983313567","ki98u7"),
	                                            new User("0912345678","ngaythu5"),
	                                            new User("0987452100","so1dcv")
	                                            };
	
	@Override
	public String toString() {
		return String.format("User: %s. Pass: %s\n", user, password);
	}
	
}