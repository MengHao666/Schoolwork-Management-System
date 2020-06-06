<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="backstage.*"%>
<%@ page import="datapack.*"%>
<!DOCTYPE html>
<html>
<head>


<%--<script type="text/javascript" src="js/jquery/jquery-1.11.1.min.js"></script>--%>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<meta charset="ISO-8859-1">
<title>学生-写作业</title>
</head>
<body >


	<%
		String name;
	String hwname;
	User user;
	LogicalCheck logical;
	Vector<Question> questions;
	int question_numbers;
	int current_question_id;
	Vector<String> student_answers;
	boolean all_answerd;

	User theStudent = (User) session.getAttribute("theUser");
	String homework_name = new String((request.getParameter("homework_name")).getBytes("ISO-8859-1"), "UTF-8");
	int question_nums = Integer.parseInt(request.getParameter("question_nums")); 
	

	question_numbers = question_nums;
	logical = new LogicalCheck();
	questions = new Vector<Question>(question_nums);
	student_answers = new Vector<String>(question_nums);
	logical.get_question(question_nums, questions, student_answers, homework_name, theStudent.get_userid());
	question_numbers = questions.size();
	all_answerd = true;
	
	//out.print(questions);
	session.setAttribute("questions", questions);//将name的内容赋值给UserName
	session.setAttribute("student_answers", student_answers);//将name的内容赋值给UserName
	%>
    
    <p><font size="10" face="arial" >&emsp;<%=theStudent.get_user_name() %> &emsp; <%=homework_name%></font></p>
    <br>
	<p><font size="5" face="arial" color="red">&emsp; &emsp; &emsp; &emsp;题目 &emsp;&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; 作答</font></p>
	
	


	



	<form action="submit_or_update_hw.jsp" method="POST">
	    <% for (int i = 0; i < question_numbers; i++) { %>
		<textarea cols="40" rows="10" readonly     > <%="第"+(i+1)+"题:  "+questions.elementAt(i).get_qmain().replace(" ", "") %> </textarea>
		&emsp; &emsp; &emsp; &emsp;
		<textarea cols="40" rows="10" name=<%= i+"th_hw"%>> <%="第"+(i+1)+"题:"  %> </textarea>
		<br><br><br>
		<% }%>
		<br><br><br>
		<input type="hidden" name="homework_name_" value="<%=homework_name%>">
		<input type="submit" value="提交" style="width:100px;height:60px;background:yellow;color:black ">    <input type="reset" style="width:100px;height:60px;background:yellow;color:black "><br>
	</form>






</body>



</html>