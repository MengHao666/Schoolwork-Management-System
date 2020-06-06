package datapack;

import java.util.Vector;

public class QuestionWithStudentAnswer {
	Vector<Question> questions;
	Vector<String> answers;
	int question_nums;
	public QuestionWithStudentAnswer(String qmain, String student_answer, String true_answer) {
		// TODO Auto-generated constructor stub
		
		String[] qmains = qmain.split("---");
		String[] student_answers = student_answer.split("---", -1);
		String[] true_answers = true_answer.split("---");
		questions = new Vector<Question>(qmains.length);
		answers = new Vector<String>(qmains.length);
		question_nums = qmains.length;
//		System.out.println(question_nums);
		for (int i = 0; i < qmains.length; i++) {
			questions.add(new Question(i, qmains[i], true_answers[i]));
			answers.add(student_answers[i]);
		}
	}
	public void fill_questions_answer_vector(Vector<Question> questions, Vector<String> student_answers) {
		questions.clear();
		student_answers.clear();
		for (int i = 0; i < question_nums; i++) {
			questions.add(this.questions.get(i));
			student_answers.add(this.answers.get(i));
		}
	}
}
