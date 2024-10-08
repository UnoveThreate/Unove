<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận đặt vé</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #1c1c1c;
            color: white;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #2c2c2c;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(255,255,255,0.1);
        }
        h1, h2 {
            color: #ff4081;
            text-align: center;
        }
        .info-block {
            margin-bottom: 20px;
        }
        .info-block h3 {
            color: #ff4081;
            margin-bottom: 10px;
        }
        .info-item {
            margin-bottom: 5px;
        }
        .seat-list {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }
        .seat {
            background-color: #d42a87;
            color: white;
            padding: 5px 10px;
            border-radius: 5px;
        }
        .total-price {
            font-size: 24px;
            font-weight: bold;
            text-align: center;
            margin-top: 20px;
        }
        .button {
            display: block;
            width: 200px;
            margin: 20px auto;
            padding: 10px;
            background-color: #ff4081;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Xác nhận đặt vé</h1>
        
        <div class="info-block">
            <h3>Thông tin phim</h3>
            <div class="info-item">Tên phim: ${movieSlot.movie.title}</div>
            <div class="info-item">Suất chiếu: <fmt:formatDate value="${movieSlot.startTime}" pattern="dd/MM/yyyy HH:mm"/></div>
            <div class="info-item">Phòng chiếu: ${movieSlot.room.name}</div>
        </div>
        
        <div class="info-block">
            <h3>Ghế đã chọn</h3>
            <div class="seat-list">
                <c:forEach var="seat" items="${selectedSeats}">
                    <span class="seat">${seat.name}</span>
                </c:forEach>
            </div>
        </div>
        
        <div class="info-block">
            <h3>Thông tin đơn hàng</h3>
            <div class="info-item">Mã đơn hàng: ${order.orderID}</div>
            <div class="info-item">Thời gian đặt: <fmt:formatDate value="${order.timeCreated}" pattern="dd/MM/yyyy HH:mm:ss"/></div>
            <div class="info-item">Trạng thái: ${order.status}</div>
        </div>
        
        <div class="total-price">
            Tổng tiền: <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
        </div>
        
        <a href="${pageContext.request.contextPath}/home" class="button">Quay về trang chủ</a>
    </div>
</body>
</html>