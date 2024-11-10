<%-- 
    Document   : navbar
    Created on : May 23, 2024, 3:59:44 PM
    Author     : duyqu
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.ServletContext" %>

<!--jstl import-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--java import-->

<%@ page import="java.util.ArrayList" %>
<%@ page import="jakarta.servlet.ServletContext" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>

<html>


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <style type="text/css">
        /* Định dạng cho navbar */

        /* Định dạng cho navbar */
        /* Navbar Styles */
        /* Navbar Styles */
        .nav-item.dropdown:hover .dropdown-menu {
            display: block;
            animation: fadeIn 0.3s ease;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .dropdown > .dropdown-toggle:active {
            pointer-events: none;
        }

        /* Borderless Button Styles */
        .borderless-btn {
            background: none;
            border: none;
            color: #6a11cb;
            font-size: 1.5rem;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .borderless-btn:hover {
            color: #2575fc;
            transform: scale(1.1);
        }

        .borderless-btn:focus {
            outline: none;
            box-shadow: 0 0 0 3px rgba(106, 17, 203, 0.25);
        }

        /* Modal Styles */
        .custom-modal .modal-dialog {
            position: absolute;
            top: 5%;
            left: 25%;
            width: 50vw;
            max-width: 600px;
        }

        .custom-modal .modal-content {
            border-radius: 20px;
            padding: 30px;
            background: linear-gradient(135deg, #ffffff 0%, #f0f4f8 100%);
            border: none;
        }

        .navbar {
            position: fixed !important;
            top: 0;
            left: 0;
            right: 0;
            width: 100%;
            z-index: 1000;
            background: white !important;
            padding: 15px 0;
            border: 1px solid #ebebeb;
        }

        .navbar-brand, .nav-link {
            color: #f171b7 !important;
            font-weight: 500;
        }

        .nav-link:hover {
            background-color: rgba(255, 255, 255, 0.2);
            transform: translateY(-2px);
        }


        .navbar-toggler {
            border-color: rgba(255, 255, 255, 0.5);
        }

        .navbar-toggler-icon {
            background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='30' height='30' viewBox='0 0 30 30'%3e%3cpath stroke='rgba(255, 255, 255, 0.8)' stroke-linecap='round' stroke-miterlimit='10' stroke-width='2' d='M4 7h22M4 15h22M4 23h22'/%3e%3c/svg%3e");
        }
        .carousel-item active img{
            margin-top: 33px;
        }

        /* Movie Image Styles */
        .movie-image {
            max-width: 100px;
            border-radius: 8px;
            margin-right: 20px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .movie-image:hover {
            transform: scale(1.05);
        }

        /* Movie Info Styles */
        .movie-info {
            margin-left: 20px;
            display: flex;
            flex-direction: column;
            gap: 8px;
        }

        .movie-info p, .movie-info span {
            margin: 0;
            line-height: 1.4;
        }

        .movie-details {
            display: flex;
            align-items: center;
            padding: 20px;
            border-bottom: 1px solid #eaeaea;
            transition: background-color 0.3s ease;
        }

        .movie-details:hover {
            background: linear-gradient(135deg, #f5f7fa 0%, #e0e5ec 100%);
        }

        .genre {
            display: inline-block;
            margin-right: 5px;
            padding: 3px 8px;
            background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
            border-radius: 20px;
            font-size: 0.85rem;
            color: #ffffff;
            transition: all 0.3s ease;
        }

        .genre:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(37, 117, 252, 0.2);
        }

        /* Search Input Styles */
        #movieNameInput {
            width: calc(100% - 20px);
            padding: 12px 15px;
            font-size: 1rem;
            border: 2px solid #ced4da;
            border-radius: 25px;
            outline: none;
            transition: all 0.3s ease;
            background: linear-gradient(135deg, #ffffff 0%, #f5f7fa 100%);
        }

        #movieNameInput:focus {
            border-color: #6a11cb;
            box-shadow: 0 0 0 0.2rem rgba(106, 17, 203, 0.25);
        }

        /* Modal Footer Button Styles */
        .modal-footer button {
            width: 120px;
            height: 48px;
            border-radius: 24px;
            font-weight: 600;
            transition: all 0.3s ease;
            background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
            color: #ffffff;
            border: none;
        }

        .modal-footer button:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(37, 117, 252, 0.2);
        }

        /* "ĐANG CHIẾU" Button Styles */
        #showingButton {
            height: 30px;
            border: none;
            border-radius: 15px;
            background: linear-gradient(135deg, #ff416c 0%, #ff4b2b 100%);
            color: white;
            font-size: 14px;
            font-weight: 600;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: all 0.3s ease;
        }

        #showingButton:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(255, 65, 108, 0.2);
        }

        /* Movie Container Styles */
        #movieDetailsContainer {
            cursor: pointer;
        }

        #movieContainerForm {
            max-height: 60vh;
            overflow-y: auto;
            padding-right: 15px;
            scrollbar-width: thin;
            scrollbar-color: #6a11cb #f0f0f0;
        }

        #movieContainerForm::-webkit-scrollbar {
            width: 8px;
        }

        #movieContainerForm::-webkit-scrollbar-track {
            background: #f0f0f0;
        }

        #movieContainerForm::-webkit-scrollbar-thumb {
            background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
            border-radius: 20px;
            border: 3px solid #f0f0f0;
        }

        /* Search Button Styles */
        #searchButton {
            margin-left: 20px;
            padding: 10px 20px;
            color: #f171b7;
            border: none;
            font-size: 25px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        #searchButton:hover {
            transform: translateY(-2px);
        }

        #searchButton:active {
            transform: translateY(1px);
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        /* Logo Styles */
        .icon-logo-btn {
            font-size: 2em;
        }

        .wrapper-navbar-header {
            display: flex;
            align-items: center;
        }

        .icon-logo_header {
            width: 50px;
            height: 50px;
            border: none;
            border-radius: 12px;
            object-fit: cover;
            transition: transform 0.3s ease;
        }

        .icon-logo_header:hover {
            transform: scale(1.1);
        }

        /* Responsive Styles */
        @media (max-width: 767px) {
            #searchButton {
                margin-left: 10px;
            }

            .custom-modal .modal-dialog {
                width: 95vw;
                left: 2.5%;
            }

            .borderless-btn {
                font-size: 1.2rem;
            }

            .movie-info {
                margin-left: 15px;
            }

            .movie-details {
                padding: 15px;
            }

            #movieNameInput {
                width: calc(100% - 15px);
            }

            .modal-footer button {
                width: 100px;
                height: 40px;
            }

            #showingButton {
                height: 28px;
                font-size: 12px;
            }

            .movie-image {
                max-width: 80px;
            }
        }

        @media (min-width: 768px) {
            .navbar-expand-lg .navbar-nav {
                flex-direction: row;
            }

            .custom-modal .modal-dialog {
                max-width: 500px;
            }

            .borderless-btn {
                font-size: 1.5rem;
            }
        }


        /* Dropdown Menu Gradient */
        .dropdown-menu {
            background: linear-gradient(135deg, #ffffff 0%, #f5f7fa 100%);
        }

        /* Hover effects */
        .nav-link:hover, .dropdown-item:hover {
            background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        body {
            padding-top: 80px !important;
        }


        @media (max-width: 768px) {
            body {
                padding-top: 60px !important;
            }
        }

        .navbar .dropdown-menu {
            margin-top: 0;
        }

        .wave-container {
            z-index: -1;
        }

        .modal {
            z-index: 1050;
        }
        .navbar-brand {
            display: flex;
            align-items: center;
            padding: 0;
        }

        .brand-text {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            line-height: 1;
        }

        .brand-name {
            font-family: 'Montserrat', sans-serif;
            font-size: 1.8rem;
            font-weight: 700;
            color: #ff4d8d;
            letter-spacing: 1px;
            margin-bottom: 2px;
        }

        .brand-subtitle {
            font-family: 'Poppins', sans-serif;
            font-size: 0.9rem;
            font-weight: 600;
            color: #ff8fb4;
            letter-spacing: 3px;
            text-transform: uppercase;
        }

        /* Hover effect */
        .navbar-brand:hover .brand-name {
            background: linear-gradient(45deg, #ff4d8d, #ff8fb4);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            transition: all 0.3s ease;
        }


    </style>

    <!--get chains from login servlet-->
    <c:set var="cinemaNames" value="${sessionScope.chains}" />

    <nav class="navbar navbar-expand-lg navbar-light bg-light" >
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <div class="brand-text">
                    <div class="brand-name">UNOVE</div>
                    <div class="brand-subtitle">CINEMA</div>
                </div>
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="wrapper-navbar-header navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="icon-logo-btn nav-link active" aria-current="page" href= ""> <img class="icon-logo_header" src="${pageContext.request.contextPath}/page/image/logoHeader1.svg" alt="Logo"/></a>
                    </li>
                    <!--                    <li class="nav-item">
                                            <button type="button" class="btn btn-warning"><a class="nav-link" href="${pageContext.request.contextPath}/movie/schedule">BUY TICKET</a></button>
                                        </li>-->
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/showtimes">Lịch chiếu phim</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/filter-movies">Phim</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/top-movies">Top Phim</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/top-movies">Phim sắp chiếu</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Rạp
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <c:forEach var="cinema" items="${cinemaNames}">
                                <li>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/cinemaItem?cinemaName=${cinema}">${cinema}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </li>
                </ul>

                <div>
                    <a href="${pageContext.request.contextPath}/filterMovies">
                        <button id="searchButton" class="borderless-btn">
                            <i class="fa-solid fa-magnifying-glass"></i> 
                        </button>
                    </a>


                    <c:set var="movieName" value="${requestScope.movieName}"/>
                    <c:set var="movies" value="${requestScope.movies}"/>
                    <div class="modal fade custom-modal" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <form id="searchMovieForm" action="${pageContext.request.contextPath}/searchmovie" method="post">
                                        <div class="search-input-container">
                                            <input type="search" id="movieNameInput" name="movieName" class="modal-title" placeholder="Search..." aria-label="Search" value="${movieName}">
                                        </div>
                                    </form>
                                </div>
                                <%--<c:if test="${not empty movies}">--%>
                                <!--<form id="movieContainerForm">-->
                                <%--<c:forEach var="movie" items="${movies}">--%>
                                    <!--<div class="modal-body movie-details" id="movieDetailsContainer" onclick="displayMovieDetails('${movie.movieID}')">-->
                                        <!--<img src="${movie.imageURL}" alt="${movie.title} image" class="movie-image" />-->
                                <!--<div class="movie-info">-->
                                    <!--<b style="font-size: 22px;">${movie.title}</b>-->
                                <%--<c:set var="genresString" value="${fn:replace(movie.genres.toString(), '[', '')}" />--%>
                                <%--<c:set var="genresString" value="${fn:replace(genresString, ']', '')}" />--%>
                                <!--<p style="font-size: 18px;">${genresString}</p>-->
                                <!--<p style="font-size: 18px;"><i class="fa-regular fa-star"></i> ${movie.rating}</p>-->
                                <!--<button type="button" id="showingButton"><i class="fas fa-video" style="margin-right: 8px;"></i>ĐANG CHIẾU</button>-->
                                <!--</div>-->
                                <!--</div>-->
                                <!--<hr/>-->
                                <%--</c:forEach>--%>
                                <!--<input type="hidden" id="movieIDInput" name="movieID">-->
                                <!--</form>-->
                                <%--</c:if>--%>
                                <div class="modal-footer">
                                    <button style="background-color: rgb(216, 45, 139)" type="button" class="btn btn-primary" onclick="closeModal();" data-bs-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <ul class="navbar-nav ms-auto mb-2 me-lg-5">
                    <c:choose>
                        <c:when test="${not empty sessionScope.username}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <c:out value="${sessionScope.username}" />
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/display">Xem hồ sơ</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/myfavouritemovie">Phim đã yêu thích</a></li>

                                    <!-- Liên kết mới đến trang HistoryOrder -->
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/history">Lịch sử đơn hàng</a></li>

                                    <c:if test="${sessionScope.role eq 'OWNER'}">
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/owner/dashboard">Quản trị viên</a></li>
                                        </c:if>
                                        <c:if test="${sessionScope.role eq 'ADMIN'}">
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/dashboard">Quản trị viên</a></li>
                                        </c:if>

                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
                                </ul>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <script>

        <c:set var="modalStatus" value="${requestScope.modalStatus}"></c:set>
                                        console.log("modalStatus:", ${modalStatus});
        <c:if test="${not empty modalStatus and modalStatus}">
                                        showModal();
        </c:if>

                                        const movieNameInput = document.getElementById("movieNameInput");
                                        document.addEventListener("DOMContentLoaded", function () {
                                            movieNameInput.focus();
                                            const length = movieNameInput.value.length;
                                            movieNameInput.setSelectionRange(length, length);
                                        });

                                        function closeModal() {
                                            document.getElementById("movieNameInput").innerText = "";

                                            document.getElementById("movieContainerForm").style.display = "none";

                                            //window.location.href = "/movie";

                                        }

                                        function debounce(cb) {
                                            let timeout;
                                            let delay = 1200;

                                            return (...args) => {
                                                clearTimeout(timeout);
                                                timeout = setTimeout(() => {
                                                    cb(...args);
                                                }, delay);
                                            };
                                        }

                                        function callServlet(id, url, methodType) {
                                            document.getElementById(id).action = url;
                                            document.getElementById(id).method = methodType;
                                            document.getElementById(id).submit();
                                        }

                                        function showModal() {
                                            var myModal = new bootstrap.Modal(document.getElementById('exampleModal'));
                                            myModal.show();
                                        }

                                        const queryMovies = debounce(() => {
                                            callServlet('searchMovieForm', '/movie/searchmovie', 'POST');
                                        });



                                        movieNameInput.addEventListener("input", () => {
                                            queryMovies();
                                        });

                                        function displayMovieDetails(movieID) {
                                            document.getElementById('movieIDInput').value = movieID;
                                            callServlet('movieContainerForm', '/movie/HandleDisplayMovieInfo', 'GET');
                                        }

                                        function callServlet(id, url, methodType) {
                                            document.getElementById(id).action = url;
                                            document.getElementById(id).method = methodType;
                                            document.getElementById(id).submit();
                                        }


    </script>
</html>