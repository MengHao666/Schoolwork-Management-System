<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>管理员-增加功能</title>
</head>
<body >

	<%
		//获取姓名密码和年龄
	String id_ = request.getParameter("new_user_id");
	String psd_ = request.getParameter("new_user_password");
	String name_ = new String((request.getParameter("new_user_name")).getBytes("ISO-8859-1"), "UTF-8");
	int grade_ = Integer.parseInt(request.getParameter("new_user_grade"));
	//创建User对象，赋值
	User u = new User(name_, id_, psd_, grade_);

	LogicalCheck lc1 = new LogicalCheck();
	int row = lc1.insert_dbUsers(u);

	out.print("增加了" + row + "行数据:\n");
	out.print("<br>");
	out.print("this is " + name_ + " id : " + id_ + " grade : " + grade_ + " password : " + psd_);
	%>




</body>
</html>