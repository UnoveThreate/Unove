<%@ page import="util.RouterURL"%>
<%@ page import="model.CinemaChain"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Manage Cinema Chain</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/css/adminlte.min.css">
        <!-- overlayScrollbars -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/overlayscrollbars/1.13.0/css/OverlayScrollbars.min.css">
        <!-- FullCalendar CSS -->
        <link href='https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.css' rel='stylesheet' />

        <style>
            .content-wrapper {
                padding: 20px;
                background-color: #f8f9fa;
            }
            .centered-content {
                text-align: center;
            }
            .cinema-name {
                font-family: 'Poppins', sans-serif;
                font-size: 24px;
                color: red;
                font-weight: bold;
            }
            .img-circle {
                border-radius: 50%;
                width: 130px;
                height: 130px;
                object-fit: cover;
            }
            .table {
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            /* Nút chính: Xanh dương đậm */
            .btn-primary, .btn-success {
                background-color: #007bff;
                border-color: #007bff;
                color: white;
            }
            .btn-primary:hover, .btn-success:hover {
                background-color: #0056b3; /* Màu xanh đậm hơn khi rê chuột */
                border-color: #0056b3;
            }
            /* Nút phụ: Xám đậm */
            .btn-secondary {
                background-color: #6c757d;
                border-color: #6c757d;
                color: white;
            }
            .btn-secondary:hover {
                background-color: #565e64; /* Màu xám đậm hơn khi rê chuột */
                border-color: #565e64;
            }
            .btn {
                margin: 5px;
                padding: 8px 12px;
                font-weight: bold;
                border-radius: 5px;
            }
        </style>

    </head>
    <body class="hold-transition sidebar-mini layout-fixed">
        <div class="wrapper">
            <nav class="main-header navbar navbar-expand navbar-white navbar-light">
                <!-- Left navbar links -->
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
                    </li>
                </ul>
            </nav>
            <!-- /.navbar -->

            <!-- Main Sidebar Container -->
            <jsp:include page="/page/ownerdashboard/sidebar.jsp" />

            <!-- Content Wrapper -->
            <div class="content-wrapper">
                <!-- Nội dung trang -->
                <section class="content">
                    <div class="container-fluid centered-content">
                        <h2>Manage Cinema Chain</h2>
                        <p class="cinema-name"><strong>${cinemaChain.name}</strong></p>
                        <p><strong>${cinemaChain.information}</strong></p>

                        <p><img src="${cinemaChain.avatarURL}" alt="Avatar" class="img-circle elevation-2"></p>
                        <h3>Danh sách Rạp Phim</h3>

                        <div class="d-flex justify-content-center mb-3">
                            <a href="<%= RouterURL.OWNER_CREATE_CINEMA%>?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-primary">Add New Cinema</a>
                            <a href="<%= RouterURL.OWNER_UPDATE_CINEMACHAIN%>?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-secondary">Edit CinemaChain</a>
                        </div>

                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th class="cinema-id-column">Cinema ID</th>
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
                                        <td class="cinema-id-column">${cinema.cinemaID}</td>
                                        <td>${cinema.name}</td> 
                                        <td>${cinema.address}</td>
                                        <td>${cinema.province}</td>
                                        <td>${cinema.district}</td>
                                        <td>${cinema.commune}</td>
                                        <td>
                                            <a href="<%= RouterURL.OWNER_EDIT_CINEMA%>?cinemaID=${cinema.cinemaID}" class="btn btn-primary">Edit</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </section>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/admin-lte@3.2/dist/js/adminlte.min.js"></script>
    </body>
</html>
