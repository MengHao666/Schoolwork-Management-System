<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>教师-提交批改分数</title>
</head>
<body >

<p><font size="7.5" face="arial" >&emsp;&emsp;提交学生分数成功！</font></p>
<%
User theTeacher = (User) session.getAttribute("theUser");
StudentHwInfo hw = (StudentHwInfo) session.getAttribute("hw");
int question_numbers=Integer.parseInt(request.getParameter("question_numbers"));
Vector<String> scores;
//out.print(student_answers.size());

scores = new Vector<String>(question_numbers);
for (int i = 0; i < question_numbers; i++) {
String d=new String((request.getParameter( i+"th_score")).getBytes("ISO-8859-1"), "UTF-8");
scores.addElement(d);
}




LogicalCheck logical;
logical = new LogicalCheck();
logical.submit_correct(hw, scores, theTeacher );
%>

</body>
</html>