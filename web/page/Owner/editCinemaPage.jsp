<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="util.RouterURL"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Edit Cinema</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        /* Thêm CSS tương tự như trong các trang khác */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
        }
        h2 {
            margin-bottom: 20px;
            text-align: center;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        input[type="text"], select {
            width: 100%;
            padding: 8px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Edit Cinema</h2>
        <form action="${pageContext.request.contextPath}/owner/edit/cinema" method="POST">
            <input type="hidden" name="cinemaID" value="<c:out value='${cinema.cinemaID}' />">

            <label for="address">Address:</label>
            <input type="text" id="address" name="address" value="<c:out value='${cinema.address}' />" required>

            <label for="province">Province:</label>
            <select id="tinh" name="province" required>
                <option value="">Chọn Tỉnh</option>
                <!-- Options sẽ được thêm thông qua JavaScript -->
            </select>

            <label for="district">District:</label>
            <select id="quan" name="district" required>
                <option value="">Chọn Quận/Huyện</option>
                <!-- Options sẽ được thêm thông qua JavaScript -->
            </select>

            <label for="commune">Commune:</label>
            <select id="phuong" name="commune" required>
                <option value="">Chọn Xã/Phường</option>
                <!-- Options sẽ được thêm thông qua JavaScript -->
            </select>

            <input type="submit" value="Update Cinema">
        </form>
    </div>

    <script>
        $(document).ready(function () {
            // Bước 1: Lấy danh sách tỉnh từ API
            $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
                if (data_tinh.error === 0) {
                    $.each(data_tinh.data, function (key_tinh, val_tinh) {
                        $("#tinh").append('<option value="' + val_tinh.id + '">' + val_tinh.full_name + '</option>');
                    });

                    // Đặt giá trị mặc định cho tỉnh
                    var defaultProvince = '<c:out value="${cinema.province}" />';
                    $("#tinh").val(defaultProvince);
                }
            });

            // Bước 2: Lấy danh sách quận/huyện theo tỉnh
            $("#tinh").change(function () {
                var idtinh = $(this).val();
                $("#quan").empty().append('<option value="">Chọn Quận/Huyện</option>'); // Reset quận/huyện
                $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                    if (data_quan.error === 0) {
                        $.each(data_quan.data, function (key_quan, val_quan) {
                            $("#quan").append('<option value="' + val_quan.id + '">' + val_quan.full_name + '</option>');
                        });
                    }
                });
            });

            // Bước 3: Lấy danh sách xã/phường theo quận/huyện
            $("#quan").change(function () {
                var idquan = $(this).val();
                $("#phuong").empty().append('<option value="">Chọn Xã/Phường</option>'); // Reset xã/phường
                $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                    if (data_phuong.error === 0) {
                        $.each(data_phuong.data, function (key_phuong, val_phuong) {
                            $("#phuong").append('<option value="' + val_phuong.id + '">' + val_phuong.full_name + '</option>');
                        });
                    }
                });
            });

            // Đặt giá trị mặc định cho quận và xã
            var defaultDistrict = '<c:out value="${cinema.district}" />';
            $("#quan").val(defaultDistrict);

            var defaultCommune = '<c:out value="${cinema.commune}" />';
            $("#phuong").val(defaultCommune);
        });
    </script>
</body>
</html>
