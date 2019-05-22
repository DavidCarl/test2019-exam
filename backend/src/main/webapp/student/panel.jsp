<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Panel</title>
</head>
<body>
Email: <input type="email" id="emailField"/>

<button onclick="studentCourses()" id="coursesbutton">Load Courses</button>
<p>Attending courses:</p>
<div id="courses"></div>

<button onclick="studentInfo()" id="studentInfoButton">Load info</button>
<p>Student Info:</p>
<div id="studentInfo"></div>

<script src="../javascript/student.js"></script>
</body>
</html>
