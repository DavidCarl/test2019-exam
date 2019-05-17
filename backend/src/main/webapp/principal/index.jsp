<%--
  Created by IntelliJ IDEA.
  User: david
  Date: 5/17/19
  Time: 6:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Principal section</title>
</head>
<body onload="mainPage()">
<h2>Welcome Principal</h2>
<p>To go back, click <a href="../index.jsp" id="mainHref">here!</a></p>
<p>To setup a topic, click <a href="topic.jsp" id="topicHref">here!</a></p>
<p>To setup a course, click <a href="course.jsp" id="courseHref">here!</a></p>
<p>Teachers</p>
<div id="teacherTable"></div>
<p>Students</p>
<div id="studentTable"></div>
<p>Topics</p>
<div id="topicTable"></div>
<p>Courses</p>
<div id="courseTable"></div>
<div id="messageDiv"></div>
<script src="../javascript/principal.js"></script>
</body>
</html>
