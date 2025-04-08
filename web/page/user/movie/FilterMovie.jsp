<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Movie Filter</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Swiper CSS -->
        <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
        <!-- Select2 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <!-- AOS CSS -->
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <style>
            :root {
                --primary-color: #722ed1;
                --secondary-color: #f8f0ff;
                --accent-color: #d42a87;
                --text-color: #333;
                --background-color: #f8f9fa;
            }

            body {
                background-color: var(--background-color);
                font-family: 'Source Sans Pro', sans-serif;
            }

            .container {
                max-width: 1200px;
                margin: 20px auto;
                padding: 20px;
                background: #fff;
                border-radius: 10px;
            }

            h1 {
                font-size: 24px;
                color: #722ed1 !important;
                text-align: center;
                margin-bottom: 20px;
                font-weight: 500;
            }

            
            .select2-container {
                width: 100% !important;
            }

            .select2-container .select2-selection--single {
                height: 38px;
                border: 1px solid #e0e0e0;
                border-radius: 8px;
                background-color: #fff;
            }

            .select2-container--default .select2-selection--single .select2-selection__rendered {
                line-height: 38px;
                padding-left: 12px;
                color: #333;
            }

            .select2-container--default .select2-selection--single .select2-selection__arrow {
                height: 36px;
            }

            .select2-dropdown {
                border: 1px solid #e0e0e0;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            }

            .select2-search__field {
                border-radius: 4px !important;
                padding: 5px 8px !important;
            }

            .select2-results__option {
                padding: 8px 12px;
            }

            .select2-container--default .select2-results__option--highlighted[aria-selected] {
                background-color: var(--primary-color);
            }

            
            .btn-success {
                background: #28a745;
                border-radius: 8px;
                padding: 8px 20px;
                font-size: 14px;
                font-weight: normal;
                border: none;
            }

            .btn-success:hover {
                background: #218838;
            }

           
            .form-control {
                border: 1px solid #e0e0e0;
                border-radius: 8px;
                padding: 8px 12px;
                height: auto;
                font-size: 14px;
            }

            .form-control:focus {
                border-color: var(--primary-color);
                box-shadow: 0 0 0 0.2rem rgba(114, 46, 209, 0.1);
            }

            
            .swiper-container {
                padding: 20px 40px;
                margin-top: 20px;
            }

            .movie-card {
                width: 200px;
                height: 350px;
                background: white;
                border-radius: 10px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                overflow: hidden;
                transition: transform 0.3s ease;
                margin: 10px;
                border: 3px dashed #b2b2b2;
            }

            .movie-poster-container {
                height: 250px;
                overflow: hidden;
            }

            .movie-poster {
                width: 100%;
                height: 100%;
                object-fit: cover;
                transition: transform 0.3s ease;
            }

            .movie-info {
                padding: 10px;
            }

            .movie-title {
                font-size: 14px;
                font-weight: 600;
                color: #333;
                margin-bottom: 5px;
                display: -webkit-box;
                -webkit-line-clamp: 1;
                -webkit-box-orient: vertical;
                overflow: hidden;
            }

            .movie-genre {
                color: #666;
                font-size: 12px;
                margin-bottom: 10px;
            }

            .btn-details {
                display: inline-block;
                padding: 6px 15px;
                font-size: 12px;
                color: #fff;
                background: var(--primary-color);
                border-radius: 20px;
                text-decoration: none;
                transition: all 0.3s ease;
            }

            .btn-details:hover {
                background: var(--accent-color);
                color: #fff;
                text-decoration: none;
            }

            @media (max-width: 768px) {
                .container {
                    margin: 10px;
                    padding: 15px;
                }

                .movie-card {
                    width: 160px;
                    height: 300px;
                }

                .movie-poster-container {
                    height: 200px;
                }
            }
            /* Thêm vào phần style hiện tại */

/* Animated Background Circles */
.animated-background {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
    overflow: hidden;
}

.circle {
    position: absolute;
    border-radius: 50%;
    background: linear-gradient(45deg, #722ed1, #d42a87);
    opacity: 0.1;
    animation: float 15s infinite;
}

.circle:nth-child(1) {
    width: 300px;
    height: 300px;
    left: -150px;
    top: -150px;
    animation-delay: 0s;
}

.circle:nth-child(2) {
    width: 400px;
    height: 400px;
    right: -200px;
    bottom: -200px;
    animation-delay: -5s;
}

.circle:nth-child(3) {
    width: 200px;
    height: 200px;
    right: 30%;
    top: 30%;
    animation-delay: -10s;
}

@keyframes float {
    0% {
        transform: translate(0, 0) rotate(0deg);
    }
    50% {
        transform: translate(100px, 100px) rotate(180deg);
    }
    100% {
        transform: translate(0, 0) rotate(360deg);
    }
}

/* Enhanced Container Styling */
.container {
    background: rgba(255, 255, 255, 0.95);
    backdrop-filter: blur(10px);
    border: none;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

/* Enhanced Movie Card */
.movie-card {
    background: rgba(255, 255, 255, 0.9);
    border: none;
    box-shadow: 0 5px 15px rgba(114, 46, 209, 0.1);
    transform: translateY(0);
    transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

.movie-card:hover {
    transform: translateY(-10px);
    box-shadow: 0 15px 30px rgba(114, 46, 209, 0.2);
}

.movie-poster {
    transform: scale(1);
    transition: transform 0.6s ease;
}

.movie-card:hover .movie-poster {
    transform: scale(1.1);
}

/* Enhanced Form Controls */
.select2-container .select2-selection--single {
    background: rgba(255, 255, 255, 0.9);
    border: 2px solid rgba(114, 46, 209, 0.1);
    transition: all 0.3s ease;
}

.select2-container .select2-selection--single:hover {
    border-color: rgba(114, 46, 209, 0.3);
}

.form-control {
    background: rgba(255, 255, 255, 0.9);
    border: 2px solid rgba(114, 46, 209, 0.1);
    transition: all 0.3s ease;
}

.form-control:focus {
    border-color: #722ed1;
    box-shadow: 0 0 0 3px rgba(114, 46, 209, 0.1);
}

/* Enhanced Buttons */
.btn-success {
    background: linear-gradient(45deg, #722ed1, #d42a87);
    border: none;
    padding: 10px 25px;
    transition: all 0.3s ease;
}

.btn-success:hover {
    background: linear-gradient(45deg, #d42a87, #722ed1);
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(114, 46, 209, 0.3);
}

/* Swiper Navigation Buttons */
.swiper-button-next,
.swiper-button-prev {
    color: #722ed1;
    background: rgba(255, 255, 255, 0.9);
    width: 40px;
    height: 40px;
    border-radius: 50%;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.swiper-button-next:after,
.swiper-button-prev:after {
    font-size: 18px;
}

/* Swiper Pagination */
.swiper-pagination-bullet {
    background: #722ed1;
    opacity: 0.5;
}

.swiper-pagination-bullet-active {
    opacity: 1;
    background: #d42a87;
}
/* Animated Background Circles */
.animated-background {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
    overflow: hidden;
}

.circle {
    position: absolute;
    border-radius: 50%;
    opacity: 0.1;
}

/* Circle 1 */
.circle:nth-child(1) {
    width: 300px;
    height: 300px;
    left: -150px;
    top: -150px;
    background: linear-gradient(45deg, #722ed1, #d42a87);
    animation: float1 20s infinite;
}

/* Circle 2 */
.circle:nth-child(2) {
    width: 400px;
    height: 400px;
    right: -200px;
    bottom: -200px;
    background: linear-gradient(135deg, #d42a87, #722ed1);
    animation: float2 25s infinite;
}

/* Circle 3 */
.circle:nth-child(3) {
    width: 200px;
    height: 200px;
    right: 30%;
    top: 30%;
    background: linear-gradient(225deg, #722ed1, #ff6b6b);
    animation: float3 18s infinite;
}

/* Circle 4 */
.circle:nth-child(4) {
    width: 250px;
    height: 250px;
    left: 20%;
    bottom: 20%;
    background: linear-gradient(315deg, #ff6b6b, #722ed1);
    animation: float4 22s infinite;
}

/* Circle 5 */
.circle:nth-child(5) {
    width: 180px;
    height: 180px;
    right: 15%;
    top: 15%;
    background: linear-gradient(45deg, #d42a87, #ff6b6b);
    animation: float5 23s infinite;
}

/* Circle 6 */
.circle:nth-child(6) {
    width: 350px;
    height: 350px;
    left: 40%;
    top: -100px;
    background: linear-gradient(135deg, #722ed1, #ff6b6b);
    animation: float6 21s infinite;
}

/* Circle 7 */
.circle:nth-child(7) {
    width: 280px;
    height: 280px;
    right: 35%;
    bottom: -50px;
    background: linear-gradient(225deg, #ff6b6b, #d42a87);
    animation: float7 24s infinite;
}

/* Circle 8 */
.circle:nth-child(8) {
    width: 220px;
    height: 220px;
    left: 25%;
    top: 45%;
    background: linear-gradient(315deg, #d42a87, #722ed1);
    animation: float8 19s infinite;
}

/* Circle 9 */
.circle:nth-child(9) {
    width: 320px;
    height: 320px;
    right: 22%;
    bottom: 35%;
    background: linear-gradient(45deg, #ff6b6b, #722ed1);
    animation: float9 26s infinite;
}

/* Circle 10 */
.circle:nth-child(10) {
    width: 270px;
    height: 270px;
    left: 38%;
    top: 25%;
    background: linear-gradient(135deg, #722ed1, #d42a87);
    animation: float10 17s infinite;
}

/* Animation Keyframes */
@keyframes float1 {
    0% { transform: translate(0, 0) rotate(0deg); }
    50% { transform: translate(100px, 100px) rotate(180deg); }
    100% { transform: translate(0, 0) rotate(360deg); }
}

@keyframes float2 {
    0% { transform: translate(0, 0) rotate(0deg); }
    50% { transform: translate(-100px, -50px) rotate(-180deg); }
    100% { transform: translate(0, 0) rotate(-360deg); }
}

@keyframes float3 {
    0% { transform: translate(0, 0) scale(1); }
    50% { transform: translate(50px, -50px) scale(1.1); }
    100% { transform: translate(0, 0) scale(1); }
}

@keyframes float4 {
    0% { transform: translate(0, 0) rotate(0deg) scale(1); }
    50% { transform: translate(-70px, 70px) rotate(180deg) scale(0.9); }
    100% { transform: translate(0, 0) rotate(360deg) scale(1); }
}

@keyframes float5 {
    0% { transform: translate(0, 0) rotate(0deg); }
    50% { transform: translate(80px, -80px) rotate(-180deg); }
    100% { transform: translate(0, 0) rotate(-360deg); }
}

@keyframes float6 {
    0% { transform: scale(1) rotate(0deg); }
    50% { transform: scale(1.2) rotate(180deg); }
    100% { transform: scale(1) rotate(360deg); }
}

@keyframes float7 {
    0% { transform: translate(0, 0) scale(1); }
    50% { transform: translate(-60px, 60px) scale(0.8); }
    100% { transform: translate(0, 0) scale(1); }
}

@keyframes float8 {
    0% { transform: translate(0, 0) rotate(0deg); }
    50% { transform: translate(90px, 90px) rotate(-180deg); }
    100% { transform: translate(0, 0) rotate(-360deg); }
}

@keyframes float9 {
    0% { transform: scale(1) translate(0, 0); }
    50% { transform: scale(1.1) translate(-40px, 40px); }
    100% { transform: scale(1) translate(0, 0); }
}

@keyframes float10 {
    0% { transform: rotate(0deg) translate(0, 0); }
    50% { transform: rotate(180deg) translate(70px, -70px); }
    100% { transform: rotate(360deg) translate(0, 0); }
}
        </style>
    </head>
    <body>
        <jsp:include page="/page/landingPage/Header.jsp" />
<div class="animated-background">
    <div class="circle"></div>
    <div class="circle"></div>
    <div class="circle"></div>
    <div class="circle"></div>
    <div class="circle"></div>
    <div class="circle"></div>
    <div class="circle"></div>
    <div class="circle"></div>
    <div class="circle"></div>
    <div class="circle"></div>
</div>
        <div class="container" data-aos="fade-up">
            <h1>TÌM KIẾM PHIM</h1>
            <div class="row mb-4">
                <!-- Genre Filter -->
                <div class="col-md-6 mb-3">
                    <form action="${pageContext.request.contextPath}/filterMovies" method="get">
                        <select class="select2" name="genre" onchange="this.form.submit()">
                            <option value="">Chọn thể loại</option>
                            <c:forEach var="genre" items="${genres}">
                                <option value="${genre.genreName}" 
                                        <c:if test="${genre.genreName == selectedGenre}">selected</c:if>>
                                    ${genre.genreName}
                                </option>
                            </c:forEach>
                        </select>
                    </form>
                </div>

                <!-- Country Filter -->
                <div class="col-md-6 mb-3">
                    <form action="${pageContext.request.contextPath}/filterMovies" method="get">
                        <select class="select2" name="country" onchange="this.form.submit()">
                            <option value="">Chọn quốc gia</option>
                            <c:forEach var="country" items="${countries}">
                                <option value="${country}" 
                                        <c:if test="${country == selectedCountry}">selected</c:if>>
                                    ${country}
                                </option>
                            </c:forEach>
                        </select>
                    </form>
                </div>
            </div>

            <!-- Search Bar -->
            <div class="row">
                <div class="col-md-12">
                    <form action="${pageContext.request.contextPath}/filterMovies" method="get" class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="Tìm kiếm phim..." 
                               name="searchQuery" value="${searchQuery}">
                        <button class="btn btn-success" type="submit">Tìm kiếm</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Movie Carousel -->
        <div class="swiper-container movie-carousel">
            <div class="swiper-wrapper">
                <c:forEach var="movie" items="${movies}" varStatus="status">
                    <div class="swiper-slide">
                        <div class="movie-card">
                            <div class="movie-poster-container">
                                <img src="${movie.imageURL}" alt="${movie.title}" class="movie-poster">
                            </div>
                            <div class="movie-info">
                                <h3 class="movie-title">${movie.title}</h3>
                                <p class="movie-genre">
                                    <c:forEach var="genre" items="${movie.genres}" varStatus="genreStatus">
                                        ${genre.genreName}${!genreStatus.last ? ', ' : ''}
                                    </c:forEach>
                                </p>
                                <div class="movie-buttons">
                                    <a href="${pageContext.request.contextPath}/HandleDisplayMovieInfo?movieID=${movie.movieID}" 
                                       class="btn-details">CHI TIẾT</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="swiper-button-next"></div>
            <div class="swiper-button-prev"></div>
            <div class="swiper-pagination"></div>
        </div>

        <!-- Scripts -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>
            $(document).ready(function() {
                // Initialize Select2
                $('.select2').select2({
                    placeholder: "Chọn một option",
                    allowClear: true,
                    width: '100%',
                    language: {
                        noResults: function() {
                            return "Không tìm thấy kết quả";
                        }
                    }
                });
            });

            // Initialize AOS
            AOS.init({
                duration: 800,
                once: true
            });

            // Initialize Swiper
            var swiper = new Swiper('.swiper-container', {
                slidesPerView: 'auto',
                spaceBetween: 15,
                loop: true,
                autoplay: {
                    delay: 3000,
                    disableOnInteraction: false,
                },
                pagination: {
                    el: '.swiper-pagination',
                    clickable: true,
                },
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                },
                breakpoints: {
                    320: {
                        slidesPerView: 2,
                        spaceBetween: 10,
                    },
                    480: {
                        slidesPerView: 3,
                        spaceBetween: 15,
                    },
                    768: {
                        slidesPerView: 4,
                        spaceBetween: 15,
                    },
                    1024: {
                        slidesPerView: 5,
                        spaceBetween: 15,
                    }
                }
            });
        </script>
    </body>
</html>