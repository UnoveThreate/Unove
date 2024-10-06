<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Ticket Details</title>
        <style>
            * {
                box-sizing: border-box;
            }

            body {
                font-family: Arial, sans-serif;
                background-color: #f3f4f6;
                margin: 0;
                padding: 20px;
            }

            .container {
                max-width: 600px;
                margin: 0 auto;
                background-color: #fff;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }

            .header {
                text-align: center;
                margin-bottom: 20px;
            }

            .header h1 {
                font-size: 24px;
                color: #333;
            }

            .info-section {
                margin-bottom: 20px;
                border-bottom: 1px solid #ddd;
                padding-bottom: 15px;
            }

            .info-section h2 {
                font-size: 20px;
                color: #333;
                margin-bottom: 10px;
            }

            .wrapper-movie_infor {
                display: grid;
                grid-template-columns: repeat(2, 1fr);
                gap: 15px;
            }

            .info-block {
                padding: 10px;
                background-color: #f9f9f9;
                border-radius: 5px;
            }

            .info-block h4 {
                font-size: 16px;
                margin: 0;
                color: #555;
            }

            .info-block p {
                margin: 5px 0;
                color: #333;
            }

            .canteen-items {
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
            }

            .canteen-item {
                display: flex;
                align-items: center;
                background-color: #f9f9f9;
                border-radius: 5px;
                padding: 10px;
                box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
                flex: 1 1 calc(50% - 15px); /* Adjust width based on available space */
            }

            .canteen-item img {
                width: 50px;
                height: 50px;
                border-radius: 5px;
                margin-right: 10px;
            }

            .item-details {
                display: flex;
                flex-direction: column;
            }

            .item-name {
                font-size: 16px;
                font-weight: bold;
            }

            .item-amount {
                color: #777;
            }

            .item-price {
                font-size: 14px;
                color: #333;
            }

            .seats {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
            }

            .seat {
                background-color: #e0e0e0;
                padding: 5px 10px;
                border-radius: 5px;
                color: #333;
            }

            .qr-code {
                text-align: center;
            }

            .qr-code img {
                margin-top: 10px;
            }

            .payment-button {
                background-color: #28a745;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
                text-align: center;
                display: block;
                width: 100%;
                text-decoration: none;
                margin-top: 20px;
            }

            .payment-button:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>
        
        <div class="container">
            <div class="header">
                <h1>Ticket Details</h1>
            </div>
            <div class="content">
                <div class="info-section">
                    <h2>Movie Information</h2>
                    <div class="wrapper-movie_infor">
                        <div class="info-block">
                            <h4>Title</h4>
                            <p>${movieInfor.getTitle()}</p>
                        </div>
                        <div class="info-block">
                            <h4>Cinema Chain</h4>
                            <p>${cinemaChain.getName()}</p>
                        </div>
                        <div class="info-block">
                            <h4>Address</h4>
                            <p>${cinema.getAddress()}</p>
                        </div>
                        <div class="info-block">
                            <h4>Province</h4>
                            <p>${cinema.getProvince()}</p>
                        </div>
                    </div>
                </div>

                <div class="info-section">
                    <h2>Canteen Items</h2>
                    <div class="canteen-items">
                        <c:forEach var="item" items="${canteenItems}">
                            <div class="canteen-item">
                                <img src="${item.getImage()}" alt="${item.getName()}"/>
                                <div class="item-details">
                                    <span class="item-name">${item.getName()}</span>
                                    <span class="item-amount">x${item.getAmount()}</span>
                                    <span class="item-price">${item.getPrice()} VND</span> <!-- Giá tiền -->
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="info-section mt-3">
                    <h2>QRCode</h2>
                    <div class="qr-code">
                        <img src="${order.QRCodeURL}" alt='QR Code' style='height: 150px; width: 150px'/>
                    </div>
                </div>

                <div class="info-section">
                    <h2>Seats</h2>
                    <div class="seats">
                        <c:forEach var="seat" items="${seats}">
                            <div class="seat">
                                ${seat.getName()} (Row: ${seat.getX()}, Seat: ${seat.getY()})
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <a href="/Unove/payment" class="payment-button">Continue to Payment</a> <!-- Nút thanh toán -->
            </div>
        </div>
    </body>
</html>
