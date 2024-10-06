<%-- 
    Document   : owner_manage_cinema
    Created on : 6 thg 10, 2024, 03:23:03
    Author     : nguyendacphong
--%>
<%@page import="util.RouterURL"%>
<%@page import="model.Cinema"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="navbar.jsp" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Cinema</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            margin-bottom: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Manage Cinema - ${cinema.name}</h2>
        <table class="table">
            <tr>
                <th>Cinema ID</th>
                <td>${cinema.cinemaID}</td>
            </tr>
            <tr>
                <th>Name</th>
                <td>${cinema.name}</td>
            </tr>
            <tr>
                <th>Address</th>
                <td>${cinema.address}</td>
            </tr>
            <tr>
                <th>Province</th>
                <td>${cinema.province}</td>
            </tr>
            <tr>
                <th>District</th>
                <td>${cinema.district}</td>
            </tr>
            <tr>
                <th>Commune</th>
                <td>${cinema.commune}</td>
            </tr>
        </table>
        
        <div class="buttons">
            <a href="<%= RouterURL.OWNER_EDIT_CINEMA %>?cinemaID=${cinema.cinemaID}" class="btn btn-primary">Edit</a>
            <a href="<%= RouterURL.MANAGE_MOVIES %>?cinemaID=${cinema.cinemaID}" class="btn btn-secondary">Manage Movies</a>
       
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
</body>
</html>
