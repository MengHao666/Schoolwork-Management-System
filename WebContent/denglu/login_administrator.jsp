<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员-我的系统</title>
<embed src="/SchoolworkManagementSystem/musique/la_vie_en_rose.mp3" hidden="true" autostart="true" loop=2 volume =10>
</head>
<body >





	<hr>
	<font color="green">Welcome <%=session.getAttribute("UserName")%>!
	</font>
	<font color="blue">(管理员用户) </font>
	<br>
	
	
	
	<h2>1.显示功能</h2>

	<form action="/SchoolworkManagementSystem/administrator/display.jsp" method="POST">
		<input type="submit" value="显示所有用户信息" />
		<%
			int number = 100;
		%>
		<input type="hidden" name="userGrade" value="<%=number%>">
	</form>

	<form action="/SchoolworkManagementSystem/administrator/display.jsp" method="POST">
		<input type="submit" value="显示管理员信息" />
		<%
			number = 0;
		%>
		<input type="hidden" name="userGrade" value="<%=number%>">
	</form>

	<form action="/SchoolworkManagementSystem/administrator/display.jsp" method="POST">
		<input type="submit" value="显示教师信息" />
		<%
			number = 1;
		%>
		<input type="hidden" name="userGrade" value="<%=number%>">
	</form>

	<form action="/SchoolworkManagementSystem/administrator/display.jsp" method="POST">
		<input type="submit" value="显示学生信息" />
		<%
			number = 2;
		%>
		<input type="hidden" name="userGrade" value="<%=number%>">
	</form>



	<h2>2.增加功能</h2>
	<p>增加管理员、教师或学生信息</p>
	<form action="/SchoolworkManagementSystem/administrator/add.jsp" method="post">

		新用户ID：<input name="new_user_id">
		&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp新用户密码：<input name="new_user_password">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
		新用户姓名：<input name="new_user_name">
		&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp新用户权限：<input name="new_user_grade">
		<hr>
		<input type="submit" value="提交"> <input type="reset">

	</form>



	<h2>3.删除功能</h2>
	<p>删除特定USERID的用户信息</p>
	<form action="/SchoolworkManagementSystem/administrator/delete.jsp" method="POST">
		所删用户ID：<input name="del_id">
		<hr>
		<input type="submit" value="提交"> <input type="reset">
	</form>



	<h2>4.更新功能</h2>
	<p>更新特定USERID的用户信息</p>
	<form action="/SchoolworkManagementSystem/administrator/update.jsp" method="post">

		所修改用户ID：<input name="user_id">
		&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp更新用户密码：<input name="new_user_password">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
		更新用户姓名：<input name="new_user_name">
		&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp更新用户权限：<input name="new_user_grade">
		<hr>
		<input type="submit" value="提交"> <input type="reset">

	</form>




	<font color="red"><a href="login.jsp">Click me</a> to log out!</font>




</body>
</html>