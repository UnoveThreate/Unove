<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="model.owner.Movie" %>
<%@ page import="java.util.List" %>
<jsp:include page="navbar.jsp" />
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Phim</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                background-color: #f8f9fa; /* Màu nền nhẹ cho trang */
            }
            .container {
                margin-top: 20px; /* Khoảng cách trên container */
            }
            h2 {
                margin-bottom: 20px; /* Khoảng cách dưới tiêu đề */
                color: #007bff; /* Màu sắc tiêu đề */
            }
            .table th, .table td {
                vertical-align: middle; /* Căn giữa nội dung ô */
            }
            .btn-group .btn {
                min-width: 100px; /* Chiều rộng tối thiểu cho nút */
                height: 38px; /* Chiều cao cho tất cả nút */
                text-align: center; /* Căn giữa văn bản trong nút */
            }
            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.8);
                justify-content: center;
                align-items: center;
            }
            .modal-content {
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                position: relative;
                text-align: center;
                max-width: 80%; /* Tối đa chiều rộng của modal */
                margin: auto; /* Căn giữa modal */
            }
            .close {
                position: absolute;
                top: 10px;
                right: 20px;
                color: #000;
                font-size: 30px;
                font-weight: bold;
                cursor: pointer;
            }
            .close:hover {
                color: red;
            }
            iframe {
                width: 100%; /* Chiều rộng của iframe là 100% */
                height: 800px; /* Chiều cao của iframe */
            }
        </style>
    </head>
    <body>

        <div class="container">
            <h2>Danh Sách Phim - Rạp: ${cinemaName}</h2>

            <!-- Nút Thêm Phim -->
            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/owner/createMovie?cinemaID=${cinemaID}" class="btn btn-primary">Thêm Phim</a>
            </div>

            <!-- Bảng Danh Sách Phim -->
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th>Tiêu Đề</th>
                            <th>Tóm Tắt</th>
                            <th>Ngày Phát Hành</th>
                            <th>Hình Ảnh</th>
                            <th>Đánh Giá</th>
                            <th>Quốc Gia</th>
                            <th>Trailer</th>
                            <th>Thể loại</th>
                            <th>Trạng thái</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="movie" items="${movies}">
                            <tr>
                                <td>${movie.title}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:length(movie.synopsis) > 100}">
                                            ${fn:substring(movie.synopsis, 0, 100)}...
                                        </c:when>
                                        <c:otherwise>
                                            ${fn:escapeXml(movie.synopsis)}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${movie.datePublished}</td>
                                <td>
                                    <img src="${movie.imageURL}" alt="Movie Image" class="img-fluid" style="max-width: 150px;"/>
                                </td>
                                <td>${movie.rating}</td>
                                <td>${movie.country}</td>
                                <td>
                                    <button class="btn btn-info btn-sm" onclick="openModal('${movie.linkTrailer}')">Xem Trailer</button>
                                </td>
                                <td>
                                    <c:forEach var="genre" items="${movie.genres}">
                                        <span class="badge badge-secondary">${genre.genreName}</span>
                                    </c:forEach>
                                </td>
                                <td>${movie.type}</td>
                                <td>
                                    <div class="btn-group" role="group" style="display: flex; align-items: center;">
                                        <a href="${pageContext.request.contextPath}/owner/updateMovie?movieID=${movie.movieID}&cinemaID=${cinemaID}" class="btn btn-warning">Chỉnh Sửa</a>
                                        <form action="${pageContext.request.contextPath}/owner/deleteMovie" method="post" style="display: inline;">
                                            <input type="hidden" name="movieID" value="${movie.movieID}">
                                            <input type="hidden" name="cinemaID" value="${cinemaID}">
                                            <button type="submit" class="btn btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa phim này không?');">Xóa</button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Modal cho Trailer -->
        <div id="trailerModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <iframe id="trailerVideo" title="YouTube video player" frameborder="0" allowfullscreen></iframe>
            </div>
        </div>

        <script>
            function openModal(link) {
                const trailerModal = document.getElementById("trailerModal");
                const trailerVideo = document.getElementById("trailerVideo");
                const embedLink = link.replace("watch?v=", "embed/");
                trailerVideo.src = embedLink + "?autoplay=1"; // Phát video khi modal mở
                trailerModal.style.display = "flex";
            }
            const closeBtn = document.querySelector(".close");
            closeBtn.onclick = function () {
                closeModal();
            };

            window.onclick = function (event) {
                const trailerModal = document.getElementById("trailerModal");
                if (event.target == trailerModal) {
                    closeModal();
                }
            };

            function closeModal() {
                const trailerModal = document.getElementById("trailerModal");
                const trailerVideo = document.getElementById("trailerVideo");
                trailerModal.style.display = "none";
                trailerVideo.src = ""; // Dừng video khi đóng modal
            }
        </script>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
