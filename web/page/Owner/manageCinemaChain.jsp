<%@page import="util.RouterURL"%>
<%@page import="model.CinemaChain"%>
<jsp:include page="navbar.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Manage Cinema Chain</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
        }

        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 30px;
            background-color: white;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        h2 {
            color: #333;
            font-size: 28px;
            margin-bottom: 20px;
        }

        h3 {
            color: #555;
            margin-top: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }

        th, td {
            padding: 15px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        a {
            text-decoration: none;
            padding: 8px 12px;
            border-radius: 5px;
        }

        .edit-btn {
            background-color: #007bff;
            color: white;
            margin-right: 5px;
        }

        .edit-btn:hover {
            background-color: #0056b3;
        }

        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }

        .btn-secondary:hover {
            background-color: #5a6268;
        }

        p {
            color: #666;
            font-size: 16px;
        }

        img {
            width: 100px;
            height: auto;
            border-radius: 50%;
        }

        .no-cinema {
            color: #dc3545;
            font-style: italic;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Manage Cinema Chain</h2>
        <p><strong>Name:</strong> ${cinemaChain.name}</p>
        <p><strong>Information:</strong> ${cinemaChain.information}</p>
        <p><strong>Avatar:</strong> <img src="${cinemaChain.avatarURL}" alt="Avatar"></p>

        <h3>Danh sách Rạp Phim</h3>
        <table>
            <thead>
                <tr>
                    <th>Cinema ID</th>
                    <th>Name</th> 
                    <th>Address</th>
                    <th>Province</th>
                    <th>District</th>
                    <th>Commune</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cinema" items="${cinemas}">
                    <tr>
                        <td>${cinema.cinemaID}</td>
                        <td>${cinema.name}</td> 
                        <td>${cinema.address}</td>
                        <td>${cinema.province}</td>
                        <td>${cinema.district}</td>
                        <td>${cinema.commune}</td>
                        <td>
                            
                            <a href="<%= RouterURL.MANAGE_CINEMA_DETAIL %>?cinemaID=${cinema.cinemaID}" class="btn btn-secondary">Manage Cinema</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="<%= RouterURL.OWNER_CREATE_CINEMA%>?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-success">Add New Cinema</a>
    </div>

    <!-- Bootstrap JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
</body>
</html>
