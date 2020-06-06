package datapack;

public class UserWithRetCode {
	User user;
	int return_code;
	public UserWithRetCode(User user, int return_code) {
		// TODO Auto-generated constructor stub
		this.user = user;
		this.return_code = return_code;
	}
	public int get_return_code() {
		return return_code;
	}
	public User get_user() {
		return user;
	}
}
