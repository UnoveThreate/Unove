<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch Sử Đơn Hàng</title>
    <link rel="stylesheet" href="path/to/your/styles.css"> <!-- Thay đổi đường dẫn nếu cần -->
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        .container {
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .canteen-items {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }
        .canteen-items li {
            margin-bottom: 5px;
        }
        .empty-message {
            text-align: center;
            color: #888;
            margin-top: 20px;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #4CAF50;
            text-decoration: none;
            font-weight: bold;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Lịch Sử Đơn Hàng</h1>

        <c:if test="${not empty historyOrders}">
            <table>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Movie Slot ID</th>
                        <th>Movie Title</th>
                        <th>Show Time</th>
                        <th>Total Price</th>
                        <th>Status</th>
                        <th>Canteen Items</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${historyOrders}">
                        <tr>
                            <td>${order.OrderID}</td>
                            <td>${order.MovieSlotID}</td>
                            <td>${order.MovieTitle}</td>
                            <td>${order.ShowTime}</td>
                            <td>${order.TotalPrice}</td>
                            <td>${order.Status}</td>
                            <td>
                                <c:if test="${not empty order.CanteenItems}">
                                    <ul class="canteen-items">
                                        <c:forEach var="canteenItem" items="${order.CanteenItems}">
                                            <li>${canteenItem.CanteenItemName} - Số lượng: ${canteenItem.Quantity}</li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                                <c:if test="${empty order.CanteenItems}">
                                    Không có mục canteen nào
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty historyOrders}">
            <p class="empty-message">Không có lịch sử đơn hàng nào để hiển thị.</p>
        </c:if>

        <a href="your_home_page.jsp" class="back-link">Trở về trang chủ</a> <!-- Thay đổi đường dẫn nếu cần -->
    </div>
</body>
</html>
