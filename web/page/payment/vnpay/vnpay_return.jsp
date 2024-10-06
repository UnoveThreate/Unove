<%@page import="util.RouterURL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="util.VnPayConfig"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <title>KẾT QUẢ THANH TOÁN</title>
        <!-- Bootstrap core CSS -->
        <link href="/movie/assets/vnpay/bootstrap.min.css" rel="stylesheet"/>
        <!-- Custom styles for this template -->
        <link href="/movie/assets/vnpay/jumbotron-narrow.css" rel="stylesheet">      
        <script src="/movie/assets/vnpay/jquery-1.11.3.min.js"></script>
        <style>
            .container {
                width: 60%;
                margin: 0 auto;
                padding: 20px;
                background-color: #f9f9f9;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .header {
                text-align: center;
                margin-bottom: 20px;
            }
            .header h3 {
                color: #333;
                margin: 0;
                font-size: 24px;
            }
            .form-group {
                display: flex;
                justify-content: space-between;
                padding: 10px 0;
                border-bottom: 1px solid #ddd;
            }
            .form-group:last-child {
                border-bottom: none;
            }
            .form-group label {
                font-weight: bold;
                color: #555;
            }
            .form-group label + label {
                font-weight: normal;
                color: #000;
            }
            .table-responsive {
                margin-top: 20px;
            }
            .container  p {
                text-align: center;
                margin-top: 20px;
            }
            .btn {
                display: inline-block;
                padding: 10px 20px;
                margin: 10px 5px;
                font-size: 16px;
                text-decoration: none;
                color: #fff;
                background-color: #007bff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .btn:hover {
                background-color: #0056b3;
            }
            #countdown {
                color: #e74c3c;
                font-weight: bold;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <%
            //Begin process return from VNPAY
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = VnPayConfig.hashAllFields(fields);

        %>
        <jsp:include page="../.././landingPage/Header.jsp" />
        <!--Begin display -->
        <div class="container">
            <div class="header clearfix">
                <h3 class="text-muted">KẾT QUẢ THANH TOÁN</h3>
            </div>
            <div class="table-responsive">
                <div class="form-group">
                    <label >Mã giao dịch thanh toán:</label>
                    <label><%=request.getParameter("vnp_TxnRef")%></label>
                </div>    
                <div class="form-group">
                    <label >Số tiền:</label>
                    <label><%=request.getParameter("vnp_Amount")%></label>
                </div>  
                <div class="form-group">
                    <label >Mô tả giao dịch:</label>
                    <label><%=request.getParameter("vnp_OrderInfo")%></label>
                </div> 
                <div class="form-group">
                    <label >Mã lỗi thanh toán:</label>
                    <label><%=request.getParameter("vnp_ResponseCode")%></label>
                </div> 
                <div class="form-group">
                    <label >Mã giao dịch tại CTT VNPAY-QR:</label>
                    <label><%=request.getParameter("vnp_TransactionNo")%></label>
                </div> 
                <div class="form-group">
                    <label >Mã ngân hàng thanh toán:</label>
                    <label><%=request.getParameter("vnp_BankCode")%></label>
                </div> 
                <div class="form-group">
                    <label >Thời gian thanh toán:</label>
                    <label><%=request.getParameter("vnp_PayDate")%></label>
                </div> 
                <div class="form-group">
                    <label >Tình trạng giao dịch:</label>
                    <label>
                        <%
                            request.getParameter("message");
                        %>
                    </label>
                </div> 
            </div>
            <p>
                &nbsp;
            </p>
            <div>
                <button class="btn" onclick="window.location.href = '<%= RouterURL.VIEW_ORDER%>';">View Order</button>
                <button class="btn" onclick="window.location.href = '<%= RouterURL.HOMEPAGE%>';">Back Home</button>
            </div>
            <div id="countdown">30</div> 
        </div>  
    </body>
    <script>
        // Countdown timer function
        function startCountdown(seconds) {
            var counter = seconds;
            var countdownElement = document.getElementById('countdown');

            var interval = setInterval(function () {
                countdownElement.textContent = counter;
                counter--;

                if (counter < 0) {
                    clearInterval(interval);
                    window.location.href = '<%= RouterURL.HOMEPAGE%>'; // Redirect to home page
                }
            }, 1000);
        }

        // Start the countdown timer when the page loads
        window.onload = function () {
            startCountdown(30);
        }
    </script>
</html>
