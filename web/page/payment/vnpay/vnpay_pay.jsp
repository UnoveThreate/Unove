<%@page import="model.booking.CanteenItemOrder"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Thanh Toán</title>
        <!-- Bootstrap core CSS -->
        <link href="/Unove/assets/vnpay/bootstrap.min.css" rel="stylesheet"/>
        <!-- Custom styles for this template -->
        <link href="/Unove/assets/vnpay/jumbotron-narrow.css" rel="stylesheet">      
        <script src="/Unove/assets/vnpay/jquery-1.11.3.min.js"></script>
    </head>

    <body>

        <div class="container">
            <div class="header clearfix">

                <h3 class="text-muted">VNPAY PAYMENT</h3>
            </div>
            <h3>Tổng Hóa Đơn</h3>
            <div class="table-responsive">
                
                <div class="">
                    <!-- Hiển thị tổng giá vé -->
                    <p>Tổng giá vé: <%= request.getAttribute("totalPriceTicket")%>đ</p>

                    <!-- Hiển thị tổng giá các món đồ ăn -->
                    <p>Tổng giá đồ ăn: <%= request.getAttribute("totalPriceCanteen")%>đ</p>

                    <!-- Hiển thị tên phim -->
                    <p>Tên phim: <%= request.getAttribute("movieName")%></p>

                    <!-- Hiển thị khung giờ đặt -->
                    <p>Khung giờ đặt: <%= request.getAttribute("slotMovie")%></p>

                    <!-- Hiển thị ngày đặt -->
                    <p>Ngày đặt: <%= request.getAttribute("date")%></p>

                </div>

                <form action="/Unove/payment/vnpay" id="frmCreateOrder" method="post"> 

                    <div class="form-group">
                        <%
                            // Lấy tổng số tiền vé và đồ ăn từ request
                            double totalPriceTicket = (double) request.getAttribute("totalPriceTicket");
                            double totalPriceCanteen = (double) request.getAttribute("totalPriceCanteen");

                            // Tính tổng số tiền
                            double totalAmount = totalPriceTicket + totalPriceCanteen;
                            int roundedTotalAmount = (int) Math.ceil(totalAmount);
                        %>
                        <label for="amount">Số tiền thanh toán</label>
                        <input class="form-control" id="amount" name="amount" type="text" value='<%=roundedTotalAmount%>' readonly />
                    </div>

                    <h4>Chọn phương thức thanh toán</h4>
                    
                    <div class="form-group">
                        <h5>Cách 1: Chuyển hướng sang Cổng VNPAY chọn phương thức thanh toán</h5>
                        <input type="radio" Checked="True" id="bankCode" name="bankCode" value="">
                        <label for="bankCode">Cổng thanh toán VNPAYQR</label><br>

                        <h5>Cách 2: Tách phương thức tại site của đơn vị kết nối</h5>
                        <input type="radio" id="bankCode" name="bankCode" value="VNPAYQR">
                        <label for="bankCode">Thanh toán bằng ứng dụng hỗ trợ VNPAYQR</label><br>

                        <input type="radio" id="bankCode" name="bankCode" value="VNBANK">
                        <label for="bankCode">Thanh toán qua thẻ ATM/Tài khoản nội địa</label><br>

                        <input type="radio" id="bankCode" name="bankCode" value="INTCARD">
                        <label for="bankCode">Thanh toán qua thẻ quốc tế</label><br> 
                    </div>
                    
                    <div class="form-group">

                        <h5>Chọn ngôn ngữ giao diện thanh toán:</h5>

                        <input type="radio" id="language" Checked="True" name="language" value="vn">
                        <label for="language">Tiếng việt</label><br>
                        <input type="radio" id="language" name="language" value="en">
                        <label for="language">Tiếng anh</label><br>

                    </div>
                    <button type="submit" class="btn btn-default" href>Thanh toán</button>
                </form>
            </div>
            <p>
                &nbsp;
            </p>
            <footer class="footer">
                <p>&copy; VNPAY 2024</p>
            </footer>
        </div>

        <link href="https://pay.vnpay.vn/lib/vnpay/vnpay.css" rel="stylesheet" />
        <script src="https://pay.vnpay.vn/lib/vnpay/vnpay.min.js"></script>

        <script type="text/javascript">
            $("#frmCreateOrder").submit(function () {
                var postData = $("#frmCreateOrder").serialize();
                var submitUrl = $("#frmCreateOrder").attr("action");
                $.ajax({
                    type: "POST",
                    url: submitUrl,
                    data: postData,
                    dataType: 'JSON',
                    success: function (x) {
                        if (x.code === '00') {
                            if (window.vnpay) {
                                vnpay.open({width: 768, height: 600, url: x.data});
                            } else {
                                location.href = x.data;
                            }
                            return false;
                        } else {
                            alert(x.Message);
                        }
                    }
                });
                return false;
            });
        </script>       
    </body>
</html>