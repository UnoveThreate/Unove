<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý Chuỗi Rạp</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/css/adminlte.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
    <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            font-family: 'Source Sans Pro', sans-serif;
        }
        .content-wrapper {
            background: transparent;
            padding: 20px;
        }
        .content-header h1 {
            font-size: 2.5rem;
            color: #2c3e50;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
            text-align: center;
            font-weight: 700;
            letter-spacing: 1px;
        }
        .card {
            background: rgba(255, 255, 255, 0.9);
            backdrop-filter: blur(10px);
            border-radius: 15px;
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
            overflow: hidden;
            transition: all 0.3s ease;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 40px 0 rgba(31, 38, 135, 0.47);
        }
        .card-header {
            background: linear-gradient(60deg, #1e3c72 0%, #2a5298 100%);
            color: white;
            border-radius: 15px 15px 0 0;
            padding: 15px 20px;
        }
        .card-title {
            font-size: 1.5rem;
            font-weight: 600;
        }
        .table {
            margin-bottom: 0;
        }
        .table thead th {
            background-color: #3f51b5;
            color: white;
            border: none;
            padding: 12px;
            font-size: 0.9rem;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        .table-striped tbody tr:nth-of-type(odd) {
            background-color: rgba(0, 0, 0, 0.02);
        }
        .table td {
            vertical-align: middle;
            padding: 12px;
        }
        .btn {
            border-radius: 20px;
            padding: 8px 16px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            transition: all 0.3s ease;
        }
        .btn-primary {
            background: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
            border: none;
        }
        .btn-info {
            background: linear-gradient(to right, #667eea 0%, #764ba2 100%);
            border: none;
        }
        .btn-danger {
            background: linear-gradient(to right, #ff416c 0%, #ff4b2b 100%);
            border: none;
        }
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .modal-content {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            border-radius: 15px;
        }
        .modal-header {
            background: linear-gradient(60deg, #1e3c72 0%, #2a5298 100%);
            color: white;
            border-radius: 15px 15px 0 0;
            padding: 15px 20px;
        }
        .modal-title {
            font-weight: 600;
        }
        .form-control {
            background-color: rgba(255, 255, 255, 0.8);
            border-radius: 10px;
            border: 1px solid #ced4da;
            padding: 10px 15px;
            transition: all 0.3s ease;
        }
        .form-control:focus {
            background-color: white;
            box-shadow: 0 0 0 0.2rem rgba(0,123,255,.25);
        }
        .avatar-img {
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 50%;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }
        .avatar-img:hover {
            transform: scale(1.1);
        }
        .dataTables_wrapper .dataTables_paginate .paginate_button {
            padding: 0.5em 1em;
            margin: 0 0.2em;
            border-radius: 20px;
        }
        .dataTables_wrapper .dataTables_paginate .paginate_button.current {
            background: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
            border: none;
            color: white !important;
        }
        .dataTables_wrapper .dataTables_filter input {
            border-radius: 20px;
            padding: 5px 10px;
        }

        /* CSS cho notification popup */
        .notification-popup {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: white;
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 5px 30px rgba(0, 0, 0, 0.2);
            z-index: 1050;
            display: none;
            animation: fadeIn 0.3s ease;
            min-width: 350px;
        }

        .notification-header {
            margin-bottom: 20px;
        }

        .notification-header h4 {
            color: #2c3e50;
            margin: 0;
            font-weight: 600;
        }

        .notification-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 12px;
            background: #f8f9fa;
            border-radius: 8px;
            margin-bottom: 10px;
        }

        .notification-label {
            color: #666;
            font-weight: 500;
        }

        .notification-value {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .copy-btn {
            background: none;
            border: none;
            color: #007bff;
            cursor: pointer;
            padding: 5px;
            transition: all 0.3s ease;
        }

        .copy-btn:hover {
            color: #0056b3;
            transform: scale(1.1);
        }

        .notification-footer {
            text-align: right;
            margin-top: 20px;
        }

        .close-btn {
            background: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
            color: white;
            border: none;
            padding: 8px 20px;
            border-radius: 20px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .close-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        .copy-tooltip {
            position: fixed;
            background: #333;
            color: white;
            padding: 5px 10px;
            border-radius: 5px;
            font-size: 12px;
            pointer-events: none;
            opacity: 0;
            transition: opacity 0.3s ease;
            z-index: 1060;
        }

        .backdrop {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            z-index: 1040;
            display: none;
        }
    </style>
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    
    <jsp:include page="/page/admin/sidebar.jsp" />

    <div class="content-wrapper">
        <section class="content-header">
            <div class="container-fluid">
                <h1 data-aos="fade-right">Quản Lí Chuỗi Rạp</h1>
            </div>
        </section>

        <section class="content">
            <div class="container-fluid">
                <div class="card" data-aos="fade-up">
                    <div class="card-header">
                        <h3 class="card-title">Danh sách Chuỗi Rạp</h3>
                        <button type="button" class="btn btn-primary float-right" data-toggle="modal" data-target="#addCinemaChainModal">
                            Thêm Chuỗi Rạp
                        </button>
                    </div>
                    <div class="card-body">
                        <table id="cinemaChainTable" class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Tên Chuỗi Rạp</th>
                                    <th>Avatar</th>
                                    <th>Thông tin</th>
                                    <th>Số lượng Rạp</th>
                                    <th>Thao Tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="chain" items="${cinemaChains}" varStatus="status">
                                    <tr data-aos="fade-up" data-aos-delay="${status.index * 100}">
                                        <td>${chain.cinemaChainID}</td>
                                        <td>${chain.name}</td>
                                        <td><img src="${chain.avatarURL}" alt="Avatar" class="avatar-img"></td>
                                        <td>${chain.information}</td>
                                        <td>${cinemaCounts[chain.cinemaChainID]}</td>
                                        <td>
                                            <button type="button" class="btn btn-sm btn-info edit-cinema-chain" 
                                                    data-id="${chain.cinemaChainID}" 
                                                    data-name="${chain.name}"
                                                    data-avatar="${chain.avatarURL}"
                                                    data-info="${chain.information}">Sửa</button>
                                            <button type="button" class="btn btn-sm btn-danger delete-cinema-chain" 
                                                    data-id="${chain.cinemaChainID}">Xóa</button>
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

<!-- Add Cinema Chain Modal -->
<div class="modal fade" id="addCinemaChainModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thêm Chuỗi Rạp Mới</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="addCinemaChainForm">
                <div class="modal-body">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="chainName">Tên Chuỗi Rạp</label>
                        <input type="text" class="form-control" id="chainName" name="chainName" required>
                    </div>
                    <div class="form-group">
                        <label for="avatarURL">URL Avatar</label>
                        <input type="text" class="form-control" id="avatarURL" name="avatarURL">
                    </div>
                    <div class="form-group">
                        <label for="information">Thông tin</label>
                        <textarea class="form-control" id="information" name="information" rows="3"></textarea>
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

<!-- Edit Cinema Chain Modal -->
<div class="modal fade" id="editCinemaChainModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Sửa Chuỗi Rạp</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="editCinemaChainForm">
                <div class="modal-body">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" id="editCinemaChainID" name="cinemaChainID">
                    <div class="form-group">
                        <label for="editChainName">Tên Chuỗi Rạp</label>
                        <input type="text" class="form-control" id="editChainName" name="chainName" required>
                    </div>
                    <div class="form-group">
                        <label for="editAvatarURL">URL Avatar</label>
                        <input type="text" class="form-control" id="editAvatarURL" name="avatarURL">
                    </div>
                    <div class="form-group">
                        <label for="editInformation">Thông tin</label>
                        <textarea class="form-control" id="editInformation" name="information" rows="3"></textarea>
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

<!-- Notification Popup -->
<div class="backdrop" id="notificationBackdrop"></div>
<div class="notification-popup" id="accountNotification">
    <div class="notification-header">
        <h4>Tài khoản owner mới</h4>
    </div>
    <div class="notification-content">
        <div class="notification-item">
            <span class="notification-label">Tên đăng nhập:</span>
            <div class="notification-value">
                <span id="ownerUsername"></span>
                <button class="copy-btn" onclick="copyText('ownerUsername')">
                    <i class="fas fa-copy"></i>
                </button>
            </div>
        </div>
        <div class="notification-item">
            <span class="notification-label">Mật khẩu tạm thời:</span>
            <div class="notification-value">
                <span id="ownerPassword"></span>
                <button class="copy-btn" onclick="copyText('ownerPassword')">
                    <i class="fas fa-copy"></i>
                </button>
            </div>
        </div>
        <div class="notification-item">
            <span class="notification-label">Email:</span>
            <div class="notification-value">
                <span id="ownerEmail"></span>
                <button class="copy-btn" onclick="copyText('ownerEmail')">
                    <i class="fas fa-copy"></i>
                </button>
            </div>
        </div>
    </div>
    <div class="notification-footer">
        <button class="close-btn" onclick="closeNotification()">Đóng</button>
    </div>
</div>

<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/js/adminlte.min.js"></script>
<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>

<script>
$(document).ready(function() {
    AOS.init({
        duration: 1000,
        once: true
    });

    var table = $('#cinemaChainTable').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "responsive": true,
        "language": {
            "url": "${pageContext.request.contextPath}/resources/js/Vietnamese.json"
        }
    });

    $('#addCinemaChainForm').submit(function(e) {
        e.preventDefault();
        $.ajax({
            url: '${pageContext.request.contextPath}/admin/cinemaChains',
            type: 'POST',
            data: $(this).serialize(),
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    $('#addCinemaChainModal').modal('hide');
                    showAccountNotification(
                        response.ownerUsername,
                        response.tempPassword,
                        response.ownerEmail
                    );
                } else {
                    alert(response.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("Error details:", xhr.responseText);
                alert('Có lỗi xảy ra khi thêm chuỗi rạp: ' + error);
            }
        });
    });

    $(document).on('click', '.edit-cinema-chain', function() {
        var cinemaChainID = $(this).data('id');
        var chainName = $(this).data('name');
        var avatarURL = $(this).data('avatar');
        var information = $(this).data('info');
        
        $('#editCinemaChainID').val(cinemaChainID);
        $('#editChainName').val(chainName);
        $('#editAvatarURL').val(avatarURL);
        $('#editInformation').val(information);
        $('#editCinemaChainModal').modal('show');
    });

    $('#editCinemaChainForm').submit(function(e) {
        e.preventDefault();
        $.ajax({
            url: '${pageContext.request.contextPath}/admin/cinemaChains',
            type: 'POST',
            data: $(this).serialize(),
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    alert(response.message);
                    $('#editCinemaChainModal').modal('hide');
                    location.reload();
                } else {
                    alert(response.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("Error details:", xhr.responseText);
                alert('Có lỗi xảy ra khi cập nhật chuỗi rạp: ' + error);
            }
        });
    });

    $(document).on('click', '.delete-cinema-chain', function() {
        var cinemaChainID = $(this).data('id');
        if (confirm('Bạn có chắc chắn muốn xóa chuỗi rạp này?')) {
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/cinemaChains',
                type: 'POST',
                data: { 
                    action: 'delete', 
                    cinemaChainID: cinemaChainID 
                },
                dataType: 'json',
                success: function(response) {
                    if (response.success) {
                        alert(response.message);
                        location.reload();
                    } else {
                        alert(response.message);
                    }
                },
                error: function(xhr, status, error) {
                    console.error("Error details:", xhr.responseText);
                    alert('Có lỗi xảy ra khi xóa chuỗi rạp: ' + error);
                }
            });
        }
    });
});

// Notification functions
function showAccountNotification(username, password, email) {
    document.getElementById('ownerUsername').textContent = username;
    document.getElementById('ownerPassword').textContent = password;
    document.getElementById('ownerEmail').textContent = email;
    document.getElementById('accountNotification').style.display = 'block';
    document.getElementById('notificationBackdrop').style.display = 'block';
}

function closeNotification() {
    document.getElementById('accountNotification').style.display = 'none';
    document.getElementById('notificationBackdrop').style.display = 'none';
    location.reload();
}

function copyText(elementId) {
    const text = document.getElementById(elementId).textContent;
    navigator.clipboard.writeText(text).then(() => {
        showTooltip('Đã sao chép!');
    });
}

function showTooltip(message) {
    const tooltip = document.createElement('div');
    tooltip.className = 'copy-tooltip';
    tooltip.textContent = message;
    
    document.body.appendChild(tooltip);
    
    const mouseX = event.clientX;
    const mouseY = event.clientY;
    tooltip.style.left = `${mouseX + 10}px`;
    tooltip.style.top = `${mouseY - 20}px`;
    
    setTimeout(() => tooltip.style.opacity = '1', 0);
    
    setTimeout(() => {
        tooltip.style.opacity = '0';
        setTimeout(() => document.body.removeChild(tooltip), 300);
    }, 1500);
}
</script>
</body>
</html>