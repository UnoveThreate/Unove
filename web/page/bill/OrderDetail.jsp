<%@page import="model.MovieSlot"%>
<%@page import="util.RouterURL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.BookingSession" %>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Ticket Details</title>
        <style>
            .container {
                max-width: 600px; /* Đặt chiều rộng tối đa */
                margin: 0 auto; /* Căn giữa */
                font-family: Arial, sans-serif; /* Phông chữ */
                border: 1px solid #ddd; /* Viền khung */
                border-radius: 10px; /* Bo góc */
                overflow: hidden; /* Ẩn các phần thừa */
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Đổ bóng */
            }

            .header {
                background-color: #333; /* Màu nền header */
                color: white; /* Màu chữ */
                text-align: center; /* Căn giữa */
                padding: 15px 0; /* Đệm trên dưới */
            }

            .header h1 {
                margin: 0; /* Không có khoảng cách */
                font-size: 1.8rem; /* Kích thước chữ tiêu đề */
            }

            .content {
                padding: 20px; /* Đệm cho nội dung */
                background-color: #f8f8f8; /* Màu nền nội dung */
            }

            .info-section {
                margin-bottom: 20px; /* Khoảng cách giữa các phần thông tin */
            }

            .info-section h2 {
                color: #d32f2f; /* Màu tiêu đề phần thông tin */
                font-size: 1.5rem; /* Kích thước chữ */
                margin-bottom: 10px; /* Khoảng cách dưới */
                border-bottom: 1px solid #d32f2f; /* Đường viền dưới */
                padding-bottom: 5px; /* Đệm dưới */
            }

            .info-block {
                display: flex; /* Sử dụng Flexbox */
                justify-content: space-between; /* Căn giữa nội dung */
                padding: 10px; /* Đệm cho mỗi khối thông tin */
                background-color: #fff; /* Màu nền trắng */
                margin-bottom: 10px; /* Khoảng cách giữa các khối thông tin */
                border: 1px solid #ddd; /* Đường viền */
                border-radius: 5px; /* Bo góc */
            }

            .info-block h4 {
                font-size: 1.1rem; /* Kích thước chữ tiêu đề khối */
                color: #333; /* Màu chữ */
            }

            .info-block p {
                font-size: 1rem; /* Kích thước chữ thông tin */
                color: #666; /* Màu chữ nhạt hơn */
            }

            .payment-section {
                text-align: center; /* Căn giữa cho phần thanh toán */
                margin-top: 20px; /* Khoảng cách trên */
            }

            .payment-button {
                display: inline-block; /* Hiển thị dưới dạng khối nội tuyến */
                background-color: #d32f2f; /* Màu nền nút */
                color: white; /* Màu chữ */
                padding: 10px 20px; /* Đệm cho nút */
                border-radius: 5px; /* Bo góc nút */
                text-decoration: none; /* Không có gạch chân */
                font-size: 1rem; /* Kích thước chữ */
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Đổ bóng */
                transition: background-color 0.3s; /* Hiệu ứng chuyển màu */
            }

            .payment-button:hover {
                background-color: #c12727; /* Màu khi hover */
            }

        </style>
    </head>
    <body>
        <jsp:include page=".././landingPage/Header.jsp" />
        <div class="container">
            <div class="header">
                <h1>Ticket Details</h1>
            </div>
            <div class="content">
                <div class="info-section">
                    
                    <h2>Movie Information</h2>
                   
                    <div class="info-block">
                        <h4>Movie Slot ID:</h4>
                        <p>${movieSlot.movieSlotID}</p>
                    </div>
                    <div class="info-block">
                        <h4>Room ID:</h4>
                        <p>${movieSlot.roomID}</p>
                    </div>
                    <div class="info-block">
                        <h4>Start Time:</h4>
                        <p>${movieSlot.startTime}</p>
                    </div>
                    <div class="info-block">
                        <h4>End Time:</h4>
                        <p>${movieSlot.endTime}</p>
                    </div>
                    <div class="info-block">
                        <h4>Type:</h4>
                        <p>${movieSlot.type}</p>
                    </div>
                    <div class="info-block">
                        <h4>Price:</h4>
                        <p>${movieSlot.price}</p>
                    </div>
                    <div class="info-block">
                        <h4>Discount:</h4>
                        <p>${movieSlot.discount}</p>
                    </div>
                    <div class="info-block">
                        <h4>Status:</h4>
                        <p>${movieSlot.status}</p>
                    </div>
                
                                         
                    <div class="info-block">
                        <h4>Cinema</h4>
                        <c:if test="${not empty cinema}">
                            <p>${cinema}</p>
                            <p><strong>Name:</strong> ${cinema.name}</p>
                            <p><strong>Address:</strong> ${cinema.address}</p>
                            <p><strong>Province:</strong> ${cinema.province}</p>
                            <p><strong>District:</strong> ${cinema.district}</p>
                            <p><strong>Commune:</strong> ${cinema.commune}</p>
                        </c:if>
                    </div>

                    

                </div>
            </div>
            <div class="payment-section">
                <a href="${RouterURL.PAYMENT_VNPAY}?userID=${bookingSession.userID}" class="btn btn-secondary">Continue Payment</a>
            </div>
        </div>
    </div>
</body>
</html>
