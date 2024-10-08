<%@page import="model.Cinema"%>
<%@page import="model.CinemaReview"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cinema Details</title>
    <!-- Bootstrap 5 CDN for beautiful UI -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h1, h2 {
            color: #333;
        }
        .btn-back {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Cinema Detail Information</h1>
        <c:if test="${not empty cinema}">
            <div class="card mb-3">
                <div class="card-body">
                    <p><strong>Address:</strong> ${cinema.address}</p>
                    <p><strong>Province:</strong> ${cinema.province}, 
                       <strong>District:</strong> ${cinema.district}, 
                       <strong>Commune:</strong> ${cinema.commune}</p>
                </div>
            </div>
        </c:if>
        <c:if test="${empty cinema}">
            <div class="alert alert-warning">Cinema information not found.</div>
        </c:if>

        <h2>Reviews</h2>
        <c:if test="${not empty reviews}">
            <ul class="list-group">
                <c:forEach var="review" items="${reviews}">
                    <li class="list-group-item">
                        <strong>Rating:</strong> ${review.rating} / 5
                        <p><strong>Content:</strong> ${review.content}</p>
                        <p><em>Time created: <fmt:formatDate value="${review.timeCreated}" pattern="dd/MM/yyyy"/></em></p>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty reviews}">
            <div class="alert alert-info">No reviews yet.</div>
        </c:if>

        <div class="btn-back">
            <a href="cinemaList.jsp" class="btn btn-primary">Return to cinema list</a>
        </div>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
