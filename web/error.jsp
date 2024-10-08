<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lỗi</title>
</head>
<body>
    <h1>Đã xảy ra lỗi</h1>
    <%
        String message = request.getParameter("message");
        if (message != null) {
            message = URLDecoder.decode(message, "UTF-8");
    %>
        <p><%= message %></p>
    <%
        }
    %>
</body>
</html>