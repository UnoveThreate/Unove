<%-- 
    Document   : genre-form
    Created on : 5 thg 10, 2024, 19:05:43
    Author     : nguyendacphong
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.owner.Genre" %>
<%@ page import="DAO.cinemaChainOwnerDAO.GenreDAO" %>
<jsp:include page="/page/owner/navbar.jsp" />
<!DOCTYPE html>
<html>
    <head>
        <title><c:choose>
                <c:when test="${genre != null}">Edit Genre</c:when>
                <c:otherwise>Create Genre</c:otherwise>
            </c:choose></title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-5">
            <h1><c:choose>
                    <c:when test="${genre != null}">Edit Genre</c:when>
                    <c:otherwise>Create Genre</c:otherwise>
                </c:choose></h1>

            <!-- Hiển thị thông báo lỗi nếu có -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form action="genre" method="post">
                <input type="hidden" name="genreID" value="${genre != null ? genre.genreID : ''}">

                <div class="form-group">
                    <label for="genreName">Genre Name:</label>
                    <input type="text" class="form-control" id="genreName" name="genreName"
                           value="${genre != null ? genre.genreName : ''}" required>
                </div>

                <button type="submit" class="btn btn-success">
                    <c:choose>
                        <c:when test="${genre != null}">Update</c:when>
                        <c:otherwise>Create</c:otherwise>
                    </c:choose>
                </button>
                <a href="genre?action=list" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </body>
</html>
