package datapack;

public class StudentHwInfo extends HomeworkInfo{
	public StudentHwInfo(String hwname, String teacher, int qnumber, String studentID) {
		super(hwname, teacher, qnumber);
		// TODO Auto-generated constructor stub
		this.studentID = studentID;
		this.hwname = hwname;
		this.qnumber = qnumber;
		is_correct = false;
	}
	String studentID;
	String hwname;
	boolean is_correct;
	public void set_correct(boolean is_correct) {
		this.is_correct = is_correct;
	}
	
	public String get_studentID() {
		return studentID;
	}
	
	public String get_hwname() {
		return hwname;
	}
	
	public int get_qnumber() {
		return qnumber;
	}
	
	public boolean is_correct() {
		return is_correct;
	}
}
