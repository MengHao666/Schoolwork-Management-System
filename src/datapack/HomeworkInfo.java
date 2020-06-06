package datapack;

public class HomeworkInfo {
	String hwname;
	String teacher;
	int qnumber;
	boolean is_completed;
	public HomeworkInfo(String hwname, String teacher, int qnumber) {
		// TODO Auto-generated constructor stub
		this.hwname = hwname;
		this.teacher = teacher;
		this.qnumber = qnumber;
		is_completed = false;
	}
	
	public void set_completed(boolean is_completed) {
		this.is_completed = is_completed;
	}
	
	public String get_hwname() {
		return hwname;
	}
	
	public int get_qnumber() {
		return qnumber;
	}
	public String get_teacher() {
		return teacher;
	}
	
	public boolean is_completed() {
		return is_completed;
	}
}
