<%-- 
    Document   : owner_update_room
    Created on : 8 thg 10, 2024, 21:06:20
    Author     : nguyendacphong
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Câp nhập phòng</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .container {
                margin-top: 50px;
                background-color: #fff;
                padding: 30px;
                border-radius: 5px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
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
            <h1>Update Room</h1>
            <form action="${pageContext.request.contextPath}/owner/room/updateRoom" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="roomID" value="${room.roomID}">
                <input type="hidden" name="cinemaID" value="${room.cinemaID}">

                <div class="form-group">
                    <label for="roomName">Room Name:</label>
                    <input type="text" class="form-control" name="roomName" value="${room.roomName}" required>
                </div>

                <div class="form-group">
                    <label for="capacity">Capacity:</label>
                    <input type="number" class="form-control" name="capacity" value="${room.capacity}" required>
                </div>

                <div class="form-group">
                    <label for="screenType">Screen Type:</label>
                    <input type="text" class="form-control" name="screenType" value="${room.screenType}" required>
                </div>

                <div class="form-group">
                    <label>Select Room Types:</label><br>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="roomType" value="3D" id="roomType3D">
                        <label class="form-check-label" for="roomType3D">3D</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="roomType" value="IMAX" id="roomTypeIMAX">
                        <label class="form-check-label" for="roomTypeIMAX">IMAX</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="roomType" value="Standard" id="roomTypeStandard">
                        <label class="form-check-label" for="roomTypeStandard">Standard</label>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">Cập nhập phòng</button>
            </form>

            <script>
                function validateForm() {
                    // Lấy tất cả các checkbox có tên 'roomType'
                    const roomTypes = document.querySelectorAll('input[name="roomType"]:checked');
                    if (roomTypes.length === 0) {
                        alert("Please select at least one room type.");
                        return false; // Ngăn không cho form gửi đi
                    }
                    return true; // Cho phép form gửi đi
                }
            </script>
            <a class="btn btn-secondary mt-3" href="${pageContext.request.contextPath}/owner/room/manageRoom?cinemaID=${room.cinemaID}">Quay lại</a>
        </div>
    </body>
</html>
