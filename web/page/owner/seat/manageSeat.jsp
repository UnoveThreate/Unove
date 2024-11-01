<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Quản Lý Ghế</title>

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
                background-color: #f4f6f9;
            }
            .content-wrapper {
                background-color: #fff;
            }
            .form-row {
                display: flex;
                justify-content: center;
                margin-bottom: 20px;
            }
            .form-group {
                margin: 0 15px; /* Thêm khoảng cách giữa các trường */
            }
            .screen {
                text-align: center;
                margin: 20px 0;
                font-weight: bold;
                font-size: 24px;
            }
            .seats-container {
                display: grid;
                grid-template-columns: repeat(18, 1fr); /* Thay đổi số cột nếu cần */
                gap: 5px;
                margin: 20px auto;
                max-width: 900px;
            }
            .seat {
                width: 40px;
                height: 40px;
                display: flex;
                align-items: center;
                justify-content: center;
                border: 1px solid #ccc;
                border-radius: 5px;
                cursor: pointer;
                font-size: 12px;
            }
            .available {
                background-color: #28a745;
                color: white;
            }
            .booked {
                background-color: #dc3545;
                color: white;
            }
            .selected {
                background-color: #007bff;
                color: white;
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

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <div class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1 class="m-0">Quản Lý Ghế</h1>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.content-header -->

                <!-- Chọn rạp và phòng -->
                <div class="form-row">
                    <form id="cinemaSelectForm" method="post" class="form-group">
                        <label for="cinemaSelect">Chọn Rạp:</label>
                        <select class="form-control" id="cinemaSelect" name="cinemaID" onchange="this.form.submit()">
                            <option value="">-- Chọn Rạp --</option>
                            <c:forEach items="${cinemas}" var="cinema">
                                <option value="${cinema.cinemaID}" ${cinema.cinemaID == selectedCinemaID ? 'selected' : ''}>
                                    ${cinema.name}
                                </option>
                            </c:forEach>
                        </select>
                    </form>

                    <form id="roomSelectForm" method="post" class="form-group">
                        <label for="roomSelect">Chọn Phòng:</label>
                        <select class="form-control" id="roomSelect" name="roomID" onchange="this.form.submit()">
                            <option value="">-- Chọn Phòng --</option>
                            <c:forEach items="${rooms}" var="room">
                                <option value="${room.roomID}" ${room.roomID == selectedRoomID ? 'selected' : ''}>
                                    ${room.roomName}
                                </option>
                            </c:forEach>
                        </select>
                    </form>
                </div>

                <!-- Danh sách ghế -->
                <div class="row">
                    <div class="col-12">
                        <h3 class="mb-3 text-center">Danh Sách Ghế</h3>
                        <div class="screen">MÀN HÌNH</div>
                        <div class="seats-container">
                            <c:forEach var="row" begin="1" end="9"> <!-- Số hàng ghế -->
                                <c:forEach var="col" begin="1" end="18"> <!-- Số cột ghế -->
                                    <c:set var="currentSeat" value="${null}" />
                                    <c:forEach var="seat" items="${seats}">
                                        <c:if test="${seat.coordinateX == col && seat.coordinateY == row}">
                                            <c:set var="currentSeat" value="${seat}" />
                                        </c:if>
                                    </c:forEach>

                                    <c:choose>
                                        <c:when test="${currentSeat != null}">
                                            <div class="seat ${currentSeat.available ? 'available' : 'booked'}" 
                                                 data-id="${currentSeat.seatID}" 
                                                 title="${currentSeat.available ? 'Ghế Trống' : 'Ghế Đã Đặt'}">
                                                ${currentSeat.name}
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="seat" style="visibility: hidden;"></div>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div><!-- /.content-wrapper -->

            <footer class="main-footer">
                <strong>Copyright &copy; 2023 <a href="#">Unove</a>.</strong>
                All rights reserved.
            </footer>
        </div>
        <!-- ./wrapper -->
    </body>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/admin-lte@3.2/dist/js/adminlte.min.js"></script>
</html>
