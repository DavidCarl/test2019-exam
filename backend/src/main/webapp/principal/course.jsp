<%--
  Created by IntelliJ IDEA.
  User: david
  Date: 5/17/19
  Time: 7:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course setup</title>
</head>
<body onload="test()">
<h2>Add course</h2>
<p>To go back click <a href="index.jsp">here!</a></p>
Course Name: <input type="text" id="courseField"/>
<br/>
Topic: <select id="topicField">
    <option disabled selected value> -- select an option -- </option>
</select>
<br/>
Teacher: <select id="teacherField">
    <option disabled selected value> -- select an option -- </option>
</select>
<br/>
Room nr: <input type="text" id="roomField"/>
<br/>
Price: <input type="number" id="priceField"/>
<br/>
<button onclick="registerCourse()" id="courseButton">Register Course</button>
<div id="messageDiv"></div>
<p>If there is nothing to select in Teacher and Topic that means there is no teachers and no topics. Please create some!</p>
<div id="courseTable"></div>

<script src="../javascript/course.js"></script>
</body>
</html>
