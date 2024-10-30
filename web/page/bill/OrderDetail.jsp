<%@page import="model.Cinema"%>
<%@page import="model.Movie"%>
<%@page import="model.MovieSlot"%>
<%@page import="util.RouterURL"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết vé - UNOVE Cinema</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                margin: 0;
                padding: 0;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: #333;
            }

            .ticket-container {
                max-width: 1200px;
                width: 95%;
                margin: 100px auto 40px; /* Tăng margin-top để tránh bị che bởi header */
                background-color: rgba(255, 255, 255, 0.95);
                border-radius: 20px;
                box-shadow: 0 25px 50px rgba(0, 0, 0, 0.3);
                overflow: hidden;
                backdrop-filter: blur(10px);
            }

            .ticket-header {
                background: linear-gradient(90deg, #4a00e0 0%, #8e2de2 100%);
                color: #ffffff;
                text-align: center;
                padding: 30px;
                font-size: 32px;
                font-weight: 700;
                letter-spacing: 2px;
                text-transform: uppercase;
            }

            .ticket-content {
                display: flex;
                flex-wrap: wrap;
                padding: 40px;
            }

            .info-section, .qr-section {
                padding: 30px;
                background-color: #ffffff;
                border-radius: 15px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
                transition: all 0.3s ease;
            }

            .info-section {
                flex: 2;
                margin-right: 40px;
            }

            .info-section:hover, .qr-section:hover {
                transform: translateY(-5px);
                box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
            }

            .movie-title h2 {
                color: #4a00e0;
                margin-top: 0;
                font-size: 28px;
                border-bottom: 3px solid #4a00e0;
                padding-bottom: 15px;
                margin-bottom: 30px;
            }

            .info-block {
                margin-bottom: 25px;
                position: relative;
                padding-left: 40px;
            }

            .info-block h4 {
                font-size: 18px;
                color: #4a00e0;
                margin: 0 0 10px 0;
                font-weight: 600;
            }

            .info-block::before {
                content: '';
                position: absolute;
                left: 0;
                top: 5px;
                width: 25px;
                height: 25px;
                background-color: #4a00e0;
                border-radius: 50%;
                opacity: 0.2;
            }

            .info-block i {
                position: absolute;
                left: 5px;
                top: 10px;
                color: #4a00e0;
            }

            .info-block p, .info-block ul {
                font-size: 16px;
                color: #555;
                margin: 0;
                line-height: 1.6;
            }

            .info-block ul {
                list-style-type: none;
                padding: 0;
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
            }

            .info-block ul li {
                background-color: #e8f0fe;
                padding: 5px 15px;
                border-radius: 20px;
                display: inline-block;
                transition: all 0.3s ease;
            }

            .info-block ul li:hover {
                background-color: #4a00e0;
                color: #ffffff;
            }

            .payment-button {
                display: block;
                width: 100%;
                padding: 15px;
                background-color: #4a00e0;
                color: #ffffff;
                text-align: center;
                font-size: 18px;
                font-weight: 600;
                border: none;
                border-radius: 10px;
                cursor: pointer;
                transition: all 0.3s ease;
                text-transform: uppercase;
                letter-spacing: 1px;
                margin-top: 30px;
            }

            .payment-button:hover {
                background-color: #3c00b7;
                transform: translateY(-3px);
                box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
            }

            .qr-section {
                flex: 1;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                text-align: center;
            }

            .qr-section h4 {
                font-size: 20px;
                color: #4a00e0;
                margin: 0 0 20px 0;
                font-weight: 600;
            }

            .qr-section img {
                max-width: 200px;
                border-radius: 15px;
                box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
                transition: transform 0.3s ease;
            }

            .qr-section img:hover {
                transform: scale(1.05);
            }

            .qr-section p {
                font-size: 14px;
                color: #666;
                max-width: 250px;
                line-height: 1.6;
            }

            @media (max-width: 1024px) {
                .ticket-content {
                    flex-direction: column;
                }
                .info-section, .qr-section {
                    width: 100%;
                    margin-right: 0;
                    margin-bottom: 30px;
                }
            }

            @media (max-width: 768px) {
                .ticket-container {
                    width: 90%;
                    margin: 80px auto 20px; /* Điều chỉnh margin-top cho màn hình nhỏ */
                }
                .ticket-header {
                    font-size: 24px;
                    padding: 20px;
                }
                .ticket-content {
                    padding: 20px;
                }
                .info-section, .qr-section {
                    padding: 20px;
                }
                .movie-title h2 {
                    font-size: 22px;
                }
                .info-block h4 {
                    font-size: 16px;
                }
                .info-block p, .info-block ul {
                    font-size: 14px;
                }
                .payment-button {
                    font-size: 16px;
                    padding: 12px;
                }
            }
        </style>
    </head>
    <body>
        <jsp:include page="../landingPage/Header.jsp" />

        <div class="ticket-container">
            <div class="ticket-header">Chi tiết vé</div>
            <div class="ticket-content">
                <!-- Movie Info Section -->
                <div class="info-section">
                    <div class="movie-title">
                        <h2><c:out value="${movie.title}" default="Chưa có thông tin phim"/></h2>
                    </div>
                    <div class="info-block">
                        <h4><i class="fas fa-clock"></i> Thời Gian:</h4>
                        <p>
                            <c:if test="${not empty movieSlot}">
                                <fmt:formatDate value="${movieSlot.startTime}" pattern="HH:mm" /> - 
                                <fmt:formatDate value="${movieSlot.endTime}" pattern="HH:mm" />
                            </c:if>
                            <c:if test="${empty movieSlot}">Chưa có thông tin</c:if>
                            </p>
                        </div>
                        <div class="info-block">
                            <h4><i class="fas fa-calendar-alt"></i> Ngày Chiếu:</h4>
                            <p>
                            <c:if test="${not empty movieSlot}">
                                <fmt:formatDate value="${movieSlot.startTime}" pattern="dd/MM/yyyy" />
                            </c:if>
                            <c:if test="${empty movieSlot}">Chưa có thông tin</c:if>
                            </p>
                        </div>
                        <div class="info-block">
                            <h4><i class="fas fa-film"></i> Rạp:</h4>
                            <p><c:out value="${cinema.name}" default="Chưa có thông tin rạp"/></p>
                    </div>
                    <div class="info-block">
                        <h4><i class="fas fa-map-marker-alt"></i> Địa chỉ:</h4>
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
                        <h4><i class="fas fa-door-open"></i> Phòng Chiếu:</h4>
                        <p><c:out value="${movieSlot.roomID}" default="Chưa có thông tin"/></p>
                    </div>
                    <div class="info-block">
                        <h4><i class="fas fa-video"></i> Định Dạng:</h4>
                        <p><c:out value="${movieSlot.type}" default="Chưa có thông tin"/></p>
                    </div>
                    <div class="info-block">
                        <h4><i class="fas fa-couch"></i> Ghế:</h4>
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
                    <!-- Existing content... -->

                    <div class="info-block">
                        <h4><i class="fas fa-utensils"></i> Đồ ăn và nước uống:</h4>
                        <ul>
                            <c:choose>
                                <c:when test="${not empty selectedCanteenItems}">
                                    <c:forEach var="item" items="${selectedCanteenItems}">
                                        <li>
                                            <c:out value="${item.name}" /> - 
                                            Số lượng: <c:out value="${item.quantity}" /> 
                                            
                                          
                                        </li>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <li>Chưa có đồ ăn và nước uống</li>
                                    </c:otherwise>
                                </c:choose>
                        </ul>
                    </div>

                    <!-- Existing content... -->

                    <div class="info-block">
                        <h4><i class="fas fa-money-bill-wave"></i> Tạm Tính:</h4>
                        <p><fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></p>
                    </div>
                    <form action="<%= RouterURL.PAYMENT_VNPAY%>" method="get">
                        <button type="submit" class="payment-button">Tiếp tục thanh toán</button>
                    </form>
                </div>
                <!-- QR Code Section -->
                <div class="qr-section">
                    <h4>Quét mã QR bằng VNPay để thanh toán</h4>
                    <img src="https://jeju.com.vn/wp-content/uploads/2020/05/vnpay-qr-23-06-2020-2.jpg" alt="QR Code VNPay">
                    <p>Sử dụng ứng dụng VNPay hoặc ứng dụng Camera hỗ trợ QR code để quét mã.</p>
                </div>
            </div>
        </div>
    </body>
</html>