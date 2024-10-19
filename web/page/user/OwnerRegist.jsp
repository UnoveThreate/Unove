<%-- 
    Document   : OwnerRegist
    Created on : Oct 19, 2024, 10:05:55 AM
    Author     : Kaan
--%>

<%@page import="util.RouterURL"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register as Owner</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1>Register as Owner</h1>
    <form action="<%= RouterURL.REGISTER_AS_OWNER %>" method="post">
        <div class="form-group">
            <label for="reason">Reason for Registration:</label>
            <input type="text" class="form-control" id="reason" name="reason" required>
        </div>
        <input type="hidden" name="userID" value="${userID}"/> <!-- Pass userID -->
        <button type="submit" class="btn btn-primary">Submit Request</button>
    </form>
</div>
</body>
</html>
