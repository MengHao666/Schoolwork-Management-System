<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>管理员-显示功能</title>
</head>
<body >

<%
int number=Integer.valueOf(request.getParameter("userGrade"));
System.out.println(request.getParameter("userGrade"));%>
<table border="2">
		<tr>
			<td width="100" number="title">USERID</td>
			<td width="100" name="title">USERNAME</td>
			<td width="100" age="title">PASSWORD</td>
			<td width="100" age="title">USERGRADE</td>
		</tr>
		<%
		    if(number==100){
		    	out.print("所有用户信息如下：");
		    }
		    else if(number==0){
		    	out.print("所有管理员信息如下：");
		    }
		    else if(number==1){
		    	out.print("所有教师信息如下：");
		    }
		    else if(number==2){
		    	out.print("所有学生信息如下：");
		    }
			
		out.print("<br/>");
		LogicalCheck lc1 = new LogicalCheck();
		Vector<User> re = lc1.get_dbUsers(number);
		for (User user : re) {
		%>
		<tr>
			<td width="100"><%=user.get_userid()%></td>
			<td width="100"><%=user.get_user_name()%></td>
			<td width="100"><%=user.get_password()%></td>
			<td width="100"><%=user.get_grade()%></td>
		</tr>
		<%
			}
		%>
	</table>





</body>
</html>