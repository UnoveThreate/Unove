<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="model.owner.Room" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Phòng</title>
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
            /* Định dạng cho bảng */
            .table {
                border-radius: 0.5rem;
                overflow: hidden;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .table th {
                background-color: #007bff; /* Màu nền tiêu đề bảng */
                color: white; /* Màu chữ tiêu đề bảng */
            }

            .table td {
                background-color: #ffffff; /* Màu nền ô bảng */
                transition: background-color 0.3s ease; /* Hiệu ứng chuyển động */
            }

            .table td:hover {
                background-color: #f1f1f1; /* Màu nền khi hover vào ô bảng */
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

            /* Căn giữa tiêu đề */
            .text-center {
                text-align: center;
                margin-bottom: 20px; /* Khoảng cách dưới tiêu đề */
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

            <div class="content-wrapper">
                <div class="container-fluid">
                    <h2 class="text-center">Danh Sách Phòng</h2>
                    <!-- Hàng chứa form chọn rạp và nút thêm phòng -->
                    <div class="form-group mb-4">
                        <label for="cinemaSelect">Chọn Rạp:</label>
                        <form id="cinemaSelectForm" method="get" class="form-inline mb-2">
                            <select class="form-control" id="cinemaSelect" name="cinemaID" onchange="this.form.submit()">
                                <option value="">-- Chọn Rạp --</option>
                                <c:forEach items="${cinemas}" var="cinema">
                                    <option value="${cinema.cinemaID}" ${cinema.cinemaID == selectedCinemaID ? 'selected' : ''}>
                                        ${cinema.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </form>
                        <a href="${pageContext.request.contextPath}/owner/room/createRoom?cinemaID=${selectedCinemaID}" class="btn btn-primary">Thêm Phòng</a>
                    </div>

                    <!-- Bảng Danh Sách Phòng -->
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead class="thead-dark">
                                <tr>
                                    <th>Tên Phòng</th>
                                    <th>Số Ghế</th>
                                    <th>Loại Phòng</th>
                                    <th>Hành Động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="room" items="${rooms}">
                                    <tr>
                                        <td>${room.roomName}</td>
                                        <td>${room.capacity}</td>
                                        <td>
                                            <c:forEach var="type" items="${room.roomTypes}" varStatus="status">
                                                ${type}<c:if test="${status.last}"> </c:if>
                                                <c:if test="${!status.last}">, </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <div class="btn-group" role="group">
                                                <a href="${pageContext.request.contextPath}/owner/room/updateRoom?roomID=${room.roomID}&cinemaID=${selectedCinemaID}" class="btn btn-warning">Chỉnh Sửa</a>
                                            </div>
                                            <form action="${pageContext.request.contextPath}/owner/room/deleteRoom" method="post" style="display:inline-block;" onsubmit="return confirm('Bạn có chắc chắn muốn xóa phòng này?');">
                                                <input type="hidden" name="roomID" value="${room.roomID}">
                                                <input type="hidden" name="cinemaID" value="${selectedCinemaID}">
                                                <button type="submit" class="btn btn-danger">Xoá</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/admin-lte@3.2/dist/js/adminlte.min.js"></script>
    </body>
</html>
