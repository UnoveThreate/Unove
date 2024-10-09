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
                                <div class="btn-group" role="group">
                                    <form action="${pageContext.request.contextPath}/CanteenItemServlet" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="canteenItemID" value="${item.canteenItemID}">
                                        <input type="hidden" name="cinemaID" value="${cinemaID}">
                                        <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                    </form>
                                    <a href="${pageContext.request.contextPath}/CanteenItemServlet?action=update&canteenItemID=${item.canteenItemID}&cinemaID=${cinemaID}" class="btn btn-warning btn-sm">Sửa</a>
                                </div>
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
        closeBtn.onclick = function () {
            closeModal();
        };

        window.onclick = function (event) {
            const modal = document.getElementById("imageModal");
            if (event.target == modal) {
                closeModal();
            }
        };

        function closeModal() {
            const modal = document.getElementById("imageModal");
            modal.style.display = "none";
        }
    </script>
</body>
</html>
