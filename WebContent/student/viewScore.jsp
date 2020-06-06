<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>学生-查看分数</title>
</head>
<body >





<%
String name;
String hwname;
User user;
LogicalCheck logical;
Vector<Question> questions;
int question_numbers;
int current_question_id;
Vector<String> student_answers;
Vector<String> scores;
String teacher;
boolean all_answerd;



User theStudent = (User) session.getAttribute("theUser");
String homework_name = new String((request.getParameter("homework_name")).getBytes("ISO-8859-1"), "UTF-8");
int question_nums = Integer.parseInt(request.getParameter("question_nums")); 

hwname = homework_name;

logical = new LogicalCheck();
questions = new Vector<Question>(question_nums);
student_answers = new Vector<String>(question_nums);
scores = new Vector<String>(question_nums);

logical.get_question(question_nums, questions, student_answers, homework_name, theStudent.get_userid());
question_numbers = questions.size();
all_answerd = true;
teacher = logical.get_scores(scores, hwname,theStudent.get_userid());

%>


<p><font size="7.5" face="arial" >&emsp;<%=theStudent.get_user_name() +"同学，你正在查看"%> &emsp;<%=hwname%>&emsp; 的  &emsp;得分情况</font></p>

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