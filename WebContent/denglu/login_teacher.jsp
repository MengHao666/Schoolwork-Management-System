<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教师-我的系统</title>
<embed src="/SchoolworkManagementSystem/musique/la_vie_en_rose.mp3" hidden="true" autostart="true" loop=2 volume =10>
</head>
<body >





	<hr>
	<font color="green">Welcome <%=session.getAttribute("UserName")%>!
	</font>
	<font color="blue">(教师用户) </font>


	<h2>1.发布作业</h2>
	<form action="/SchoolworkManagementSystem/teacher/TeacherHomeworkRelease.jsp"
		method="post">

		&emsp;请输入作业名称:<input type="text" name="homework_name"><br>
		&emsp;请输入作业的题目数量: <input type="text" name="homework_number"><br>
		<br> &emsp;<input type="submit" value="提交">
		&emsp;<input type="reset"><br>

	</form>



	<h2>2.批改作业</h2>

	<%
		LogicalCheck logical;
	User theTeacher = (User) session.getAttribute("theUser");

	Vector<HomeworkInfo> homeworks;
	Vector<Range> ranges;
	Vector<StudentHwInfo> shws;
	int total_homeworks_counts;

	logical = new LogicalCheck();
	homeworks = new Vector<HomeworkInfo>();
	ranges = new Vector<Range>();
	shws = new Vector<StudentHwInfo>();
	total_homeworks_counts = logical.get_homework_counts(theTeacher, homeworks);
	logical.get_all_student_hw(theTeacher, homeworks, shws, ranges);
	logical.check_is_correct(shws);
	System.out.println(total_homeworks_counts + " " + homeworks.size() + " " + ranges.size());
	
	int unsubmited=0;
	int submited=0;
	int []submit=new int[total_homeworks_counts];
	//for (Range range : ranges) {
	//System.out.println(range.begin + " " + range.end);
	//}
	
	session.setAttribute("shws", shws);//将name的内容赋值给UserName

	for (int i = 0; i < total_homeworks_counts; i++) {
	%>

	<p>
		&emsp;
		<%=homeworks.get(i).get_hwname()%></p>

	<%
		boolean have = false;
	
	submit[i]=ranges.get(i).end()-ranges.get(i).begin();
	
	for (int b = ranges.get(i).begin(); b < ranges.get(i).end(); b++) {
		have = true;
	%>

	<%
		if (!shws.get(b).is_correct()) {
			
	%>
	<form action="/SchoolworkManagementSystem/teacher/correctHomework.jsp" method="POST">
		&emsp;&emsp;&emsp;
		<%=shws.get(b).get_studentID()%>
		&emsp;&emsp;&emsp;<input type="submit" value="批改" />
		<input type="hidden" name="bth" value="<%=b%>">
		


	</form>
	<%
		} else {
	%>
	<form action="/SchoolworkManagementSystem/teacher/viewCorrect.jsp" method="POST">
		&emsp;&emsp;&emsp;
		<%=shws.get(b).get_studentID()%>
		&emsp;&emsp;&emsp;<input type="submit" value="查看" />
		<input type="hidden" name="bth_" value="<%=b%>">


	</form>

	<%
		//out.print("查看");
	}

	}
	if (!have) {
		%>
		&emsp;&emsp;&emsp;
		<% 

		out.print("本次作业暂无人提交");

	}

	}
	%>

	<h2>3.作业提交情况(提交人数/总人数)</h2>
	
	<%
	Vector<User> re = logical.get_dbUsers(2);	
	int total=re.size();
	
	for (int i = 0; i < total_homeworks_counts; i++) {
		%>

		<p>
			&emsp;
			<%=homeworks.get(i).get_hwname()+">>>"+submit[i]+"/"+total%> </p>
			
			
	<%} %>

	<font color="red"><a href="login.jsp">Click me</a> to log out!</font>




</body>
</html>