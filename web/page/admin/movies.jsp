<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Quản lý Phim</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/css/adminlte.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <style>
            :root {
                --primary-color: #3498db;
                --secondary-color: #2ecc71;
                --accent-color: #e74c3c;
                --text-color: #34495e;
                --light-bg: #ecf0f1;
                --dark-bg: #2c3e50;
            }

            body {
                font-family: 'Source Sans Pro', sans-serif;
                background-color: var(--light-bg);
                color: var(--text-color);
            }

            .content-wrapper {
                background-color: var(--light-bg);
                padding: 30px;
            }

            .card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
                transition: all 0.3s ease;
                overflow: hidden;
                margin-bottom: 30px;
                background: rgba(255, 255, 255, 0.8);
                backdrop-filter: blur(10px);
            }

            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
            }

            .card-header {
                background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
                color: white;
                border-bottom: none;
                padding: 20px;
                font-weight: 600;
            }

            .card-title {
                font-size: 1.5rem;
                margin-bottom: 0;
            }

            .btn-primary {
                background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
                border: none;
                border-radius: 50px;
                padding: 10px 20px;
                font-weight: 500;
                transition: all 0.3s ease;
            }

            .btn-primary:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(52, 152, 219, 0.3);
            }
            .table {
                border-collapse: separate;
                border-spacing: 0 15px;
                margin-top: -15px;
            }

            .table thead th {
                border: none;
                background-color: var(--dark-bg);
                color: white;
                padding: 15px;
                font-weight: 500;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .table tbody tr {
                background-color: white;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
            }

            .table tbody tr:hover {
                transform: translateY(-3px);
                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            }

            .table td {
                border: none;
                padding: 20px 15px;
                vertical-align: middle;
            }


            .btn-info, .btn-danger {
                border: none;
                border-radius: 50px;
                padding: 8px 15px;
                font-weight: 500;
                transition: all 0.3s ease;
            }

            .btn-info {
                background-color: var(--secondary-color);
            }

            .btn-danger {
                background-color: var(--accent-color);
            }

            .btn-info:hover, .btn-danger:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            }

            .modal-content {
                border: none;
                border-radius: 15px;
                overflow: hidden;
            }

            .modal-header {
                background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
                color: white;
                border-bottom: none;
                padding: 20px;
            }

            .modal-title {
                font-weight: 600;
            }

            .form-control, .select2-container--default .select2-selection--single {
                border-radius: 50px;
                border: 2px solid #e0e0e0;
                padding: 10px 20px;
                transition: all 0.3s ease;
            }

            .form-control:focus, .select2-container--default.select2-container--focus .select2-selection--single {
                border-color: var(--primary-color);
                box-shadow: 0 0 0 0.2rem rgba(52, 152, 219, 0.25);
            }

            .select2-container--default .select2-selection--single {
                height: auto;
            }

            .select2-container--default .select2-selection--single .select2-selection__rendered {
                line-height: normal;
                padding: 10px 20px;
            }

            .select2-container--default .select2-selection--single .select2-selection__arrow {
                height: 100%;
            }

            .main-footer {
                background-color: var(--dark-bg);
                color: white;
                border-top: none;
                padding: 20px;
            }

            .main-footer a {
                color: var(--secondary-color);
            }
            .switch {
                position: relative;
                display: inline-block;
                width: 60px;
                height: 34px;
            }

            .switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }

            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                transition: .4s;
                border-radius: 34px;
            }

            .slider:before {
                position: absolute;
                content: "";
                height: 26px;
                width: 26px;
                left: 4px;
                bottom: 4px;
                background-color: white;
                transition: .4s;
                border-radius: 50%;
            }

            input:checked + .slider {
                background-color: #2196F3;
            }

            input:checked + .slider:before {
                transform: translateX(26px);
            }

            .pagination .page-item {
                margin: 0 5px;
            }

            .pagination .page-link {
                border: none;
                border-radius: 50%;
                width: 40px;
                height: 40px;
                display: flex;
                align-items: center;
                justify-content: center;
                color: var(--text-color);
                background-color: var(--light-bg);
                transition: all 0.3s ease;
            }

            .pagination .page-item.active .page-link,
            .pagination .page-link:hover {
                background-color: var(--primary-color);
                color: white;
                box-shadow: 0 5px 15px rgba(52, 152, 219, 0.3);
            }
            .table td img {
                width: 50px;
                height: 70px;
                object-fit: cover;
                border-radius: 4px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }
            .cinema-chain-logo {
                width: 80px;
                height: 40px;
                object-fit: contain;
                background-color: #f8f9fa;
                border-radius: 6px;
                padding: 5px;
                margin: 0 5px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                transition: transform 0.2s ease, box-shadow 0.2s ease;
                display: inline-flex;
                align-items: center;
                justify-content: center;
            }

            .cinema-chain-logo:hover {
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.15);
            }

            .cinema-chain-logo img {
                max-width: 100%;
                max-height: 100%;
                object-fit: contain;
            }
            .swal2-popup {
                border-radius: 15px !important;
                font-family: 'Source Sans Pro', sans-serif !important;
            }

            .swal2-title {
                color: #2c3e50 !important;
                font-size: 24px !important;
            }

            .swal2-text {
                color: #34495e !important;
                font-size: 16px !important;
            }

            .swal2-confirm, .swal2-cancel {
                border-radius: 50px !important;
                padding: 12px 30px !important;
                font-size: 16px !important;
                font-weight: 500 !important;
            }
        </style>
    </style>
