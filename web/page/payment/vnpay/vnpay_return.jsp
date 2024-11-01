<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="util.RouterURL"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Kết Quả Thanh Toán</title>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <style>
            :root {
                --momo-pink: #A50064;
                --momo-pink-light: #FF6B9C;
                --card-pink: #FFE6EF;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: #ffffff;
                margin: 0;
                padding: 20px;
                min-height: 100vh;
                position: relative;
                color: #333;
            }

            .pattern-bg {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                z-index: -1;
                background: linear-gradient(to bottom, #fff, #ffe6ef30);
            }

            .container {
                max-width: 600px;
                margin: 50px auto;
                padding: 30px;
                background: rgba(255, 255, 255, 0.95);
                border-radius: 20px;
                box-shadow: 0 15px 30px rgba(165, 0, 100, 0.1);
                backdrop-filter: blur(10px);
            }

            .header {
                text-align: center;
                margin-bottom: 30px;
            }

            .header h3 {
                color: var(--momo-pink);
                font-size: 2rem;
                font-weight: bold;
                margin: 0;
                text-transform: uppercase;
            }

            .form-group {
                display: flex;
                justify-content: space-between;
                padding: 15px;
                border-bottom: 1px solid #FFE6EF;
                font-size: 16px;
                transition: all 0.3s ease;
                background: white;
                margin: 10px 0;
                border-radius: 10px;
            }

            .form-group:hover {
                transform: translateX(10px);
                background: var(--card-pink);
            }

            .form-group label {
                font-weight: 500;
            }

            .form-group label.highlight {
                color: var(--momo-pink);
                font-weight: bold;
            }

            .btn {
                display: inline-block;
                padding: 12px 30px;
                margin: 20px auto;
                font-size: 16px;
                font-weight: bold;
                text-decoration: none;
                color: white;
                background: var(--momo-pink);
                border: none;
                border-radius: 25px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .btn:hover {
                background: var(--momo-pink-light);
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(165, 0, 100, 0.2);
            }

            #countdown {
                color: var(--momo-pink);
                font-weight: bold;
                text-align: center;
                margin-top: 20px;
                font-size: 1.5rem;
                animation: pulse 1s infinite;
            }

            @keyframes pulse {
                0% { opacity: 1; }
                50% { opacity: 0.5; }
                100% { opacity: 1; }
            }

            [style*="color: green"] {
                color: #2ecc71 !important;
            }

            [style*="color: red"] {
                color: #e74c3c !important;
            }

            @media (max-width: 768px) {
                .container {
                    margin: 20px;
                    padding: 20px;
                }

                .form-group {
                    flex-direction: column;
                    gap: 5px;
                }

                .header h3 {
                    font-size: 1.5rem;
                }
            }
        </style>
    </head>
    <body>
        <div class="pattern-bg"></div>
        <div class="container" data-aos="fade-up">
            <div class="header" data-aos="fade-down">
                <h3>Kết Quả Thanh Toán</h3>
            </div>
            <div class="form-group" data-aos="fade-right" data-aos-delay="100">
                <label>Mã giao dịch thanh toán:</label>
                <label><%= request.getParameter("vnp_TxnRef")%></label>
            </div>
            <%
                String amountString = request.getParameter("vnp_Amount");
                String formattedAmount = "";
                if (amountString != null) {
                    try {
                        long amount = Long.parseLong(amountString);
                        long actualAmount = amount / 100;
                        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
                        formattedAmount = formatter.format(actualAmount) + "đ";
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            %>

            <div class="form-group" data-aos="fade-right" data-aos-delay="200">
                <label>Số tiền:</label>
                <label class="highlight"><%= formattedAmount%></label>
            </div>
            <div class="form-group" data-aos="fade-right" data-aos-delay="300">
                <label>Mô tả giao dịch:</label>
                <label><%= request.getParameter("vnp_OrderInfo")%></label>
            </div>
            <div class="form-group" data-aos="fade-right" data-aos-delay="400">
                <label>Mã lỗi thanh toán:</label>
                <label><%= request.getParameter("vnp_ResponseCode")%></label>
            </div>
            <div class="form-group" data-aos="fade-right" data-aos-delay="500">
                <label>Mã giao dịch tại CTT VNPAY-QR:</label>
                <label><%= request.getParameter("vnp_TransactionNo")%></label>
            </div>
            <div class="form-group" data-aos="fade-right" data-aos-delay="600">
                <label>Mã ngân hàng thanh toán:</label>
                <label><%= request.getParameter("vnp_BankCode")%></label>
            </div>
            <%
                String vnpPayDate = request.getParameter("vnp_PayDate");
                String formattedPayDate = "";
                if (vnpPayDate != null) {
                    try {
                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date date = inputFormat.parse(vnpPayDate);
                        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                        formattedPayDate = outputFormat.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            %>

            <div class="form-group" data-aos="fade-right" data-aos-delay="700">
                <label>Thời gian thanh toán:</label>
                <label><%= formattedPayDate%></label>
            </div>
            <div class="form-group" data-aos="fade-right" data-aos-delay="800">
                <label>Tình trạng giao dịch:</label>
                <%
                    String message = (String) request.getAttribute("message");
                    String color = "black";
                    if ("success".equals(message)) {
                        color = "green";
                    } else if ("failed".equals(message)) {
                        color = "red";
                    }
                %>
                <label style="color: <%= color%>; font-weight: bold;">
                    <%= message != null ? message : "Thông tin không có!"%>
                </label>
            </div>

            <div style="text-align: center;" data-aos="fade-up" data-aos-delay="900">
                <button class="btn" onclick="window.location.href = '<%= RouterURL.HOME_PAGE%>';">
                    Quay lại Trang chủ
                </button>
            </div>
            <div id="countdown" data-aos="fade-up" data-aos-delay="1000">45</div>
        </div>

        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>
            AOS.init({
                duration: 800,
                easing: 'ease-in-out',
                once: true
            });
            
            function startCountdown(seconds) {
                var counter = seconds;
                var countdownElement = document.getElementById('countdown');
                var interval = setInterval(function () {
                    countdownElement.textContent = counter;
                    counter--;
                    if (counter < 0) {
                        clearInterval(interval);
                        window.location.href = '<%= RouterURL.HOME_PAGE%>';
                    }
                }, 1000);
            }
            window.onload = function () {
                startCountdown(45);
            }
        </script>
    </body>
</html>