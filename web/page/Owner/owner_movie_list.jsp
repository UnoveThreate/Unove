<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="model.owner.Movie" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Phim</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h2>Danh Sách Phim - Rạp : ${cinemaName}</h2>

    <!-- Nút Thêm Phim -->
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/owner/createMovie?cinemaID=${cinemaID}" class="btn btn-primary">Thêm Phim</a>
    </div>

    <!-- Bảng Danh Sách Phim -->
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Tiêu Đề</th>
                <th>Tóm Tắt</th>
                <th>Ngày Phát Hành</th>
                <th>Hình Ảnh</th>
                <th>Đánh Giá</th>
                <th>Quốc Gia</th>
                <th>Link Trailer</th>
                <th>Thể loại</th>
            </tr>
        </thead>
      <tbody>
    <c:forEach var="movie" items="${movies}">
        <tr>
            <td>${movie.title}</td>
            <td>${fn:escapeXml(movie.synopsis)}</td>
            <td>${movie.datePublished}</td>
            <td><img src="${movie.imageURL}" alt="Movie Image" style="width: 100px;"/></td>
            <td>${movie.rating}</td>
            <td>${movie.country}</td>
            <td><a href="${movie.linkTrailer}" target="_blank">Xem Trailer</a></td>
            <td>
                <!-- Hiển thị danh sách thể loại -->
                <c:forEach var="genre" items="${movie.genres}">
                    <span>${genre.genreName}</span><br/>
                </c:forEach>
            </td>
            <td>
                <!-- Nút Chỉnh Sửa -->
                <a href="${pageContext.request.contextPath}/owner/updateMovie?movieID=${movie.movieID}&cinemaID=${cinemaID}" class="btn btn-warning btn-sm">Chỉnh Sửa</a>
                <!-- Nút Xóa -->
                <form action="${pageContext.request.contextPath}/owner/deleteMovie" method="post" style="display:inline-block;">
                    <input type="hidden" name="movieID" value="${movie.movieID}">
                    <input type="hidden" name="cinemaID" value="${cinemaID}">
                    <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc chắn muốn xóa phim này không?');">Xóa</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</tbody>

    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
