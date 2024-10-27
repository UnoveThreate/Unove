<%@page import="util.RouterURL"%>
<%@page import="model.CinemaChain"%>
<jsp:include page="/page/owner/navbar.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Manage Cinema Chain</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet">

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
            }

            .container {
                max-width: 1200px;
                margin: 20px auto;
                padding: 30px;
                background-color: white;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                text-align: center; /* Căn giữa văn bản và hình ảnh theo chiều ngang */
                display: block;  /* Hiển thị ảnh như một block để margin hoạt động */
            }

            h2 {
                color: #333;
                font-size: 28px;
                margin-bottom: 20px;

            }

            h3 {
                color: #555;
                margin-top: 30px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 30px;
            }

            th, td {
                padding: 15px;
                text-align: left;
                border: 1px solid #ddd;
            }

            th {
                background-color: #4CAF50;
                color: white;
            }

            tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            a {
                text-decoration: none;
                padding: 8px 12px;
                border-radius: 5px;
            }
            .btn-secondary {
                background-color: #4CAF50;
                color: white;
            }

            .btn-secondary:hover {
                background-color: #5a6268;
            }

            p {
                color: #666;
                font-size: 16px;
            }

            img {
                width: 130px;
                height: 130px;
                border-radius:50%;
                object-fit:cover;
                object-position:50% 50%;
            }

            .no-cinema {
                color: #dc3545;
                font-style: italic;
            }

            .cinema-name {
                font-family: 'Poppins', sans-serif;  /* Đổi font chữ thành Poppins */
                font-size: 24px;  /* Kích thước chữ to hơn */
                color:red;   /* Màu chữ xanh (hoặc màu bạn thích) */
                font-weight: bold; /* Làm chữ đậm */
                text-align: center; /* Căn giữa chữ nếu muốn */
            }

            /* CSS để ẩn cột Cinema ID */
            .cinema-id-column {
                display: none; /* Ẩn cột CinemaID */
            }

            .bta-1 {
                margin-bottom:4px;
                color: white;
            }

        </style>
    </head>
    <body>
        <div class="container">

            <h2>Manage Cinema Chain</h2>
            <p class="cinema-name"> <strong></strong> ${cinemaChain.name}</p>
            <p><strong></strong> ${cinemaChain.information}</p>
            <p><strong></strong> <img src="${cinemaChain.avatarURL}" alt="Avatar"></p>

            <h3>Danh sách Rạp Phim</h3>

            <div class="d-flex justify-content-between bta-1">
                <a href="<%= RouterURL.OWNER_CREATE_CINEMA%>?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-success">Add New Cinema</a>
                <a href="<%= RouterURL.OWNER_UPDATE_CINEMACHAIN%>?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-success">Edit CinemaChain</a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th class="cinema-id-column">Cinema ID</th> <!-- Ẩn tiêu đề cột Cinema ID -->
                        <th>Name</th> 
                        <th>Address</th>
                        <th>Province</th>
                        <th>District</th>
                        <th>Commune</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cinema" items="${cinemas}">
                        <tr>
                            <td class="cinema-id-column">${cinema.cinemaID}</td> <!-- Ẩn giá trị Cinema ID -->
                            <td>${cinema.name}</td> 
                            <td>${cinema.address}</td>
                            <td>${cinema.province}</td>
                            <td>${cinema.district}</td>
                            <td>${cinema.commune}</td>
                            <td>
                                <a href="<%= RouterURL.MANAGE_CINEMA_DETAIL%>?cinemaID=${cinema.cinemaID}" class="btn btn-secondary">Manage Cinema</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Bootstrap JS and Popper.js -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>
