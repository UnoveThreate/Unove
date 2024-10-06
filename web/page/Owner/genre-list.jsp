<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.owner.Genre" %>
<%@ page import="DAO.cinemaChainOwnerDAO.GenreDAO" %>
<jsp:include page="navbar.jsp" />
<!DOCTYPE html>
<html>
    <head>
        <title>Genre List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-5">
            <h1>Genres</h1>
            <a href="genre?action=create" class="btn btn-primary mb-3">Add New Genre</a>

            <!-- Hiển thị thông báo lỗi nếu có -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Genre Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="genre" items="${genres}">
                        <tr>
                            <td>${genre.genreID}</td>
                            <td>${genre.genreName}</td>
                            <td>
                                <a href="genre?action=edit&id=${genre.genreID}" class="btn btn-warning btn-sm">Edit</a>
                                <a href="genre?action=delete&id=${genre.genreID}" class="btn btn-danger btn-sm"
                                   onclick="return confirm('Are you sure you want to delete this genre?');">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
