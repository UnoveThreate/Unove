<%@page import="util.RouterURL"%>
<%@page import="model.CinemaChain"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Manage Cinema Chain</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                margin: 0;
                padding: 20px;
            }

            h2 {
                color: #333;
                font-size: 24px;
                margin-bottom: 20px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }

            table, th, td {
                border: 1px solid #ddd;
            }

            th, td {
                padding: 12px;
                text-align: left;
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
                background-color: #4CAF50;
                color: white;
                padding: 10px 15px;
                border-radius: 5px;
            }

            a:hover {
                background-color: #45a049;
            }

            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
                background-color: white;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
            }

            p {
                color: #666;
                font-size: 16px;
            }

            .no-cinema {
                color: #ff0000;
                font-style: italic;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Manage Cinema Chain</h2>
            <p>Name: ${cinemaChain.name}</p>
            <p>Information: ${cinemaChain.information}</p>
            <p>Avatar: <img src="${cinemaChain.avatarURL}" alt="Avatar" style="width: 100px; height: auto; border-radius: 50%;"></p>

            <h3>Danh sách Rạp Phim</h3>
            <table border="1">
                <thead>
                    <tr>
                        <th>Cinema ID</th>
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
                            <td>${cinema.address}</td>
                            <td>${cinema.province}</td>
                            <td>${cinema.district}</td>
                            <td>${cinema.commune}</td>
                            <td>
                                <a href="<%= RouterURL.OWNER_EDIT_CINEMA %>?cinemaID=${cinema.cinemaID}" class="edit-btn">Edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <a href="<%= RouterURL.OWNER_CREATE_CINEMA %>">Add New Cinema</a>
        </div>
    </body>
</html>
