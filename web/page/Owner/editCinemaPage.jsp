<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="util.RouterURL"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chỉnh sửa Rạp Phim</title>
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
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Chỉnh sửa Rạp Phim</h2>
            <form action="<%= RouterURL.OWNER_EDIT_CINEMA%>" method="POST">
                <c:if test="${not empty cinema}">
                    <input type="hidden" name="cinemaID" value="${cinema.cinemaID}">
                    <input type="hidden" name="provinceName" id="provinceName" value="${cinema.province}">
                    <input type="hidden" name="districtName" id="districtName" value="${cinema.district}">
                    <input type="hidden" name="communeName" id="communeName" value="${cinema.commune}">
                </c:if>
                <c:if test="${empty cinema}">
                    <p>Rạp phim không tồn tại hoặc đã bị xóa.</p>
                </c:if>

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
                    <input type="text" id="address" name="address" class="form-control" value="${cinema.address}" required>
                </div>

                <button type="submit" class="btn btn-primary">Cập nhật Rạp</button>
            </form>
            <form action="<%= RouterURL.OWNER_DELETE_CINEMA%>" method="POST" style="margin-top: 20px;">
                <input type="hidden" name="cinemaID" value="${cinema.cinemaID}">
                <button type="submit" class="btn btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa rạp phim này không?');">Xóa Rạp</button>
            </form>
        </div>

        <script>
            $(document).ready(function () {
                // Đặt giá trị hiện tại cho các trường
                var currentProvince = "${cinema.province}";
                var currentDistrict = "${cinema.district}";
                var currentCommune = "${cinema.commune}";

                // Hàm để cập nhật hidden input khi tỉnh thay đổi
                function updateProvinceName() {
                    var provinceText = $("#tinh option:selected").text();
                    $("#provinceName").val(provinceText);
                }

                // Hàm để cập nhật hidden input khi quận/huyện thay đổi
                function updateDistrictName() {
                    var districtText = $("#quan option:selected").text();
                    $("#districtName").val(districtText);
                }

                // Hàm để cập nhật hidden input khi xã/phường thay đổi
                function updateCommuneName() {
                    var communeText = $("#phuong option:selected").text();
                    $("#communeName").val(communeText);
                }

                // Hàm để tải quận/huyện khi thay đổi tỉnh
                function loadDistrict(provinceID, selectedDistrict) {
                    $("#quan").empty().append('<option value="">Chọn Quận/Huyện</option>');
                    if (provinceID) {
                        $.getJSON('https://esgoo.net/api-tinhthanh/2/' + provinceID + '.htm', function (data_quan) {
                            if (data_quan.error === 0) {
                                $.each(data_quan.data, function (key_quan, val_quan) {
                                    var selected = val_quan.full_name === selectedDistrict ? 'selected' : '';
                                    $("#quan").append('<option value="' + val_quan.id + '" ' + selected + '>' + val_quan.full_name + '</option>');
                                });
                                // Sau khi load quận/huyện, nếu có giá trị hiện tại, tải xã/phường
                                if (currentDistrict) {
                                    var selectedDistrictID = $("#quan option:selected").val();
                                    loadCommune(selectedDistrictID, currentCommune);
                                }
                            }
                        });
                    }
                }

                // Hàm để tải xã/phường khi thay đổi quận/huyện
                function loadCommune(districtID, selectedCommune) {
                    $("#phuong").empty().append('<option value="">Chọn Xã/Phường</option>');
                    if (districtID) {
                        $.getJSON('https://esgoo.net/api-tinhthanh/3/' + districtID + '.htm', function (data_phuong) {
                            if (data_phuong.error === 0) {
                                $.each(data_phuong.data, function (key_phuong, val_phuong) {
                                    var selected = val_phuong.full_name === selectedCommune ? 'selected' : '';
                                    $("#phuong").append('<option value="' + val_phuong.id + '" ' + selected + '>' + val_phuong.full_name + '</option>');
                                });
                            }
                        });
                    }
                }

                // Gọi API để lấy danh sách tỉnh
                $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
                    if (data_tinh.error === 0) {
                        $.each(data_tinh.data, function (key_tinh, val_tinh) {
                            var selected = val_tinh.full_name === currentProvince ? 'selected' : '';
                            $("#tinh").append('<option value="' + val_tinh.id + '" ' + selected + '>' + val_tinh.full_name + '</option>');
                        });

                        // Sau khi load tỉnh, nếu có giá trị hiện tại thì tải danh sách quận/huyện
                        if (currentProvince) {
                            var selectedProvinceID = $("#tinh option:selected").val();
                            loadDistrict(selectedProvinceID, currentDistrict);
                        }
                    }
                });

                // Khi tỉnh thay đổi, tải lại quận/huyện
                $("#tinh").change(function () {
                    var provinceID = $(this).val();
                    loadDistrict(provinceID, "");
                    $("#phuong").empty().append('<option value="">Chọn Xã/Phường</option>');
                    updateProvinceName(); // Cập nhật giá trị của trường hidden
                });

                // Khi quận/huyện thay đổi, tải lại xã/phường
                $("#quan").change(function () {
                    var districtID = $(this).val();
                    loadCommune(districtID, "");
                    updateDistrictName(); // Cập nhật giá trị của trường hidden
                });

                // Khi xã/phường thay đổi, cập nhật giá trị hidden
                $("#phuong").change(function () {
                    updateCommuneName(); // Cập nhật giá trị của trường hidden
                });

                // Tự động chọn lại các giá trị hiện tại
                if (currentDistrict) {
                    var selectedDistrictID = $("#quan option:selected").val();
                    loadCommune(selectedDistrictID, currentCommune);
                }
            });
        </script>


    </body>
</html>