</head>
<body class="hold-transition sidebar-mini">
    <div class="wrapper">

        <jsp:include page="/page/admin/sidebar.jsp" />

        <div class="content-wrapper">
            <section class="content-header">
                <div class="container-fluid">
                    <h1 class="mb-4" data-aos="fade-right">Quản lý Phim</h1>
                </div>
            </section>

            <section class="content">
                <div class="container-fluid">
                    <div class="mb-4" data-aos="fade-up">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addMovieModal">
                            <i class="fas fa-plus-circle mr-2"></i>Thêm Phim Mới
                        </button>
                    </div>
                    <div class="card" data-aos="fade-up">
                        <div class="card-header">
                            <h3 class="card-title">Danh sách Phim</h3>
                            <div class="card-tools">
                                <form action="${pageContext.request.contextPath}/admin/movies" method="GET" class="form-inline">
                                    <input type="text" name="keyword" value="${keyword}" class="form-control mr-2" placeholder="Tìm kiếm...">
                                    <select name="sortBy" class="form-control mr-2">
                                        <option value="Title" ${sortBy == 'Title' ? 'selected' : ''}>Tiêu đề</option>
                                        <option value="DatePublished" ${sortBy == 'DatePublished' ? 'selected' : ''}>Ngày phát hành</option>
                                        <option value="Rating" ${sortBy == 'Rating' ? 'selected' : ''}>Đánh giá</option>
                                    </select>
                                    <select name="sortOrder" class="form-control mr-2">
                                        <option value="ASC" ${sortOrder == 'ASC' ? 'selected' : ''}>Tăng dần</option>
                                        <option value="DESC" ${sortOrder == 'DESC' ? 'selected' : ''}>Giảm dần</option>
                                    </select>
                                    <button type="submit" class="btn btn-primary">Áp dụng</button>
                                </form>
                            </div>
                        </div>
                        <div class="card-body p-0">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Tiêu đề</th>
                                        <th>Rạp</th>
                                        <th>Ảnh</th>
                                        <th>Ngày phát hành</th>
                                        <th>Đánh giá</th>
                                        <th>Trạng thái</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="movie" items="${movies}" >
                                        <tr>
                                            <td></td>
                                            <td>${movie.title}</td>
                                            <td></td>
                                            <td>
                                                <img src="${movie.imageURL}" alt="${movie.title}" style="width: 50px; height: auto;">
                                            </td>
                                            <td><fmt:formatDate value="${movie.datePublished}" pattern="dd/MM/yyyy" /></td>
                                            <td>${movie.rating}</td>
                                            <td>
                                                ${movie.type == 'SHOWING' ? "Đang chiếu" : movie.type == 'COMMING' ? "Sắp  chiếu" : "N/A"}
                                            </td>
                                            <td>
                                                <label class="switch">
                                                    <input type="checkbox" class="status-toggle" data-id="${movie.movieID}" ${movie.status ? 'checked' : ''}>
                                                    <span class="slider"></span>
                                                </label>
                                            </td>

                                            <td>
                                                <c:forEach var="chain" items="${movieCinemaChains[movie.movieID]}">
                                                    <img src="${chain.avatarURL}" alt="${chain.name}" title="${chain.name}" 
                                                         class="cinema-chain-logo">
                                                </c:forEach>
                                            </td>
                                            <!--                                            <td>
                                                                                            <button type="button" class="btn btn-sm btn-info edit-movie" 
                                                                                                    data-id="${movie.movieID}"
                                                                                                    data-title="${movie.title}"
                                                                                                    data-synopsis="${movie.synopsis}"
                                                                                                    data-datepublished="<fmt:formatDate value="${movie.datePublished}" pattern="yyyy-MM-dd" />"
                                                                                                    data-imageurl="${movie.imageURL}"
                                                                                                    data-rating="${movie.rating}"
                                                                                                    data-country="${movie.country}"
                                                                                                    data-linktrailer="${movie.linkTrailer}"
                                                                                                    data-cinemaid="${movie.cinemaID}"
                                                                                                    data-type="${movie.type}"
                                                                                                    data-status="${movie.status}">
                                                                                                <i class="fas fa-edit mr-1"></i>Sửa
                                                                                            </button>
                                                                                            <button type="button" class="btn btn-sm btn-danger delete-movie" data-id="${movie.movieID}">
                                                                                                <i class="fas fa-trash-alt mr-1"></i>Ban
                                                                                            </button>
                                                                                        </td>-->
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="card-footer clearfix">
                            <ul class="pagination pagination-sm m-0 float-right">
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${currentPage - 1}&sortBy=${sortBy}&sortOrder=${sortOrder}&keyword=${keyword}">&laquo;</a>
                                    </li>
                                </c:if>
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                                        <a class="page-link" href="?page=${i}&sortBy=${sortBy}&sortOrder=${sortOrder}&keyword=${keyword}">${i}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${currentPage + 1}&sortBy=${sortBy}&sortOrder=${sortOrder}&keyword=${keyword}">&raquo;</a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <footer class="main-footer">
            <strong>Copyright &copy; 2024 <a href="#">Unove Cinema</a>.</strong>
            All rights reserved.
        </footer>
    </div>

    <!-- Add Movie Modal -->
    <div class="modal fade" id="addMovieModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Thêm Phim Mới</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form id="addMovieForm">
                    <div class="modal-body">
                        <input type="hidden" name="action" value="add">
                        <div class="form-group">
                            <label for="title">Tiêu đề</label>
                            <input type="text" class="form-control" id="title" name="title" required>
                        </div>
                        <div class="form-group">
                            <label for="synopsis">Tóm tắt</label>
                            <textarea class="form-control" id="synopsis" name="synopsis" rows="3"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="datePublished">Ngày phát hành</label>
                            <input type="date" class="form-control" id="datePublished" name="datePublished" required>
                        </div>
                        <div class="form-group">
                            <label for="imageURL">URL Hình ảnh</label>
                            <input type="url" class="form-control" id="imageURL" name="imageURL">
                        </div>
                        <div class="form-group">
                            <label for="rating">Đánh giá</label>
                            <input type="number" class="form-control" id="rating" name="rating" min="0" max="10" step="0.1">
                        </div>
                        <div class="form-group">
                            <label for="country">Quốc gia</label>
                            <input type="text" class="form-control" id="country" name="country">
                        </div>
                        <div class="form-group">
                            <label for="linkTrailer">Link Trailer</label>
                            <input type="url" class="form-control" id="linkTrailer" name="linkTrailer">
                        </div>
                        <div class="form-group">
                            <label for="cinemaID">ID Rạp chiếu</label>
                            <input type="number" class="form-control" id="cinemaID" name="cinemaID">
                        </div>
                        <div class="form-group">
                            <label for="type">Thể loại</label>
                            <input type="text" class="form-control" id="type" name="type">
                        </div>
                        <div class="form-group">
                            <label for="status">Trạng thái</label>
                            <select class="form-control" id="status" name="status">
                                <option value="true">Đang chiếu</option>
                                <option value="false">Ngừng chiếu</option>
                            </select>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                        <button type="submit" class="btn btn-primary">Thêm</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Edit Movie Modal -->
    <div class="modal fade" id="editMovieModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Sửa Thông Tin Phim</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form id="editMovieForm">
                    <div class="modal-body">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" id="editMovieID" name="movieID">
                        <div class="form-group">
                            <label for="editTitle">Tiêu đề</label>
                            <input type="text" class="form-control" id="editTitle" name="title" required>
                        </div>
                        <div class="form-group">
                            <label for="editSynopsis">Tóm tắt</label>
                            <textarea class="form-control" id="editSynopsis" name="synopsis" rows="3"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="editDatePublished">Ngày phát hành</label>
                            <input type="date" class="form-control" id="editDatePublished" name="datePublished" required>
                        </div>
                        <div class="form-group">
                            <label for="editImageURL">URL Hình ảnh</label>
                            <input type="url" class="form-control" id="editImageURL" name="imageURL">
                        </div>
                        <div class="form-group">
                            <label for="editRating">Đánh giá</label>
                            <input type="number" class="form-control" id="editRating" name="rating" min="0" max="10" step="0.1">
                        </div>
                        <div class="form-group">
                            <label for="editCountry">Quốc gia</label>
                            <input type="text" class="form-control" id="editCountry" name="country">
                        </div>
                        <div class="form-group">
                            <label for="editLinkTrailer">Link Trailer</label>
                            <input type="url" class="form-control" id="editLinkTrailer" name="linkTrailer">
                        </div>
                        <div class="form-group">
                            <label for="editCinemaID">ID Rạp chiếu</label>
                            <input type="number" class="form-control" id="editCinemaID" name="cinemaID">
                        </div>
                        <div class="form-group">
                            <label for="editType">Thể loại</label>
                            <input type="text" class="form-control" id="editType" name="type">
                        </div>
                        <div class="form-group">
                            <label for="editStatus">Trạng thái</label>
                            <select class="form-control" id="editStatus" name="status">
                                <option value="true">Đang chiếu</option>
                                <option value="false">Ngừng chiếu</option>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                        <button type="submit" class="btn btn-primary">Cập nhật</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/js/adminlte.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
    <script>
        $(document).ready(function () {
            AOS.init({
                duration: 800,
                once: true
            });

            // Xử lý phân trang và sắp xếp
            $(document).on('click', '.pagination .page-link, .card-tools form button', function (e) {
                e.preventDefault();
                var url = $(this).attr('href') || $('.card-tools form').attr('action') + '?' + $('.card-tools form').serialize();
                loadMovies(url);
            });

            function loadMovies(url) {
                $.ajax({
                    url: url,
                    type: 'GET',
                    success: function (response) {
                        $('.card-body').html($(response).find('.card-body').html());
                        $('.card-footer').html($(response).find('.card-footer').html());
                        history.pushState(null, null, url);
                    },
                    error: function (xhr, status, error) {
                        console.error("Error loading movies:", error);
                        alert('Có lỗi xảy ra khi tải danh sách phim');
                    }
                });
            }

            // Xử lý thêm phim
            $('#addMovieForm').submit(function (e) {
                e.preventDefault();
                var formData = $(this).serialize();
                $.ajax({
                    url: '${pageContext.request.contextPath}/admin/movies',
                    type: 'POST',
                    data: formData,
                    dataType: 'json',
                    success: function (response) {
                        if (response.success) {
                            alert(response.message);
                            $('#addMovieModal').modal('hide');
                            loadMovies(window.location.href);
                        } else {
                            alert(response.message);
                        }
                    },
                    error: function (xhr, status, error) {
                        console.error("Error details:", xhr.responseText);
                        alert('Có lỗi xảy ra khi thêm phim: ' + xhr.responseText);
                    }
                });
            });

            // Xử lý sửa phim
            $(document).on('click', '.edit-movie', function () {
                var movieID = $(this).data('id');
                $('#editMovieID').val(movieID);
                $('#editTitle').val($(this).data('title'));
                $('#editSynopsis').val($(this).data('synopsis'));
                $('#editDatePublished').val($(this).data('datepublished'));
                $('#editImageURL').val($(this).data('imageurl'));
                $('#editRating').val($(this).data('rating'));
                $('#editCountry').val($(this).data('country'));
                $('#editLinkTrailer').val($(this).data('linktrailer'));
                $('#editCinemaID').val($(this).data('cinemaid'));
                $('#editType').val($(this).data('type'));
                $('#editStatus').val($(this).data('status').toString());

                $('#editMovieModal').modal('show');
            });

            $('#editMovieForm').submit(function (e) {
                e.preventDefault();
                var formData = $(this).serialize();
                $.ajax({
                    url: '${pageContext.request.contextPath}/admin/movies',
                    type: 'POST',
                    data: formData,
                    dataType: 'json',
                    success: function (response) {
                        if (response.success) {
                            alert(response.message);
                            $('#editMovieModal').modal('hide');
                            loadMovies(window.location.href);
                        } else {
                            alert(response.message);
                        }
                    },
                    error: function (xhr, status, error) {
                        console.error("Error details:", xhr.responseText);
                        alert('Có lỗi xảy ra khi cập nhật phim: ' + xhr.responseText);
                    }
                });
            });

            // Xử lý cập nhật trạng thái
            $(document).on('change', '.status-toggle', function () {
                var movieID = $(this).data('id');
                var newStatus = this.checked;
                var toggleElement = this;

                $.ajax({
                    url: '${pageContext.request.contextPath}/admin/movies',
                    type: 'POST',
                    data: {
                        action: 'updateStatus',
                        movieID: movieID,
                        status: newStatus
                    },
                    dataType: 'json',
                    success: function (response) {
                        if (response.success) {
                            Swal.fire({
                                title: 'Thành công!',
                                text: 'Cập nhật trạng thái thành công',
                                icon: 'success',
                                confirmButtonText: 'OK',
                                confirmButtonColor: '#3498db'
                            });
                        } else {
                            Swal.fire({
                                title: 'Lỗi!',
                                text: response.message,
                                icon: 'error',
                                confirmButtonText: 'OK',
                                confirmButtonColor: '#e74c3c'
                            });
                            $(toggleElement).prop('checked', !newStatus);
                        }
                    },
                    error: function (xhr, status, error) {
                        Swal.fire({
                            title: 'Lỗi!',
                            text: 'Có lỗi xảy ra khi cập nhật trạng thái phim',
                            icon: 'error',
                            confirmButtonText: 'OK',
                            confirmButtonColor: '#e74c3c'
                        });
                        $(toggleElement).prop('checked', !newStatus);
                    }
                });
            });

            // Xử lý xóa phim
            $(document).on('click', '.delete-movie', function () {
                var movieID = $(this).data('id');
                if (confirm('Bạn có chắc chắn muốn xóa phim này?')) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/admin/movies',
                        type: 'POST',
                        data: {action: 'delete', movieID: movieID},
                        dataType: 'json',
                        success: function (response) {
                            if (response.success) {
                                alert(response.message);
                                loadMovies(window.location.href);
                            } else {
                                alert(response.message);
                            }
                        },
                        error: function (xhr, status, error) {
                            console.error("Error details:", xhr.responseText);
                            alert('Có lỗi xảy ra khi xóa phim: ' + error);
                        }
                    });
                }
            });
        });
    </script>
</body>
</html>