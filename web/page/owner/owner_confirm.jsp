<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Confirm Ticket</title>
    <style>
      body {
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f0f4f8;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
      }

      .container {
        background-color: #ffffff;
        padding: 40px;
        border-radius: 12px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        max-width: 600px;
        width: 100%;
        margin: 20px;
      }

      h2,
      h1 {
        text-align: center;
        color: #333333;
      }

      h2 {
        font-size: 20px;
        margin-top: 20px;
      }

      p {
        font-size: 14px;
        color: #555555;
        margin: 10px 0;
        padding: 0 10px;
      }

      ul {
        list-style-type: none;
        padding: 0;
        margin: 10px 0;
      }

      li {
        background-color: #e9ecef;
        margin: 5px 0;
        padding: 10px;
        border-radius: 5px;
        transition: background-color 0.3s;
      }

      li:hover {
        background-color: #d0d7dc;
      }

      .form-group {
        margin-top: 20px;
        text-align: center;
      }

      input[type="submit"] {
        width: 100%;
        padding: 12px 15px;
        background-color: #007bff;
        color: #ffffff;
        border: none;
        border-radius: 8px;
        font-size: 16px;
        font-weight: bold;
        cursor: pointer;
        transition: background-color 0.3s ease, box-shadow 0.3s ease;
      }

      input[type="submit"]:hover {
        background-color: #0056b3;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h1>Confirm Ticket Details</h1>

      <form
        action="${pageContext.request.contextPath}/order/confirm"
        method="post"
      >
        <input type="hidden" name="orderID" value="${orderID}" />
        <input type="hidden" name="userID" value="${userID}" />
        <input type="hidden" name="code" value="${code}" />

        <h2>Order Information</h2>
        <p><strong>Time Created:</strong> ${orderDetails.timeCreated}</p>

        <h2>Movie Information</h2>
        <p><strong>Title:</strong> ${orderDetails.movieTitle}</p>
        <p><strong>Room:</strong> ${orderDetails.roomName}</p>
        <p><strong>Synopsis:</strong> ${orderDetails.synopsis}</p>
        <p><strong>Country:</strong> ${orderDetails.country}</p>
        <p><strong>Start Time:</strong> ${orderDetails.startTime}</p>
        <p><strong>End Time:</strong> ${orderDetails.endTime}</p>

        <h2>Cinema Information</h2>
        <p><strong>Cinema:</strong> ${orderDetails.cinemaName}</p>
        <p>
          <strong>Address:</strong> ${orderDetails.address},
          ${orderDetails.commune}, ${orderDetails.district},
          ${orderDetails.province}
        </p>

        <h2>Seats</h2>
        <ul>
          <c:forEach var="seat" items="${seats}">
            <li>${seat.name}</li>
          </c:forEach>
        </ul>

        <h2>Canteen Items</h2>
        <ul>
          <c:forEach var="item" items="${canteenItems}">
            <li>${item.name} - ${item.price}</li>
          </c:forEach>
        </ul>

        <div class="form-group">
          <input type="submit" value="Confirm Ticket" class="btn btn-primary" />
        </div>
      </form>
    </div>
  </body>
</html>
