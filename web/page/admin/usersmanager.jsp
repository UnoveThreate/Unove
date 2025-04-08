<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Người dùng</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/css/adminlte.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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
                opacity: 0;
                animation: pageLoad 0.6s ease forwards;
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
                transform: perspective(1000px);
            }

            .card:hover {
                transform: translateY(-5px) scale(1.01);
                box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
            }

            .card-header {
                background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
                color: white;
                border-bottom: none;
                padding: 20px;
                font-weight: 600;
            }

            .table {
                border-collapse: separate;
                border-spacing: 0 15px;
                margin-top: -15px;
            }

            .table thead th {
                border: none;
                background-color: var(--dark-bg);
                color: white;
                padding: 15px;
                font-weight: 500;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .table tbody tr {
                background-color: white;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
                opacity: 0;
                animation: fadeIn 0.5s ease forwards;
                animation-delay: calc(var(--delay) * 100ms);
            }

            .table tbody tr:hover {
                transform: translateY(-3px);
                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            }

            .table td {
                border: none;
                padding: 20px 15px;
                vertical-align: middle;
            }

            .switch {
                position: relative;
                display: inline-block;
                width: 60px;
                height: 34px;
            }

            .switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }

            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                transition: .4s;
                border-radius: 34px;
            }

            .slider:before {
                position: absolute;
                content: "O";
                display: flex;
                align-items: center;
                justify-content: center;
                height: 26px;
                width: 26px;
                left: 4px;
                bottom: 4px;
                background-color: white;
                transition: .4s;
                border-radius: 50%;
                color: #ccc;
                font-weight: bold;
                font-size: 12px;
            }

            input:checked + .slider {
                background-color: #2ecc71;
            }

            input:checked + .slider:before {
                transform: translateX(26px);
                content: "I";
                color: #86D293;
            }
            .btn-danger {
                background-color: var(--accent-color);
                border: none;
                border-radius: 50px;
                padding: 8px 15px;
                font-weight: 500;
                transition: all 0.3s ease;
            }

            .btn-danger:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(231, 76, 60, 0.3);
            }

            .form-control {
                border-radius: 20px;
                border: 2px solid #e0e0e0;
                padding: 8px 15px;
                transition: all 0.3s ease;
            }

            .form-control:focus {
                border-color: var(--primary-color);
                box-shadow: 0 0 0 0.2rem rgba(52, 152, 219, 0.25);
            }

            .input-group .input-group-text {
                border-radius: 0 20px 20px 0;
                background: var(--primary-color);
                color: white;
                border: none;
            }

            select.form-control {
                appearance: none;
                background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
                background-repeat: no-repeat;
                background-position: right 1rem center;
                background-size: 1em;
            }

            .role-badge {
                padding: 5px 12px;
                border-radius: 15px;
                font-size: 12px;
                font-weight: 600;
                display: inline-block;
                text-align: center;
                min-width: 80px;
                transition: all 0.3s ease;
                cursor: default;
            }

            .role-badge:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            }

            .role-owner {
                background-color: #D4BEE4;
                color: white;
            }

            .role-admin {
                background-color: #3498db;
                color: white;
            }

            .role-user {
                background-color: #C6E7FF;
                color: white;
            }

            .status-label {
                margin-left: 10px;
                font-size: 13px;
                font-weight: 600;
                padding: 5px 12px;
                border-radius: 15px;
                display: inline-block;
                min-width: 100px;
                text-align: center;
            }

            .text-success {
                background-color: rgba(46, 204, 113, 0.1);
                color: #2ecc71;
                border: 1px solid #2ecc71;
            }

            .text-danger {
                background-color: rgba(231, 76, 60, 0.1);
                color: #e74c3c;
                border: 1px solid #e74c3c;
            }

            .status-label:hover {
                transform: translateY(-2px);
                box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
                transition: all 0.3s ease;
                cursor: pointer;
            }


            [data-aos] {
                pointer-events: none;
            }

            [data-aos].aos-animate {
                pointer-events: auto;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            @keyframes pageLoad {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
        </style>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <jsp:include page="/page/admin/sidebar.jsp" />

            <div class="content-wrapper">
                <section class="content-header">
                    <div class="container-fluid">
                        <h1 class="mb-4" data-aos="fade-right">Quản lý Người dùng</h1>
                    </div>
                </section>

                <section class="content">
                    <div class="container-fluid">
                        <div class="card" data-aos="fade-up">
                            <div class="card-header">
                                <h3 class="card-title">Danh sách Người dùng</h3>
                            </div>
                            <div class="card-body">
                                <!-- Phần Filter -->
                                <div class="row mb-4" data-aos="fade-up" data-aos-delay="100">
                                    <div class="col-md-4 mb-3">
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="searchInput" 
                                                   placeholder="Tìm kiếm người dùng...">
                                            <div class="input-group-append">
                                                <span class="input-group-text">
                                                    <i class="fas fa-search"></i>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <select class="form-control" id="statusFilter">
                                            <option value="">Tất cả trạng thái</option>
                                            <option value="active">Đang hoạt động</option>
                                            <option value="banned">Đã khóa</option>
                                        </select>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <select class="form-control" id="roleFilter">
                                            <option value="">Tất cả vai trò</option>
                                            <option value="OWNER">Owner</option>
                                            <option value="ADMIN">Admin</option>
                                            <option value="USER">User</option>
                                        </select>
                                    </div>
                                </div>
                                <!-- Bảng người dùng -->
                                <table class="table" id="userTable" data-aos="fade-up" data-aos-delay="200">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Tên đăng nhập</th>
                                            <th>Email</th>
                                            <th>Vai trò</th>
                                            <th>Trạng thái</th>
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${users}" var="user" varStatus="status">
                                            <tr data-aos="fade-up" data-aos-delay="${200 + status.index * 50}">
                                                <td>${user.userID}</td>
                                                <td>${user.username}</td>
                                                <td>${user.email}</td>
                                                <td>
                                                    <span class="role-badge ${user.role == 'OWNER' ? 'role-owner' : 
                                                                              user.role == 'ADMIN' ? 'role-admin' : 'role-user'}"
                                                          data-toggle="tooltip" 
                                                          title="${user.role == 'OWNER' ? 'Quản trị cao nhất' : 
                                                                   user.role == 'ADMIN' ? 'Quản trị viên' : 'Người dùng'}">
                                                              ${user.role}
                                                          </span>
                                                    </td>
                                                    <td>
                                                        <label class="switch">
                                                            <input type="checkbox" class="status-toggle" 
                                                                   data-id="${user.userID}" 
                                                                   ${user.status == 1 ? 'checked' : ''} 
                                                                   onchange="toggleUserStatus(this)">
                                                            <span class="slider"></span>
                                                        </label>
                                                        <span class="status-label ${user.status == 1 ? 'text-success' : 'text-danger'}">
                                                            ${user.status == 1 ? 'Hoạt động' : 'Bị khóa'}
                                                        </span>
                                                    </td>
                                                    </td>
                                                    <td>
                                                        <button class="btn btn-danger btn-sm delete-user" data-id="${user.userID}">
                                                            <i class="fas fa-trash"></i> Xóa
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>

                <footer class="main-footer">
                    <strong>Copyright &copy; 2024 <a href="#">Unove Cinema</a>.</strong>
                    All rights reserved.
                </footer>
            </div>

        <!-- Scripts -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/js/adminlte.min.js"></script>
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>

        <script>
            // Khởi tạo AOS
            AOS.init({
                duration: 800,
                easing: 'ease-out',
                once: true,
                offset: 50,
                delay: 100
            });

            // Khởi tạo tooltips
            $(function () {
                $('[data-toggle="tooltip"]').tooltip();
            });

            // Hàm filter chính
            function filterTable() {
                const searchText = $('#searchInput').val().toLowerCase();
                const statusFilter = $('#statusFilter').val();
                const roleFilter = $('#roleFilter').val();

                $('#userTable tbody tr').each(function() {
                    const row = $(this);
                    const text = row.text().toLowerCase();
                    const status = row.find('.status-toggle').prop('checked') ? 'active' : 'banned';
                    const role = row.find('.role-badge').text().trim();

                    const searchMatch = text.includes(searchText);
                    const statusMatch = !statusFilter || status === statusFilter;
                    const roleMatch = !roleFilter || role === roleFilter;

                    if (searchMatch && statusMatch && roleMatch) {
                        row.show();
                    } else {
                        row.hide();
                    }
                });
            }

            // Xử lý sự kiện tìm kiếm và filter
            $(document).ready(function() {
                $('#searchInput').on('keyup', filterTable);
                $('#statusFilter, #roleFilter').change(filterTable);
            });

            // Hàm toggle trạng thái người dùng
            function toggleUserStatus(element) {
                const userId = $(element).data('id');
                const userRole = $(element).closest('tr').find('.role-badge').text().trim();
                const currentUserRole = '${sessionScope.user.role}';
                const isActive = $(element).prop('checked');
                const action = isActive ? 'unban' : 'ban';
                
                if (currentUserRole === 'ADMIN' && userRole === 'OWNER') {
                    Swal.fire({
                        title: 'Không thể thực hiện',
                        text: 'Admin không thể thay đổi trạng thái tài khoản Owner',
                        icon: 'error',
                        confirmButtonColor: '#3498db'
                    });
                    $(element).prop('checked', !isActive);
                    return;
                }
                
                Swal.fire({
                    title: 'Xác nhận',
                    text: isActive ? 'Bạn có muốn mở khóa người dùng này?' : 'Bạn có muốn khóa tài khoản người dùng này?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy',
                    confirmButtonColor: '#3498db',
                    cancelButtonColor: '#e74c3c'
                }).then((result) => {
                    if (result.isConfirmed) {
                        $.ajax({
                            url: '${pageContext.request.contextPath}/admin/users',
                            type: 'POST',
                            data: {
                                action: action,
                                userId: userId
                            },
                            success: function(response) {
                                if (response.success) {
                                    const statusLabel = $(element).closest('td').find('.status-label');
                                    statusLabel.text(isActive ? 'Hoạt động' : 'Bị khóa');
                                    statusLabel.removeClass('text-success text-danger')
                                             .addClass(isActive ? 'text-success' : 'text-danger');
                                    
                                    Swal.fire('Thành công!', response.message, 'success');
                                    filterTable();
                                } else {
                                    Swal.fire('Lỗi!', response.message, 'error');
                                    $(element).prop('checked', !isActive);
                                }
                            },
                            error: function(xhr, status, error) {
                                Swal.fire('Lỗi!', 'Có lỗi xảy ra khi xử lý yêu cầu', 'error');
                                $(element).prop('checked', !isActive);
                            }
                        });
                    } else {
                        $(element).prop('checked', !isActive);
                    }
                });
            }

            // Xử lý xóa người dùng
            $(document).ready(function() {
                $('.delete-user').click(function() {
                    const userId = $(this).data('id');
                    const userRole = $(this).closest('tr').find('.role-badge').text().trim();
                    const currentUserRole = '${sessionScope.user.role}';
                    
                    if (currentUserRole === 'ADMIN' && userRole === 'OWNER') {
                        Swal.fire({
                            title: 'Không thể xóa',
                            text: 'Admin không thể xóa tài khoản Owner',
                            icon: 'error',
                            confirmButtonColor: '#3498db'
                        });
                        return;
                    }
                    
                    Swal.fire({
                        title: 'Xác nhận xóa',
                        text: 'Bạn có chắc chắn muốn xóa người dùng này?',
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Xóa',
                        cancelButtonText: 'Hủy',
                        confirmButtonColor: '#e74c3c',
                        cancelButtonColor: '#7f8c8d'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            $.ajax({
                                url: '${pageContext.request.contextPath}/admin/users',
                                type: 'POST',
                                data: {
                                    action: 'delete',
                                    userId: userId
                                },
                                success: function(response) {
                                    if (response.success) {
                                        Swal.fire('Thành công!', response.message, 'success')
                                        .then(() => {
                                            location.reload();
                                        });
                                    } else {
                                        Swal.fire('Lỗi!', response.message, 'error');
                                    }
                                },
                                error: function(xhr, status, error) {
                                    Swal.fire('Lỗi!', 'Có lỗi xảy ra khi xử lý yêu cầu', 'error');
                                }
                            });
                        }
                    });
                });
            });
        </script>
    </body>
</html>