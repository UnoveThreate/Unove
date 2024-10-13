<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.CanteenItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý sản phẩm căn tin</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 20px;
        }
        .table th, .table td {
            vertical-align: middle;
        }
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.8);
            justify-content: center;
            align-items: center;
        }
        .modal-content {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            position: relative;
            text-align: center;
            max-width: 80%;
            margin: auto;
        }
        .close {
            position: absolute;
            top: 10px;
            right: 20px;
            color: #000;
            font-size: 30px;
            font-weight: bold;
            cursor: pointer;
        }
        .close:hover {
            color: red;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center">Quản lý sản phẩm căn tin</h1>

        <!-- Form thêm sản phẩm mới -->
        <form action="${pageContext.request.contextPath}/CanteenItemServlet" method="post" class="mb-4" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="cinemaID" value="${cinemaID}">
            <div class="form-row">
                <div class="form-group col-md-4">
                    <label for="name">Tên sản phẩm</label>
                    <input type="text" name="name" class="form-control" required>
                </div>
                <div class="form-group col-md-4">
                    <label for="price">Giá</label>
                    <input type="number" step="0.01" name="price" class="form-control" required>
                </div>
                <div class="form-group col-md-4">
                    <label for="stock">Số lượng</label>
                    <input type="number" name="stock" class="form-control" required>
                </div>
                <div class="form-group col-md-4">
                    <label for="status">Trạng thái</label>
                    <select name="status" class="form-control" required>
                        <option value="true">Còn hàng</option>
                        <option value="false">Hết hàng</option>
                    </select>
                </div>
                <div class="form-group col-md-4">
                    <label for="imageURL">Hình ảnh</label>
                    <input type="file" name="imageURL" class="form-control" accept="image/*" required>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Thêm sản phẩm</button>
        </form>

        <h2>Danh sách sản phẩm</h2>
        <div class="table-responsive">
            <table class="table table-bordered table-hover">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Tên</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Trạng thái</th>
                        <th>Hình ảnh</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Hiển thị danh sách sản phẩm -->
                    <c:forEach var="item" items="${canteenItems}">
                        <tr>
                            <td>${item.canteenItemID}</td>
                            <td>${item.name}</td>
                            <td>${item.price}</td>
                            <td>${item.stock}</td>
                            <td>${item.status}</td>
                            <td>
                                <img src="${item.imageURL}" alt="Canteen Item Image" class="img-fluid" style="max-width: 100px;" onclick="openModal('${item.imageURL}')">
                            </td>
                            <td>
                                <!-- Nút Update để mở modal chỉnh sửa -->
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#updateModal-${item.canteenItemID}">
                                    Sửa
                                </button>

                                <!-- Modal chỉnh sửa sản phẩm -->
                                <div class="modal fade" id="updateModal-${item.canteenItemID}" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel-${item.canteenItemID}">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">Chỉnh sửa sản phẩm</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="CanteenItemServlet?action=update" method="post">
                                                    <input type="hidden" name="canteenItemID" value="${item.canteenItemID}">
                                                    <input type="hidden" name="cinemaID" value="${cinemaID}">
                                                    
                                                    <!-- Tên -->
                                                    <div class="form-group">
                                                        <label for="name-${item.canteenItemID}">Tên sản phẩm</label>
                                                        <input type="text" class="form-control" id="name-${item.canteenItemID}" name="name" value="${item.name}">
                                                    </div>
                                                    
                                                    <!-- Số lượng -->
                                                    <div class="form-group">
                                                        <label for="stock-${item.canteenItemID}">Số lượng</label>
                                                        <input type="number" class="form-control" id="stock-${item.canteenItemID}" name="stock" value="${item.stock}">
                                                    </div>
                                                    
                                                    <!-- Trạng thái -->
                                                    <div class="form-group">
                                                        <label for="status-${item.canteenItemID}">Trạng thái</label>
                                                        <select class="form-control" id="status-${item.canteenItemID}" name="status">
                                                            <option value="true" ${item.status == 'true' ? 'selected' : ''}>Còn hàng</option>
                                                            <option value="false" ${item.status == 'false' ? 'selected' : ''}>Hết hàng</option>
                                                        </select>
                                                    </div>
                                                    
                                                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Form xóa sản phẩm -->
                                <form action="${pageContext.request.contextPath}/CanteenItemServlet" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="canteenItemID" value="${item.canteenItemID}">
                                    <input type="hidden" name="cinemaID" value="${cinemaID}">
                                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Modal để xem hình ảnh -->
        <div id="imageModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <img id="modalImage" src="" alt="Canteen Item Image" class="img-fluid">
            </div>
        </div>

    </div>

    <!-- Bootstrap JS và phụ thuộc -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <!-- Modal Script -->
    <script>
        function openModal(imageURL) {
            const modal = document.getElementById("imageModal");
            const modalImage = document.getElementById("modalImage");
            modalImage.src = imageURL;
            modal.style.display = "flex";
        }

        const closeBtn = document.querySelector(".close");
        closeBtn.addEventListener("click", () => {
            document.getElementById("imageModal").style.display = "none";
        });
    </script>
</body>
</html>
