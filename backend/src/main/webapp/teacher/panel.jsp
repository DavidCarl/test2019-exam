<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Control Panel</title>
</head>
<body>
Enter your email: <input type="email" id="emailField"/>
<button onclick="getInformation()" id="infobutton">Get info</button>
<div id="messagediv"></div>
<br/>
Name: <div id="nameDiv"></div>
<br/>
<div id="eligibleDiv"></div>
<br/>
<div id="courseTable"></div>
<script src="../javascript/teacher.js"></script>
</body>
</html>