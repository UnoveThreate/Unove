<%@page import="model.owner.Movie"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jakarta.servlet.ServletContext" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Unove</title>
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <!-- Slick Carousel -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css">

        <!-- Custom Styles -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/movie/movie-style.css">

        <!-- Inline Styling for responsiveness -->
        <style>
            @media only screen and (min-width:768px){
                .nav-item.dropdown:hover .dropdown-menu {
                    display: block;
                }
                .dropdown > .dropdown-toggle:active {
                    pointer-events: none;
                }
            }

            /* Carousel responsive adjustments */
            .carousel-inner img {
                object-fit: cover;
                height: 500px;
            }

            /* Responsive film cards */
            .card {
                transition: transform 0.2s;
                position: relative;
                overflow: hidden;
                margin-bottom: 20px;
            }

            .card:hover {
                transform: scale(1.05);
            }

            .trailer-button {
                display: none;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }

            .card:hover .trailer-button {
                display: block;
            }

            .rating {
                position: absolute;
                bottom: 10px;
                left: 10px;
                background-color: rgba(0, 0, 0, 0.7);
                color: white;
                padding: 5px;
                border-radius: 5px;
                font-size: 14px;
            }

            /* Navbar Responsiveness */
            .navbar-custom {
                background-color: #343a40;
            }

            .navbar-custom .nav-link {
                color: #fff;
            }

            /* Footer styling */
            footer {
                background-color: whitesmoke;
                color: gray;
                padding: 20px 0;
            }

            footer .col-md-4 {
                margin-bottom: 20px;
            }

            /* Responsive footer icons */
            .icon-logo_header {
                max-width: 150px;
                height: auto;
            }

        </style>
    </head>

    <body>

        <jsp:include page="Header.jsp" />

        <!-- Carousel Section -->
        <div id="carouselExampleDark" class="carousel slide" data-bs-ride="carousel" style="width: 100%; max-width: 1300px; margin: 0 auto;">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="0" class="active"></button>
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="1"></button>
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="2"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="https://cdn.galaxycine.vn/media/2024/8/13/transformers-2048_1723544458749.jpg" class="d-block w-100" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="https://cdn.galaxycine.vn/media/2024/9/17/it-end-with-us-2048_1726546444253.jpg" class="d-block w-100" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="https://cdn.galaxycine.vn/media/2024/8/13/transformers-2048_1723544458749.jpg" class="d-block w-100" alt="...">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
            </button>
        </div>

        <!-- Film Section -->
        <div class="container mt-5">
            <h2 class="text-center text-secondary mb-4">FILM</h2>

            <nav class="navbar navbar-expand-lg navbar-custom mb-3">
                <div class="container-fluid">
                    <div class="navbar-nav mx-auto">
                        <a class="nav-link" href="#">MONDAY</a>
                        <a class="nav-link" href="#">TUESDAY</a>
                        <a class="nav-link" href="#">WEDNESDAY</a>
                        <a class="nav-link" href="#">THURSDAY</a>
                        <a class="nav-link" href="#">FRIDAY</a>
                        <a class="nav-link" href="#">SATURDAY</a>
                        <a class="nav-link" href="#">SUNDAY</a>
                    </div>
                </div>
            </nav>

                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav" style="display: flex; justify-content: space-around; width: 100%;">
                        <a class="nav-link">MONDAY</a>
                        <a class="nav-link">TUESDAY</a>
                        <a class="nav-link">WEDNESDAY</a>
                        <a class="nav-link">THURSDAY</a>
                        <a class="nav-link">FRIDAY</a>
                        <a class="nav-link">SATURDAY</a>
                        <a class="nav-link">SUNDAY</a>
                    </div>
                </div>
            </div>
        </nav>

        <br>

        <div style="display: flex; flex-wrap: wrap; gap: 16px;">
            <c:if test="${empty movies}">
                <p style="color:red;text-align: center">Không có phim nào để hiển thị.</p>
            </c:if>

            <c:forEach var="movie" items="${movies}">
                <div class="card" style="width: 18rem;position: relative; overflow: hidden;">
                    <img src="${movie.imageURL}" class="card-img-top" >
                    <!-- Hiển thị rating ở góc dưới bên trái -->
                    <div class="rating" style="position: absolute; bottom: 10px; left: 230px; background-color: rgba(0, 0, 0, 0.7); color: white; padding: 5px; border-radius: 5px; white-space: nowrap;margin-bottom: 9px">
                        ${movie.rating} ★
                    </div>
                    <div class="trailer-button">
                        <a href="/Unove/HandleDisplayMovieInfo?movieID=${movie.movieID}" class="btn btn-outline-warning">Chi Tiết</a>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">${movie.title}</h5>
                        <a href="#" class="btn btn-danger">Buy ticket</a>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Footer -->
        <footer class="text-center text-lg-start bg-light text-muted mt-5">
            <div class="container p-4">
                <div class="row">
                    <div class="col-lg-4 col-md-6 mb-4 mb-md-0">
                        <h5>Contact Information</h5>
                        <p>Email: Unove@gmail.com</p>
                        <p>Phone: (123) 456-7890</p>
                    </div>
                    <div class="col-lg-4 col-md-6 mb-4 mb-md-0">
                        <h5>Quick Links</h5>
                        <ul class="list-unstyled">
                            <li><a href="#" class="text-dark">Home Page</a></li>
                            <li><a href="#" class="text-dark">Movies Showing</a></li>
                            <li><a href="#" class="text-dark">Book Tickets</a></li>
                            <li><a href="#" class="text-dark">Contact</a></li>
                        </ul>
                    </div>
                    <div class="col-lg-4 col-md-6 mb-4 mb-md-0">
                        <h5>About Us</h5>
                        <img class="icon-logo_header" src="${pageContext.request.contextPath}/page/image/logoHeader.png" alt="Logo">
                        <p>We provide fast and convenient online movie ticket booking services.</p>
                    </div>
                </div>
            </div>
            <div class="text-center p-3">
                &copy; 2024 Unove. All rights reserved.
            </div>
        </footer>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>
    </body>
</html>