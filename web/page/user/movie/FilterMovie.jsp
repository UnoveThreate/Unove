<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Filter</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 20px;
        }
        h1 {
            margin-bottom: 20px;
        }
        .form-control, .btn {
            min-width: 200px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center">Lọc Phim</h1>
        <div class="row mb-4">
            <!-- Lọc theo thể loại -->
            <div class="col-md-6 mb-3">
                <form action="${pageContext.request.contextPath}/searchMoviesByGenre" method="get" class="d-flex">
                    <select name="genre" class="form-control me-2">
                        <option value="">Chọn thể loại</option>
                        <c:forEach var="genre" items="${genres}">
                            <option value="${genre.genreName}">${genre.genreName}</option>
                        </c:forEach>
                    </select>
                    <button class="btn btn-primary" type="submit">Lọc phim</button>
                </form>
            </div>
            <!-- Lọc theo quốc gia -->
            <div class="col-md-6 mb-3">
                <form action="${pageContext.request.contextPath}/moviesByCountry" method="get" class="d-flex">
                    <select name="country" class="form-control me-2">
                        <option value="">Chọn quốc gia</option>
                        <c:forEach var="country" items="${countries}">
                            <option value="${country}">${country}</option>
                        </c:forEach>
                    </select>
                    <button class="btn btn-primary" type="submit">Lọc Phim</button>
                </form>
            </div>
        </div>
        <div class="row">
            <!-- Tìm kiếm phim -->
            <div class="col-md-12 text-center">
                <form action="${pageContext.request.contextPath}/searchMovies" method="get" class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="Tìm kiếm phim..." name="searchQuery" aria-label="Tìm kiếm phim">
                    <button class="btn btn-success" type="submit">Tìm kiếm</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
