<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>管理员-删除功能</title>
</head>
<body >


<%   //获取姓名密码和年龄
	String id_=request.getParameter("del_id");
    System.out.println("hhhhhhhhhhhhhhhh"+id_);
 	LogicalCheck lc1 = new LogicalCheck();
 	int row=lc1.delete_dbUsers(id_);
 	out.print("删除了"+row+"行数据:\n");
%>



</body>
</html>