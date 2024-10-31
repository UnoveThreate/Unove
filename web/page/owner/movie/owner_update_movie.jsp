<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cập Nhật Phim</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
                margin: 0;
                font-family: Arial, sans-serif;
            }
            .container {
                margin-top: 30px;
                padding: 20px;
                background-color: #ffffff;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            h1 {
                color: #007bff;
                margin-bottom: 30px;
                text-align: center;
            }
            .form-group {
                margin-bottom: 20px;
            }
            .form-text {
                font-size: 0.9em;
                color: #6c757d;
            }
            .btn {
                width: 100%;
                padding: 12px;
            }
            @media (max-width: 768px) {
                .btn {
                    padding: 10px;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Cập Nhật Phim</h1>
            <form action="${pageContext.request.contextPath}/owner/updateMovie" method="post">
                <input type="hidden" name="movieID" value="${movie.movieID}"> <!-- Truyền movieID -->
                <input type="hidden" name="cinemaID" value="${movie.cinemaID}"> <!-- Truyền cinemaID -->

                <div class="form-group">
                    <label for="title">Tiêu đề:</label>
                    <input type="text" class="form-control" id="title" name="title" value="${movie.title}" required>
                </div>

                <div class="form-group">
                    <label for="synopsis">Nội dung:</label>
                    <textarea class="form-control" id="synopsis" name="synopsis" rows="4" required>${movie.synopsis}</textarea>
                </div>

                <div class="form-group">
                    <label for="datePublished">Ngày phát hành:</label>
                    <input type="date" class="form-control" id="datePublished" name="datePublished" value="${fn:substring(movie.datePublished, 0, 10)}" required>
                </div>

                <div class="form-group">
                    <label for="imageURL">URL hình ảnh:</label>
                    <input type="text" class="form-control" id="imageURL" name="imageURL" value="${movie.imageURL}" required>
                </div>

                <div class="form-group">
                    <label for="country">Quốc gia:</label>
                    <input type="text" class="form-control" id="country" name="country" value="${movie.country}" required>
                </div>

                <div class="form-group">
                    <label for="linkTrailer">Liên kết Trailer:</label>
                    <input type="text" class="form-control" id="linkTrailer" name="linkTrailer" value="${movie.linkTrailer}">
                </div>

                <div class="form-group">
                    <label for="genreIDs">Thể loại:</label>
                    <select class="form-control" id="genreIDs" name="genreIDs" multiple required>
                        <c:forEach var="genre" items="${genres}"> <!-- Duyệt qua danh sách thể loại -->
                            <option value="${genre.genreID}" 
                                    <c:if test="${fn:contains(movie.genres, genre.genreID)}">selected</c:if>
                                        >
                                    ${genre.genreName}
                            </option>
                        </c:forEach>
                    </select>
                    <small class="form-text">Giữ phím Ctrl (Windows) hoặc Command (Mac) để chọn nhiều thể loại.</small>
                </div>

                <div class="form-group">
                    <label for="type">Loại phim:</label>
                    <select class="form-control" id="type" name="type" required>
                        <option value="Đang chiếu" ${movie.type == 'Đang chiếu' ? 'selected' : ''}>Phim đang chiếu</option>
                        <option value="Sắp chiếu" ${movie.type == 'Sắp chiếu' ? 'selected' : ''}>Phim sắp công chiếu</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Cập Nhật Phim</button>
                <a href="${pageContext.request.contextPath}/owner/movie?cinemaID=${movie.cinemaID}" class="btn btn-secondary mt-2">Quay lại</a>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script> <!-- Đường dẫn tới bootstrap bundle -->
    </body>
</html>
