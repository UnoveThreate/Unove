<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Movie Filter</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .container {
                margin-top: 20px;
            }
            h1 {
                margin-bottom: 20px;
            }
            .form-control, .btn {
                min-width: 200px;
            }

            .movie-carousel {
                padding: 20px;
            }

            .swiper-container {
                width: 100%; /* Chiều rộng đầy đủ */
                overflow: hidden; /* Ẩn phần tràn ra ngoài */
            }

            .swiper-wrapper {
                display: flex; /* Hiển thị các slide nằm ngang */
            }

            .swiper-slide {
                display: flex;
                justify-content: center; /* Center the cards */
                padding: 10px; /* Thêm padding xung quanh các slide */
            }

            /*            .movie-card {
                            background: #fff;
                            border-radius: 8px;
                            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                            overflow: hidden;
                            text-align: center;
                            width: 200px;  Chiều rộng cố định 
                            max-width: 100%;  Đảm bảo không tràn ra ngoài 
                            height: 350px;  Đặt chiều cao cố định cho card 
                            display: flex;
                            flex-direction: column;  Để nội dung xếp chồng lên nhau 
                        }
            
                        .movie-poster-container {
                            flex-grow: 1;  Làm cho phần poster chiếm không gian còn lại 
                            display: flex;
                            justify-content: center;  Căn giữa hình ảnh 
                            align-items: center;  Căn giữa hình ảnh theo chiều dọc 
                        }
            
                        .movie-poster {
                            max-width: 100%;
                            max-height: 200px;  Đặt chiều cao tối đa cho hình ảnh 
                            object-fit: cover;  Đảm bảo hình ảnh không bị méo 
                        }
            
                        .movie-info {
                            padding: 10px;  Điều chỉnh padding cho phần thông tin 
                        }
            
                        .movie-title {
                            font-size: 1.2em;  Kích thước chữ tiêu đề 
                            margin: 10px 0;
                        }
            
                        .movie-genre {
                            color: #777;
                            font-size: 0.9em;
                        }
            
                        .movie-buttons {
                            margin-top: 10px;  Điều chỉnh khoảng cách giữa các nút 
                        }*/

            .movie-card {
                background: #ffffff;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* Đổ bóng nhẹ */
                overflow: hidden;
                text-align: center;
                width: 220px;
                max-width: 100%;
                height: 380px;
                display: flex;
                flex-direction: column;
                transition: transform 0.3s ease, box-shadow 0.3s ease; /* Hiệu ứng khi hover */
            }

            .movie-card:hover {
                transform: translateY(-5px); /* Nhẹ nhàng nâng lên khi hover */
                box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15); /* Đổ bóng rõ hơn khi hover */
            }

            .movie-poster-container {
                display: flex;
                justify-content: center;
                align-items: center;
                overflow: hidden;
                height: 220px;
            }

            .movie-poster {
                width: 100%;
                height: auto;
                object-fit: cover;
                transition: transform 0.3s ease;
            }

            .movie-card:hover .movie-poster {
                transform: scale(1.05); /* Phóng to hình ảnh khi hover */
            }

            .movie-info {
                padding: 15px;
                flex-grow: 1;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
            }

            .movie-title {
                font-size: 1.2em;
                font-weight: 600;
                color: #333;
                margin: 10px 0 5px;
            }

            .movie-genre {
                color: #777;
                font-size: 0.9em;
                margin-bottom: 15px;
            }

            .movie-buttons .btn-details {
                display: inline-block;
                padding: 10px 20px;
                font-size: 0.95em;
                font-weight: 500;
                color: #fff;
                background: linear-gradient(135deg, #007bff, #0056b3);
                border-radius: 20px; /* Bo góc tròn */
                text-decoration: none;
                box-shadow: 0 4px 8px rgba(0, 123, 255, 0.3); /* Đổ bóng cho nút */
                transition: background 0.3s ease, transform 0.2s ease, box-shadow 0.3s ease;
            }

            .movie-buttons .btn-details:hover {
                background: linear-gradient(135deg, #0056b3, #004080); /* Thay đổi màu gradient khi hover */
                transform: translateY(-4px); /* Nâng nút lên khi hover */
                box-shadow: 0 6px 12px rgba(0, 86, 179, 0.4); /* Đổ bóng đậm hơn khi hover */
            }

            .movie-buttons .btn-details:active {
                transform: translateY(1px); /* Nhấn nút xuống khi click */
                box-shadow: 0 3px 6px rgba(0, 86, 179, 0.3); /* Giảm độ đổ bóng khi click */
            }


            .btn-book, .btn-details {
                display: inline-block;
                margin: 5px;
                padding: 8px 12px; /* Điều chỉnh padding */
                border-radius: 5px;
                text-decoration: none;
                font-size: 0.9em; /* Kích thước chữ cho nút */
            }

            .btn-book {
                background-color: #28a745;
            }

            .btn-details {
                background-color: #007bff;
            }

            .mb-3 {
                margin-bottom: 1rem; /* Điều chỉnh khoảng cách dưới */
            }

            .btn-details {
                display: inline-block;
                padding: 10px 20px;
                font-size: 16px;
                color: #fff;
                background-color: #007bff; /* Màu nền */
                border: none;
                border-radius: 5px; /* Bo góc */
                text-decoration: none; /* Bỏ gạch chân */
                transition: background-color 0.3s, transform 0.2s; /* Hiệu ứng chuyển tiếp */
            }

            .btn-details:hover {
                background-color: #0056b3; /* Màu nền khi hover */
                transform: translateY(-2px); /* Hiệu ứng di chuyển */
            }

        </style>
    </head>
    <body>
        <div>
            <div class="container">
                <h1 class="text-center">Lọc Phim</h1>
                <div class="row mb-4">
                    <!-- Lọc theo thể loại -->
                    <div class="col-md-6 mb-3">
                        <form action="${pageContext.request.contextPath}/filterMovies" method="get" class="d-flex">
                            <select name="genre" class="form-control me-2" onchange="this.form.submit()">
                                <option value="">Chọn thể loại</option>
                                <c:forEach var="genre" items="${genres}">
                                    <option value="${genre.genreName}" 
                                            <c:if test="${genre.genreName == selectedGenre}">selected</c:if>>${genre.genreName}</option>
                                </c:forEach>
                            </select>
                        </form>
                    </div>

                    <!-- Lọc theo quốc gia -->
                    <div class="col-md-6 mb-3">
                        <form action="${pageContext.request.contextPath}/filterMovies" method="get" class="d-flex">
                            <select name="country" class="form-control me-2" onchange="this.form.submit()">
                                <option value="">Chọn quốc gia</option>
                                <c:forEach var="country" items="${countries}">
                                    <option value="${country}" 
                                            <c:if test="${country == selectedCountry}">selected</c:if>>${country}</option>
                                </c:forEach>
                            </select>
                        </form>
                    </div>
                </div>

                <div class="row">
                    <!-- Tìm kiếm phim -->
                    <div class="col-md-12 text-center">
                        <form action="${pageContext.request.contextPath}/filterMovies" method="get" class="input-group mb-3">
                            <input type="text" class="form-control" placeholder="Tìm kiếm phim..." name="searchQuery" aria-label="Tìm kiếm phim" value="${searchQuery}">
                            <button class="btn btn-success" type="submit">Tìm kiếm</button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
        <div class="swiper-container movie-carousel">
            <div class="swiper-wrapper">
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
                                        <a href="${pageContext.request.contextPath}/HandleDisplayMovieInfo?movieID=${movie.movieID}" class="btn-details">CHI TIẾT</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

            </div>
            <div class="swiper-button-next"></div>
            <div class="swiper-button-prev"></div>
        </div>

    </body>
</html>
