<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Topic course</title>
</head>
<body onload="topicLoad()">
<h2>Add topic</h2>
<p>To go back click <a href="index.jsp">here!</a></p>
<input type="text" id="topicField"/>
<button onclick="addTopic()" id="topicButton">Register Topic</button>
<script src="../javascript/principal.js"></script>
<div id="messageDiv"></div>
<div id="topicTable"></div>
</body>
</html>
