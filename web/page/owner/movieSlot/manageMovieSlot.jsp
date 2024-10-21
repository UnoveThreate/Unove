<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="/page/owner/navbar.jsp" />
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Movie Slots</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" integrity="sha512-Fo3rlrZj/k7ujTnH2RjBgYZQe1J6yofZXKmlVRyM2xs38vyt1i6X2PQV+lmRv6/6H18E5joUBXNlPvE5dC9YBQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            /* Định dạng bảng và các thành phần khác */
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                padding: 1rem;
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
    <body>
        <div class="container mt-4">
            <h1 class="text-center mb-4">Manage Movie Slots</h1>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
            </c:if>

            <!-- Dropdown filter cho Movie -->
            <div class="mb-4">
                <form action="" method="GET">
                    <label for="movieFilter" class="form-label">Filter by Movie:</label>
                    <select id="movieFilter" name="movieID" class="form-select" onchange="this.form.submit()">
                        <option value="">All Movies</option>
                        <c:forEach var="movieEntry" items="${movieNames}">
                            <option value="${movieEntry.key}" <c:if test="${movieEntry.key == selectedMovieID}">selected</c:if>>${movieEntry.value}</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="cinemaID" value="${cinemaID}"/> <!-- Đảm bảo cinemaID được gửi cùng -->
                    <input type="hidden" name="roomID" value="${selectedRoomID}"/> <!-- Để giữ roomID đã chọn -->
                </form>
            </div>

            <!-- Dropdown filter cho Room -->
            <div class="mb-4">
                <form action="" method="GET">
                    <label for="roomFilter" class="form-label">Filter by Room:</label>
                    <select id="roomFilter" name="roomID" class="form-select" onchange="this.form.submit()">
                        <option value="">All Rooms</option>
                        <c:forEach var="room" items="${rooms}">
                            <option value="${room.roomID}" <c:if test="${room.roomID == selectedRoomID}">selected</c:if>>${room.roomName}</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="cinemaID" value="${cinemaID}"/> <!-- Đảm bảo cinemaID được gửi cùng -->
                    <input type="hidden" name="movieID" value="${selectedMovieID}"/> <!-- Để giữ movieID đã chọn -->
                </form>
            </div>

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
    </body>
</html>
