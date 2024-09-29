<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="util.RouterURL"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tạo Rạp Phim</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                color: #333;
                line-height: 1.8;
            }

            .container {
                max-width: 600px;
                margin: 80px auto;
                padding: 30px;
                background-color: rgba(255, 255, 255, 0.95);
                border-radius: 10px;
                box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            }

            h2 {
                text-align: center;
                margin-bottom: 30px;
                color: #2c3e50;
            }

            .form-group label {
                font-weight: bold;
                color: #34495e;
            }

            .btn-primary {
                width: 100%;
                background: linear-gradient(to right, #3498db, #2980b9);
                color: white;
                border: none;
                padding: 12px;
                border-radius: 5px;
                font-size: 18px;
                font-weight: bold;
                transition: background 0.3s;
            }

            .btn-primary:hover {
                background: linear-gradient(to right, #2980b9, #3498db);
            }

            select {
                height: 45px;
                border-radius: 5px;
                border: 1px solid #e0e0e0;
                padding: 10px;
                background-color: #f8f9fa;
                transition: border 0.3s;
            }

            select:focus {
                border-color: #3498db;
                outline: none;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Tạo Rạp Phim</h2>
            <form action="<%= RouterURL.OWNER_CREATE_CINEMA%>" method="POST">
                <div class="form-group">
                    <label for="province">Tỉnh:</label>
                    <select id="tinh" name="province" required>
                        <option value="">Chọn Tỉnh</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="district">Quận/Huyện:</label>
                    <select id="quan" name="district" required>
                        <option value="">Chọn Quận/Huyện</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="commune">Xã/Phường:</label>
                    <select id="phuong" name="commune" required>
                        <option value="">Chọn Xã/Phường</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="address">Địa chỉ:</label>
                    <input type="text" id="address" name="address" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-primary">Tạo Rạp</button>
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
            });
        </script>
    </body>
</html>
