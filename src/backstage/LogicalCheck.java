package backstage;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import datapack.DoubleString;
import datapack.Range;
import datapack.User;

import datapack.UserWithRetCode;
import datapack.HomeworkInfo;
import datapack.HomeworkInfo;
import datapack.Question;
import datapack.StudentHwInfo;

public class LogicalCheck {

	DBConnector dbConnector;
	int mode; // 1 for database otherwise for local file

	public LogicalCheck() {
		// TODO Auto-generated constructor stub
		dbConnector = new DBConnector();
		mode = 1;
	}

	public UserWithRetCode check_user(String userid, String password) {

		if (mode == 1) {
			dbConnector.open_connect();
			Vector<User> users = new Vector<User>();
			dbConnector.select_user(users, 100);
			dbConnector.close_connect();
			for (User user : users) {
				if (user.get_userid().equals(userid)) {
					if (user.get_password().equals(password)) {
						return new UserWithRetCode(user, user.get_grade()); // success
					} else {
						return new UserWithRetCode(user, 100); // password error
					}
				}
			}
		} else {

		}
		return new UserWithRetCode(new User(), 2); // no such user
	}

	public Vector<User> get_dbUsers(int gradetochoose) {

		dbConnector.open_connect();
		Vector<User> users = new Vector<User>();
		dbConnector.select_user(users, gradetochoose);
		dbConnector.close_connect();
		return users;
	}

	public int insert_dbUsers(User u) throws SQLException {

		dbConnector.open_connect();
		int rows = dbConnector.insert_user(u);
		dbConnector.close_connect();
		return rows;
	}

	public int delete_dbUsers(String user_id) throws SQLException {

		dbConnector.open_connect();
		int rows = dbConnector.delete_user(user_id);
		dbConnector.close_connect();
		return rows;
	}

	public int update_dbUsers(User u) throws SQLException {

		dbConnector.open_connect();
		int rows = dbConnector.update_user(u);
		dbConnector.close_connect();
		return rows;
	}

	public void set_mode(int mode) {
		this.mode = mode;
	}

	public int get_mode() {
		return mode;
	}

	public void get_question(int numbers, Vector<Question> questions, Vector<String> student_answers, String hwname,
			String userid) {
		// get nums questions
		if (mode == 1) {
			dbConnector.open_connect();
			if (!dbConnector.is_temp_save(hwname, userid)) {

				dbConnector.select_question(questions, numbers);
				for (Question q : questions) {
					student_answers.add("");
				}
			} else {

				dbConnector.reproduce_student_answer(hwname, userid, questions, student_answers);
			}

			dbConnector.close_connect();
		} else {

		}

	}

	public void release_homework(User teacher, int homework_nums, String hwname) {
		if (mode == 1) {
			dbConnector.open_connect();
			int count = dbConnector.get_homework_counts();
			dbConnector.insert_homework(hwname, teacher.get_user_name(), homework_nums, count);
			dbConnector.close_connect();
		}
	}

	public void select_homeworks(Vector<HomeworkInfo> homeworks, String userid) {
		if (mode == 1) {
			dbConnector.open_connect();
			dbConnector.select_homeworks(homeworks);
			dbConnector.check_homeworks_completed(homeworks, userid);
			dbConnector.close_connect();
		} else {

		}
	}

	public void submit(String hwname, String userid, Vector<Question> questions, Vector<String> student_answers)
			throws SQLException {
		if (mode == 1) {
			dbConnector.open_connect();
			dbConnector.submit_answer(hwname, userid, questions, student_answers);
			dbConnector.close_connect();
		} else {

		}
	}

	public void get_all_student_hw(User teacher, Vector<HomeworkInfo> homeworks, Vector<StudentHwInfo> shw,
			Vector<Range> ranges) {
		if (mode == 1) {
			dbConnector.open_connect();
			for (HomeworkInfo homework : homeworks) {
				dbConnector.select_teacher_student_hw(homework, shw, ranges);
			}
			dbConnector.close_connect();
		}
	}

	public void check_is_correct(Vector<StudentHwInfo> shw) {
		if (mode == 1) {
			dbConnector.open_connect();
			for (StudentHwInfo homework : shw) {
				dbConnector.check_is_correct(homework);
			}
			dbConnector.close_connect();
		}
	}

	public int get_homework_counts(User teacher, Vector<HomeworkInfo> homeworks) {
		int count = 0;
		if (mode == 1) {
			dbConnector.open_connect();

			count = dbConnector.get_homework_counts(teacher.get_user_name());
			dbConnector.select_homeworks(homeworks, teacher.get_user_name());

			dbConnector.close_connect();
		} else {

		}
		return count;
	}
	
	public void get_student_answer(Vector<Question> questions, Vector<String> answers, StudentHwInfo hw) {
		if (mode == 1) {
			dbConnector.open_connect();
			dbConnector.reproduce_student_answer(hw.get_hwname(), hw.get_studentID(), questions, answers);
			dbConnector.close_connect();
		}
	}
	
	public void submit_correct(StudentHwInfo hw, Vector<String> scores, User teacher) {
		if (mode == 1) {
			dbConnector.open_connect();
			String scores_s = "";
			for (String score : scores) {
				scores_s += score;
				scores_s += "---";
			}
			dbConnector.submit_correct(hw.get_hwname(), hw.get_qnumber(), scores_s, hw.get_studentID(), teacher.get_user_name());
			dbConnector.close_connect();
		}
	}
	
	public void get_scores(Vector<Question> questions, Vector<String> student_answers, StudentHwInfo hw, Vector<String> scores) {
		if (mode == 1) {
			dbConnector.open_connect();
			dbConnector.reproduce_student_answer(hw.get_hwname(), hw.get_studentID(), questions, student_answers);
			dbConnector.select_scores(hw.get_hwname(), hw.get_studentID(), scores);
			dbConnector.close_connect();
		}
	}
	
	public String get_scores(Vector<String> scores, String hwname, String userid) {
		if (mode == 1) {
			dbConnector.open_connect();
			DoubleString ds = dbConnector.get_scores(hwname, userid);
			String teacher = ds.s2;
			String score_string = ds.s1;
			if (score_string.equals("")){
				scores.clear();
				return "undefined";
			}
			else {
				String[] ss = score_string.split("---");
				for (String s : ss) {
					scores.add(s);
				}
			}
			dbConnector.close_connect();
			return teacher;
		}
		else {
			
		}
		return "undefined";
	}

}