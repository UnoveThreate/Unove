<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Quản lý Rạp Chiếu Phim</title>

        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/css/adminlte.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <style>
            :root {
                --primary-color: #3498db;
                --secondary-color: #2ecc71;
                --accent-color: #e74c3c;
                --text-color: #34495e;
                --light-bg: #ecf0f1;
                --dark-bg: #2c3e50;
            }

            body {
                font-family: 'Source Sans Pro', sans-serif;
                background-color: var(--light-bg);
                color: var(--text-color);
            }

            .content-wrapper {
                background-color: var(--light-bg);
                padding: 30px;
            }

            .card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
                transition: all 0.3s ease;
                overflow: hidden;
                margin-bottom: 30px;
                background: rgba(255, 255, 255, 0.8);
                backdrop-filter: blur(10px);
            }

            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
            }

            .card-header {
                background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
                color: white;
                border-bottom: none;
                padding: 20px;
                font-weight: 600;
            }

            .card-title {
                font-size: 1.5rem;
                margin-bottom: 0;
            }

            .btn-primary {
                background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
                border: none;
                border-radius: 50px;
                padding: 10px 20px;
                font-weight: 500;
                transition: all 0.3s ease;
            }

            .btn-primary:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(52, 152, 219, 0.3);
            }

            .table {
                border-collapse: separate;
                border-spacing: 0 15px;
            }

            .table thead th {
                border: none;
                background-color: var(--dark-bg);
                color: white;
                padding: 15px;
                font-weight: 500;
            }

            .table tbody tr {
                background-color: white;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
            }

            .table tbody tr:hover {
                transform: scale(1.02);
            }

            .table td {
                border: none;
                padding: 20px 15px;
                vertical-align: middle;
            }

            .btn-info, .btn-danger {
                border: none;
                border-radius: 50px;
                padding: 8px 15px;
                font-weight: 500;
                transition: all 0.3s ease;
            }

            .btn-info {
                background-color: var(--secondary-color);
            }

            .btn-danger {
                background-color: var(--accent-color);
            }

            .btn-info:hover, .btn-danger:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            }

            .modal-content {
                border: none;
                border-radius: 15px;
                overflow: hidden;
            }

            .modal-header {
                background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
                color: white;
                border-bottom: none;
                padding: 20px;
            }

            .modal-title {
                font-weight: 600;
            }

            .form-control, .select2-container--default .select2-selection--single {
                border-radius: 50px;
                border: 2px solid #e0e0e0;
                padding: 10px 20px;
                transition: all 0.3s ease;
            }

            .form-control:focus, .select2-container--default.select2-container--focus .select2-selection--single {
                border-color: var(--primary-color);
                box-shadow: 0 0 0 0.2rem rgba(52, 152, 219, 0.25);
            }

            .select2-container--default .select2-selection--single {
                height: auto;
            }

            .select2-container--default .select2-selection--single .select2-selection__rendered {
                line-height: normal;
                padding: 10px 20px;
            }

            .select2-container--default .select2-selection--single .select2-selection__arrow {
                height: 100%;
            }

            .main-footer {
                background-color: var(--dark-bg);
                color: white;
                border-top: none;
                padding: 20px;
            }

            .main-footer a {
                color: var(--secondary-color);
            }
        </style>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">

            <jsp:include page="/page/admin/sidebar.jsp" />

            <div class="content-wrapper">
                <section class="content-header">
                    <div class="container-fluid">
                        <h1 class="mb-4" data-aos="fade-right">Quản lý Rạp Chiếu Phim</h1>
                    </div>
                </section>

                <section class="content">
                    <div class="container-fluid">
                        <div class="mb-4" data-aos="fade-up">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addCinemaModal">
                                <i class="fas fa-plus-circle mr-2"></i>Thêm Rạp Mới
                            </button>
                            
                            <!-- Dropdown để lọc theo phim -->
                            <div class="form-group mt-3">
                                <label for="movieFilter">Lọc theo phim đang chiếu:</label>
                                <select class="form-control" id="movieFilter">
                                    <option value="">Tất cả phim</option>
                                    <c:forEach var="movie" items="${movies}">
                                        <option value="${movie.movieID}">${movie.title}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <c:forEach var="chain" items="${cinemaChains}" varStatus="status">
                            <div class="card cinema-chain" data-aos="fade-up" data-aos-delay="${status.index * 100}">
                                <div class="card-header">
                                    <h3 class="card-title">${chain.name}</h3>
                                </div>
                                <div class="card-body p-0">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Tên Rạp</th>
                                                <th>Địa chỉ</th>
                                                <th>Tỉnh/Thành phố</th>
                                                <th>Quận/Huyện</th>
                                                <th>Phường/Xã</th>
                                                <th>Thao Tác</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="cinema" items="${cinemasByChain[chain.cinemaChainID]}">
                                                <tr class="cinema-row" data-cinema-id="${cinema.cinemaID}">
                                                    <td>${cinema.name}</td>
                                                    <td>${cinema.address}</td>
                                                    <td>${cinema.province}</td>
                                                    <td>${cinema.district}</td>
                                                    <td>${cinema.commune}</td>
                                                    <td>
                                                        <button type="button" class="btn btn-sm btn-info edit-cinema" 
                                                                data-id="${cinema.cinemaID}" 
                                                                data-name="${cinema.name}"
                                                                data-address="${cinema.address}"
                                                                data-province="${cinema.province}"
                                                                data-district="${cinema.district}"
                                                                data-commune="${cinema.commune}"
                                                                data-chainid="${cinema.cinemaChainID}">
                                                            <i class="fas fa-edit mr-1"></i>Sửa
                                                        </button>
                                                        <button type="button" class="btn btn-sm btn-danger delete-cinema" data-id="${cinema.cinemaID}">
                                                            <i class="fas fa-trash-alt mr-1"></i>Xóa
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </section>
            </div>

            <footer class="main-footer">
                <strong>Copyright &copy; 2024 <a href="#">Unove Cinema</a>.</strong>
                All rights reserved.
            </footer>
        </div>

        <!-- Add Cinema Modal -->
        <div class="modal fade" id="addCinemaModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Thêm Rạp Mới</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form id="addCinemaForm">
                        <div class="modal-body">
                            <input type="hidden" name="action" value="add">
                            <div class="form-group">
                                <label for="cinemaName">Tên Rạp</label>
                                <input type="text" class="form-control" id="cinemaName" name="cinemaName" required>
                            </div>
                            <div class="form-group">
                                <label for="address">Địa chỉ</label>
                                <input type="text" class="form-control" id="address" name="address" required>
                            </div>
                            <div class="form-group">
                                <label for="province">Tỉnh/Thành phố</label>
                                <select class="form-control" id="province" name="province" required>
                                    <option value="">Chọn Tỉnh/Thành phố</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="district">Quận/Huyện</label>
                                <select class="form-control" id="district" name="district" required>
                                    <option value="">Chọn Quận/Huyện</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="commune">Phường/Xã</label>
                                <select class="form-control" id="commune" name="commune" required>
                                    <option value="">Chọn Phường/Xã</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="cinemaChainID">Chuỗi Rạp</label>
                                <select class="form-control" id="cinemaChainID" name="cinemaChainID" required>
                                    <c:forEach var="chain" items="${cinemaChains}">
                                        <option value="${chain.cinemaChainID}">${chain.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                            <button type="submit" class="btn btn-primary">Thêm</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Edit Cinema Modal -->
        <div class="modal fade" id="editCinemaModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Sửa Thông Tin Rạp</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form id="editCinemaForm">
                        <div class="modal-body">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" id="editCinemaID" name="cinemaID">
                            <div class="form-group">
                                <label for="editCinemaName">Tên Rạp</label>
                                <input type="text" class="form-control" id="editCinemaName" name="cinemaName" required>
                            </div>
                            <div class="form-group">
                                <label for="editAddress">Địa chỉ</label>
                                <input type="text" class="form-control" id="editAddress" name="address" required>
                            </div>
                            <div class="form-group">
                                <label for="editProvince">Tỉnh/Thành phố</label>
                                <select class="form-control" id="editProvince" name="province" required>
                                    <option value="">Chọn Tỉnh/Thành phố</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="editDistrict">Quận/Huyện</label>
                                <select class="form-control" id="editDistrict" name="district" required>
                                    <option value="">Chọn Quận/Huyện</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="editCommune">Phường/Xã</label>
                                <select class="form-control" id="editCommune" name="commune" required>
                                    <option value="">Chọn Phường/Xã</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="editCinemaChainID">Chuỗi Rạp</label>
                                <select class="form-control" id="editCinemaChainID" name="cinemaChainID" required>
                                    <c:forEach var="chain" items="${cinemaChains}">
                                        <option value="${chain.cinemaChainID}">${chain.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/js/adminlte.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>
            $(document).ready(function () {
    AOS.init({
        duration: 800,
        once: true
    });

    var apiUrl = "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json";
    var provinces = [];

    // Khởi tạo Select2 cho các dropdown
    $('#province, #district, #commune, #editProvince, #editDistrict, #editCommune, #movieFilter').select2({
        width: '100%'
    });

    // Lấy dữ liệu từ API
    $.getJSON(apiUrl, function (data) {
        provinces = data;
        renderProvinces();
    });

    // Render danh sách tỉnh/thành phố
    function renderProvinces() {
        var options = '<option value="">Chọn Tỉnh/Thành phố</option>';
        for (var i = 0; i < provinces.length; i++) {
            options += '<option value="' + provinces[i].Name + '">' + provinces[i].Name + '</option>';
        }
        $('#province, #editProvince').html(options);
    }

    // Xử lý khi chọn tỉnh/thành phố
    $('#province, #editProvince').change(function () {
        var provinceName = $(this).val();
        var districtSelect = $(this).attr('id') === 'province' ? '#district' : '#editDistrict';
        var communeSelect = $(this).attr('id') === 'province' ? '#commune' : '#editCommune';

        var selectedProvince = provinces.find(p => p.Name === provinceName);
        if (selectedProvince) {
            renderDistricts(selectedProvince.Districts, districtSelect);
        } else {
            $(districtSelect).html('<option value="">Chọn Quận/Huyện</option>');
            $(communeSelect).html('<option value="">Chọn Phường/Xã</option>');
        }
        $(districtSelect).trigger('change');
    });

    // Render danh sách quận/huyện
    function renderDistricts(districts, selectElement) {
        var options = '<option value="">Chọn Quận/Huyện</option>';
        for (var i = 0; i < districts.length; i++) {
            options += '<option value="' + districts[i].Name + '">' + districts[i].Name + '</option>';
        }
        $(selectElement).html(options);
        $(selectElement).trigger('change');
    }

    // Xử lý khi chọn quận/huyện
    $('#district, #editDistrict').change(function () {
        var districtName = $(this).val();
        var provinceName = $(this).closest('form').find('[name="province"]').val();
        var communeSelect = $(this).attr('id') === 'district' ? '#commune' : '#editCommune';

        var selectedProvince = provinces.find(p => p.Name === provinceName);
        var selectedDistrict = selectedProvince ? selectedProvince.Districts.find(d => d.Name === districtName) : null;
        if (selectedDistrict) {
            renderCommunes(selectedDistrict.Wards, communeSelect);
        } else {
            $(communeSelect).html('<option value="">Chọn Phường/Xã</option>');
        }
    });

    // Render danh sách phường/xã
    function renderCommunes(communes, selectElement) {
        var options = '<option value="">Chọn Phường/Xã</option>';
        for (var i = 0; i < communes.length; i++) {
            options += '<option value="' + communes[i].Name + '">' + communes[i].Name + '</option>';
        }
        $(selectElement).html(options);
    }

    // Xử lý thêm rạp mới
    $('#addCinemaForm').submit(function (e) {
        e.preventDefault();
        var formData = $(this).serialize();
        console.log("Form data:", formData); // Để debug

        $.ajax({
            url: '${pageContext.request.contextPath}/admin/cinemas',
            type: 'POST',
            data: formData,
            dataType: 'json',
            success: function (response) {
                if (response.success) {
                    alert(response.message);
                    $('#addCinemaModal').modal('hide');
                    location.reload();
                } else {
                    alert(response.message);
                }
            },
            error: function (xhr, status, error) {
                console.error("Error details:", xhr.responseText);
                alert('Có lỗi xảy ra khi thêm rạp: ' + xhr.responseText);
            }
        });
    });

    // Xử lý sửa rạp
    $(document).on('click', '.edit-cinema', function () {
        var cinemaID = $(this).data('id');
        var cinemaName = $(this).data('name');
        var address = $(this).data('address');
        var province = $(this).data('province');
        var district = $(this).data('district');
        var commune = $(this).data('commune');
        var cinemaChainID = $(this).data('chainid');

        $('#editCinemaID').val(cinemaID);
        $('#editCinemaName').val(cinemaName);
        $('#editAddress').val(address);
        $('#editCinemaChainID').val(cinemaChainID);

        // Cập nhật các select box địa chỉ
        $('#editProvince').val(province).trigger('change');
        setTimeout(function () {
            $('#editDistrict').val(district).trigger('change');
            setTimeout(function () {
                $('#editCommune').val(commune).trigger('change');
            }, 100);
        }, 100);

        $('#editCinemaModal').modal('show');
    });

    $('#editCinemaForm').submit(function (e) {
        e.preventDefault();
        var formData = $(this).serialize();
        console.log("Edit form data:", formData); // Để debug

        $.ajax({
            url: '${pageContext.request.contextPath}/admin/cinemas',
            type: 'POST',
            data: formData,
            dataType: 'json',
            success: function (response) {
                if (response.success) {
                    alert(response.message);
                    $('#editCinemaModal').modal('hide');
                    location.reload();
                } else {
                    alert(response.message);
                }
            },
            error: function (xhr, status, error) {
                console.error("Error details:", xhr.responseText);
                alert('Có lỗi xảy ra khi cập nhật rạp: ' + xhr.responseText);
            }
        });
    });

    // Xử lý xóa rạp
    $(document).on('click', '.delete-cinema', function () {
        var cinemaID = $(this).data('id');
        if (confirm('Bạn có chắc chắn muốn xóa rạp này?')) {
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/cinemas',
                type: 'POST',
                data: {action: 'delete', cinemaID: cinemaID},
                dataType: 'json',
                success: function (response) {
                    if (response.success) {
                        alert(response.message);
                        location.reload();
                    } else {
                        alert(response.message);
                    }
                },
                error: function (xhr, status, error) {
                    console.error("Error details:", xhr.responseText);
                    alert('Có lỗi xảy ra khi xóa rạp: ' + error);
                }
            });
        }
    });

    // Xử lý lọc rạp theo phim
    $('#movieFilter').change(function() {
        var selectedMovieId = $(this).val();
        console.log("Selected movie ID:", selectedMovieId);
        
        if (selectedMovieId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/getCinemasForMovie',
                type: 'GET',
                data: { movieId: selectedMovieId },
                success: function(response) {
                    console.log("AJAX response:", response);
                    // Ẩn tất cả các rạp và chuỗi rạp trước khi hiển thị kết quả mới
                    $('.cinema-row').hide();
                    $('.cinema-chain').hide();
                    
                    if (response.success) {
                        if (response.cinemas.length === 0) {
                            console.log("Không có rạp nào đang chiếu phim này");
                            alert("Không có rạp nào đang chiếu phim này");
                        } else {
                            response.cinemas.forEach(function(cinema) {
                                console.log("Showing cinema:", cinema.id);
                                var $cinemaRow = $('.cinema-row[data-cinema-id="' + cinema.id + '"]');
                                $cinemaRow.show();
                                $cinemaRow.closest('.cinema-chain').show();
                            });
                        }
                    } else {
                        console.error('Lỗi khi lọc rạp:', response.message);
                        alert('Có lỗi xảy ra khi lọc rạp: ' + response.message);
                    }
                },
                error: function(xhr, status, error) {
                    console.error("Lỗi AJAX:", error);
                    console.error("Response text:", xhr.responseText);
                    alert("Có lỗi xảy ra khi lọc rạp. Vui lòng thử lại.");
                }
            });
        } else {
            // Nếu không chọn phim nào, hiển thị tất cả rạp và chuỗi rạp
            $('.cinema-row').show();
            $('.cinema-chain').show();
        }
    });
});
        </script>
    </body>
</html>