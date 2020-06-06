<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生-我的系统</title>
<embed src="/SchoolworkManagementSystem/musique/la_vie_en_rose.mp3" hidden="true" autostart="true" loop=2 volume =10>
</head>
<body >





	<hr>
	<font color="green">Welcome <%=session.getAttribute("UserName")%>!
	</font>
	<font color="blue">(学生用户) </font>
	<br>
	<%
		User theStudent = (User) session.getAttribute("theUser");

	Vector<HomeworkInfo> homeworks;
	Vector<HomeworkInfo> complete_homeworks;
	Vector<HomeworkInfo> un_complete_homeworks;
	LogicalCheck logical;

	logical = new LogicalCheck();
	homeworks = new Vector<HomeworkInfo>();
	complete_homeworks = new Vector<HomeworkInfo>();
	un_complete_homeworks = new Vector<HomeworkInfo>();
	logical.select_homeworks(homeworks, theStudent.get_userid());

	for (HomeworkInfo homework : homeworks) {
		if (homework.is_completed()) {
			complete_homeworks.add(homework);
		} else {
			un_complete_homeworks.add(homework);
		}
	}%>
	
	<h2>1.已提交作业</h2>
	<% 
    //已完成作业
	for (HomeworkInfo homework : complete_homeworks) {

		out.print(homework.get_hwname());
		%>
		
		<form action="/SchoolworkManagementSystem/student/viewScore.jsp" method="POST">
		<input type="submit" value="查看" />
		<%
			int question_nums = homework.get_qnumber();
		    String homework_name=homework.get_hwname();
		%>
		<input type="hidden" name="homework_name" value="<%=homework.get_hwname()%>">
		<input type="hidden" name="question_nums" value="<%=homework.get_qnumber()%>">
		
	    </form>
		
		<% 
		
		
		
		out.print("<br>");

	}
    %>
    <br>
    <h2>2.未提交作业</h2>
    <% 
	for (HomeworkInfo homework : un_complete_homeworks) {

		out.print(homework.get_hwname());
		%>
		<form action="/SchoolworkManagementSystem/student/writeHomework.jsp" method="POST">
		<input type="submit" value="做作业" />
		<%
			int question_nums = homework.get_qnumber();
		    String homework_name=homework.get_hwname();
		%>
		<input type="hidden" name="homework_name" value="<%=homework.get_hwname()%>">
		<input type="hidden" name="question_nums" value="<%=homework.get_qnumber()%>">
		
	    </form>
		
		<%
		out.print("<br>");
	}
    
    
	%>




    <br><br><br>
	<font color="red"><a href="login.jsp">Click me</a> to log out!</font>




</body>
</html>