<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Movie" %>

<%
    Movie movie = (Movie) request.getAttribute("movie");
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= movie.getTitle()%></title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>
            body, html {
                margin: 0;
                padding: 0;
                font-family: Arial, sans-serif;
                background-color: white;
                color: white;
            }
            .container {
                display: flex;
                background-color: black;
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
                flex-wrap: wrap;
                justify-content: space-between;
                align-items: flex-start;
                width: 100%;
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
            }
            .poster {
                position: relative;
                width: 30%;
                min-width: 250px;
                height: 450px;
                background-size: cover;
                background-position: center;
            }
            .poster .play-button {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: rgba(255, 255, 255, 0.7);
                border-radius: 50%;
                padding: 20px;
            }
            .play-button img {
                width: 64px;
                height: 64px;
            }
            .movie-details {
                width: 65%;
                padding-left: 20px;
            }
            .movie-title {
                font-size: 36px;
                font-weight: bold;
            }
            .ratings {
                display: flex;
                align-items: center;
                margin: 10px 0;
            }
            .ratings .rating {
                font-size: 20px;
                margin-right: 15px;
                display: flex;
                align-items: center;
            }
            .ratings .rating i {
                color: yellow;
                margin-right: 5px;
            }
            .ratings .imdb-rating {
                background-color: #e5c708;
                padding: 5px 10px;
                border-radius: 3px;
                font-size: 18px;
            }
            .description {
                margin: 20px 0;
                line-height: 1.5;
            }
            .metadata {
                display: flex;
                margin-top: 20px;
            }
            .metadata .publicdate,
            .metadata .genre,
            .metadata .country {
                flex-basis: 15%;
            }
            .metadata .publicdate span,
            .metadata .genre span,
            .metadata .country span {
                display: block;
                font-size: 15px;
                margin-top: 5px;
                color: lightgray;
            }
            .metadata .country {
                margin-left: 33px;
            }
            .metadata strong {
                font-size: 15px;
                color: white;
                display: block;
            }
            .buttons {
                margin-top: 20px;
            }
            .buttons button {
                padding: 10px 20px;
                border: none;
                cursor: pointer;
                font-weight: bold;
                margin-right: 10px;
            }
            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.8);
                justify-content: center;
                align-items: center;
            }
            .modal-content {
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                position: relative;
                text-align: center;
            }
            #reviewBtn,
            #trailerBtn {
                background-color: #ff5733;
                margin-top: 57px;
                color: #fff;
                padding: 14px 13px;
                border: none;
                border-radius: 57px;
                font-size: 13px;
                font-weight: bold;
                cursor: pointer;
                transition: all 0.3s ease;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }
            #btnYeuThich {
                background-color: transparent;
                margin-left:412px;
                border: none;
                color: red;
                font-size: 24px;
                cursor: pointer;
                transition: transform 0.3s ease, color 0.3s ease;
            }

            #btnYeuThich:hover {
                color: darkred;
            }
            #reviewBtn {
                margin-left: 15px;
            }
            #reviewBtn:hover,
            #trailerBtn:hover {
                background-color: #ff6f47;
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
                transform: translateY(-3px);
            }
            #reviewBtn:active,
            #trailerBtn:active {
                transform: translateY(1px);
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
            }
            .close {
                position: absolute;
                top: 10px;
                right: 20px;
                color: #000;
                font-size: 60px;
                font-weight: bold;
                cursor: pointer;
                transition: color 0.5s ease;
            }
            .close:hover {
                color: red;
            }
            @media (max-width: 768px) {
                .container {
                    flex-direction: column;
                    align-items: center;
                }
                .movie-details {
                    width: 100%;
                    padding-left: 0;
                }
                .poster {
                    width: 100%;
                    height: 350px;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="poster" style="background-image: url('<%= movie.getImageURL()%>');">
                <button type="button" class="play-button">
                    <i class="fa fa-play"></i>
                </button>
            </div>

            <div class="movie-details">
                <h1 class="movie-title"><%= movie.getTitle()%></h1>
                <div class="ratings">
                    <div class="rating">
                        <i class="fa fa-star"></i> <%= movie.getRating()%>
                    </div>
                </div>
                    <p class="mb-4 tracking-wide">
                    <strong>Nội dung:</strong> <%= movie.getSynopsis()%>
                </p>

                <div class="metadata">
                    <div class="publicdate">
                        <span>Ngày chiếu:</span>
                        <strong><%= movie.getDatePublished()%></strong>
                    </div>
                    <div class="genre">
                        <span>Thể loại:</span>
                        <strong><%= movie.getGenresAsString()%></strong>
                    </div>
                    <div class="country">
                        <span>Quốc Gia:</span>
                        <strong><%= movie.getCountry()%></strong>
                    </div>
                </div>

                <button id="trailerBtn">Xem Trailer</button>
                <button id="reviewBtn">Xem Review</button>
                <button id="btnYeuThich" onclick="addToFavorites(1)">
                    <i class="fa fa-heart"></i>
                </button>


                <div id="trailerModal" class="modal">
                    <div class="modal-content">
                        <span class="close">&times;</span>
                        <iframe id="trailerVideo" width="560" height="315" src="<%= movie.getLinkTrailer()%>" title="YouTube video player" frameborder="0" allowfullscreen></iframe>
                    </div>
                </div>
            </div>
        </div>

        <script>
            const trailerBtn = document.getElementById("trailerBtn");
            const trailerModal = document.getElementById("trailerModal");
            const closeBtn = document.querySelector(".close");
            const trailerVideo = document.getElementById("trailerVideo");

            trailerBtn.onclick = function () {
                trailerModal.style.display = "flex";
                trailerVideo.src += "?autoplay=1";
            };

            closeBtn.onclick = function () {
                trailerModal.style.display = "none";
                trailerVideo.src = trailerVideo.src.split("?")[0];
            };

            window.onclick = function (event) {
                if (event.target == trailerModal) {
                    trailerModal.style.display = "none";
                    trailerVideo.src = trailerVideo.src.split("?")[0];
                }
            };
        </script>
    </body>
</html>
