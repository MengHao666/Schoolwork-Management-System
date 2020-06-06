package datapack;

public class Question {
	int queid;
	String qmain;
	String qanswer;
	public Question(int queid, String qmain, String qanswer) {
		// TODO Auto-generated constructor stub
		this.queid = queid;
		this.qmain = qmain;
		this.qanswer = qanswer;
	}
	
	public String get_qmain() {
		return qmain;
	}
	
	public String get_true_answer() {
		return qanswer;
	}
	
}
