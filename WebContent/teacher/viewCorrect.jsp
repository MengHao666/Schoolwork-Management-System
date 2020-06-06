<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>教师，查看批改</title>
</head>
<body >



<%

LogicalCheck logical;
User user;
StudentHwInfo hw;
int height;
int width;

Vector<Question> questions;
int question_numbers;
int current_question_id;
Vector<String> student_answers;
Vector<String> scores;

User theTeacher = (User) session.getAttribute("theUser");
//out.print(theTeacher);
int bth_ = Integer.parseInt(request.getParameter("bth_")); 
//out.print(bth);
Vector<StudentHwInfo> shws = (Vector<StudentHwInfo>) session.getAttribute("shws");
StudentHwInfo hw_ = shws.get(bth_);
//out.print(hw_);

logical = new LogicalCheck();
questions = new Vector<Question>(hw_.get_qnumber());
student_answers = new Vector<String>(hw_.get_qnumber());
scores = new Vector<String>(hw_.get_qnumber());
//logical.get_question(hw.get_qnumber(), questions, student_answers, hw.get_hwname(), user.get_userid());
logical.get_scores(questions, student_answers, hw_, scores);
question_numbers = questions.size();





%>

<p><font size="7.5" face="arial" >&emsp;<%=theTeacher.get_user_name() +"老师，您正在查看"%> &emsp;<%=hw_.get_studentID()%>&emsp; 的  &emsp;<%=hw_.get_hwname()%></font></p>

<p><font size="5" face="arial" color="red">
&emsp; &emsp; &emsp;&emsp;原题 &emsp; &emsp;&emsp;&emsp; &emsp; &emsp;&emsp;&emsp; &emsp; &emsp;&emsp;&emsp;   
学生答案&emsp;&emsp; &emsp; &emsp;&emsp; &emsp;&emsp; &emsp;&emsp; &emsp;&emsp;
参考答案 &emsp; &emsp; &emsp;&emsp; &emsp; &emsp;&emsp; &emsp;给分（每题满分为10）</font></p>
<form  >
	    <% for (int i = 0; i < question_numbers; i++) { %>
		<textarea cols="40" rows="10" readonly     > <%="第"+(i+1)+"题:  "+questions.elementAt(i).get_qmain().replace(" ", "") %> </textarea>
		&emsp; &emsp; &emsp; &emsp;
		<textarea cols="40" rows="10"  readonly > <%=student_answers.get(i)  %> </textarea>
		&emsp; &emsp; &emsp; &emsp;
		<textarea cols="50" rows="10"  readonly > <%="第"+(i+1)+"题:" +questions.elementAt(i).get_true_answer() %> </textarea>
		&emsp; &emsp; &emsp; &emsp;
		<textarea cols="5" rows="5" readonly > <%=scores.get(i)%> </textarea>
		
		<br><br><br>
		<% }%>
	</form>




</body>
</html>