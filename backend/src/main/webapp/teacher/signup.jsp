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
    <title>Teacher signup</title>
</head>
<body>
Email: <input type="email" id="emailField"/>
<br/>
Name: <input type="text" id="nameField"/>
<br/>
Education Level:
<select id="eduField">
    <option disabled selected value> -- select an option -- </option>
    <option value="Edu_1">Edu 1</option>
    <option value="Edu_2">Edu 2</option>
    <option value="Edu_3">Edu 3</option>
    <option value="Edu_4">Edu 4</option>
</select>
<br/>
<button onclick="teachersignup()" id="signupbutton">Click me :)</button>
<script src="../javascript/api.js"></script>
<script src="../javascript/teacher.js"></script>
</body>
</html>
