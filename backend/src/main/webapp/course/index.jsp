<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Courses section</title>
</head>
<body>
    <h3 id="listTitle">List of courses</h3>
    <ul id="listOfCourses"></ul>
    <button id="refreshBtn" onclick="getNewestCourses();">Refresh list</button>



    <button id="tt" onclick="fill();">fill list</button>

    <h3>Course Details</h3>
    <div id="courseDetails"></div>
    <script src="../javascript/course.js"></script>
</body>
</html>
