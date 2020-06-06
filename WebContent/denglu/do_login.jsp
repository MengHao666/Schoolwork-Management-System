<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Server Page Depend !</title>
<embed src="/SchoolworkManagementSystem/material/la_vie_en_rose.mp3" hidden="true" autostart="true" loop=2 volume =10>
</head>
<body >
	<h3>Which Page will be depend by the user's message!</h3>

	<%
		String id = new String((request.getParameter("name")).getBytes("ISO-8859-1"), "UTF-8");
	String password = request.getParameter("password");//获取password的参数值
	LogicalCheck lc0 = new LogicalCheck();
	UserWithRetCode re = lc0.check_user(id, password);
	User user_ = re.get_user();
	session.setAttribute("theUser", user_);//将name的内容赋值给UserName
	session.setAttribute("UserName", user_.get_user_name());//将name的内容赋值给UserName
	response.sendRedirect(user_.pagetoEnter());
	%>

</body>
</html>