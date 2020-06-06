<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>学生-作业提交成功</title>
</head>
<body >

	<%
	User theStudent = (User) session.getAttribute("theUser");
	Vector<Question> questions= (Vector<Question>) session.getAttribute("questions");
	//out.print(questions.elementAt(0).get_qmain());
	
	LogicalCheck lc1 = new LogicalCheck();
	String homework_name_ = new String((request.getParameter("homework_name_")).getBytes("ISO-8859-1"), "UTF-8");
	
	Vector<String> student_answers=new Vector<String>();
	//out.print(student_answers.size());
	
	
	int j=0;
	while(j<questions.size()){
		String d=new String((request.getParameter( j+"th_hw")).getBytes("ISO-8859-1"), "UTF-8");
		//out.print(d);
		j++;
		student_answers.addElement(d);
		
	}
	
	lc1.submit(homework_name_, theStudent.get_userid(), questions, student_answers);
	out.print(theStudent.get_user_name() + "，你好！");
	out.print("<br>");
	out.print("你的 " +homework_name_+ "提交成功！ ");
	%>


</body>
</html>