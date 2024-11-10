<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lịch Sử Đơn Hàng</title>
        <link rel="stylesheet" href="path/to/your/styles.css"> <!-- Update if needed -->
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #eef2f5;
                color: #444;
                margin: 0;
                padding: 0;
            }
            h1 {
                text-align: center;
                color: #333;
                font-size: 2em;
                margin-top: 20px;
            }
            .container {
                max-width: 1000px;
                margin: 30px auto;
                padding: 25px;
                background-color: #fff;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 25px;
            }
            th, td {
                padding: 15px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            th {
                background-color: #00796b;
                color: #fff;
                font-size: 1em;
                font-weight: 600;
            }
            tr:hover {
                background-color: #f1f1f1;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            .canteen-items {
                padding: 0;
                margin: 0;
                list-style-type: none;
            }
            .canteen-items li {
                padding: 5px 0;
            }
            .empty-message {
                text-align: center;
                font-size: 1.2em;
                color: #888;
                margin: 30px 0;
            }
            .back-button {
                display: inline-block;
                padding: 12px 30px;
                font-size: 1em;
                color: #fff;
                background-color: #00796b;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-weight: bold;
                transition: background-color 0.3s ease, transform 0.2s ease;
            }
            .back-button:hover {
                background-color: #004d40;
                transform: scale(1.05);
            }
            .back-button:active {
                background-color: #00352c;
                transform: scale(0.97);
            }
            .detail-link {
                color: #00796b;
                font-weight: bold;
                text-decoration: none;
                transition: color 0.3s;
            }
            .detail-link:hover {
                color: #004d40;
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
                            <th>Movie Slot ID</th>
                            <th>Movie Title</th>
                            <th>Show Date</th>
                            <th>Show Time</th>
                            <th>Total Price</th>
                            <th>Canteen Items</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${historyOrders}">
                            <tr>
                                <td>${order.MovieSlotID}</td>
                                <td>${order.MovieTitle}</td>
                                <td><fmt:formatDate value="${order.ShowTime}" pattern="yyyy-MM-dd" /></td>
                                <td><fmt:formatDate value="${order.ShowTime}" pattern="HH:mm" /></td>
                                <td>${order.TotalPrice}</td>
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
                                <td><a href="OrderHistoryDetailServlet?orderID=${order.OrderID}" class="detail-link">Xem chi tiết</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty historyOrders}">
                <p class="empty-message">Không có lịch sử đơn hàng nào để hiển thị.</p>
            </c:if>

            <div style="text-align: center; margin-top: 30px;">
                <form action="${pageContext.request.contextPath}/history" method="post">
                    <button type="submit" name="action" value="back" class="back-button">Trở về trang chủ</button>
                </form>
            </div>
        </div>
    </body>
</html>
