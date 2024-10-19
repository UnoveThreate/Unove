<%@page import="java.text.NumberFormat"%> <%@page import="java.util.Locale"%>
<%@page import="java.text.ParseException"%> <%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%> <%@ page
contentType="text/html;charset=UTF-8" language="java" %> <%@page
import="util.RouterURL"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Kết Quả Thanh Toán</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f4f7f6;
        color: #333;
        margin: 0;
        padding: 0;
      }
      .container {
        max-width: 600px;
        margin: 50px auto;
        padding: 20px;
        background-color: #fff;
        border-radius: 10px;
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
      }
      .header {
        text-align: center;
        margin-bottom: 30px;
      }
      .header h3 {
        font-size: 24px;
        color: #444;
        font-weight: bold;
        margin: 0;
      }
      .form-group {
        display: flex;
        justify-content: space-between;
        padding: 12px 0;
        border-bottom: 1px solid #ddd;
        font-size: 16px;
      }
      .form-group:last-child {
        border-bottom: none;
      }
      .form-group label failed {
        font-weight: bold;
        color: red;
      }
      .form-group label success {
        font-weight: bold;
        color: green;
      }

      .form-group .highlight {
        color: #e74c3c;
        font-weight: bold;
      }
      .btn {
        display: inline-block;
        padding: 12px 25px;
        margin: 20px auto;
        font-size: 16px;
        font-weight: bold;
        text-decoration: none;
        color: #fff;
        background-color: #007bff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s;
        text-align: center;
      }
      .btn:hover {
        background-color: #0056b3;
      }
      #countdown {
        color: #e74c3c;
        font-weight: bold;
        text-align: center;
        margin-top: 20px;
        font-size: 18px;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <div class="header">
        <h3>Kết Quả Thanh Toán</h3>
      </div>
      <div class="form-group">
        <label>Mã giao dịch thanh toán:</label>
        <label><%= request.getParameter("vnp_TxnRef")%></label>
      </div>
      <% // Get the amount from the request String amountString =
      request.getParameter("vnp_Amount"); String formattedAmount = ""; //
      Variable to hold the formatted amount // Check if the amountString is not
      null if (amountString != null) { try { // Parse the amount to a long
      (assuming it is in VND and is an integer) long amount =
      Long.parseLong(amountString); // Chia số tiền cho 100 để lấy giá trị thực
      tế long actualAmount = amount / 100; // Format the amount using
      NumberFormat NumberFormat formatter = NumberFormat.getInstance(new
      Locale("vi", "VN")); formattedAmount = formatter.format(actualAmount) +
      "đ"; // Add "đ" for Vietnamese Dong } catch (NumberFormatException e) {
      e.printStackTrace(); // Handle the exception (you might want to log this
      or show an error) } } %>

      <div class="form-group">
        <label>Số tiền:</label>
        <label class="highlight"><%= formattedAmount%></label>
      </div>
      <div class="form-group">
        <label>Mô tả giao dịch:</label>
        <label><%= request.getParameter("vnp_OrderInfo")%></label>
      </div>
      <div class="form-group">
        <label>Mã lỗi thanh toán:</label>
        <label><%= request.getParameter("vnp_ResponseCode")%></label>
      </div>
      <div class="form-group">
        <label>Mã giao dịch tại CTT VNPAY-QR:</label>
        <label><%= request.getParameter("vnp_TransactionNo")%></label>
      </div>
      <div class="form-group">
        <label>Mã ngân hàng thanh toán:</label>
        <label><%= request.getParameter("vnp_BankCode")%></label>
      </div>
      <% // Get the payment date from the request String vnpPayDate =
      request.getParameter("vnp_PayDate"); String formattedPayDate = ""; //
      Variable to hold the formatted date // Check if the vnp_PayDate is not
      null if (vnpPayDate != null) { try { // Parse the original date string
      (adjust the format according to the actual input format) SimpleDateFormat
      inputFormat = new SimpleDateFormat("yyyyMMddHHmm"); // Example input
      format Date date = inputFormat.parse(vnpPayDate); // Format the date to
      the desired output format SimpleDateFormat outputFormat = new
      SimpleDateFormat("HH:mm dd/MM/yyyy"); formattedPayDate =
      outputFormat.format(date); } catch (ParseException e) {
      e.printStackTrace(); // Handle the exception (you might want to log this
      or show an error) } } %>

      <div class="form-group">
        <label>Thời gian thanh toán:</label>
        <label><%= formattedPayDate%></label>
      </div>
      <div class="form-group">
        <label>Tình trạng giao dịch:</label>
        <% String message = (String) request.getAttribute("message"); String
        color = "black"; // Default color if ("success".equals(message)) { color
        = "green"; } else if ("failed".equals(message)) { color = "red"; } %>

        <label style="color: <%= color%>; font-weight: bold">
          <%= message != null ? message : "Thông tin không có!"%>
        </label>
      </div>

      <div style="text-align: center">
        <button
          class="btn"
          onclick="window.location.href = '<%= RouterURL.HOME_PAGE%>';"
        >
          Quay lại Trang chủ
        </button>
      </div>
      <div id="countdown">45</div>
    </div>

    <script>
      function startCountdown(seconds) {
        var counter = seconds;
        var countdownElement = document.getElementById("countdown");

        var interval = setInterval(function () {
          countdownElement.textContent = counter;
          counter--;

          if (counter < 0) {
            clearInterval(interval);
            window.location.href = "<%= RouterURL.HOME_PAGE%>"; // Redirect to home page
          }
        }, 1000);
      }

      window.onload = function () {
        startCountdown(45);
      };
    </script>
  </body>
</html>
