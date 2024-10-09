<%@page import="model.Cinema"%>
<%@page import="model.Movie"%>
<%@page import="model.MovieSlot"%>
<%@page import="util.RouterURL"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <jsp:include page="../landingPage/Header.jsp" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết vé</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
            }

            .container {
                max-width: 800px;
                margin: auto;
                padding: 20px;
                background-color: #ffffff;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .header {
                text-align: center;
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
                color: #333;
            }

            .content {
                display: flex;
                border: 1px solid #ddd;
                border-radius: 10px;
                overflow: hidden;
            }

            .info-section, .qr-section {
                width: 50%;
                padding: 20px;
            }

            .info-section {
                background-color: #f7f7f7;
            }

            .info-block {
                margin-bottom: 15px;
            }

            .info-block h4 {
                font-size: 14px;
                font-weight: bold;
                color: #333;
                margin: 0;
            }

            .info-block p, .info-block ul {
                font-size: 14px;
                color: #555;
                margin: 5px 0 0;
            }

            .info-block ul {
                list-style-type: none;
                padding: 0;
            }

            .payment-button {
                display: inline-block;
                padding: 10px;
                background-color: #ff6b6b;
                color: #ffffff;
                text-align: center;
                font-size: 16px;
                font-weight: bold;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                width: 100%;
                margin-top: 20px;
            }

            .payment-button:hover {
                background-color: #ff4b4b;
            }

            .qr-section {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                background-color: #fff0f7;
            }

            .qr-section h4 {
                font-size: 16px;
                font-weight: bold;
                color: #900C3F;
                margin: 10px 0;
            }

            .qr-section img {
                max-width: 150px;
                border: 5px solid #fff;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                margin-bottom: 10px;
            }

            .qr-section p {
                font-size: 12px;
                color: #666;
                text-align: center;
                margin: 0;
            }

        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">Chi tiết vé</div>
            <div class="content">
                <!-- Movie Info Section -->
                <div class="info-section">
                    <div class="movie-title">
                        <h2><c:out value="${movie.title}" default="Chưa có thông tin phim"/></h2>
                    </div>
                    <div class="info-block">
                        <h4>Thời Gian:</h4>
                        <p>
                            <c:if test="${not empty movieSlot}">
                                <fmt:formatDate value="${movieSlot.startTime}" pattern="HH:mm" /> - 
                                <fmt:formatDate value="${movieSlot.endTime}" pattern="HH:mm" />
                            </c:if>
                            <c:if test="${empty movieSlot}">Chưa có thông tin</c:if>
                            </p>
                        </div>
                        <div class="info-block">
                            <h4>Ngày Chiếu:</h4>
                            <p>
                            <c:if test="${not empty movieSlot}">
                                <fmt:formatDate value="${movieSlot.startTime}" pattern="dd/MM/yyyy" />
                            </c:if>
                            <c:if test="${empty movieSlot}">Chưa có thông tin</c:if>
                            </p>
                        </div>
                        <div class="info-block">
                            <h4>Rạp:</h4>
                            <p><c:out value="${cinema.name}" default="Chưa có thông tin rạp"/></p>
                    </div>
                    <div class="info-block">
                        <h4>Địa chỉ:</h4>
                        <p>
                            <c:out value="${cinema.address}" default=""/>
                            <c:if test="${not empty cinema.address}">, </c:if>
                            <c:out value="${cinema.province}" default=""/>
                            <c:if test="${not empty cinema.province}">, </c:if>
                            <c:out value="${cinema.district}" default=""/>
                            <c:if test="${not empty cinema.district}">, </c:if>
                            <c:out value="${cinema.commune}" default=""/>
                        </p>
                    </div>
                    <div class="info-block">
                        <h4>Phòng Chiếu:</h4>
                        <p><c:out value="${movieSlot.roomID}" default="Chưa có thông tin"/></p>
                    </div>
                    <div class="info-block">
                        <h4>Định Dạng:</h4>
                        <p><c:out value="${movieSlot.type}" default="Chưa có thông tin"/></p>
                    </div>
                    <div class="info-block">
                        <h4>Ghế:</h4>
                        <ul>
                            <c:choose>
                                <c:when test="${not empty selectedSeats}">
                                    <c:forEach var="seat" items="${selectedSeats}">
                                        <li>${seat.name}</li>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                    <li>Chưa chọn ghế</li>
                                    </c:otherwise>
                                </c:choose>
                        </ul>
                    </div>
                    <div class="info-block">
                        <h4>Tạm Tính:</h4>
                        <p><fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></p>
                    </div>
                    <form action="<%= RouterURL.PAYMENT_VNPAY%>" method="get">
                        <button type="submit" class="payment-button">Tiếp tục thanh toán</button>
                    </form>
                </div>
                <!-- QR Code Section -->
                <div class="qr-section">
                    <h4>Quét mã QR bằng Vnpay để thanh toán</h4>
                    <img src="https://jeju.com.vn/wp-content/uploads/2020/05/vnpay-qr-23-06-2020-2.jpg" alt="QR Code">
                    <p>Sử dụng App VnPay hoặc ứng dụng Camera hỗ trợ QR code để quét mã.</p>
                </div>
            </div>
        </div>
    </body>
</html>
