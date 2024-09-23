<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Verify Account</title>
        <!-- Bootstrap CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .error-message {
                color: red;
            }
            .success-message {
                color: green;
            }
            .verification-container {
                min-height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: #f8f9fa;
            }
            .verification-box {
                background: #fff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="container verification-container">
            <div class="verification-box">
                <h3>Verify Your Account</h3>
                <p>Hệ thống đã gửi mã kích hoạt đến Email của bạn.<br> Xin vui lòng kiểm tra Email để lấy mã kích hoạt tài khoản của bạn.</p>
                    <%-- Hiển thị thông báo lỗi --%>
                    <% String error = (String) request.getAttribute("error");
               if (error != null) { %>
                <div class="error-message"><%= error %></div>
                <% } %>
                <%-- Hiển thị thông báo thành công --%>
                <% String message = (String) request.getAttribute("message");
               if (message != null) { %>
                <div class="success-message"><%= message %></div>
                <% } %>
                <form id="verifyForm" action="verifycode" method="post" class="mt-4">
                    <div class="form-group">
                        <label for="authcode">Mã kích hoạt:</label>
                        <input type="text" id="authcode" name="authcode" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Kích hoạt</button>
                </form>
                <div id="timer" class="mt-3"></div>
                <a href="register" class="btn btn-primary mt-3">Trở lại trang đăng ký</a>

            </div>
        </div>

        <!-- Bootstrap JS, Popper.js, and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
            // Timer script
            var timeLeft = 60;
            var timer = document.getElementById('timer');
            var countdown = setInterval(function () {
                if (timeLeft <= 0) {
                    clearInterval(countdown);
                    document.getElementById('verifyForm').submit();
                } else {
                    timer.innerHTML = 'Thời gian còn lại: ' + timeLeft + 's';
                }
                timeLeft -= 1;
            }, 1000);
        </script>
    </body>
</html>
