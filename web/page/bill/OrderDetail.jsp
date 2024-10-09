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
    <!-- Giữ nguyên phần CSS -->
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
                <form action="<%= RouterURL.PAYMENT_VNPAY %>" method="get">
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
