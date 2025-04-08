<%-- 
    Document   : ManageDiscount
    Created on : Nov 2, 2024, 9:45:04 AM
    Author     : Kaan
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Giảm Giá Phim</title>
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
                margin-left: 250px; /* Adjust margin to accommodate sidebar */
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
                border: 3px dashed #3f51b5;
                padding: 3px;
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
                width: 100%;
                border-collapse: collapse;
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
                margin: 5px;
            }
            .btn-primary {
                background: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
                border: none;
                color: white;
            }
            .modal {
                display: none;
                position: fixed;
                z-index: 1050;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0, 0, 0, 0.5);
            }
            .modal-content {
                background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                border-radius: 15px;
                border: 3px dashed #E2BFD9;
                margin: 15% auto;
                padding: 20px;
                width: 80%;
                max-width: 500px;
            }
            .modal-header {
                background: linear-gradient(60deg, #1e3c72 0%, #2a5298 100%);
                color: white;
                border-radius: 15px 15px 0 0;
                padding: 15px 20px;
                text-align: center;
            }
            .form-control {
                background-color: rgba(255, 255, 255, 0.8);
                border-radius: 10px;
                border: 1px solid #ced4da;
                padding: 10px 15px;
                transition: all 0.3s ease;
                width: 100%;
                margin: 10px 0;
            }
            .form-control:focus {
                background-color: white;
                box-shadow: 0 0 0 0.2rem rgba(0,123,255,.25);
            }
            .close {
                cursor: pointer;
                float: right;
                font-size: 24px;
                font-weight: bold;
                color: white;
            }
            .close:hover {
                color: #ffdddd;
            }
        </style>
    </head>
    <body class="hold-transition sidebar-mini">   

        <jsp:include page="/page/admin/sidebar.jsp" />  
        <div class="content-wrapper">

            <div class="content-header">
                <h1>Manage Discounts</h1>
            </div>
            <div class="card">
                <div class="card-header">
                    <div class="card-title">Current Discounts</div>
                    <button class="btn btn-primary" onclick="openAddDiscountModal()">Add Discount</button>
                </div>
                <div class="card-body">
                    <table id="discountTable" class="table table-striped">
                        <thead>
                            <tr>
                                <th>Discount Code</th>
                                <th>Percentage</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="discount" items="${discounts}">
                                <tr>
                                    <td>${discount.discountCode}</td>
                                    <td>${discount.discountPercentage}</td>
                                    <td>${fn:substring(discount.startDate, 0, 10)}</td>
                                    <td>${fn:substring(discount.endDate, 0, 10)}</td>
                                    <td>${discount.status}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Modal for Adding Discount -->
        <div id="addDiscountModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>Add New Discount</h2>
                    <span class="close" onclick="closeAddDiscountModal()">&times;</span>
                </div>
                <div class="modal-body">
                    <form id="addDiscountForm">
                        <label for="discountCode">Discount Code</label>
                        <input type="text" id="discountCode" name="discountCode" class="form-control" required>

                        <label for="discountPercentage">Discount Percentage (%)</label>
                        <input type="number" id="discountPercentage" name="discountPercentage" class="form-control" min="0" max="100" required>

                        <label for="startDate">Start Date</label>
                        <input type="date" id="startDate" name="startDate" class="form-control" required 
                               min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>"
                               onchange="updateEndDateMin()">

                        <label for="endDate">End Date</label>
                        <input type="date" id="endDate" name="endDate" class="form-control" required 
                               min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>">


                        <button type="submit" class="btn btn-primary">Add Discount</button>
                    </form>
                </div>
            </div>
        </div>

        <script>
            function openAddDiscountModal() {
                document.getElementById('addDiscountModal').style.display = 'block';
            }

            function closeAddDiscountModal() {
                document.getElementById('addDiscountModal').style.display = 'none';
                document.getElementById('addDiscountForm').reset();
            }

            document.getElementById('addDiscountForm').addEventListener('submit', function (event) {
                event.preventDefault();

                // Tạo một FormData từ form để gửi thông tin đúng cách
                const formData = new FormData(this);

                // Chuyển FormData thành chuỗi URL encoded
                const urlEncodedData = new URLSearchParams(formData).toString();

                fetch('${pageContext.request.contextPath}/admin/discount/movieDiscount', {
                    method: 'POST',
                    body: urlEncodedData,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded' // Thiết lập loại dữ liệu là form-urlencoded
                    }
                })
                        .then(response => {
                            if (response.ok) {
                                return response.json();  // Giả sử servlet trả về JSON
                            } else {
                                throw new Error('Failed to add discount.');
                            }
                        })
                        .then(data => {
                            alert(data.message);  // Hiển thị thông điệp trả về từ servlet
                            closeAddDiscountModal();
                        })
                        .catch(error => {
                            console.error('Error:', error);
                            alert('An error occurred while adding the discount.');
                        });
            });

            function updateEndDateMin() {
                const startDate = document.getElementById('startDate').value;
                document.getElementById('endDate').min = startDate;
            }
        </script>


    </body>
</html>

