<%--
  Created by IntelliJ IDEA.
  User: tjalfe
  Date: 5/17/19
  Time: 12:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Panel</title>
</head>
<body>
Email: <input type="email" id="emailField"/> <button onclick="studentCourses()" id="coursesbutton">Register</button>
<button onclick="testCouse()" >test</button> <%-- TODO remove later --%>

<p>Attending courses:</p>
<div id="courses"></div>
<script src="../javascript/student.js"></script>
</body>
</html>
