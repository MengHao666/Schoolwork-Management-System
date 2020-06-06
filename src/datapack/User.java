package datapack;

public class User {
	String username;
	String userid;
	String password;
	int grade;
	public static int STUDENT=2;
	public static int TEACHER=1;
	public static int ADMIN=0;
	public User(String usernameString, String useridString, String passwordString, int grade) {
		// TODO Auto-generated constructor stub
		this.grade = grade;
		this.userid = useridString;
		this.username = usernameString;
		this.password = passwordString;
	}	
	public User() {
		this.grade = -1;
		this.userid = "";
		this.username = "";
		this.password = "";
	}
	
	
	public void print_self() {
		System.out.println("this is " + username + " id : " + userid + " grade : " + grade + " password : " + password);
	}
	public String get_user_name() {
		return username;
	}
	public int get_grade() {
		return grade;
	}
	
	public String get_userid() {
		return userid;
	}
	public String get_password() {
		return password;
	}
	
	public String pagetoEnter() {
		if (grade==0) {
			return ("login_administrator.jsp");
		}
		else if(grade==1) {
			return("login_teacher.jsp");
		}
		else if(grade==2) {
			return("login_student.jsp");
		}
		else {
			return("login_failed.jsp");
		}
	}
}
