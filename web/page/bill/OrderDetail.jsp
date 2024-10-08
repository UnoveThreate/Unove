<!DOCTYPE html>
<html lang="en">
    <head>
        <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <jsp:include page="../landingPage/Header.jsp" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Ticket Details</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 800px;
                margin: 20px auto;
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                overflow: hidden;
            }

            .header {
                background-color: #d32f2f;
                color: white;
                text-align: center;
                padding: 15px 0;
                font-size: 1.8rem;
            }

            .content {
                display: flex;
                padding: 20px;
                align-items: flex-start;
            }

            .info-section {
                width: 60%;
                margin-right: 20px;
            }

            .qr-section {
                width: 40%;
                text-align: center;
                background-color: #f8f8f8;
                padding: 20px;
                border-left: 1px solid #ddd;
            }

            .info-block {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 0;
                border-bottom: 1px solid #ddd;
            }

            .info-block p, .info-block h4 {
                margin: 0;
                color: #333;
            }

            .info-block h4 {
                font-size: 1rem;
                font-weight: bold;
            }

            .info-block p {
                font-size: 1rem;
                text-align: right;
                color: #666;
            }

            .movie-title {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
            }

            .movie-title .badge {
                background-color: #d32f2f;
                color: #fff;
                padding: 5px 10px;
                border-radius: 5px;
                font-size: 0.9rem;
                margin-right: 10px;
            }

            .movie-title h2 {
                margin: 0;
                font-size: 1.5rem;
                color: #d32f2f;
            }

            .qr-section img {
                max-width: 100%;
                margin-top: 20px;
            }

            .qr-section p {
                font-size: 0.9rem;
                color: #666;
                margin-top: 10px;
            }

            .payment-button {
                display: block;
                background-color: #d32f2f;
                color: #fff;
                padding: 10px 20px;
                border-radius: 5px;
                text-decoration: none;
                text-align: center;
                font-size: 1rem;
                margin-top: 20px;
                transition: background-color 0.3s ease;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }

            .payment-button:hover {
                background-color: #b71c1c;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">Ticket Details</div>
            <div class="content">
                <!-- Movie Info Section -->
                <div class="info-section">
                    <div class="movie-title">

                        <h2>${movie.title}</h2>
                    </div>
                    <div class="info-block">
                        <h4>Thời Gian:</h4>
                        <p>${movieSlot.startTime} ~ ${movieSlot.endTime}</p>
                    </div>
                    <div class="info-block">
                        <h4>Ngày Chiếu:</h4>
                        <p>${movie.datePublished}</p>
                    </div>
                    <div class="info-block">
                        <h4>Rạp:</h4>
                        <p>${cinema.name}</p>
                    </div>
                    <div class="info-block">
                        <h4>Địa chỉ:</h4>
                        <p>${cinema.address}, ${cinema.province}, ${cinema.district},${cinema.commune}</p>
                    </div>
                    <div class="info-block">
                        <h4>Phòng Chiếu:</h4>
                        <p>03</p>
                    </div>
                    <div class="info-block">
                        <h4>Định Dạng:</h4>
                        <p>${movieSlot.type}</p>
                    </div>
                    <div class="info-block">
                        <h4>Ghế:</h4>
                        <p>F06</p>
                    </div>
                    <div class="info-block">
                        <h4>Tạm Tính:</h4>
                        <p>${movieSlot.price}</p>
                    </div>
                  <a href="${RouterURL.PAYMENT_VNPAY}?userID=${bookingSession.userID}" class="payment-button">Tiếp tục thanh toán</a>


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
