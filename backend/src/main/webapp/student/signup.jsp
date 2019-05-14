<%--
  Created by IntelliJ IDEA.
  User: david
  Date: 5/12/19
  Time: 6:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student signup</title>
</head>
<body>
Email: <input type="email" id="emailField"/>
<br/>
First Name: <input type="text" id="firstNameField"/>
<br/>
Last Name: <input type="text" id="lastNameField"/>
<br/>
Birthdate: <input type="date" id="birthdayField">
<br/>
<button onclick="studentsignup()" id="signupbutton">Click me :)</button>
<div id="messagediv"></div>
<script src="../javascript/student.js"></script>
</body>
</html>
