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
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                margin: 0;
                padding: 0;
                background: linear-gradient(135deg, #ffe6ef 0%, #ffd1dc 100%);
                color: #333;
                min-height: 100vh;
            }

            .ticket-container {
                max-width: 1200px;
                width: 95%;
                margin: 100px auto 40px;
                background-color: rgba(255, 255, 255, 0.95);
                border-radius: 20px;
                overflow: hidden;
                backdrop-filter: blur(10px);
                transition: all 5s ease;
            }

            .ticket-container:hover {
                transform: translateY(-5px);
                box-shadow: 0 30px 60px rgba(0, 0, 0, 0.2);
            }

            .ticket-header {
                background: linear-gradient(90deg, #ff8fab 0%, #ff5d8f 100%);
                color: #ffffff;
                text-align: center;
                padding: 30px;
                font-size: 32px;
                font-weight: 700;
                letter-spacing: 2px;
                text-transform: uppercase;
                position: relative;
                overflow: hidden;
            }

            .ticket-header::after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;
                height: 4px;
                background: linear-gradient(90deg, transparent, #fff, transparent);
                animation: shimmer 5s infinite;
            }

            @keyframes shimmer {
                0% {
                    transform: translateX(-100%);
                }
                100% {
                    transform: translateX(100%);
                }
            }

            .ticket-content {
                display: flex;
                flex-wrap: wrap;
                padding: 40px;
                gap: 40px;
            }

            .info-section, .qr-section {
                padding: 30px;
                background-color: #ffffff;
                border-radius: 15px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
                transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
            }

            .info-section {
                flex: 2;
                border: 2px solid #ff8fab;
            }

            .info-section:hover, .qr-section:hover {
                transform: translateY(-5px);
                box-shadow: 0 15px 40px rgba(0, 0, 0, 0.12);
            }

            .movie-title h2 {
                color: #ff5d8f;
                margin-top: 0;
                font-size: 28px;
                border-bottom: 3px solid #ff5d8f;
                padding-bottom: 15px;
                margin-bottom: 30px;
                position: relative;
            }

            .movie-title h2::after {
                content: '';
                position: absolute;
                bottom: -3px;
                left: 0;
                width: 50px;
                height: 3px;
                background: #ff5d8f;
                animation: widthAnim 2s ease-in-out infinite;
            }

            @keyframes widthAnim {
                0% {
                    width: 50px;
                }
                50% {
                    width: 100px;
                }
                100% {
                    width: 50px;
                }
            }

            .info-block {
                margin-bottom: 25px;
                position: relative;
                padding-left: 40px;
                transition: all 0.3s ease;

            }

            .info-block:hover {
                transform: translateX(10px);
            }

            .info-block h4 {
                font-size: 18px;
                color: #ff5d8f;
                margin: 0 0 10px 0;
                font-weight: 600;
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .info-block i {
                position: absolute;
                left: 5px;
                top: 3px;
                font-size: 20px;
                color: #ff5d8f;
                transition: all 0.3s ease;
            }

            .info-block:hover i {
                transform: scale(1.2);
            }

            .info-block p {

                margin: 0;
                padding: 0;
            }

            .info-value {
                background-color: #fff0f5;
                padding: 8px 15px;
                border-radius: 20px;
                display: inline-block;
                transition: all 0.3s ease;
                border: 3px solid #ff8fab;
            }

            .info-value:hover {
                background-color: #ff5d8f;
                color: #ffffff;
                transform: translateY(-3px);
                box-shadow: 0 5px 15px rgba(255, 93, 143, 0.3);
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
                background-color: #fff0f5;
                padding: 8px 15px;
                border-radius: 20px;
                display: inline-block;
                transition: all 0.3s ease;
                border: 1px solid #ffd1dc;
                border: 2px solid #ff8fab;
            }

            .info-block ul li:hover {
                background-color: #ffe2eb;
                color: #555;
                transform: translateY(-3px);

            }

            .payment-button {
                display: block;
                width: 100%;
                padding: 15px;
                background: linear-gradient(45deg, #ff5d8f, #ff8fab);
                color: #ffffff;
                text-align: center;
                font-size: 18px;
                font-weight: 600;
                border: none;
                border-radius: 10px;
                cursor: pointer;
                transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
                text-transform: uppercase;
                letter-spacing: 1px;
                margin-top: 30px;
                position: relative;
                overflow: hidden;
            }

            .payment-button:hover {
                transform: translateY(-3px);
                box-shadow: 0 10px 20px rgba(255, 93, 143, 0.3);
            }

            .payment-button::after {
                content: '';
                position: absolute;
                top: -50%;
                left: -50%;
                width: 200%;
                height: 200%;
                background: linear-gradient(45deg, transparent, rgba(255,255,255,0.3), transparent);
                transform: rotate(45deg);
                animation: buttonShine 0.5s infinite;
            }

            @keyframes buttonShine {
                0% {
                    transform: translateX(-100%) rotate(45deg);
                }
                100% {
                    transform: translateX(100%) rotate(45deg);
                }
            }

            .qr-section {
                flex: 1;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                text-align: center;
                position: relative;
                border: 2px solid #ff5d8f;
            }

            .qr-section::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                border-radius: 15px;
/*                animation: borderDash 20s linear infinite;*/
                pointer-events: none;
            }

            

            .qr-section h4 {
                font-size: 20px;
                color: #ff5d8f;
                margin: 0 0 20px 0;
                font-weight: 600;
            }

            .qr-section img {
                max-width: 200px;
                border-radius: 15px;
                margin-bottom: 20px;
                transition: all 0.5s ease;
                border: 4px solid #fff;
                border: 3px dashed #ff8fab;
                padding: 10px;
            }

            .qr-section img:hover {
                transform: scale(1.05) rotate(2deg);
                box-shadow: 0 15px 30px rgba(255, 93, 143, 0.2);
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
                    margin: 80px auto 20px;
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
                    border-bottom: 2px dashed #ff5d8f;
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
            .price-tag {
                background-color: #fff0f5;
                padding: 8px 15px;
                border-radius: 20px;
                display: inline-block;
                transition: all 0.3s ease;
                border: 3px dashed #ff8fab;
                font-weight: 600;
                color: #ff5d8f;
            }

            .price-tag:hover {
                background-color: #ff5d8f;
                color: #ffffff;
                transform: translateY(-3px);
                box-shadow: 0 5px 15px rgba(255, 93, 143, 0.3);
            }
            .breadcrumb-nav {
                background-color: transparent;
                padding: 15px 0;
                margin: 20px auto;
                max-width: 1200px;
                width: 95%;
            }

            .breadcrumb {
                margin-bottom: 0;
                padding: 15px 30px;
                background: rgba(255, 255, 255, 0.95);
                border-radius: 20px;
                backdrop-filter: blur(10px);
            }

            .breadcrumb-item {
                color: #ff5d8f;
            }

            .breadcrumb-item a {
                color: #7E60BF;
                text-decoration: none;
                transition: all 0.3s ease;
                font-weight: 500;
            }

            .breadcrumb-item a:hover {
                color: #d9c6e7;
            }

            .breadcrumb-item.active {
                color: #666;
            }

            .breadcrumb-item + .breadcrumb-item::before {
                content: "\276f" !important;
                color: #b2b2b2 !important;
            }


            @media (max-width: 768px) {
                .breadcrumb-nav {
                    padding: 10px;
                    margin: 10px;
                }

                .breadcrumb {
                    padding: 10px 15px;
                    font-size: 14px;
                }
            }
        </style>
    </head>
    <body>
        <jsp:include page="../landingPage/Header.jsp" />

        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb" class="breadcrumb-nav">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Trang chủ</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/showtimes">Lịch chiếu phim</a></li>
                <li class="breadcrumb-item"><a href="#" onclick="history.back(); return false;">Chọn ghế</a></li>
                <li class="breadcrumb-item"><a href="#" onclick="history.back(); return false;">Combo - Bắp nước</a></li>
                <li class="breadcrumb-item active" aria-current="page">Chi tiết vé</li>
            </ol>
        </nav>

        <div class="ticket-container" data-aos="fade-up" data-aos-duration="1000">
            <div class="ticket-header" data-aos="fade-down" data-aos-duration="1000">Chi tiết vé</div>
            <div class="ticket-content">
                <!-- Movie Info Section -->
                <div class="info-section" data-aos="fade-right" data-aos-delay="200" data-aos-duration="1000">
                    <div class="movie-title">
                        <h2 data-aos="fade-up" data-aos-delay="300">
                            <c:out value="${movie.title}" default="Chưa có thông tin phim"/>
                        </h2>
                    </div>

                    <div class="info-block" data-aos="fade-up" data-aos-delay="400">
                        <h4><i class="fas fa-clock"></i> Suất chiếu:</h4>
                        <ul>
                            <li>
                                <c:if test="${not empty movieSlot}">
                                    <fmt:formatDate value="${movieSlot.startTime}" pattern="HH:mm" /> - 
                                    <fmt:formatDate value="${movieSlot.endTime}" pattern="HH:mm" />
                                </c:if>
                                <c:if test="${empty movieSlot}">Chưa có thông tin</c:if>
                                </li>
                            </ul>
                        </div>

                        <div class="info-block" data-aos="fade-up" data-aos-delay="450">
                            <h4><i class="fas fa-calendar-alt"></i> Ngày Chiếu:</h4>
                            <ul>
                                <li>
                                <c:if test="${not empty movieSlot}">
                                    <fmt:formatDate value="${movieSlot.startTime}" pattern="dd/MM/yyyy" />
                                </c:if>
                                <c:if test="${empty movieSlot}">Chưa có thông tin</c:if>
                                </li>
                            </ul>
                        </div>


                        <div class="info-block" data-aos="fade-up" data-aos-delay="500">
                            <h4><i class="fas fa-film"></i> Rạp:</h4>
                            <ul>
                                <li><c:out value="${cinema.name}" default="Chưa có thông tin rạp"/></li>
                        </ul>
                    </div>


                    <div class="info-block" data-aos="fade-up" data-aos-delay="550">
                        <h4><i class="fas fa-map-marker-alt"></i> Địa chỉ:</h4>
                        <ul>
                            <li>
                                <c:out value="${cinema.address}" default=""/>
                                <c:if test="${not empty cinema.address}">, </c:if>
                                <c:out value="${cinema.province}" default=""/>
                                <c:if test="${not empty cinema.province}">, </c:if>
                                <c:out value="${cinema.district}" default=""/>
                                <c:if test="${not empty cinema.district}">, </c:if>
                                <c:out value="${cinema.commune}" default=""/>
                            </li>
                        </ul>
                    </div>

                    <div class="info-block" data-aos="fade-up" data-aos-delay="600">
                        <h4><i class="fas fa-door-open"></i> Phòng Chiếu:</h4>
                        <ul>
                            <li><c:out value="${movieSlot.roomID}" default="Chưa có thông tin"/></li>
                        </ul>
                    </div>

                    <div class="info-block" data-aos="fade-up" data-aos-delay="650">
                        <h4><i class="fas fa-video"></i> Định Dạng:</h4>
                        <ul>
                            <li><c:out value="${movieSlot.type}" default="Chưa có thông tin"/></li>
                        </ul>
                    </div>

                    <div class="info-block" data-aos="fade-up" data-aos-delay="700">
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

                    <div class="info-block" data-aos="fade-up" data-aos-delay="200">
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

                    <div class="info-block" data-aos="fade-up" data-aos-delay="200">
                        <h4><i class="fas fa-money-bill-wave"></i> Tạm Tính:</h4>
                        <ul>
                            <li>
                                <fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="đ" maxFractionDigits="0"/>
                            </li>
                        </ul>
                    </div>

                    <form action="<%= RouterURL.PAYMENT_VNPAY%>" method="get">
                        <button type="submit" class="payment-button" data-aos="zoom-in" data-aos-delay="800">
                            Tiếp tục thanh toán
                        </button>
                    </form>
                </div>

                <!-- QR Code Section -->
                <div class="qr-section" data-aos="fade-left" data-aos-delay="200">
                    <h4 data-aos="fade-up" data-aos-delay="200">Quét mã QR bằng VNPay để thanh toán</h4>
                    <img src="https://jeju.com.vn/wp-content/uploads/2020/05/vnpay-qr-23-06-2020-2.jpg" 
                         alt="QR Code VNPay" 
                         data-aos="zoom-in" 
                         data-aos-delay="200">
                    <p data-aos="fade-up" data-aos-delay="200">
                        Sử dụng ứng dụng VNPay hoặc ứng dụng Camera hỗ trợ QR code để quét mã.
                    </p>
                </div>
            </div>
        </div>

        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>
                    AOS.init({
                        duration: 400,
                        easing: 'ease-in-out',
                        once: true,
                        mirror: false,
                        anchorPlacement: 'top-bottom',
                        offset: 20
                    });
        </script>
    </body>
</html>