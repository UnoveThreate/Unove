<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%> <%@ page import="model.Movie" %> <%@ taglib
         uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ taglib
    uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <% Movie movie = (Movie) request.getAttribute("movie");%>

        <!DOCTYPE html>
        <html lang="en">
            <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <title><%= movie.getTitle()%></title>
                <link
                    rel="stylesheet"
                    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
                    />
                <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet" />
                <style>
                    @import url("https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap");

                    body,
                    html {
                        margin: 0;
                        padding: 0;
                        font-family: "Poppins", sans-serif;
                        background: linear-gradient(135deg, #1c1c1c 0%, #2c2c2c 100%);
                        color: white;
                        min-height: 100vh;
                    }

                    .container {
                        display: flex;
                        background: rgba(255, 255, 255, 0.05);
                        backdrop-filter: blur(10px);
                        border-radius: 30px;
                        padding: 50px;
                        max-width: 1200px;
                        margin: 40px auto;
                        border: 5px dashed rgba(126, 96, 191, 0.2);
                    }

                    .poster {
                        width: 40%;
                        min-width: 300px;
                        height: 450px;
                        border-radius: 20px;
                        background-size: cover;
                        background-position: center;
                        position: relative;
                        overflow: hidden;
                        transition: transform 0.5s ease, box-shadow 0.5s ease;
                        padding: 3px;
                    }

                    .poster:hover {
                        transform: scale(1.05) rotate(1deg);
                        box-shadow: 0 15px 40px rgba(0, 0, 0, 0.6);
                    }

                    .play-button {
                        position: absolute;
                        top: 50%;
                        left: 50%;
                        transform: translate(-50%, -50%);
                        background-color: rgba(255, 255, 255, 0.2);
                        border: none;
                        border-radius: 50%;
                        width: 80px;
                        height: 80px;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        cursor: pointer;
                        transition: all 0.3s ease;
                    }

                    .play-button:hover {
                        background-color: rgba(255, 255, 255, 0.4);
                        transform: translate(-50%, -50%) scale(1.1);
                    }

                    .play-button i {
                        font-size: 40px;
                        color: white;
                    }

                    .movie-details {
                        width: 55%;
                        padding-left: 50px;
                    }

                    .movie-title {
                        font-size: 48px;
                        font-weight: 700;
                        color: #ffffff;
                        margin-bottom: 20px;

                    }

                    .ratings {
                        display: flex;
                        align-items: center;
                        margin: 20px 0;
                    }

                    .rating {
                        font-size: 24px;
                        display: flex;
                        align-items: center;
                        background: #d4bee4;
                        padding: 8px 15px;
                        border-radius: 30px;
                        color: white;
                        font-weight: bold;
                        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                    }

                    .rating i {
                        margin-right: 10px;
                        color: white;
                    }

                    .metadata {
                        display: flex;
                        margin-top: 30px;
                        font-size: 18px;
                    }

                    .metadata div {
                        flex-basis: 33.33%;
                        padding-right: 20px;
                    }

                    .metadata strong {
                        color: #d4bee4;
                        font-weight: 600;
                    }

                    .buttons {
                        margin-top: 40px;
                    }

                    button {
                        padding: 15px 30px;
                        border: none;
                        cursor: pointer;
                        font-weight: bold;
                        border-radius: 50px;
                        font-size: 18px;
                        transition: all 0.3s ease;
                    }

                    #trailerBtn {
                        background: #f8f0ff;
                        color: black;
                        margin-right: 20px;
                        border: 3px dashed #c0a3ff;
                    }

                    #trailerBtn:hover {
                        transform: translateY(-5px);
                        background: #d4bee4;
                    }

                    #addToFavoriteForm a,
                    #deleteFavoriteMovieForm a,
                    #viewFavouriteMoviesForm a {
                        display: inline-block;
                        text-decoration: none;
                        cursor: pointer;
                        margin-top: 20px;
                        color: #ffffff;
                        font-size: 16px;
                        transition: all 0.3s ease;
                    }

                    #addToFavoriteForm a:hover,
                    #deleteFavoriteMovieForm a:hover,
                    #viewFavouriteMoviesForm a:hover {
                        color: #ffd700;
                        transform: translateY(-2px);
                    }

                    #addToFavoriteForm img,
                    #deleteFavoriteMovieForm img,
                    #viewFavouriteMoviesForm img {
                        vertical-align: middle;
                        margin-right: 10px;
                        transition: transform 0.3s ease;
                    }

                    #addToFavoriteForm a:hover img,
                    #deleteFavoriteMovieForm a:hover img,
                    #viewFavouriteMoviesForm a:hover img {
                        transform: scale(1.1);
                    }

                    .modal {
                        display: none;
                        position: fixed;
                        z-index: 999;
                        left: 0;
                        top: 0;
                        width: 100%;
                        height: 100%;
                        background-color: rgba(0, 0, 0, 0.9);
                        justify-content: center;
                        align-items: center;
                        transition: opacity 0.5s ease;
                    }

                    .modal-content {
                        background-color: #1e1e1e;
                        color: #f0f0f0;
                        padding: 40px;
                        border-radius: 20px;
                        position: relative;
                        text-align: center;
                        box-shadow: 0 15px 30px rgba(0, 0, 0, 0.5);
                    }

                    .close {
                        position: absolute;
                        top: 15px;
                        right: 25px;
                        color: #ffffff;
                        font-size: 40px;
                        font-weight: bold;
                        cursor: pointer;
                        transition: all 0.3s ease;
                    }

                    .close:hover {
                        color: #ff416c;
                        transform: scale(1.2) rotate(90deg);
                    }

                    @media (max-width: 768px) {
                        .container {
                            flex-direction: column;
                            align-items: center;
                            padding: 30px;
                        }

                        .movie-details {
                            width: 100%;
                            padding-left: 0;
                            text-align: center;
                            margin-top: 30px;
                        }

                        .poster {
                            width: 100%;
                            height: 400px;
                            margin-bottom: 30px;
                        }

                        .metadata {
                            flex-direction: column;
                        }

                        .metadata div {
                            margin-bottom: 10px;
                        }
                    }
                    body {
                        font-family: 'Poppins', sans-serif;
                        margin: 0;
                        padding: 20px;
                        background: #ffe6ef;
                        color: #333;
                        min-height: 100vh;
                        position: relative;
                        overflow-x: hidden;
                    }

                    .wave-container {
                        position: fixed;
                        width: 100%;
                        height: 100%;
                        top: 0;
                        left: 0;
                        z-index: -1;
                        overflow: hidden;
                    }

                    .wave {
                        position: absolute;
                        width: 200%;
                        height: 200%;
                        background: #ffccd5;
                        opacity: 0.5;
                    }

                    .wave-1 {
                        top: -50%;
                        border-radius: 40%;
                        animation: wave 20s infinite linear;
                    }

                    .wave-2 {
                        top: -60%;
                        border-radius: 35%;
                        animation: wave 15s infinite linear;
                        opacity: 0.3;
                    }

                    @keyframes wave {
                        0% {
                            transform: rotate(0deg);
                        }
                        100% {
                            transform: rotate(360deg);
                        }
                    }

                    .container {
                        max-width: 1200px;
                        margin: auto;
                        padding: 30px;
                        background: rgba(255, 255, 255, 0.95);
                        border-radius: 20px;
                        backdrop-filter: blur(10px);
                        position: relative;
                        z-index: 1;
                    }

                    .header {
                        background: linear-gradient(135deg, #7E60BF, #7E60BF);
                        color: white;
                        padding: 20px;
                        border-radius: 10px;
                        margin-bottom: 30px;
                    }

                    .title {
                        font-size: 32px;
                        color: #FCFAEE;
                        text-align: center;
                        margin-bottom: 20px;
                    }

                    .selector {
                        margin-bottom: 20px;
                    }

                    .selector h3 {
                        color: #7E60BF;
                        margin-bottom: 10px;
                    }

                    .button-group {
                        display: flex;
                        flex-wrap: wrap;
                        gap: 15px;
                        justify-content: flex-start;
                    }

                    .selector-button {
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        width: 80px;
                        height: 80px;
                        background: #fff;
                        border: 1px solid #e0e0e0;
                        border-radius: 12px;
                        cursor: pointer;
                        transition: all 0.3s ease;
                        padding: 8px;
                        border: 3px dashed #e0e0e0;
                    }

                    .selector-button:hover,
                    .selector-button.active {
                        border-color: #7E60BF;
                        transform: translateY(-2px);
                        border: 3px dashed #7E60BF;
                    }

                    .cinema-chain-avatar {
                        width: 40px;
                        height: 40px;
                        object-fit: contain;
                        margin-bottom: 5px;
                    }

                    .selector-button span {
                        font-size: 10px;
                        text-align: center;
                        color: #333;
                        max-width: 100%;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        white-space: nowrap;
                    }

                    .selector-button:hover span,
                    .selector-button.active span {
                        color: #7E60BF;
                        font-weight: bold;
                    }

                    .date-selector {
                        display: flex;
                        overflow-x: auto;
                        gap: 10px;
                        padding-bottom: 10px;
                    }

                    .date-button {
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        padding: 10px;
                        border: 1px solid #ddd;
                        border-radius: 8px;
                        background-color: white;
                        cursor: pointer;
                        transition: all 0.3s ease;
                        min-width: 60px;
                        border: 3px dashed #7e60bf;
                    }

                    .date-button:hover {
                        transform: translateY(-2px);
                    }

                    .date-button.active {
                        background-color: #f8f0ff;
                        color: black;
                        border-color: #7E60BF;
                    }

                    .date-number {
                        font-size: 18px;
                        font-weight: bold;
                    }

                    .date-day {
                        font-size: 14px;
                    }

                    .cinema-list {
                        display: flex;
                        flex-direction: column;
                        gap: 10px;
                        max-height: 300px;
                        overflow-y: auto;
                        padding: 10px;
                        background: rgba(255, 255, 255, 0.5);
                        border-radius: 10px;
                        border: 3px dashed #e0e0e0;
                    }

                    .cinema-item {
                        display: flex;
                        align-items: center;
                        padding: 15px;
                        background: white;
                        border: none;
                        border-radius: 8px;
                        cursor: pointer;
                        transition: all 0.3s ease;
                        width: 100%;
                        text-align: left;
                    }

                    .cinema-item:hover {
                        transform: translateX(10px);
                        background-color: #f8f0ff;
                    }

                    .cinema-item.active {
                        background-color: #f8f0ff;
                        color: black;
                    }

                    .cinema-name {
                        flex-grow: 1;
                        font-size: 14px;
                    }

                    .movie-list {
                        display: grid;
                        gap: 20px;
                        padding: 20px;
                        background: rgba(255, 255, 255, 0.5);
                        border-radius: 15px;
                        backdrop-filter: blur(10px);
                        border: 3px dashed rgba(126, 96, 191, 0.2);
                    }

                    .movie-item {
                        display: flex;
                        gap: 20px;
                        padding: 20px;
                        background: white;
                        border-radius: 12px;
                        transition: all 0.3s ease;
                        border: 3px dashed #e0e0e0;
                    }

                    .movie-item:hover {
                        transform: translateY(-5px);
                    }

                    .movie-poster {
                        width: 120px;
                        height: 180px;
                        object-fit: cover;
                        border-radius: 8px;
                        transition: all 0.3s ease;
                    }

                    .movie-info {
                        flex: 1;
                    }

                    .movie-title {
                        color: #7E60BF;
                        font-size: 20px;
                        margin-bottom: 10px;
                    }

                    .genre-tag {
                        display: inline-block;
                        padding: 4px 12px;
                        background: #f0f0f0;
                        border-radius: 15px;
                        font-size: 12px;
                        margin: 0 5px 5px 0;
                        color: #666;
                    }

                    .showtime-group {
                        margin-top: 15px;
                    }

                    .showtime-type {
                        color: #7E60BF;
                        font-weight: 600;
                        margin-bottom: 8px;
                    }

                    .showtime-item {
                        display: inline-flex;
                        align-items: center;
                        padding: 8px 15px;
                        background: #f8f0ff;
                        border-radius: 20px;
                        margin: 0 10px 10px 0;
                        cursor: pointer;
                        transition: all 0.3s ease;
                        border: 3px dashed #7e60bf;
                    }

                    .showtime-item:hover {
                        background: #d4bee4;
                        color: black;
                        transform: translateY(-2px);
                    }

                    .error-message {
                        text-align: center;
                        padding: 20px;
                        background: #fff0f0;
                        border-radius: 10px;
                        color: #ff4d4f;
                        margin-top: 20px;
                    }

                    @media (max-width: 768px) {
                        .container {
                            padding: 15px;
                        }

                        .movie-item {
                            flex-direction: column;
                        }

                        .movie-poster {
                            width: 100%;
                            height: 200px;
                        }
                    }
                    .empty-state {
                        text-align: center;
                        padding: 40px;
                        background: white;
                        border-radius: 15px;
                        box-shadow: 0 5px 15px rgba(126, 96, 191, 0.1);
                        margin: 20px auto;
                        max-width: 400px;
                    }

                    .empty-state img {
                        animation: float 3s ease-in-out infinite;
                    }

                    @keyframes float {
                        0% {
                            transform: translateY(0px);
                        }
                        50% {
                            transform: translateY(-10px);
                        }
                        100% {
                            transform: translateY(0px);
                        }
                    }

                    .empty-state h2 {
                        color: #7E60BF;
                        font-size: 24px;
                        margin-bottom: 10px;
                    }

                    .empty-state p {
                        color: #666;
                        font-size: 16px;
                        margin-bottom: 30px;
                    }
                    #favourite,
                    #unFavourite {

                        background: #f8f0ff;
                        color: black;
                        margin-right: 20px;
                        border: 3px dashed #c0a3ff;
                    }

                    #favourite,
                    #unfavourite :hover {
                        transform: translateY(-5px);
                        background: #d4bee4;
                    }

                    .breadcrumb-nav {
                        background-color: transparent;
                        padding: 15px 0;
                        margin: 20px auto;
                        max-width: 1200px;
                        width: 95%;
                        overflow: hidden;
                    }

                    .breadcrumb {
                        margin-bottom: 0;
                        padding: 15px 30px;
                        background: rgba(255, 255, 255, 0.95);
                        border-radius: 20px;
                        backdrop-filter: blur(10px);
                        display: flex;
                        flex-wrap: wrap;
                        gap: 5px;
                    }

                    .breadcrumb-item {
                        color: #7E60BF;
                        opacity: 0;
                        animation: fadeIn 0.5s ease forwards;
                    }

                    .breadcrumb-item + .breadcrumb-item::before {
                        content: '\276F'  !important;
                        color: #b2b2b2  !important;
                    }

                    .breadcrumb-item a {
                        color: #7E60BF;
                        text-decoration: none;
                        transition: all 0.3s ease;
                        font-weight: 500;
                    }

                    .breadcrumb-item a:hover {
                        color: #d4bee4;
                        transform: translateY(-2px);
                    }

                    .breadcrumb-item.active {
                        color: #666;
                    }

                    @keyframes fadeIn {
                        from {
                            opacity: 0;
                            transform: translateX(-20px);
                        }
                        to {
                            opacity: 1;
                            transform: translateX(0);
                        }
                    }


                    .movie-details-container {
                        max-width: 1200px;
                        margin: 40px auto;
                        padding: 30px;
                        background: rgba(255, 255, 255, 0.95);
                        border-radius: 20px;
                        border: 4px dashed #7E60BF;
                    }


                    .movie-info-section {
                        display: flex;
                        gap: 40px;
                        margin-bottom: 40px;
                    }

                    .movie-poster {
                        width: 300px;
                        height: 450px;
                        border-radius: 15px;
                        overflow: hidden;
                        position: relative;
                    }

                    .movie-poster img {
                        width: 100%;
                        height: 100%;
                        object-fit: cover;
                    }

                    .movie-content {
                        flex: 1;
                    }

                    .movie-title {
                        color: #7E60BF;
                        font-size: 32px;
                        margin-bottom: 20px;
                    }


                    .movie-metadata {
                        display: flex;
                        flex-wrap: wrap;
                        gap: 20px;
                        margin: 20px 0;
                    }

                    .movie-metadata-item {
                        display: flex;
                        align-items: center;
                        gap: 8px;
                    }

                    .movie-metadata-label {
                        color: #666;
                        font-size: 14px;
                    }

                    .movie-metadata-value {
                        color: #7E60BF;
                        font-weight: 600;
                        font-size: 14px;
                        background-color: #f8f0ff;
                        padding: 4px 12px;
                        border-radius: 15px;
                        border: 2px dashed #7E60BF;
                    }
                    .movie-actions {
                        display: flex;
                        gap: 15px;
                        margin-top: 25px;
                    }

                    .action-button {
                        position: relative;
                        padding: 12px 24px;
                        background: #f8f0ff;
                        border: none;
                        cursor: pointer;
                        font-weight: 600;
                        font-size: 16px;
                        color: #7E60BF;
                        border-radius: 15px;
                        transition: all 0.3s ease;
                        overflow: hidden;
                        z-index: 1;
                    }


                    .action-button::before {
                        content: '';
                        position: absolute;
                        top: -2px;
                        left: -2px;
                        right: -2px;
                        bottom: -2px;
                        background: linear-gradient(45deg, #7E60BF, #d4bee4, #7E60BF);
                        z-index: -1;
                        border-radius: 17px;
                        background-size: 200%;
                        animation: borderAnimation 3s linear infinite;
                    }


                    .action-button::after {
                        content: '';
                        position: absolute;
                        inset: 2px;
                        background: #f8f0ff;
                        border-radius: 13px;
                        z-index: -1;
                    }


                    .action-button i {
                        margin-right: 8px;
                        transition: transform 0.3s ease;
                    }


                    .action-button:hover {
                        transform: translateY(-3px);

                    }

                    .action-button:hover i {
                        transform: scale(1.2);
                    }


                    .action-button:active {
                        transform: translateY(1px);
                    }


                    #favourite {
                        background: linear-gradient(45deg, #f8f0ff, #fff);
                    }

                    #favourite i {
                        color: #FFD700;
                    }


                    #unFavourite {
                        background: linear-gradient(45deg, #fff0f0, #fff);
                    }

                    #unFavourite i {
                        color: #ff4d4d;
                    }


                    @keyframes borderAnimation {
                        0% {
                            background-position: 0% 50%;
                        }
                        50% {
                            background-position: 100% 50%;
                        }
                        100% {
                            background-position: 0% 50%;
                        }
                    }


                    .action-button .ripple {
                        position: absolute;
                        border-radius: 50%;
                        background: rgba(255, 255, 255, 0.7);
                        transform: scale(0);
                        animation: ripple 0.6s linear;
                        pointer-events: none;
                    }

                    @keyframes ripple {
                        to {
                            transform: scale(4);
                            opacity: 0;
                        }
                    }
                     .start-time strong {
                color : #7e60bf;
            }

                </style>
            </head>
            <body>
                <jsp:include page="../landingPage/Header.jsp" />

                <!-- Breadcrumb -->
                <nav aria-label="breadcrumb" class="breadcrumb-nav" data-aos="fade-down" data-aos-duration="800">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item" data-aos="fade-right" data-aos-delay="100">
                            <a href="${pageContext.request.contextPath}/">Trang chủ</a>
                        </li>
                        <li class="breadcrumb-item" data-aos="fade-right" data-aos-delay="200">
                            <a href="${pageContext.request.contextPath}/movies">Phim</a>
                        </li>
                        <li class="breadcrumb-item active" data-aos="fade-right" data-aos-delay="300" aria-current="page">
                            <%= movie.getTitle()%>
                        </li>
                    </ol>
                </nav>

                <!-- Movie Details Container -->
                <div class="movie-details-container">
                    <div class="movie-info-section">
                        <div class="movie-poster" data-aos="fade-right">
                            <img src="<%= movie.getImageURL()%>" alt="<%= movie.getTitle()%>">
                            <button class="play-button" id="posterPlayButton">
                                <i class="fas fa-play"></i>
                            </button>
                        </div>

                        <div class="movie-content" data-aos="fade-left">
                            <h1 class="movie-title"><%= movie.getTitle()%></h1>

                            <!-- Rating -->
                            <div class="movie-rating" data-aos="fade-up" data-aos-delay="100">
                                <i class="fas fa-star"></i>
                                <span class="rating-value"><%= movie.getRating()%></span>
                            </div>

                            <!-- Synopsis -->
                            <div class="movie-synopsis" data-aos="fade-up" data-aos-delay="200">
                                <%= movie.getSynopsis()%>
                            </div>

                            <!-- Metadata -->
                            <div class="movie-metadata">
                                <div class="movie-metadata-item" data-aos="fade-up" data-aos-delay="300">
                                    <span class="movie-metadata-label">Ngày chiếu:</span>
                                    <span class="movie-metadata-value"><%= movie.getDatePublished()%></span>
                                </div>
                                <div class="movie-metadata-item" data-aos="fade-up" data-aos-delay="400">
                                    <span class="movie-metadata-label">Thể loại:</span>
                                    <span class="movie-metadata-value"><%= movie.getGenresAsString()%></span>
                                </div>
                                <div class="movie-metadata-item" data-aos="fade-up" data-aos-delay="500">
                                    <span class="movie-metadata-label">Quốc gia:</span>
                                    <span class="movie-metadata-value"><%= movie.getCountry()%></span>
                                </div>
                            </div>

                            <!-- Buttons -->
                            <div class="movie-actions" data-aos="fade-up" data-aos-delay="600">
                                <button id="trailerBtn" class="action-button">
                                    <i class="fas fa-play"></i> Xem Trailer
                                </button>

                                <c:if test="${not empty sessionScope.userID}">
                                    <c:set var="isFavoritedMovie" value="${requestScope.isFavoritedMovie}"></c:set>
                                        <div class="favorite-actions">
                                        <c:if test="${isFavoritedMovie == false}">
                                            <form action="HandleDisplayMovieInfo" method="post">
                                                <input type="hidden" name="isAddingToFavorite" value="true" />
                                                <input type="hidden" name="movieID" value="${movie.movieID}" />
                                                <button id="favourite" type="submit" class="action-button">
                                                    <i class="fas fa-star"></i> Yêu thích
                                                </button>
                                            </form>
                                        </c:if>

                                        <c:if test="${isFavoritedMovie == true}">
                                            <form action="myfavouritemovie" method="post">
                                                <input type="hidden" name="deletedFavouriteMovieInput" value="${movie.movieID}" />
                                                <input type="hidden" name="isDeletingInMovieInfo" value="true" />
                                                <button id="unFavourite" type="submit" class="action-button">
                                                    <i class="fas fa-trash-alt"></i> Bỏ yêu thích
                                                </button>
                                            </form>
                                        </c:if>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Booking Section -->
                <div class="container">
                    <div class="header" data-aos="fade-down">
                        <h1 class="title">Lịch chiếu ${movie.title}</h1>
                    </div>

                    <!-- Chọn chuỗi rạp -->
                    <div class="selector" data-aos="fade-up" data-aos-delay="100">
                        <!--                        <h3>Chọn chuỗi rạp:</h3>-->
                        <div class="button-group" id="cinemaChainButtons">
                            <c:forEach var="chain" items="${cinemaChains}">
                                <button class="selector-button ${chain.cinemaChainID == selectedCinemaChainID ? 'active' : ''}" 
                                        data-id="${chain.cinemaChainID}" 
                                        onclick="selectCinemaChain(${chain.cinemaChainID})">
                                    <img src="${chain.avatarURL}" alt="${chain.name}" class="cinema-chain-avatar" 
                                         onerror="this.style.display='none';" />
                                    <span>${chain.name}</span>
                                </button>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- Chọn rạp -->
                    <div class="selector" data-aos="fade-up" data-aos-delay="200">
                        <!--                        <h3>Chọn rạp:</h3>-->
                        <div class="cinema-list" id="cinemaButtons">
                            <c:forEach var="cinema" items="${chainCinemas[selectedCinemaChainID]}">
                                <button class="cinema-item ${cinema.cinemaID == selectedCinemaID ? 'active' : ''}" 
                                        data-id="${cinema.cinemaID}" 
                                        onclick="selectCinema(${cinema.cinemaID})">
                                    <span class="cinema-name">${cinema.name}</span>
                                </button>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- Chọn ngày -->
                    <div class="selector" data-aos="fade-up" data-aos-delay="300">
                        <!--                        <h3>Chọn ngày:</h3>-->
                        <div class="date-selector">
                            <c:forEach var="date" items="${availableDates}" varStatus="status">
                                <button class="date-button ${date == selectedDate ? 'active' : ''}" 
                                        onclick="selectDate('${date}')">
                                    <span class="date-number">${date.dayOfMonth}</span>
                                    <span class="date-day">
                                        <c:choose>
                                            <c:when test="${status.index == 0}">Hôm nay</c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${date.dayOfWeek.value == 1}">Thứ 2</c:when>
                                                    <c:when test="${date.dayOfWeek.value == 2}">Thứ 3</c:when>
                                                    <c:when test="${date.dayOfWeek.value == 3}">Thứ 4</c:when>
                                                    <c:when test="${date.dayOfWeek.value == 4}">Thứ 5</c:when>
                                                    <c:when test="${date.dayOfWeek.value == 5}">Thứ 6</c:when>
                                                    <c:when test="${date.dayOfWeek.value == 6}">Thứ 7</c:when>
                                                    <c:when test="${date.dayOfWeek.value == 7}">CN</c:when>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </button>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- Hiển thị suất chiếu -->
                    <div class="movie-list" data-aos="fade-up" data-aos-delay="400">
                        <c:if test="${not empty cinemaShowtimes[selectedCinemaID]}">
                            <div class="movie-item">
                                <img src="${movie.imageURL}" alt="${movie.title}" class="movie-poster" />
                                <div class="movie-info">
                                    <h3 class="movie-title">${movie.title}</h3>
                                    <div class="movie-genres">
                                        <c:forEach var="genre" items="${movie.genres}">
                                            <span class="genre-tag">${genre}</span>
                                        </c:forEach>
                                    </div>
                                    <div class="showtime-grid">
                                        <c:forEach var="slot" items="${cinemaShowtimes[selectedCinemaID]}">
                                            <div class="showtime-item" onclick="selectSlot(${slot.movieSlotID})">
                                                <fmt:formatDate value="${slot.startTime}" pattern="HH:mm" var="formattedStartTime" />
                                                <fmt:formatDate value="${slot.endTime}" pattern="HH:mm" var="formattedEndTime" />
                                                <span class="start-time"><strong>${formattedStartTime}</strong></span>
                                                <span class="time-separator">~</span>
                                                <span class="end-time">${formattedEndTime}</span>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>

                    <c:if test="${empty cinemaShowtimes[selectedCinemaID] && selectedCinemaID != null}">
                        <div class="empty-state" data-aos="fade-up">
                            <img src="images/not-found.svg" alt="No showtime found" 
                                 style="width: 150px; height: 150px; opacity: 0.5; margin-bottom: 20px;">
                            <h2>Úi, Suất chiếu không tìm thấy</h2>
                            <p>Bạn hãy thử tìm ngày khác nhé</p>
                        </div>
                    </c:if>
                </div>

                <div id="trailerModal" class="modal">
                    <div class="modal-content">
                        <span class="close">&times;</span>
                        <iframe
                            id="trailerVideo"
                            width="560"
                            height="315"
                            src="<%= movie.getLinkTrailer()%>"
                            title="YouTube video player"
                            frameborder="0"
                            allowfullscreen
                            ></iframe>
                    </div>
                </div>

                <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
                <script>
                                                AOS.init({
                                                    duration: 1000,
                                                    once: true,
                                                });

                                                const trailerBtn = document.getElementById("trailerBtn");
                                                const posterPlayButton = document.getElementById("posterPlayButton");
                                                const trailerModal = document.getElementById("trailerModal");
                                                const closeBtn = document.querySelector(".close");
                                                const trailerVideo = document.getElementById("trailerVideo");

                                                function showTrailer() {
                                                    trailerModal.style.display = "flex";
                                                    trailerVideo.src += "?autoplay=1";
                                                }

                                                function hideTrailer() {
                                                    trailerModal.style.display = "none";
                                                    trailerVideo.src = trailerVideo.src.split("?")[0];
                                                }

                                                trailerBtn.onclick = showTrailer;
                                                posterPlayButton.onclick = showTrailer;

                                                closeBtn.onclick = hideTrailer;

                                                window.onclick = function (event) {
                                                    if (event.target == trailerModal) {
                                                        hideTrailer();
                                                    }
                                                };

                                                function getCurrentDateTime() {
                                                    const now = new Date();
                                                    const year = now.getFullYear();
                                                    const month = String(now.getMonth() + 1).padStart(2, "0");
                                                    const day = String(now.getDate()).padStart(2, "0");
                                                    const hours = String(now.getHours()).padStart(2, "0");
                                                    const minutes = String(now.getMinutes()).padStart(2, "0");
                                                    const seconds = String(now.getSeconds()).padStart(2, "0");
                                                    const milliseconds = String(now.getMilliseconds()).padStart(3, "0");
                                                    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}:${milliseconds}`;
                                                        }

                                                        function addToFavorite() {
                                                            let favoritedAtInput = document.getElementById("favoritedAtInput");
                                                            favoritedAtInput.value = getCurrentDateTime();
                                                            callServlet(
                                                                    "addToFavoriteForm",
                                                                    "HandleDisplayMovieInfo?movieID=" + movieID,
                                                                    "POST"
                                                                    );
                                                        }

                                                        function deleteFavoriteMovie() {
                                                            callServlet("deleteFavoriteMovieForm", "myfavouritemovie", "POST");
                                                        }

                                                        function viewFavouriteMovies() {
                                                            callServlet("viewFavouriteMoviesForm", "myfavouritemovie", "GET");
                                                        }
                                                        function selectCinemaChain(cinemaChainID) {
                                                            window.location.href = 'HandleDisplayMovieInfo?movieID=${movie.movieID}&cinemaChainID=' + cinemaChainID;
                                                        }

                                                        function selectCinema(cinemaID) {
                                                            const cinemaChainID = document.querySelector('#cinemaChainButtons .active').dataset.id;
                                                            window.location.href = 'HandleDisplayMovieInfo?movieID=${movie.movieID}&cinemaChainID=' + cinemaChainID + '&cinemaID=' + cinemaID;
                                                        }

                                                        function selectDate(date) {
                                                            const cinemaChainID = document.querySelector('#cinemaChainButtons .active').dataset.id;
                                                            const cinemaID = document.querySelector('#cinemaButtons .active').dataset.id;
                                                            window.location.href = 'HandleDisplayMovieInfo?movieID=${movie.movieID}&cinemaChainID=' + cinemaChainID + '&cinemaID=' + cinemaID + '&date=' + date;
                                                        }

                                                        function selectSlot(movieSlotID) {
                                                            var form = document.createElement('form');
                                                            form.method = "GET";
                                                            form.action = "selectSeat";

                                                            var actionInput = document.createElement('input');
                                                            actionInput.type = 'hidden';
                                                            actionInput.name = 'action';
                                                            actionInput.value = 'selectSlot';
                                                            form.appendChild(actionInput);

                                                            var slotInput = document.createElement('input');
                                                            slotInput.type = 'hidden';
                                                            slotInput.name = 'movieSlotID';
                                                            slotInput.value = movieSlotID;
                                                            form.appendChild(slotInput);

                                                            document.body.appendChild(form);
                                                            form.submit();
                                                        }
                </script>
            </body>
        </html>
