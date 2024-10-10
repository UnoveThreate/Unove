<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="model.owner.Room" %>
<%@ page import="java.util.List" %>
<jsp:include page="/page/owner/navbar.jsp" />
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Phòng</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <h2>Danh Sá ch Phòng </h2>

            <!-- Nút Thêm Phòng -->
            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/owner/createRoom?cinemaID=${cinemaID}" class="btn btn-primary">Thêm Phòng</a>
            </div>

            <!-- Bảng Danh Sách Phòng -->
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <!-- Trong phần bảng danh sách phòng -->
                    <!-- Trong phần bảng danh sách phòng -->
                    <thead class="thead-dark">
                        <tr>
                            <th>Tên Phòng</th>
                            <th>Số Ghế</th>
                            <th>Loại Phòng</th> <!-- Thêm cột loại phòng -->
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
                                        ${type}<c:if test="${status.last}"> </c:if> <!-- Chỉ cần thêm khoảng trắng nếu là phần tử cuối -->
                                        <c:if test="${!status.last}">, </c:if> <!-- Thêm dấu phẩy giữa các loại phòng, trừ phần tử cuối -->
                                    </c:forEach>
                                </td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="${pageContext.request.contextPath}/owner/updateRoom?roomID=${room.roomID}&cinemaID=${cinemaID}" class="btn btn-warning">Chỉnh Sửa</a>

                                    </div>
                                    <form action="${pageContext.request.contextPath}/owner/deleteRoom" method="post" style="display:inline-block;" onsubmit="return confirm('Bạn có chắc chắn muốn xóa phòng này?');">
                                        <input type="hidden" name="roomID" value="${room.roomID}">
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

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
