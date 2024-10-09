<%@page import="model.Movie"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jakarta.servlet.ServletContext" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Movie Booking</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        <!-- Slick Carousel -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css">

        <!-- Custom Styles -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/movie/movie-style.css">

        <style>
            body {
                background-color: #f8f9fa;
            }

            .carousel-item img {
                max-height: 500px; /* Set max height for carousel images */
                object-fit: cover; /* Ensure images cover the area */
            }

            .titlee {
                text-align: center;
                font-size: 28px;
                color: #333;
                font-weight: bold;
                text-transform: uppercase;
                letter-spacing: 2px;
                margin: 40px 0;
            }

            .card {
                transition: transform 0.2s;
                position: relative;
                overflow: hidden;
                border-radius: 8px; /* Rounded corners */
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
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

            footer {
                background-color: whitesmoke;
                color: gray;
                padding: 20px 0;
            }

            @media (max-width: 768px) {
                .card {
                    width: 100%; /* Full width on small screens */
                }
            }
        </style>
    </head>
    <body>

        <jsp:include page="Header.jsp" />

        <div id="carouselExampleDark" class="carousel carousel-dark slide mb-4" data-bs-ride="carousel" data-bs-interval="5000" style="margin: auto; max-width: 1300px;">
            <div class="carousel-inner">
                <div class="carousel-item active" data-bs-interval="10000">
                    <img src="https://wallpapercave.com/wp/wp4770368.jpg" class="d-block w-100" alt="Transformers" style="margin-top: 33px; object-fit: cover;">
                </div>
                <div class="carousel-item" data-bs-interval="2000">
                    <img src="https://th.bing.com/th/id/R.1952345dbe02e31f379574b804c3326d?rik=7mG8AeKPzMWpzA&pid=ImgRaw&r=0" class="d-block w-100" alt="It Ends With Us" style="margin-top: 33px; object-fit: cover;">
                </div>
                <div class="carousel-item">
                    <img src="https://th.bing.com/th/id/R.32d5fc46d9546769f5f2bf31595499ea?rik=PP%2f0oFfvJ%2bTitw&pid=ImgRaw&r=0" class="d-block w-100" alt="Transformers" style="margin-top: 33px; object-fit: cover;">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>



        <h2 class="titlee">FILM</h2>

        <div class="container">
            <div class="row">
                <c:if test="${empty movies}">
                    <p style="color:red;text-align: center">Không có phim nào để hiển thị.</p>
                </c:if>

                <c:forEach var="movie" items="${movies}">
                    <div class="col-md-4 mb-4">
                        <div class="card">
                            <img src="${movie.imageURL}" class="card-img-top" alt="${movie.title}">
                            <div class="rating">${movie.rating} ★</div>
                            <div class="trailer-button">
                                <a href="${movie.linkTrailer}" class="btn btn-outline-warning">Xem trailer</a>
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">${movie.title}</h5>
                                <a href="#" class="btn btn-danger">Buy ticket</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <footer>
            <div class="container-fluid">
                <div class="row text-center">
                    <div class="col-md-4">
                        <h5>Contact Information</h5>
                        <p>Email: Unove@gmail.com</p>
                        <p>Phone: (123) 456-7890</p>
                    </div>
                    <div class="col-md-4">
                        <h5>Quick Links</h5>
                        <ul class="list-unstyled">
                            <li><a href="#" style="color: gray;">Home page</a></li>
                            <li><a href="#" style="color: gray;">Movies Showing</a></li>
                            <li><a href="#" style="color: gray;">Book Tickets</a></li>
                            <li><a href="#" style="color: gray;">Contact</a></li>
                        </ul>
                    </div>
                    <div class="col-md-4">
                        <h5>About Us</h5>
                        <p>We provide fast and convenient online movie ticket booking service.</p>
                    </div>
                </div>
                <div class="text-center" style="margin-top: 20px;">
                    <p>&copy; 2024 Your Company. All rights reserved.</p>
                </div>
            </div>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>

    </body>
</html>
