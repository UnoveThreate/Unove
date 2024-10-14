<%@page import="model.Movie"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jakarta.servlet.ServletContext" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Unove - Đặt vé xem phim</title>
    
    <!-- CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/page/movie/movie-style.css">
    
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
        }
        
        .navbar-custom {
            background-color: #343a40;
        }
        
        .navbar-custom .nav-link {
            color: #ffffff;
        }
        
        .carousel-item img {
            object-fit: cover;
            height: 500px;
        }
        
        .card {
            transition: transform 0.3s;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        
        .card:hover {
            transform: translateY(-5px);
        }
        
        .rating {
            position: absolute;
            bottom: 10px;
            right: 10px;
            background-color: rgba(0, 0, 0, 0.7);
            color: #ffc107;
            padding: 5px 10px;
            border-radius: 20px;
            font-weight: bold;
        }
        
        .trailer-button {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            opacity: 0;
            transition: opacity 0.3s;
        }
        
        .card:hover .trailer-button {
            opacity: 1;
        }
        
        footer {
            background-color: #343a40;
            color: #ffffff;
            padding: 40px 0;
        }
        
        footer a {
            color: #ffc107;
            text-decoration: none;
        }
        
        footer a:hover {
            color: #ffffff;
        }
    </style>
</head>
<body>

    <jsp:include page="Header.jsp" />

    <!-- Carousel -->
    <div id="mainCarousel" class="carousel slide" data-bs-ride="carousel">
        <!-- Carousel content here -->
    </div>

    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-custom my-4">
        <!-- Navigation content here -->
    </nav>

    <!-- Movie Cards -->
    <div class="container">
        <h2 class="text-center mb-4">Phim đang chiếu</h2>
        <div class="row">
            <c:forEach var="movie" items="${movies}">
                <div class="col-md-3 mb-4">
                    <div class="card h-100">
                        <img src="${movie.imageURL}" class="card-img-top" alt="${movie.title}">
                        <div class="rating">${movie.rating} <i class="fas fa-star"></i></div>
                        <div class="card-body">
                            <h5 class="card-title">${movie.title}</h5>
                            <a href="${movie.linkTrailer}" class="btn btn-outline-warning trailer-button">Xem trailer</a>
                        </div>
                        <div class="card-footer">
                            <a href="#" class="btn btn-danger w-100">Đặt vé</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- Promotional News -->
    <section class="bg-light py-5">
        <div class="container">
            <h2 class="text-center mb-4">Tin tức & Khuyến mãi</h2>
            <!-- Add promotional content here -->
        </div>
    </section>

    <!-- Footer -->
    <footer class="footer mt-5">
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <h5>Thông tin liên hệ</h5>
                    <p>Email: Unove@gmail.com</p>
                    <p>Điện thoại: (123) 456-7890</p>
                </div>
                <div class="col-md-4">
                    <h5>Liên kết nhanh</h5>
                    <ul class="list-unstyled">
                        <li><a href="#">Trang chủ</a></li>
                        <li><a href="#">Phim đang chiếu</a></li>
                        <li><a href="#">Đặt vé</a></li>
                        <li><a href="#">Liên hệ</a></li>
                    </ul>
                </div>
                <div class="col-md-4">
                    <h5>Về chúng tôi</h5>
                    <img src="${pageContext.request.contextPath}/page/image/logoHeader.png" alt="Logo" class="mb-2" style="max-width: 150px;">
                    <p>Chúng tôi cung cấp dịch vụ đặt vé xem phim trực tuyến nhanh chóng và tiện lợi.</p>
                </div>
            </div>
        </div>
    </footer>

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>
    
</body>
</html>