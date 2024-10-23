<%@page import="model.owner.Movie"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jakarta.servlet.ServletContext" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <title>Unove Cinema - Trải nghiệm điện ảnh tuyệt vời</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
        <!-- AOS CSS -->
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
        <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <style>
            :root {
                --primary-color: #E2BFD9;
                --secondary-color: #E2BFD9;
                --text-color: #ffffff;
                --accent-color: #ffd700;
            }
            html {
                scroll-behavior: smooth;
            }
            body {
                background: linear-gradient(to bottom, #ffffff, #ffffff);
                overflow-x: hidden;
            }
            .navbar {
                background-color: #E2BFD9;
                backdrop-filter: blur(10px);
            }
            #movieCarousel {
                max-width: 800px;
                margin: 20px auto;
                box-shadow: 0 10px 30px #E2BFD9;
                border-radius: 15px;
                overflow: hidden;
            }
            .carousel-item {
                height: 450px;
            }
            .carousel-item img {
                object-fit: cover;
                height: 100%;
                width: 100%;
                filter: brightness(0.7);
            }
            .carousel-caption {
                background: rgba(0, 0, 0, 0.7);
                border-radius: 10px;
                padding: 15px;
                bottom: 20px;
                left: 20px;
                right: 20px;
            }
            .carousel-title {
                font-size: 1.8rem;
                font-weight: 700;
                margin-bottom: 0.3rem;
            }
            .carousel-text {
                font-size: 1rem;
                margin-bottom: 1rem;
            }
            .btn-book-now {
                background-color: var(--primary-color);
                color: var(--text-color);
                border: none;
                padding: 8px 20px;
                font-size: 1rem;
                font-weight: 600;
                text-transform: uppercase;
                transition: all 0.3s ease;
            }
            .btn-book-now:hover {
                background-color: #E2BFD9;
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.2);
            }
            .section-title {
                font-size: 3rem;
                font-weight: 700;
                color: #ffffff;
                text-transform: uppercase;
                letter-spacing: 2px;
                margin-bottom: 40px;
                position: relative;
                padding-bottom: 20px;
                text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
            }

            .section-title::after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 50%;
                transform: translateX(-50%);
                width: 100px;
                height: 4px;
                background: linear-gradient(to right, #E2BFD9, #8A2BE2);
            }

            .section-title[data-aos="fade-up"] {
                opacity: 0;
                transform: translateY(20px);
                transition: opacity 0.6s ease, transform 0.6s ease;
            }

            .section-title[data-aos="fade-up"].aos-animate {
                opacity: 1;
                transform: translateY(0);
            }

            @media (max-width: 768px) {
                .section-title {
                    font-size: 2.5rem;
                }
            }

            @media (max-width: 576px) {
                .section-title {
                    font-size: 2rem;
                }
            }

            [data-aos="fade-up"] {
                opacity: 0;
                transform: translateY(20px);
                transition: opacity 0.6s ease, transform 0.6s ease;
            }

            [data-aos="fade-up"].aos-animate {
                opacity: 1;
                transform: translateY(0);
            }
            #now-showing {
                background-image: url('images/unover-cover.jpg');
                background-size: cover;
                background-position: center;
                background-attachment: fixed;
                padding: 50px 0;
                color: #fff;
            }
            .section-title {
                font-size: 2.5rem;
                margin-bottom: 2rem;
            }
            .movie-carousel {
                padding: 20px 0;
            }
            .movie-card {
                position: relative;
                overflow: hidden;
                border-radius: 10px;
            }
            .movie-poster {
                width: 100%;
                height: auto;
                transition: transform 0.3s ease;
            }
            .movie-info {
                position: absolute;
                bottom: 0;
                left: 0;
                right: 0;
                background: linear-gradient(to top, rgba(0,0,0,0.9), rgba(0,0,0,0));
                padding: 20px;
                transform: translateY(100%);
                transition: transform 0.3s ease;
            }
            .movie-card:hover .movie-info {
                transform: translateY(0);
            }
            .movie-title {
                font-size: 1.2rem;
                margin-bottom: 5px;
            }
            .movie-genre {
                font-size: 0.9rem;
                color: #ccc;
                margin-bottom: 5px;
            }
            .movie-rating {
                font-size: 1rem;
                color: #ffd700;
                margin-bottom: 10px;
            }
            .btn-play {
                display: inline-block;
                background-color: rgba(255,255,255,0.2);
                color: #fff;
                padding: 5px 10px;
                border-radius: 5px;
                text-decoration: none;
                transition: background-color 0.3s ease;
            }
            .btn-play:hover {
                background-color: rgba(255,255,255,0.4);
            }
            .swiper-button-next, .swiper-button-prev {
                color: #fff;
            }

            .btn-book {
                background: linear-gradient(45deg, #E2BBE9, #9F91CC);
                color: #ffffff;
                border: none;
                padding: 12px 25px;
                font-size: 1rem;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 1px;
                border-radius: 30px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
                transition: all 0.3s ease;
                position: relative;
                overflow: hidden;
                display: block;
                width: 100%;
                text-align: center;
            }

            .btn-book:before {
                content: '';
                position: absolute;
                top: 0;
                left: -100%;
                width: 100%;
                height: 100%;
                background: linear-gradient(120deg, transparent, rgba(255, 255, 255, 0.3), transparent);
                transition: all 0.5s;
            }

            .btn-book:hover {
                transform: translateY(-3px);
                box-shadow: 0 6px 20px rgba(0, 0, 0, 0.3);
                color: #ffffff;
            }

            .btn-book:hover:before {
                left: 100%;
            }

            .btn-book:active {
                transform: translateY(-1px);
                box-shadow: 0 3px 10px rgba(0, 0, 0, 0.2);
            }

            @media (max-width: 768px) {
                .movie-card {
                    margin-bottom: 20px;
                }

                .movie-card img {
                    height: 300px;
                }

                .movie-title {
                    font-size: 1.1rem;
                }

                .btn-book {
                    padding: 10px 20px;
                    font-size: 0.9rem;
                }
            }
            .day-nav {
                background-color: #212529;
                border-radius: 50px;
                padding: 10px;
                margin-bottom: 40px;
            }
            .day-nav .nav-link {
                color: var(--text-color);
                font-weight: 500;
                padding: 10px 20px;
                border-radius: 30px;
                transition: all 0.3s ease;
            }
            .day-nav .nav-link.active,
            .day-nav .nav-link:hover {
                background-color: var(--primary-color);
                color: var(--text-color);
            }
            footer {
                background: linear-gradient(135deg, #1a1c20 0%, #2d3436 100%);
                color: #ffffff;
                padding: 60px 0 30px;
                font-family: 'Poppins', sans-serif;
            }

            .footer-title {
                color: #E2BFD9;
                font-size: 1.5rem;
                font-weight: 600;
                margin-bottom: 20px;
                position: relative;
                padding-bottom: 10px;
            }

            .footer-title::after {
                content: '';
                position: absolute;
                left: 0;
                bottom: 0;
                width: 50px;
                height: 2px;
                background: #E2BFD9;
            }

            footer p {
                font-size: 0.9rem;
                line-height: 1.6;
                margin-bottom: 10px;
            }

            .social-icons a {
                display: inline-block;
                width: 40px;
                height: 40px;
                background: #E2BFD9;
                color: #1a1c20;
                border-radius: 50%;
                text-align: center;
                line-height: 40px;
                margin-right: 10px;
                transition: all 0.3s ease;
            }

            .social-icons a:hover {
                background: #ffffff;
                color: #E2BFD9;
                transform: translateY(-3px);
            }

            .footer-links li {
                margin-bottom: 10px;
            }

            .footer-links a {
                color: #ffffff;
                text-decoration: none;
                transition: all 0.3s ease;
                position: relative;
            }

            .footer-links a::before {
                content: '→';
                position: absolute;
                left: -20px;
                opacity: 0;
                transition: all 0.3s ease;
            }

            .footer-links a:hover {
                color: #E2BFD9;
                padding-left: 20px;
            }

            .footer-links a:hover::before {
                opacity: 1;
                left: 0;
            }

            footer img {
                filter: brightness(0) invert(1);
                transition: all 0.3s ease;
            }

            footer img:hover {
                transform: scale(1.05);
            }

            .text-center {
                border-top: 1px solid rgba(255,255,255,0.1);
                padding-top: 20px;
                font-size: 0.8rem;
            }

            @media (max-width: 768px) {
                .footer-title {
                    margin-top: 20px;
                }
            }
            .movie-title-custom {
                color: #E2BFD9;
                transition: color 0.3s ease;
            }
            #customer-reviews {
                background-color: #cbacc4;
                color: #ffffff;
                padding: 50px 0;
            }

            .review-card {
                background-color: #ffffff;
                border-radius: 10px;
                padding: 30px;
                height: 100%;
                transition: all 0.3s ease;
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
                overflow: hidden;
                position: relative;
            }

            .review-card::before {
                content: '';
                position: absolute;
                top: -10px;
                left: -10px;
                right: -10px;
                bottom: -10px;
                background: linear-gradient(45deg, #EBD9DD, #D8AED3);
                z-index: -1;
                filter: blur(20px);
                opacity: 0;
                transition: opacity 0.3s ease;
            }

            .review-card:hover {
                transform: translateY(-10px) scale(1.02);
                box-shadow: 0 15px 30px rgba(0,0,0,0.2);
            }

            .review-card:hover::before {
                opacity: 0.7;
            }

            .review-title {
                color: #cbacc4;
                font-size: 1.5rem;
                margin-bottom: 15px;
                position: relative;
                padding-bottom: 10px;
            }

            .review-title::after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 0;
                width: 50px;
                height: 2px;
                background-color: #cbacc4;
                transition: width 0.3s ease;
            }

            .review-card:hover .review-title::after {
                width: 100%;
            }

            .review-content {
                color: #333;
                font-size: 1rem;
                line-height: 1.6;
                margin-bottom: 20px;
            }

            .reviewer-info {
                display: flex;
                align-items: center;
                transition: transform 0.3s ease;
            }

            .review-card:hover .reviewer-info {
                transform: translateY(5px);
            }

            .reviewer-avatar {
                width: 50px;
                height: 50px;
                margin-right: 15px;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #cbacc4;
                border-radius: 50%;
                transition: all 0.3s ease;
            }

            .reviewer-avatar i {
                font-size: 1.5rem;
                color: #ffffff;
            }

            .review-card:hover .reviewer-avatar {
                transform: rotate(360deg);
            }

            .reviewer-name {
                font-weight: bold;
                color: #333;
            }

            .reviewer-movie {
                font-size: 0.9rem;
                color: #666;
            }

            .swiper-button-next, .swiper-button-prev {
                color: #8A2BE2;
                transition: all 0.3s ease;
            }

            .swiper-button-next:hover, .swiper-button-prev:hover {
                transform: scale(1.2);
            }

            .swiper-pagination-bullet {
                background-color: #8A2BE2;
                opacity: 0.5;
                transition: all 0.3s ease;
            }

            .swiper-pagination-bullet-active {
                opacity: 1;
                transform: scale(1.2);
            }
            .star-rating {
                color: #FFD700;
                font-size: 1.2rem;
                margin-bottom: 15px;
            }

            .star-rating i {
                margin-right: 2px;
            }

            .star-rating i:last-child {
                margin-right: 0;
            }


            .review-card:hover .star-rating i {
                animation: star-bounce 0.3s ease infinite alternate;
            }

            @keyframes star-bounce {
                0% {
                    transform: scale(1);
                }
                100% {
                    transform: scale(1.2);
                }
            }
            .movie-ticket-promo {
                background-color: #fff1f6;
                padding: 50px 0;
                margin-bottom: 30px;
            }
            .movie-ticket-promo .btn-book-now {
                background-color: #e2147e;
                color: white;
            }

            .movie-ticket-promo .btn-book-now:hover {
                background-color: #c11269;
            }

            .promo-title {
                color: #e2147e;
                font-size: 2.5rem;
                font-weight: bold;
                margin-bottom: 20px;
            }

            .promo-description {
                color: #333;
                font-size: 1rem;
                margin-bottom: 20px;
            }

            .promo-features {
                list-style-type: none;
                padding-left: 0;
            }

            .promo-features li {
                position: relative;
                padding-left: 30px;
                margin-bottom: 15px;
                font-size: 16px;
            }

            .promo-features li::before {
                content: '✓';
                position: absolute;
                left: 0;
                top: 50%;
                transform: translateY(-50%);
                width: 20px;
                height: 20px;
                background-color: #FFE6F2;
                border-radius: 50%;
                color: #E2147E;
                font-size: 14px;
                line-height: 20px;
                text-align: center;
                font-weight: bold;
            }

            .btn-book-now {
                background-color: #e2147e;
                border: none;
                padding: 10px 20px;
                font-size: 1rem;
                font-weight: bold;
                text-transform: uppercase;
                margin-top: 20px;
            }

            .btn-book-now {
                background-color: #e2147e !important;
                color: white !important;
            }

            .promo-image {
                max-width: 100%;
                height: auto;
                border-radius: 10px;
            }
            .promotions {
                padding: 50px 0;
                background-color: #f8f9fa;
            }

            .section-title {
                color: #e2147e;
                font-weight: bold;
            }

            .promo-card {
                background: white;
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0 4px 6px rgba(0,0,0,0.1);
                transition: transform 0.3s ease;
            }

            .promo-card:hover {
                transform: translateY(-5px);
            }

            .promo-content {
                padding: 15px;
            }

            .promo-content h3 {
                font-size: 18px;
                color: #333;
                margin-bottom: 10px;
            }

            .promo-content p {
                font-size: 14px;
                color: #666;
                margin-bottom: 10px;
            }

            .promo-date {
                font-size: 12px;
                color: #999;
            }
            .promo-card .btn-primary {
                background-color: #e2147e;
                border-color: #e2147e;
                color: #ffffff;
                width: 100%;
                margin-top: 10px;
                transition: all 0.3s ease;
            }

            .promo-card .btn-primary:hover {
                background-color: #c11269;
                border-color: #c11269;
            }
            .faq-section {
                padding: 50px 0;
                background-color: #fff1f6;
                margin-top: 60px;
                position: relative;
            }

            .faq-section::before {
                content: '';
                position: absolute;
                top: -30px;
                left: 50%;
                transform: translateX(-50%);
                width: 80%;
                height: 1px;
                background: linear-gradient(to right, transparent, #e2147e, transparent);
            }

            .faq-title {
                color: #e2147e;
                font-size: 45px;
                font-weight: bold;
                margin-bottom: 15px;
            }

            .faq-subtitle {
                color: #666;
                font-size: 17px;
            }

            .faq-link {
                color: #e2147e;
                text-decoration: none;
            }

            .faq-list {
                margin-top: 20px;
            }

            .faq-item {
                border-bottom: 1px solid #ffcce6;
                padding: 15px 0;
            }

            .faq-question {
                font-size: 19px;
                color: #768c80;
                cursor: pointer;
                display: flex;
                justify-content: space-between;
                align-items: center;
                transition: color 0.3s ease;
                font-weight: bold;
            }

            .faq-question::after {
                content: '\25BC';
                font-size: 12px;
                color: #e2147e;
                transition: transform 0.3s ease;
            }

            .faq-answer {
                height: auto;
                max-height: 0;
                overflow: hidden;
                transition: max-height 0.5s ease, padding 0.5s ease, opacity 0.5s ease;
                opacity: 0;
                font-size: 14px;
                color: #666;
                padding: 0 15px;
            }

            .faq-item.active .faq-answer {
                max-height: 1000px;
                opacity: 1;
                padding: 15px;
            }

            .faq-item.active .faq-question {
                color: #e2147e;
            }

            .faq-item.active .faq-question::after {
                transform: rotate(180deg);
            }

            .faq-item.active .faq-answer {
                max-height: 1000px;
                padding-top: 10px;
            }
            .faq-section {
                margin-bottom: 60px; /* Tạo khoảng cách với section tiếp theo */
            }

            .more-info-section {
                padding: 50px 0;
                background-color: #fff;
            }

            .more-info-title {
                color: #e2147e;
                font-size: 32px;
                font-weight: bold;
                margin-bottom: 20px;
            }

            .more-info-content {
                font-size: 16px;
                color: #333;
                line-height: 1.6;
            }

            .hidden-content {
                max-height: 0;
                overflow: hidden;
                transition: max-height 0.5s ease-out, opacity 0.5s ease-out;
                opacity: 0;
            }

            .hidden-content.show {
                max-height: 500px;
                opacity: 1;
                transition: max-height 0.5s ease-in, opacity 0.5s ease-in;
            }

            .highlight {
                color: #e2147e;
            }

            .toggle-btn {
                background-color: transparent;
                color: #1877f2;
                border: none;
                padding: 10px 0;
                margin-top: 10px;
                cursor: pointer;
                font-size: 14px;
                font-weight: bold;
                display: flex;
                align-items: center;
            }

            .toggle-btn::after {
                content: '\25BC';
                font-size: 10px;
                margin-left: 5px;
            }

            .toggle-btn.active::after {
                content: '\25B2';
            }
            .price-table-title {
                color: #e2147e;
                font-size: 24px;
                margin-top: 30px;
                margin-bottom: 20px;
            }

            .price-table {
                width: 100%;
                margin-bottom: 30px;
            }

            .price-table th {
                background-color: #e2147e;
                color: white;
                text-align: center;
                padding: 10px;
            }

            .price-table td {
                text-align: center;
                padding: 10px;
            }

            .price-table tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            .price-table tr:hover {
                background-color: #ddd;
            }

            @media (max-width: 768px) {
                .table-responsive {
                    overflow-x: auto;
                }
            }
            .movie-buttons {
                display: flex;
                justify-content: space-between;
                margin-top: 10px;
            }

            .btn-book, .btn-details {
                display: inline-block;
                background-color: #e066ff;
                color: #fff;
                padding: 8px 15px;
                border-radius: 5px;
                text-decoration: none;
                text-align: center;
                transition: background-color 0.3s ease;
                text-transform: uppercase;
                font-weight: bold;
                font-size: 0.9rem;
                flex: 1;
                margin: 0 5px;
            }

            .btn-book:hover, .btn-details:hover {
                background-color: #ff69b4;
            }
        </style>
    </head>
    <body>

        <jsp:include page="Header.jsp" />


        <!<!-- Intro -->
        <section class="movie-ticket-promo">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-6">
                        <h2 class="promo-title">Mua vé xem phim Online trên Unove</h2>
                        <p class="promo-description">Với nhiều ưu đãi hấp dẫn và kết nối với tất cả các rạp lớn phủ rộng khắp Việt Nam. Đặt vé ngay tại Unove!</p>
                        <ul class="promo-features">
                            <li><strong>Mua vé Online</strong>, trải nghiệm phim hay</li>
                            <li><strong>Đặt vé an toàn</strong> trên Unove</li>
                            <li>Tha hồ <strong>chọn chỗ ngồi, mua bắp nước</strong> tiện lợi.</li>
                            <li><strong>Lịch sử đặt vé</strong> được lưu lại ngay</li>
                        </ul>
                        <a href="${pageContext.request.contextPath}/showtimes" class="btn btn-primary btn-book-now">ĐẶT VÉ NGAY</a>
                    </div>
                    <div class="col-md-6">
                        <img src="images/Unove-banner.jpg" alt="Đặt vé xem phim trên MoMo" class="img-fluid promo-image">
                    </div>
                </div>
            </div>
        </section>


        <!-- Carousel Section -->
        <div id="movieCarousel" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <c:forEach var="movie" items="${movies}" varStatus="status">
                    <div class="carousel-item ${status.first ? 'active' : ''}">
                        <img src="${movie.imageURL}" class="d-block w-100" alt="${movie.title}">
                        <div class="carousel-caption">
                            <h2 class="carousel-title">${movie.title}</h2>
                            <p class="carousel-text">
                                <c:forEach var="genre" items="${movie.genres}" varStatus="genreStatus">
                                    ${genre.genreName}${!genreStatus.last ? ', ' : ''}
                                </c:forEach>
                            </p>
                            <a href="${pageContext.request.contextPath}/showtimes" class="btn btn-book-now">Đặt vé ngay</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#movieCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#movieCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>

        <!-- Now Showing Section -->
        <section id="now-showing" class="py-5" data-aos="fade-up">
            <div class="container">
                <h2 class="section-title text-center" data-aos="fade-up" data-aos-delay="100">PHIM ĐANG CHIẾU</h2>

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
                                            <a href="${pageContext.request.contextPath}/showtimes" class="btn-book">ĐẶT VÉ</a>
                                            <a href="${pageContext.request.contextPath}/HandleDisplayMovieInfo?movieID=${movie.movieID}" class="btn-details">CHI TIẾT</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="swiper-button-next"></div>
                    <div class="swiper-button-prev"></div>
                </div>
            </div>
        </section>
        <section class="promotions">
            <div class="container">
                <h2 class="section-title text-center mb-5">Tin tức - Khuyến mãi</h2>
                <div class="row">
                    <div class="col-md-3 mb-4">
                        <div class="promo-card">
                            <img src="images/unove1.jpg" alt="Ưu đãi hơi tại rạp Lotte Cinema" class="img-fluid">
                            <div class="promo-content">
                                <h3>Ưu đãi hơi tại rạp Lotte Cinema MoMo mời bạn vé chỉ 55.000Đ. Xem phim ngay!</h3>
                                <span class="promo-date">15/10/2024</span>
                                <a href="#" class="btn btn-primary btn-sm mt-2">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="promo-card">
                            <img src="images/unove7.jpg" alt="Bỗng Bỗng Bang Bang: Thử tài nuôi cá bỗng" class="img-fluid">
                            <div class="promo-content">
                                <h3>Bỗng Bỗng Bang Bang: Thử tài nuôi cá bỗng.Trúng quà từ phim CÁM!</h3>

                                <span class="promo-date">27/09/2024</span>
                                <a href="#" class="btn btn-primary btn-sm mt-2">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="promo-card">
                            <img src="images/unove2.jpg" alt="Nhập mã HELLOMOMO" class="img-fluid">
                            <div class="promo-content">
                                <h3>Nhập mã HELLOUNOVE.Bạn mới có ngay quà 50.000Đ đặt vé xem phim & quà 950.000Đ mọi dịch vụ</h3>

                                <span class="promo-date">12/09/2024</span>
                                <a href="#" class="btn btn-primary btn-sm mt-2">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="promo-card">
                            <img src="images/unove3.jpg" alt="Nhập hội U22, Lotte Cinema" class="img-fluid">
                            <div class="promo-content">
                                <h3>Nhập hội U22, Lotte Cinema.Đồng giá 65.000Đ/vé phim 2D khi thanh toán với Unove!</h3>

                                <span class="promo-date">06/09/2024</span>
                                <a href="#" class="btn btn-primary btn-sm mt-2">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="promo-card">
                            <img src="images/unove4.jpg" alt="Beta Cinema đồng giá" class="img-fluid">
                            <div class="promo-content">
                                <h3>Beta Cinema đồng giá.40.000Đ/vé cho U22 khi thanh toán bằng MoMo: Rạp sinh viên, giá sinh viên!</h3>

                                <span class="promo-date">04/09/2024</span>
                                <a href="#" class="btn btn-primary btn-sm mt-2">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="promo-card">
                            <img src="images/unove8.jpg" alt="Ưu đãi hời tại rạp Galaxy Parc Mall" class="img-fluid">
                            <div class="promo-content">
                                <h3>Ưu đãi hời tại rạp Galaxy Parc Mall.Unove mời bạn vé chỉ 9.000Đ. Xem phim ngay!</h3>

                                <span class="promo-date">30/08/2024</span>
                                <a href="#" class="btn btn-primary btn-sm mt-2">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="promo-card">
                            <img src="images/unove5.jpg" alt="Mua vé Lotte Cinema săn bắp nước" class="img-fluid">
                            <div class="promo-content">
                                <h3>Mua vé Lotte Cinema săn bắp nước.Miễn phí trên Unove!</h3>

                                <span class="promo-date">09/08/2024</span>
                                <a href="#" class="btn btn-primary btn-sm mt-2">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="promo-card">
                            <img src="images/unove6.jpg" alt="100% trúng thưởng khi chơi Vòng quay Conan" class="img-fluid">
                            <div class="promo-content">
                                <h3>100% trúng thưởng khi chơi Vòng quay Conan.Trên Unove</h3>

                                <span class="promo-date">19/07/2024</span>
                                <a href="#" class="btn btn-primary btn-sm mt-2">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </section>

        <section class="faq-section">
            <div class="container">
                <div class="row">
                    <div class="col-md-4">
                        <h2 class="faq-title">Bạn hỏi, Unove trả lời</h2>
                        <p class="faq-subtitle">Chào mừng bạn đến với nền tảng đặt vé của chúng tôi</p>
                    </div>
                    <div class="col-md-8">
                        <div class="faq-list">
                            <div class="faq-item">
                                <div class="faq-question">Có thể mua vé xem phim những rạp nào trên Unove?</div>
                                <div class="faq-answer">Hiện tại bạn có thể đặt vé tại rạp phim cũng như xem lịch chiếu phim các rạp sau: CGV Cinemas, Lotte Cinema, Galaxy Cinema, BHD Star.</div>
                            </div>
                            <div class="faq-item">
                                <div class="faq-question">Lợi ích của việc mua vé xem phim trên Unove?</div>
                                <div class="faq-answer">Đặt vé trên Unove giúp bạn tiết kiệm thời gian, tránh xếp hàng, và thường xuyên có các ưu đãi đặc biệt.</div>
                            </div>
                            <div class="faq-item">
                                <div class="faq-question">Có thể mua vé xem phim kèm bắp nước hay không?</div>
                                <div class="faq-answer">Có, bạn có thể đặt combo bắp nước cùng với vé xem phim trên Unove để tiết kiệm thời gian và chi phí.</div>
                            </div>
                            <div class="faq-item">
                                <div class="faq-question">Mua vé xem phim tại Unove có đắt hơn mua trực tiếp tại rạp không?</div>
                                <div class="faq-answer">Không, giá vé trên Unove tương đương với giá tại rạp. Thậm chí, bạn còn có cơ hội nhận được các ưu đãi đặc biệt khi đặt vé qua Unove.</div>
                            </div>
                            <div class="faq-item">
                                <div class="faq-question">Vé xem phim có được đổi trả, hoàn hủy không?</div>
                                <div class="faq-answer">Chính sách đổi trả và hoàn hủy vé phụ thuộc vào từng rạp phim. Vui lòng kiểm tra chính sách cụ thể khi đặt vé.</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <section class="more-info-section">
            <div class="container">
                <div class="row">
                    <div class="col-md-4">
                        <h2 class="more-info-title">Mua vé dễ dàng cùng Unove</h2>
                    </div>
                    <div class="col-md-8">
                        <div class="more-info-content">
                            <p>Việc đặt vé xem phim chưa bao giờ trở nên đơn giản và thuận tiện đến thế. Chỉ với vài thao tác trên điện thoại, bạn có thể dễ dàng đặt vé cho bộ phim mình yêu thích mà không cần phải xếp hàng tại rạp. Unove đã liên kết với tất cả các rạp chiếu phim lớn trên toàn quốc, bất kỳ rạp nào bạn yêu thích, Unove đều có. Giá vé luôn được đảm bảo tốt nhất, cùng với những ưu đãi độc quyền hấp dẫn mà bạn chỉ có thể tìm thấy qua tính năng "Mua Vé Xem Phim" của Unove.</p>

                            <div class="hidden-content">
                                <h3 class="price-table-title">Giá vé rạp chiếu phim (Phim 2D - dành cho người lớn)</h3>
                                <div class="table-responsive">
                                    <table class="table table-bordered price-table">
                                        <thead>
                                            <tr>
                                                <th>Rạp</th>
                                                <th>Thứ 2</th>
                                                <th>Thứ 3</th>
                                                <th>Thứ 4</th>
                                                <th>Thứ 5</th>
                                                <th>Thứ 6</th>
                                                <th>Thứ 7</th>
                                                <th>Chủ Nhật</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>CGV</td>
                                                <td>70K - 85K</td>
                                                <td>70K - 85K</td>
                                                <td>75K</td>
                                                <td>70K - 85K</td>
                                                <td>105K</td>
                                                <td>105K</td>
                                                <td>105K</td>
                                            </tr>
                                            <tr>
                                                <td>BHD</td>
                                                <td>60K</td>
                                                <td>60K - 50K</td>
                                                <td>60K - 50K</td>
                                                <td>60K - 50K</td>
                                                <td>70K - 90K</td>
                                                <td>70K - 90K</td>
                                                <td>70K - 90K</td>
                                            </tr>
                                            <tr>
                                                <td>Lotte Cinema</td>
                                                <td>45K - 80K</td>
                                                <td>45K</td>
                                                <td>45K - 80K</td>
                                                <td>45K - 80K</td>
                                                <td>45K - 90K</td>
                                                <td>45K - 90K</td>
                                                <td>45K - 90K</td>
                                            </tr>
                                            <tr>
                                                <td>Galaxy</td>
                                                <td>45K - 75K</td>
                                                <td>45K - 50K</td>
                                                <td>45K - 75K</td>
                                                <td>45K - 75K</td>
                                                <td>60K - 95K</td>
                                                <td>60K - 95K</td>
                                                <td>60K - 95K</td>
                                            </tr>
                                            <tr>
                                                <td>Cinestar</td>
                                                <td>45K</td>
                                                <td>45K - 70K</td>
                                                <td>45K - 70K</td>
                                                <td>45K - 70K</td>
                                                <td>45K - 75K</td>
                                                <td>45K - 75K</td>
                                                <td>45K - 75K</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                </table>
                            </div>
                            <p>Nay buổi xem <span class="highlight">phim chiếu rạp</span> của bạn sẽ càng tuyệt vời hơn với tính năng mua bắp nước trước khi đến rạp, bạn sẽ dễ dàng lựa chọn bắp hay thức uống mà bạn yêu thích, sao đặt ngay cho bạn đồng hành bằng những combo cực chất lượng đến từ Unove.</p>
                        </div>
                        <button class="toggle-btn">Xem thêm</button>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section id="customer-reviews" class="py-5">
        <div class="container">
            <h2 class="section-title text-center" data-aos="fade-up">Cảm nhận của khách hàng</h2>
            <p class="text-center mb-5">Các bạn có thể đọc thêm các đánh giá của khách hàng về Unove Cinema tại trang đánh giá của chúng tôi</p>
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <div class="swiper-slide">
                        <div class="review-card">
                            <h3 class="review-title">Trải nghiệm xem phim tuyệt vời!</h3>
                            <div class="star-rating">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                            </div>
                            <p class="review-content">"Unove Cinema mang đến trải nghiệm xem phim tuyệt vời! Chất lượng hình ảnh và âm thanh đỉnh cao, ghế ngồi cực kỳ thoải mái. Đặc biệt, bộ phim 'Tee Yod 2: Quỷ Ăn Tạng 2' thật sự là một kiệt tác. Tôi sẽ quay lại đây thường xuyên."</p>
                            <div class="reviewer-info">
                                <div class="reviewer-avatar">
                                    <i class="fas fa-user-circle"></i>
                                </div>
                                <div>
                                    <div class="reviewer-name">Phạm Gia Huy</div>
                                    <div class="reviewer-movie">Đã xem: Tee Yod 2: Quỷ Ăn Tạng 2</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide">
                        <div class="review-card">
                            <h3 class="review-title">Dịch vụ xuất sắc!</h3>
                            <div class="star-rating">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star-half-alt"></i>
                            </div>
                            <p class="review-content">"Tôi rất ấn tượng với dịch vụ tại Unove Cinema. Nhân viên thân thiện và nhiệt tình. Bộ phim 'Đố Anh Còng Được Tôi' thật sự bùng nổ trên màn hình lớn. Âm thanh vòm làm tăng trải nghiệm xem phim lên rất nhiều."</p>
                            <div class="reviewer-info">
                                <div class="reviewer-avatar">
                                    <i class="fas fa-user-circle"></i>
                                </div>
                                <div>
                                    <div class="reviewer-name">Đặng Đỗ Trọng Nghĩa</div>
                                    <div class="reviewer-movie">Đã xem: Đố Anh Còng Được Tôi</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide">
                        <div class="review-card">
                            <h3 class="review-title">Công nghệ hiện đại!</h3>
                            <div class="star-rating">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                            </div>
                            <p class="review-content">"Công nghệ chiếu phim tại Unove Cinema thật sự ấn tượng. Xem 'Avatar' với hệ thống 3D tại đây là một trải nghiệm không thể quên. Tôi cảm thấy như mình đang ở trong thế giới Pandora vậy!"</p>
                            <div class="reviewer-info">
                                <div class="reviewer-avatar">
                                    <i class="fas fa-user-circle"></i>
                                </div>
                                <div>
                                    <div class="reviewer-name">Nguyễn Việt Khoa</div>
                                    <div class="reviewer-movie">Đã xem: Avatar</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide">
                        <div class="review-card">
                            <h3 class="review-title">Đặt vé dễ dàng!</h3>
                            <div class="star-rating">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="far fa-star"></i>
                            </div>
                            <p class="review-content">"Hệ thống đặt vé online của Unove Cinema rất tiện lợi và dễ sử dụng. Tôi đã đặt vé xem 'Minh Hôn' mà không gặp bất kỳ trở ngại nào. Phim hay, rạp đẹp, một buổi tối hoàn hảo!"</p>
                            <div class="reviewer-info">
                                <div class="reviewer-avatar">
                                    <i class="fas fa-user-circle"></i>
                                </div>
                                <div>
                                    <div class="reviewer-name">Nguyễn Đắc Phong</div>
                                    <div class="reviewer-movie">Đã xem: Minh Hôn</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide">
                        <div class="review-card">
                            <h3 class="review-title">Không gian thoải mái!</h3>
                            <div class="star-rating">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star-half-alt"></i>
                            </div>
                            <p class="review-content">"Ghế ngồi tại Unove Cinema cực kỳ thoải mái. Xem 'CÁM' trong 3 tiếng mà không hề cảm thấy mỏi. Không gian rạp cũng rất sạch sẽ và hiện đại. Một trải nghiệm xem phim tuyệt vời!"</p>
                            <div class="reviewer-info">
                                <div class="reviewer-avatar">
                                    <i class="fas fa-user-circle"></i>
                                </div>
                                <div>
                                    <div class="reviewer-name">Minh Lê</div>
                                    <div class="reviewer-movie">Đã xem: CÁM</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide">
                        <div class="review-card">
                            <h3 class="review-title">Âm thanh đỉnh cao!</h3>
                            <div class="star-rating">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                            </div>
                            <p class="review-content">"Hệ thống âm thanh tại Unove Cinema thật sự xuất sắc. Xem 'Transformer Một' ở đây, tôi cảm nhận được từng tiếng động, từng nhịp tim đập trong phim. Một trải nghiệm điện ảnh hoàn hảo!"</p>
                            <div class="reviewer-info">
                                <div class="reviewer-avatar">
                                    <i class="fas fa-user-circle"></i>
                                </div>
                                <div>
                                    <div class="reviewer-name">Lê Văn Hiếu</div>
                                    <div class="reviewer-movie">Đã xem: Transformer Một</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide">
                        <div class="review-card">
                            <h3 class="review-title">Giá cả hợp lý!</h3>
                            <div class="star-rating">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="far fa-star"></i>
                            </div>
                            <p class="review-content">"Với chất lượng dịch vụ tuyệt vời như vậy, giá vé tại Unove Cinema rất hợp lý. Tôi đã xem 'Nơi Tình Yêu Kết Thúc' và cảm thấy xứng đáng từng đồng. Chắc chắn sẽ quay lại!"</p>
                            <div class="reviewer-info">
                                <div class="reviewer-avatar">
                                    <i class="fas fa-user-circle"></i>
                                </div>
                                <div>
                                    <div class="reviewer-name">Đặng Văn Trí</div>
                                    <div class="reviewer-movie">Đã xem: Nơi Tình Yêu Kết Thúc</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide">
                        <div class="review-card">
                            <h3 class="review-title">Lựa chọn phim đa dạng!</h3>
                            <div class="star-rating">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star-half-alt"></i>
                            </div>
                            <p class="review-content">"Unove Cinema luôn có những bộ phim mới và đa dạng. Tôi vừa xem 'Joker: Điên Có Đôi' ở đây và rất ấn tượng với cách họ chọn lựa phim. Từ bom tấn đến phim nghệ thuật, đều có cả!"</p>
                            <div class="reviewer-info">
                                <div class="reviewer-avatar">
                                    <i class="fas fa-user-circle"></i>
                                </div>
                                <div>
                                    <div class="reviewer-name">Nguyễn Hữu Huy</div>
                                    <div class="reviewer-movie">Đã xem: Joker: Điên Có Đôi</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="swiper-pagination"></div>
            </div>
            <div class="swiper-button-next"></div>
            <div class="swiper-button-prev"></div>
        </div>
    </section>
    <footer>
        <div class="container">
            <div class="row">
                <div class="col-md-4 mb-4 mb-md-0">
                    <h5 class="footer-title">Liên hệ</h5>
                    <p><i class="fas fa-envelope mr-2"></i> Unove@gmail.com</p>
                    <p><i class="fas fa-phone mr-2"></i> (123) 456-7890</p>
                    <div class="social-icons mt-3">
                        <a href="#"><i class="fab fa-facebook-f"></i></a>
                        <a href="#"><i class="fab fa-twitter"></i></a>
                        <a href="#"><i class="fab fa-instagram"></i></a>
                        <a href="#"><i class="fab fa-youtube"></i></a>
                    </div>
                </div>
                <div class="col-md-4 mb-4 mb-md-0">
                    <h5 class="footer-title">Liên kết nhanh</h5>
                    <ul class="list-unstyled footer-links">
                        <li><a href="#">Trang chủ</a></li>
                        <li><a href="#">Phim đang chiếu</a></li>
                        <li><a href="#">Đặt vé</a></li>
                        <li><a href="#">Liên hệ</a></li>
                    </ul>
                </div>
                <div class="col-md-4">
                    <h5 class="footer-title">Về chúng tôi</h5>
<!--                    <img class="mb-3" src="${pageContext.request.contextPath}/page/image/logoHeader.png" alt="Logo" style="max-width: 150px;">-->
                    <p>Chúng tôi cung cấp dịch vụ đặt vé xem phim trực tuyến nhanh chóng và tiện lợi.</p>
                </div>
            </div>
        </div>
        <div class="text-center mt-4">
            <p>&copy; 2024 Unove Cinema. Tất cả quyền được bảo lưu.</p>
        </div>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <!-- AOS JS -->
    <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
    <script>
        AOS.init({
            duration: 1000,
            once: true,
            mirror: false
        });


        var myCarousel = document.querySelector('#movieCarousel')
        var carousel = new bootstrap.Carousel(myCarousel, {
            interval: 5000,
            wrap: true
        });
        document.addEventListener('DOMContentLoaded', function () {
            new Swiper('#customer-reviews .swiper-container', {
                slidesPerView: 1,
                spaceBetween: 30,
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
                    640: {
                        slidesPerView: 2,
                    },
                    1024: {
                        slidesPerView: 3,
                    },
                }
            });
        });
        document.querySelectorAll('.faq-question').forEach(question => {
            question.addEventListener('click', () => {
                const item = question.parentNode;
                const answer = item.querySelector('.faq-answer');

                document.querySelectorAll('.faq-item').forEach(otherItem => {
                    if (otherItem !== item) {
                        otherItem.classList.remove('active');
                    }
                });

                item.classList.toggle('active');
            });
        });
        document.addEventListener('DOMContentLoaded', function () {
            const toggleBtn = document.querySelector('.toggle-btn');
            const hiddenContent = document.querySelector('.hidden-content');

            toggleBtn.addEventListener('click', function () {
                hiddenContent.classList.toggle('show');
                if (hiddenContent.classList.contains('show')) {
                    this.textContent = 'Thu gọn';
                    // Cuộn xuống để hiển thị bảng giá
                    hiddenContent.scrollIntoView({behavior: "smooth", block: "start"});
                } else {
                    this.textContent = 'Xem thêm';
                }
            });
        });
        document.addEventListener('DOMContentLoaded', function () {
            new Swiper('.movie-carousel', {
                slidesPerView: 1,
                spaceBetween: 20,
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                },
                breakpoints: {
                    640: {
                        slidesPerView: 2,
                    },
                    768: {
                        slidesPerView: 3,
                    },
                    1024: {
                        slidesPerView: 5,
                    },
                }
            });
        });
    </script>
</body>
</html>