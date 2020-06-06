package backstage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import datapack.DoubleString;
import datapack.Range;
import datapack.User;

import datapack.HomeworkInfo;
import datapack.Question;
import datapack.QuestionWithStudentAnswer;
import datapack.StudentHwInfo;

public class DBConnector {
	String driver = "com.mysql.cj.jdbc.Driver"; // 驱动名，默认
	String url = "jdbc:mysql://localhost:3306/test1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // 将要访问的数据库名称testone
	String user = "root"; // mysql数据库用户名
	String password = "123456"; // mysql数据库用户密码
	Connection conn = null;
	Statement state = null;

	public DBConnector() {
		// TODO Auto-generated constructor stub
	}

	void open_connect() {
		try {
			Class.forName(driver); // 加载驱动
			conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed()) {
				System.out.println("DB connected!");
			}
			state = conn.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("DB connect error!");
			e.printStackTrace();
		}
	}

	void close_connect() {
		try {
			state.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("DB close error!");
			e.printStackTrace();
		}
	}

	void select_user(Vector<User> users, int gradetoChoose) {

		String sql = "select * from USER";
		try {
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				if (gradetoChoose == 0 && (rs.getInt("USERGRADE") != 0)) {
					continue;
				} else if (gradetoChoose == 1 && (rs.getInt("USERGRADE") != 1)) {
					continue;
				} else if (gradetoChoose == 2 && (rs.getInt("USERGRADE") != 2)) {
					continue;
				}
				String usernameString = rs.getString("USERNAME");
				String useridString = rs.getString("USERID");
				String passwordString = rs.getString("PASSWORD");
				int grade = rs.getInt("USERGRADE");
				users.add(new User(usernameString, useridString, passwordString, grade));
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("select user error");
			e.printStackTrace();
		}
	}

	public int insert_user(User u) throws SQLException {
		int row = 0;
		String sql = "insert into USER(USERID,USERNAME,PASSWORD,USERGRADE) values(?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, u.get_userid());
		pstmt.setString(2, u.get_user_name());
		pstmt.setString(3, u.get_password());
		pstmt.setInt(4, u.get_grade());
		row = pstmt.executeUpdate();
		return row;
	}

	public int delete_user(String del_id) throws SQLException {
		int row = 0;
		String sql_delete = "delete  from USER where USERID='" + del_id + "'";
		// 获取要删除的此id的数据库信息
		PreparedStatement pst = conn.prepareStatement(sql_delete);
		row = pst.executeUpdate();
		return row;
	}

	public int update_user(User u) throws SQLException {
		int row = 0;
		String sql = "update USER set USERNAME=?, PASSWORD=?, USERGRADE=? where USERID='" + u.get_userid() + "'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, u.get_user_name());
		pstmt.setString(2, u.get_password());
		pstmt.setInt(3, u.get_grade());
		row = pstmt.executeUpdate();
		return row;
	}

	boolean is_temp_save(String hwname, String userid) {
		String sql = "select * from ANSWER where HWNAME=" + "\'" + hwname + "\' and USERID=\'" + userid + "\'";
		try {
			ResultSet rs = state.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("temp save check error");
			e.printStackTrace();
		}
		return false;
	}

	void update_student_answer(String hwname, String userid, Vector<String> student_answers) {
		String total_answers = "";
		for (String answer : student_answers) {
			total_answers += answer;
			total_answers += "---";
		}
		String sql = "update ANSWER set SANSWER = " + "\'" + total_answers + "\' where HWNAME = \'" + hwname
				+ "\' and USERID=\'" + userid + "\'";
		try {
			state.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("answer update error");
			e.printStackTrace();
		}
	}

	DoubleString get_scores(String hwname, String userid) {
		String sql = "select * from SCORE where HWNAME=\'" + hwname + "\' and STUDENT_ID=\'" + userid + "\'";
		try {
			ResultSet rs = state.executeQuery(sql);
			if (rs.next()) {
				String scores = rs.getString("SSCORE");
				String teacher = rs.getString("TEACHER");
				return new DoubleString(scores, teacher);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new DoubleString("", "");
	}

	void select_homeworks(Vector<HomeworkInfo> homeworks) {
		String sql = "select * from HWINFO";
		try {
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				String hwname = rs.getString("HWNAME");
				String teacher = rs.getString("TEACHER");
				int number = rs.getInt("QNUMBER");
				homeworks.add(new HomeworkInfo(hwname, teacher, number));
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("select homework error");
			e.printStackTrace();
		}
	}

	void select_homeworks(Vector<HomeworkInfo> homeworks, String teachername) {
		String sql = "select * from HWINFO where TEACHER=\'" + teachername + "\'";
		try {
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				String hwname = rs.getString("HWNAME");
				String teacher = rs.getString("TEACHER");
				int number = rs.getInt("QNUMBER");
				homeworks.add(new HomeworkInfo(hwname, teacher, number));
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("select homework error");
			e.printStackTrace();
		}
	}

	void check_homeworks_completed(Vector<HomeworkInfo> homeworks, String userid) {
		String sql = "select * from ANSWER where USERID=\'" + userid + "\' and IS_SUBMIT=1";
		try {
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				String hwname = rs.getString("HWNAME");
				for (HomeworkInfo homework : homeworks) {
					if (hwname.equals(homework.get_hwname())) {
						homework.set_completed(true);
						break;
					}
				}
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("check homewok error");
			e.printStackTrace();
		}
	}

	int get_homework_counts(String teachername) {
		String sql = "select * from HWINFO where TEACHER=\'" + teachername + "\'";
		int count = 0;
		try {
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				count++;
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("homework counts get error");
			e.printStackTrace();
		}
		return count;
	}

	int get_homework_counts() {
		String sql = "select * from HWINFO";
		int count = 0;
		try {
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				count++;
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("homework counts get error");
			e.printStackTrace();
		}
		return count;
	}

	void submit_correct(String hwname, int qnumber, String scores, String studentID, String teacher) {
		String sql = "insert into SCORE (HWNAME, QNUMBER, SSCORE, STUDENT_ID, TEACHER) values (\'" + hwname + "\', "
				+ qnumber + ", \'" + scores + "\', \'" + studentID + "\', \'" + teacher + "\')";
		try {
			state.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("submit correct error!");
			e.printStackTrace();
		}
	}

	void select_scores(String hwname, String studentID, Vector<String> scores) {
		String sql = "select * from SCORE where HWNAME=\'" + hwname + "\' and STUDENT_ID=\'" + studentID + "\'";
		try {
			ResultSet rs = state.executeQuery(sql);
			if (rs.next()) {
				String scores_s = rs.getString("SSCORE");
				String[] ss = scores_s.split("---");
				for (int i = 0; i < ss.length; i++) {
					scores.add(ss[i]);
				}
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("select scores error");
			e.printStackTrace();
		}
	}

	void insert_homework(String hwname, String teacher, int homework_nums, int count) {
		String sql = "insert into HWINFO values(" + (count + 1) + ", " + homework_nums + ", \'" + teacher + "\', \'"
				+ hwname + "\')";
		try {
			state.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("insert homework error!");
			e.printStackTrace();
		}
	}

	void select_question(Vector<Question> questions, int nums) {
		String sql = "select * from QUESTION order by rand() limit " + nums;
		try {
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				int queid = rs.getInt("QUEID");
				String qmain = rs.getString("QMAIN");
				String answer = rs.getString("ANSWER");
				questions.add(new Question(queid, qmain, answer));
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("select question error");
			e.printStackTrace();
		}
	}

	void reproduce_student_answer(String hwname, String userid, Vector<Question> questions,
			Vector<String> student_answers) {
		String sql = "select * from ANSWER where HWNAME=" + "\'" + hwname + "\' and USERID=\'" + userid + "\'";
		try {
			ResultSet rs = state.executeQuery(sql);
			if (rs.next()) {
				String student_answer = rs.getString("SANSWER");
				String true_answers = rs.getString("QANSWER");
				String qmain = rs.getString("QMAIN");
				QuestionWithStudentAnswer qwsa = new QuestionWithStudentAnswer(qmain, student_answer, true_answers);
				qwsa.fill_questions_answer_vector(questions, student_answers);

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("reproduce error ");
			e.printStackTrace();
		}
	}

	void submit_answer(String hwname, String userid, Vector<Question> questions, Vector<String> student_answers) {
		if (is_temp_save(hwname, userid)) {
			String total_answers = "";
			for (String answer : student_answers) {
				total_answers += answer;
				total_answers += "---";
			}
			String sql = "update ANSWER set SANSWER = " + "\'" + total_answers + "\', IS_SUBMIT=1 where HWNAME = \'"
					+ hwname + "\' and USERID=\'" + userid + "\'";
			try {
				state.execute(sql);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("SUBMIT answer update error");
				e.printStackTrace();
			}
		} else {
			String total_answers = "";
			String total_qmain = "";
			String total_true_answers = "";

			for (String answer : student_answers) {
				total_answers += answer;
				total_answers += "---";
			}
			for (Question q : questions) {
				total_qmain += q.get_qmain();
				total_qmain += "---";
				total_true_answers += q.get_true_answer();
				total_true_answers += "---";
			}
			//System.out.println(total_qmain);
			
			//String sql = "insert into ANSWER (QMAIN, SANSWER, HWNAME, QANSWER, USERID, IS_SUBMIT) values ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing' ,'Beijing',1)";
			
			String t="A";
			String t1="解： 先在A中找到与B中首元素相等的节点 *pa， 然后比较两者对应的元素是否相等， 若相等， 则同步后移， 如果B比较完毕， 说明B是A的子序列； 否则， pa后移一个节点， 再采用相同的过程进行判断。 对应算法如下。\\r\\r\\nint Subseq(LinkList *A, LinkList *B)\\r\\r\\n{\\r\\r\\n    LinkList *pa = A->next, *pb, *q;\\r\\r\\n    while (pa != NULL) {\\r\\r\\n        pb = B->next;\\r\\r\\n        while (pa != NULL && pa->data != pb->data) //找首元素相等的位置\\r\\r\\n            pa = pa->next;\\r\\r\\n        q = pa;\\r\\r\\n        while (q != NULL && pb != NULL && q->data == pb->data) {\\r\\r\\n            q = q->next; //若对应的节点值相同， 则同步后移\\r\\r\\n            pb = pb->next;\\r\\r\\n        }\\r\\r\\n        if (pb == NULL) //B序列比较完毕\\r\\r\\n            return 1;\\r\\r\\n        "
					+ "else if (pa != NULL) //若A序列未遍历完， 则从下一个节点开始重复进行pa=pa->next;\\r\\r\\n        }\\r\\r\\n    return 0;\\r\\r\\n}";
			String t2="解： 先在A中找到与B中首元素相等的节点 *pa， 然后比较两者对应的元素是否相等， 若相等， 则同步后移， 如果B比较完毕， 说明B是A的子序列； 否则， pa后移一个节点， 再采用相同的过程进行判断。 对应算法如下。\\\\r\\\\r\\\\nint Subseq(LinkList *A, LinkList *B)\\\\r\\\\r\\\\n{\\\\r\\\\r\\\\n    LinkList *pa = A->next, *pb, *q;\\\\r\\\\r\\\\n    while (pa != NULL) {\\\\r\\\\r\\\\n        pb = B->next;\\\\r\\\\r\\\\n        while (pa != NULL && pa->data != pb->data) //找首元素相等的位置\\\\r\\\\r\\\\n            pa = pa->next;\\\\r\\\\r\\\\n        q = pa;\\\\r\\\\r\\\\n        while (q != NULL && pb != NULL && q->data == pb->data) {\\\\r\\\\r\\\\n            q = q->next; //若对应的节点值相同， 则同步后移\\\\r\\\\r\\\\n            pb = pb->next;\\\\r\\\\r\\\\n        }\\\\r\\\\r\\\\n        if (pb == NULL) //B序列比较完毕\\\\r\\\\r\\\\n            return 1;\\\\r\\\\r\\\\n        \"\r\n" + 
					"					+ \"else if (pa != NULL) //若A序列未遍历完， 则从下一个节点开始重复进行pa=pa->next;\\\\r\\\\r\\\\n        }\\\\r\\\\r\\\\n    return 0;\\\\r\\\\r\\\\n}"
					+ "解： 对二叉排序树来说， 其中序遍历序列为一个递增有序序列，884因此， 对给定的二叉树进行中序遍历， 如果始终能保持前一个值比后一个值小， 则说明该二叉树是一棵二叉排序树， 算法如下。\\r\\r\\nKeyType predt = -32767; //predt为全局变量,保存当前节点中序前驱的值,初值为-∞\\r\\r\\nint judgeBST(BSTNode *bt)\\r\\r\\n{\\r\\r\\n    int b1, b2;\\r\\r\\n    if (bt == NULL) //空树是一棵二叉排序树\\r\\r\\n        return 1;\\r\\r\\n    else {\\r\\r\\n        b1 = judgeBST(bt->lchild); //判断左子树\\r\\r\\n        if (b1 == 0 || predt >= bt->key)\\r\\r\\n            "
					+ "return 0;\\r\\r\\n        predt = bt->key; //保存当前节点的关键字\\r\\r\\n        b2 = judgeBST(bt->rchild); //判断右子树\\r\\r\\n        return b2;\\r\\r\\n    }\\r\\r\\n}";
			//System.out.println(total_true_answers);
			
			String sql = "insert into ANSWER (QMAIN, SANSWER, HWNAME, QANSWER, USERID, IS_SUBMIT)"  + " values (\'"+ total_qmain+"\', \'"+total_answers+"\', \'"  +hwname+ 
					"\',\'"+total_true_answers+"\', \'"+userid+"\' ,1)";
			
			/*
			 * String sql =
			 * "insert into ANSWER (QMAIN, SANSWER, HWNAME, QANSWER, USERID, IS_SUBMIT) " +
			 * "values (\'"             +  total_qmain +            "\', \'" + total_answers + "\', \'" + hwname +
			 * "\', \'" + total_true_answers + "\', \'" + userid + "\', 1)";
			 */

//			System.out.println("in temp save sql : " + sql);
			try {
				state.execute(sql);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("SUBMIT insert answer error");
				e.printStackTrace();
			}
		}
	}
	
	void select_teacher_student_hw(HomeworkInfo hw, Vector<StudentHwInfo> shw, Vector<Range> ranges) {
		String sql = "select * from ANSWER where HWNAME=\'" + hw.get_hwname() + "\' and IS_SUBMIT=1";
		try {
			ResultSet rs = state.executeQuery(sql);
			int begin = shw.size();
			boolean have = false;
			while(rs.next()) {
				have = true;
				String studentID = rs.getString("USERID");
				shw.add(new StudentHwInfo(hw.get_hwname(), hw.get_teacher(), hw.get_qnumber(), studentID));
			}
			
			int end = shw.size();
			ranges.add(new Range(begin, end));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("select teacher student hw error");
			e.printStackTrace();
		}
	}
	
	void check_is_correct(StudentHwInfo hw) {
		String sql = "select * from SCORE where HWNAME=\'" + hw.get_hwname() + "\' and STUDENT_ID=\'" + hw.get_studentID() + "\'";
		try {
			ResultSet rs = state.executeQuery(sql);
			if (rs.next()) {
				hw.set_correct(true);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("check correct error");
			e.printStackTrace();
		}
	}
	

}
