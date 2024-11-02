<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Người dùng</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="sidebar.jsp" />
    <div class="container mt-4">
        <h1 class="mb-4">Quản lý Người dùng</h1>
        
        <!-- Bảng danh sách người dùng -->
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên đăng nhập</th>
                    <th>Email</th>
                    <th>Họ tên</th>
                    <th>Vai trò</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.userID}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.fullName}</td>
                        <td>${user.role}</td>
                        <td>
                            <c:choose>
                                <c:when test="${!user.isBanned}">
                                    <span class="badge badge-success">Hoạt động</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-danger">Bị khóa</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <button class="btn btn-primary btn-sm edit-user" data-id="${user.userID}">
                                <i class="fas fa-edit"></i> Sửa
                            </button>
                            <c:if test="${!user.isBanned}">
                                <button class="btn btn-warning btn-sm ban-user" data-id="${user.userID}">
                                    <i class="fas fa-ban"></i> Khóa
                                </button>
                            </c:if>
                            <c:if test="${user.isBanned}">
                                <button class="btn btn-success btn-sm unban-user" data-id="${user.userID}">
                                    <i class="fas fa-unlock"></i> Mở khóa
                                </button>
                            </c:if>
                            <button class="btn btn-danger btn-sm delete-user" data-id="${user.userID}">
                                <i class="fas fa-trash"></i> Xóa
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Modal thêm/sửa người dùng -->
        <div class="modal fade" id="userModal" tabindex="-1" role="dialog" aria-labelledby="userModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="userModalLabel">Thêm/Sửa Người dùng</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="userForm">
                            <input type="hidden" id="userId" name="userId">
                            <div class="form-group">
                                <label for="username">Tên đăng nhập</label>
                                <input type="text" class="form-control" id="username" name="username" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                            <div class="form-group">
                                <label for="fullName">Họ tên</label>
                                <input type="text" class="form-control" id="fullName" name="fullName">
                            </div>
                            <div class="form-group">
                                <label for="role">Vai trò</label>
                                <select class="form-control" id="role" name="role">
                                    <option value="USER">Người dùng</option>
                                    <option value="ADMIN">Quản trị viên</option>
                                    <option value="OWNER">Chủ rạp</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="birthday">Ngày sinh</label>
                                <input type="date" class="form-control" id="birthday" name="birthday">
                            </div>
                            <div class="form-group">
                                <label for="address">Địa chỉ</label>
                                <input type="text" class="form-control" id="address" name="address">
                            </div>
                            <div class="form-group">
                                <label for="province">Tỉnh/Thành phố</label>
                                <input type="text" class="form-control" id="province" name="province">
                            </div>
                            <div class="form-group">
                                <label for="district">Quận/Huyện</label>
                                <input type="text" class="form-control" id="district" name="district">
                            </div>
                            <div class="form-group">
                                <label for="commune">Phường/Xã</label>
                                <input type="text" class="form-control" id="commune" name="commune">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                        <button type="button" class="btn btn-primary" id="saveUser">Lưu</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script>
        $(document).ready(function() {
            // Xử lý sự kiện khi nhấn nút Sửa
            $('.edit-user').click(function() {
                var userId = $(this).data('id');
                // Gọi API để lấy thông tin người dùng và điền vào form
                $.get('${pageContext.request.contextPath}/admin/users?action=get&userId=' + userId, function(user) {
                    $('#userId').val(user.userID);
                    $('#username').val(user.username);
                    $('#email').val(user.email);
                    $('#fullName').val(user.fullName);
                    $('#role').val(user.role);
                    $('#birthday').val(user.birthday);
                    $('#address').val(user.address);
                    $('#province').val(user.province);
                    $('#district').val(user.district);
                    $('#commune').val(user.commune);
                    $('#userModal').modal('show');
                });
            });

            // Xử lý sự kiện khi nhấn nút Lưu trong modal
            $('#saveUser').click(function() {
                var userData = $('#userForm').serialize();
                $.ajax({
                    url: '${pageContext.request.contextPath}/admin/users',
                    type: 'POST',
                    data: userData,
                    success: function(response) {
                        if (response.success) {
                            alert('Lưu thành công');
                            location.reload();
                        } else {
                            alert('Lỗi: ' + response.message);
                        }
                    },
                    error: function(xhr, status, error) {
                        alert('Có lỗi xảy ra: ' + error);
                    }
                });
            });

            // Xử lý sự kiện khi nhấn nút Khóa
            $('.ban-user').click(function() {
                var userId = $(this).data('id');
                if (confirm('Bạn có chắc chắn muốn khóa tài khoản người dùng này?')) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/admin/users',
                        type: 'POST',
                        data: {
                            action: 'ban',
                            userId: userId
                        },
                        success: function(response) {
                            if (response.success) {
                                alert(response.message);
                                location.reload();
                            } else {
                                alert('Lỗi: ' + response.message);
                            }
                        },
                        error: function(xhr, status, error) {
                            alert('Có lỗi xảy ra khi xử lý yêu cầu: ' + error);
                        }
                    });
                }
            });

            // Xử lý sự kiện khi nhấn nút Mở khóa
            $('.unban-user').click(function() {
                var userId = $(this).data('id');
                if (confirm('Bạn có chắc chắn muốn mở khóa người dùng này?')) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/admin/users',
                        type: 'POST',
                        data: {
                            action: 'unban',
                            userId: userId
                        },
                        success: function(response) {
                            if (response.success) {
                                alert('Mở khóa người dùng thành công');
                                location.reload();
                            } else {
                                alert('Lỗi: ' + response.message);
                            }
                        },
                        error: function(xhr, status, error) {
                            alert('Có lỗi xảy ra khi xử lý yêu cầu: ' + error);
                        }
                    });
                }
            });

            // Xử lý sự kiện khi nhấn nút Xóa
            $('.delete-user').click(function() {
                var userId = $(this).data('id');
                if (confirm('Bạn có chắc chắn muốn xóa người dùng này?')) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/admin/users',
                        type: 'POST',
                        data: {
                            action: 'delete',
                            userId: userId
                        },
                        success: function(response) {
                            if (response.success) {
                                alert('Xóa người dùng thành công');
                                location.reload();
                            } else {
                                alert('Lỗi: ' + response.message);
                            }
                        },
                        error: function(xhr, status, error) {
                            alert('Có lỗi xảy ra khi xử lý yêu cầu: ' + error);
                        }
                    });
                }
            });
        });
    </script>
</body>
</html>