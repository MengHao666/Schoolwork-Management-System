<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>教师-作业发布</title>
</head>
<body >

	<%
		String hwName = new String((request.getParameter("homework_name")).getBytes("ISO-8859-1"), "UTF-8");
	    int nums = Integer.parseInt(request.getParameter("homework_number"));//获取本次作业题目数量
	    LogicalCheck lc1 = new LogicalCheck();
	    User theTeacher = (User) session.getAttribute("theUser");
	    //theTeacher.print_self();
	    lc1.release_homework(theTeacher, nums, hwName);
	    
	    out.print("成功发布一次作业：");
	    out.print("<br><br>");
		out.print("TeacherName：" + theTeacher.get_user_name()+ ">>>>>>>>             HomeworkName : " + hwName + ">>>>>>>>>>>            HomeworkNumber : " + nums);
	%>






</body>
</html>