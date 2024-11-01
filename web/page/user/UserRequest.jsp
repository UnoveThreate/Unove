<%-- 
    Document   : userRequest
    Created on : Oct 17, 2024, 9:00:53 PM
    Author     : Kaan
--%>

<%@page import="util.RouterURL"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Request Owner Status</title>
</head>
<body>
    <h1>Request to Become an Owner</h1>
    <form action="<%= RouterURL.OWNER_REQUEST %>" method="post">
        <input type="hidden" name="userID" value="${sessionScope.userID}"/> <!-- Assuming userID is stored in session -->
        <label for="reason">Reason:</label><br>
        <textarea name="reason" id="reason" required></textarea><br><br>
        <input type="submit" value="Submit Request"/>
    </form>
</body>
</html>
