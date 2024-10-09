<%@page import="model.Movie"%>
<%@page import="java.util.List"%>


<!--java import-->

<%@ page import="java.util.ArrayList" %>
<%@ page import="jakarta.servlet.ServletContext" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Unove</title>
        <!--link font icon :-->  
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!--link boostrap :-->  
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">


        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/movie/movie-style.css">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

        <style type="text/css">
            @media only screen and (min-width:768px){
                .nav-item.dropdown:hover .dropdown-menu{
                    display:block;
                }
                .dropdown>.dropdown-toggle:active {
                    /*Without this, clicking will make it sticky*/
                    pointer-events: none;
                }
            }
        </style>

        <style>

            .titlee {
                text-align: center;
                font-size: 28px; /* Kích thước chữ */
                color: #333; /* Màu chữ */
                font-weight: bold; /* Đậm */
                text-transform: uppercase; /* Chuyển đổi thành chữ in hoa */
                letter-spacing: 2px; /* Khoảng cách giữa các ký tự */
                margin: 59px 0;
            }



        </style>


        <!--Include necessary CSS and JavaScript files only once--> 
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/movie/movie-style.css">



        <style>
            iframe {
                width: 100%;
                height: 250px;
            }
        </style>

        <style>
            .card:hover .trailer-button {
                display: block;
            }

            .card {
                transition: transform 0.2s;
                position: relative; /* Đảm bảo rằng card có position relative để định vị đúng cho trailer-button */
                overflow: hidden;
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

            .rating {
                position: absolute;
                bottom: 10px;
                left: 10px;
                background-color: rgba(0, 0, 0, 0.7);
                color: white;
                padding: 5px;
                border-radius: 5px;
                font-size: 14px; /* Thay đổi kích thước chữ nếu cần */
            }
        </style>

    </head>
    <body>

        <jsp:include page="Header.jsp" />



        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

        <!--phan Quang VInh :--> 
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>
        <br>
        <br>
        <div id="carouselExampleDark" class="carousel carousel-dark slide" style="height: 500px;width: 1300px; margin-left:100px">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="2" aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active" data-bs-interval="10000">
                    <img src="https://cdn.galaxycine.vn/media/2024/8/13/transformers-2048_1723544458749.jpg" style="height: 500px;width: 1000px" class="d-block w-100" alt="...">
                    <div class="carousel-caption d-none d-md-block">
                    </div>
                </div>
                <div class="carousel-item" data-bs-interval="2000">
                    <img src="https://cdn.galaxycine.vn/media/2024/9/17/it-end-with-us-2048_1726546444253.jpg"  style="height: 500px;width: 1000px" class="d-block w-100" alt="...">
                    <div class="carousel-caption d-none d-md-block">
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="https://cdn.galaxycine.vn/media/2024/8/13/transformers-2048_1723544458749.jpg"  style="height: 500px;width: 1000px" class="d-block w-100" alt="...">
                    <div class="carousel-caption d-none d-md-block">
                    </div>
                </div>
            </div>
            <button style="color: white" class="carousel-control-prev" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button style="color: white" class="carousel-control-next" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
        <br>
        <br>
        <nav class="navbar navbar-expand-lg navbar-custom" style="width: 1000px; margin-left: 270px;background-color: #343a40;">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav" style="display: flex; justify-content: space-around; width: 100%;">
                        <a class="nav-link">1.SELECT CINEMA</a>
                        <a class="nav-link">2.SELECT MOVIE</a>
                        <a class="nav-link">3.SELECT DAY</a>
                        <a class="nav-link">4.SELECT SHOWTIME</a>
                        <button type="button" class="btn btn-outline-warning">BUY TICKET FAST</button>
                    </div>
                </div>
            </div>
        </nav>
        <hr>
        <!--thanh navbar dưới carousel-->
        <h2 style="margin-left:270px;color: grey">FILM</h2>
        <nav class="navbar navbar-expand-lg navbar-custom" style="width: 1000px; margin-left: 270px;">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

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
        <!--card phim-->
        <!--        <div class="card" style="width: 18rem;margin-left: 200px">
                    <img src="..." class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>-->

        <!--card movie-->
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

    </body>
    <br>
    <hr>
    <!--    //GÓC ĐIỆN ẢNH-->
    <nav class="navbar navbar-expand-lg bg-body-tertiary" style="width:1300px;margin-left: 150px">
        <div class="container-fluid">
            <a class="navbar-brand" href="#" style="color:yellow">PROMOTIONAL NEWS </a>
            <form class="d-flex" role="search">
                <input class="form-control me-2" type="search" placeholder="DISCOUNT VOUCHER" aria-label="Search">
                <button class="btn btn-outline-warning" type="submit">SEARCH</button>
            </form>
        </div>
    </div>
</nav>
<!--footer-->
<footer class="footer" style="background-color: whitesmoke; color: gray; padding: 20px 0;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-4 text-center">
                <h5>Contact information</h5>
                <p>Email: Unove@gmail.com</p>
                <p>Phone: (123) 456-7890</p>
            </div>
            <div class="col-md-4 text-center">
                <h5>Quick Links</h5>
                <ul style="list-style: none; padding: 0;">
                    <li><a href="#" style="color: gray; text-decoration: none;">Home page</a></li>
                    <li><a href="#" style="color: gray; text-decoration: none;">Movie is showing</a></li>
                    <li><a href="#" style="color: gray; text-decoration: none;">Book tickets</a></li>
                    <li><a href="#" style="color: gray; text-decoration: none;">Contact</a></li>
                </ul>
            </div>
            <div class="col-md-4 text-center">
                <h5>About us</h5>
                <img class="icon-logo_header" src="${pageContext.request.contextPath}/page/image/logoHeader.png" alt="Logo"/>
                <p>We provide fast and convenient online movie ticket booking service.</p>
            </div>
        </div>
        <div class="text-center" style="margin-top: 20px;">
            <p>&copy;2024 Your Company. All rights reserved.</p>
        </div>
    </div>
</footer>



</html>
