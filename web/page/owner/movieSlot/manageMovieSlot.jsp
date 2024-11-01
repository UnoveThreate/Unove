<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--<jsp:include page="/page/ownerdashboard/sidebar.jsp" />--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Movie Slots</title>
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
            /* Định dạng bảng và các thành phần khác */
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                /*padding: 1rem;*/
                text-align: center;
            }
            th {
                background-color: #007bff;
                color: white;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            /* Định dạng cho thông tin phim */
            .movie-info {
                display: flex;
                align-items: center;
                position: relative; /* Để định vị rating */
            }
            .movie-info img {
                max-width: 50px;
                max-height: 75px;
                margin-right: 10px;
            }
            .rating {
                position: absolute;
                top: 5px; /* Đưa sao lên trên */
                right: 5px; /* Đưa sao vào bên phải */
                color: gold; /* Màu vàng cho sao */
            }

            /* Khung giờ */
            .time-box {
                border: 1px solid #007bff;
                padding: 5px;
                border-radius: 5px;
                margin: 0 auto;
                width: fit-content;
            }

            /* Responsive */
            @media (max-width: 768px) {
                .movie-info img {
                    max-width: 40px;
                    max-height: 60px;
                }
                .time-box {
                    font-size: 12px;
                }
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
                <div class="container mt-4">
                    <h1 class="text-center mb-4">Manage Movie Slots</h1>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">${errorMessage}</div>
                    </c:if>

                    <form method="post" class="mb-4">
                        <div class="row">
                            <div class="col-md-4">
                                <label for="cinemaSelect">Chọn Rạp:</label>
                                <select class="form-control" id="cinemaSelect" name="cinemaID" onchange="this.form.submit()">
                                    <option value="">-- Chọn Rạp --</option>
                                    <c:forEach items="${cinemas}" var="cinema">
                                        <option value="${cinema.cinemaID}" ${cinema.cinemaID == selectedCinemaID ? 'selected' : ''}>
                                            ${cinema.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="movieFilter">Filter by Movie:</label>
                                <select id="movieFilter" name="movieID" class="form-control" onchange="this.form.submit()">
                                    <option value="">All Movies</option>
                                    <c:forEach var="movieEntry" items="${movieNames}">
                                        <option value="${movieEntry.key}" ${movieEntry.key == selectedMovieID ? 'selected' : ''}>
                                            ${movieEntry.value}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="roomFilter">Filter by Room:</label>
                                <select id="roomFilter" name="roomID" class="form-control" onchange="this.form.submit()">
                                    <option value="">All Rooms</option>
                                    <c:forEach var="room" items="${rooms}">
                                        <option value="${room.roomID}" ${room.roomID == selectedRoomID ? 'selected' : ''}>
                                            ${room.roomName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </form>

                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>Room Name</th>
                                    <th>Movie</th>
                                    <th>Date</th>
                                    <th>Time</th>
                                    <th>Type</th>
                                    <th>Price</th>
                                    <th>Discount</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="slot" items="${movieSlots}">
                                    <tr>
                                        <td>${roomNames[slot.roomID]}</td>
                                        <td>
                                            <div class="movie-info">
                                                <img src="${slot.movieImageURL}" alt="${slot.movieTitle}" />
                                                <span class="rating">
                                                    <i class="fas fa-star"></i> ${slot.movieRating}
                                                </span>
                                                <strong>${slot.movieTitle}</strong>
                                            </div>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${fn:substringBefore(slot.startTime, 'T') eq fn:substringBefore(slot.endTime, 'T')}">
                                                    <strong>${fn:substringBefore(slot.startTime, 'T')}</strong>
                                                </c:when>
                                                <c:otherwise>
                                                    <strong>Start:</strong> ${fn:substringBefore(slot.startTime, 'T')} <br>
                                                    <strong>End:</strong> ${fn:substringBefore(slot.endTime, 'T')}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="time-box">${fn:substringAfter(slot.startTime, 'T')}</div> 
                                            <div class="time-box">${fn:substringAfter(slot.endTime, 'T')}</div>
                                        </td>
                                        <td>${slot.type}</td>
                                        <td>${slot.price}</td>
                                        <td>${slot.discount}</td>
                                        <td>${slot.status}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/owner/movieSlot/editMovieSlot?movieSlotID=${slot.movieSlotID}" class="btn btn-warning">Chỉnh Sửa</a>

                                            <a href="${pageContext.request.contextPath}/owner/movieSlot/deleteMovieSlot?movieSlotID=${slot.movieSlotID}" class="btn btn-primary">Xoá</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="text-center mt-4">
                        <a href="${pageContext.request.contextPath}/owner/movieSlot/createMovieSlot?cinemaID=${cinemaID}" class="btn btn-primary">Create New Movie Slot</a>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/admin-lte@3.2/dist/js/adminlte.min.js"></script>
    </body>
</html>
