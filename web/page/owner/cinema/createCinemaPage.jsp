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
            }
            .container {
                max-width: 600px;
                margin: 80px auto;
                padding: 30px;
                background-color: rgba(255, 255, 255, 0.95);
                border-radius: 10px;
                box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            }
            .form-group label {
                font-weight: bold;
            }
            .button-group {
                display: flex;
                justify-content: space-between;
            }
            .btn {
                flex: 1;
                margin: 5px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center mb-4">Tạo Rạp Phim</h2>
            <form action="<%= RouterURL.OWNER_CREATE_CINEMA%>" method="POST">
                <!-- Hidden field for cinemaChainID -->
                <input type="hidden" name="cinemaChainID" value="${cinemaChainID}">

                <!-- Cinema Name Field -->
                <div class="form-group">
                    <label for="name">Tên Rạp:</label>
                    <input type="text" id="name" name="name" class="form-control" required>
                </div>

                <!-- Province Dropdown -->
                <div class="form-group">
                    <label for="province">Tỉnh:</label>
                    <select id="tinh" name="province" class="form-control" required>
                        <option value="">Chọn Tỉnh</option>
                    </select>
                </div>

                <!-- District Dropdown -->
                <div class="form-group">
                    <label for="district">Quận/Huyện:</label>
                    <select id="quan" name="district" class="form-control" required>
                        <option value="">Chọn Quận/Huyện</option>
                    </select>
                </div>

                <!-- Commune Dropdown -->
                <div class="form-group">
                    <label for="commune">Xã/Phường:</label>
                    <select id="phuong" name="commune" class="form-control" required>
                        <option value="">Chọn Xã/Phường</option>
                    </select>
                </div>

                <!-- Address Field -->
                <div class="form-group">
                    <label for="address">Địa chỉ:</label>
                    <input type="text" id="address" name="address" class="form-control" required>
                </div>

                <!-- Hidden fields for province, district, and commune names -->
                <input type="hidden" name="provinceName" id="provinceName">
                <input type="hidden" name="districtName" id="districtName">
                <input type="hidden" name="communeName" id="communeName">

                <!-- Submit Button -->
                <div class="button-group">
                    <button type="submit" class="btn btn-primary">Tạo Rạp</button>
                    <button type="reset" class="btn btn-secondary">Nhập Lại</button>
                </div>
            </form>
        </div>

        <!-- jQuery for fetching Province, District, and Commune data -->
        <script>
            $(document).ready(function () {
                // Lấy danh sách tỉnh từ API
                $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
                    if (data_tinh.error === 0) {
                        $.each(data_tinh.data, function (key_tinh, val_tinh) {
                            $("#tinh").append('<option value="' + val_tinh.id + '">' + val_tinh.full_name + '</option>');
                        });
                    }
                });

                // Lấy danh sách quận/huyện theo tỉnh
                $("#tinh").change(function () {
                    var idtinh = $(this).val();
                    $("#quan").empty().append('<option value="">Chọn Quận/Huyện</option>');
                    $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                        if (data_quan.error === 0) {
                            $.each(data_quan.data, function (key_quan, val_quan) {
                                $("#quan").append('<option value="' + val_quan.id + '">' + val_quan.full_name + '</option>');
                            });
                        }
                    });
                });

                // Lấy danh sách xã/phường theo quận/huyện
                $("#quan").change(function () {
                    var idquan = $(this).val();
                    $("#phuong").empty().append('<option value="">Chọn Xã/Phường</option>');
                    $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                        if (data_phuong.error === 0) {
                            $.each(data_phuong.data, function (key_phuong, val_phuong) {
                                $("#phuong").append('<option value="' + val_phuong.id + '">' + val_phuong.full_name + '</option>');
                            });
                        }
                    });
                });

                // Cập nhật các trường ẩn khi người dùng chọn tỉnh, quận và xã
                $("#tinh").change(function () {
                    var selectedProvince = $("#tinh option:selected").text();
                    $("#provinceName").val(selectedProvince);
                });

                $("#quan").change(function () {
                    var selectedDistrict = $("#quan option:selected").text();
                    $("#districtName").val(selectedDistrict);
                });

                $("#phuong").change(function () {
                    var selectedCommune = $("#phuong option:selected").text();
                    $("#communeName").val(selectedCommune);
                });
            });
        </script>
    </body>
</html>
