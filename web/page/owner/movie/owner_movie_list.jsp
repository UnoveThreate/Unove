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
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/css/adminlte.min.css">
        <!-- overlayScrollbars -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/overlayscrollbars/1.13.0/css/OverlayScrollbars.min.css">
        <!-- FullCalendar CSS -->
        <link href='https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.css' rel='stylesheet' />

        <style>
            body {
                background-color: #f8f9fa;
            }


            .table {
                border-radius: 0.5rem;
                overflow: hidden;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            .table th, .table td {
                vertical-align: middle;
                text-align: center;
                white-space: nowrap;
                padding: 10px;
            }
            .table th {
                height: 60px;
                background-color: #007bff;
                color: white; /* Màu chữ cho tiêu đề */
            }
            .btn-group .btn {
                min-width: 100px;
                height: 38px;
            }
            /* Cấu trúc hiển thị danh sách phim */
            .movie-card {
                max-width: 150px;
                margin: 10px;
                border-radius: 10px;
                overflow: hidden;
            }
            .movie-image {
                height: 180px;
                object-fit: cover;
            }
            .movie-actions {
                display: flex;
                justify-content: space-between;
                padding: 10px;
            }
            /* Căn giữa tiêu đề */
            .text-center {
                text-align: center;
                margin-bottom: 20px; /* Khoảng cách dưới tiêu đề */
            }

            /* Định dạng cho nút thêm phòng */
            .btn-primary {
                background-color: #007bff; /* Màu nền của nút */
                border: none; /* Bỏ đường viền */
                transition: background-color 0.3s ease; /* Hiệu ứng chuyển động */
            }

            .btn-primary:hover {
                background-color: #0056b3; /* Màu nền khi hover */
            }
            /* Nút xóa */
            .btn-danger {
                background-color: #dc3545; /* Màu nền của nút xóa */
                border: none; /* Bỏ đường viền */
                transition: background-color 0.3s ease; /* Hiệu ứng chuyển động */
            }

            .btn-danger:hover {
                background-color: #c82333; /* Màu nền khi hover */
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
                max-width: 80%;
                margin: auto;
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
                width: 100%;
                height: 800px;
            }
        </style>
    </head>
    <body class="hold-transition sidebar-mini layout-fixed">
        <div class="wrapper">
            <nav class="main-header navbar navbar-expand navbar-white navbar-light">
                <!-- Left navbar links -->
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
                    </li>
                </ul>
            </nav>
            <!-- /.navbar -->

            <!-- Main Sidebar Container -->
            <jsp:include page="/page/ownerdashboard/sidebar.jsp" />

            <!-- Content Wrapper -->
            <div class="content-wrapper">
                <section class="content">
                    <div class="container-fluid">
                        <h2 class="text-center">Danh Sách Phim </h2>
                        <!-- Form chọn rạp -->
                        <div class="form-group mb-4">
                            <label for="cinemaSelect">Chọn Rạp:</label>
                            <form id="cinemaSelectForm" method="post" class="form-inline mb-2">
                                <select class="form-control" id="cinemaSelect" name="cinemaID" onchange="this.form.submit()">
                                    <option value="">-- Chọn Rạp --</option>
                                    <c:forEach items="${cinemas}" var="cinema">
                                        <option value="${cinema.cinemaID}" ${cinema.cinemaID == selectedCinemaID ? 'selected' : ''}>
                                            ${cinema.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </form>
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
                                                <img src="${movie.imageURL}" alt="Movie Image" class="img-fluid movie-image"/>
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
                                                <div class="btn-group" role="group">
                                                    <a href="${pageContext.request.contextPath}/owner/updateMovie?movieID=${movie.movieID}&cinemaID=${cinemaID}" class="btn btn-warning">Chỉnh Sửa</a>
                                                </div>
                                                <form action="${pageContext.request.contextPath}/owner/deleteMovie" method="post" style="display:inline-block;" onsubmit="return confirm('Bạn có chắc chắn muốn xóa phim này?');">
                                                    <input type="hidden" name="movieID" value="${movie.movieID}">
                                                    <input type="hidden" name="cinemaID" value="${cinemaID}">
                                                    <button type="submit" class="btn btn-danger">Xoá</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>

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
                        trailerVideo.src = embedLink + "?autoplay=1";
                        trailerModal.style.display = "flex";
                    }
                    document.querySelector(".close").onclick = function () {
                        const trailerModal = document.getElementById("trailerModal");
                        const trailerVideo = document.getElementById("trailerVideo");
                        trailerVideo.src = ""; // Dừng video
                        trailerModal.style.display = "none";
                    }
                    window.onclick = function (event) {
                        const trailerModal = document.getElementById("trailerModal");
                        if (event.target == trailerModal) {
                            trailerVideo.src = ""; // Dừng video
                            trailerModal.style.display = "none";
                        }
                    }
                </script>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/js/adminlte.min.js"></script>
        <script src='https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.js'></script>
    </body>
</html>
