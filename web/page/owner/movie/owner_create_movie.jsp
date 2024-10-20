<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/page/owner/navbar.jsp" />
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tạo Phim Mới</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                margin: 20px;
                font-family: Arial, sans-serif;
            }
            h1 {
                margin-bottom: 20px;
            }
            .form-group {
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Tạo Phim Mới</h1>
            <form action="${pageContext.request.contextPath}/owner/createMovie" method="post" enctype="multipart/form-data"> 
                <input type="hidden" name="cinemaID" value="${cinemaID}"> <!-- Truyền cinemaID -->

                <div class="form-group">
                    <label for="title">Tiêu đề:</label>
                    <input type="text" class="form-control" id="title" name="title" required>
                </div>

                <div class="form-group">
                    <label for="synopsis">Nội dung:</label>
                    <textarea class="form-control" id="synopsis" name="synopsis" required></textarea>
                </div>

                <div class="form-group">
                    <label for="datePublished">Ngày phát hành:</label>
                    <input type="date" class="form-control" id="datePublished" name="datePublished" required>
                </div>

                <!-- Thêm input để upload ảnh -->
                <div class="form-group">
                    <label for="image">Chọn ảnh:</label>
                    <input type="file" class="form-control" id="image" name="imageURL" accept="image/*" required>
                </div>
                <div class="form-group">
                    <label for="country">Quốc gia:</label>
                    <input type="text" class="form-control" id="country" name="country" required>
                </div>

                <div class="form-group">
                    <label for="linkTrailer">Liên kết Trailer:</label>
                    <input type="text" class="form-control" id="linkTrailer" name="linkTrailer">
                </div>

                <div class="form-group">
                    <label for="genreIDs">Thể loại:</label>
                    <select class="form-control" id="genreIDs" name="genreIDs" multiple required>
                        <c:forEach var="genre" items="${genres}"> <!-- Duyệt qua danh sách thể loại -->
                            <option value="${genre.genreID}">${genre.genreName}</option>
                        </c:forEach>
                    </select>
                    <small class="form-text text-muted">Giữ phím Ctrl (Windows) hoặc Command (Mac) để chọn nhiều thể loại.</small>
                </div>

                <div class="form-group">
                    <label for="type">Loại phim:</label>
                    <select class="form-control" id="type" name="type" required>
                        <option value="Đang chiếu">Phim đang chiếu</option>
                        <option value="Sắp chiếu">Phim sắp công chiếu</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Tạo Phim</button>
                <a href="${pageContext.request.contextPath}/owner/movie?cinemaID=${cinemaID}" class="btn btn-secondary">Quay lại</a>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script> <!-- Đường dẫn tới bootstrap bundle -->
    </body>
</html>
