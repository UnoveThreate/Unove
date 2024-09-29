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
        <title>${movie.title}</title>
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
                flex-wrap: wrap; /* Adjust for smaller screens */
                justify-content: space-between;
                align-items: flex-start;
                width: 100%;
                max-width: 1200px; /* Added a max-width for better layout control */
                margin: 0 auto; /* Center container */
                padding: 20px;
            }
            .poster {
                position: relative;
                width: 30%;
                min-width: 250px; /* Make sure the poster doesn't get too small */
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
            .metadata .genre
            .metadata .country{
                flex-basis: 15%; /* Adjusts the width of each section to share space */
            }

            .metadata .publicdate span,
            .metadata .genre span,
            .metadata .country span
            {
                display: block; /* Moves the value (date/genre) to a new line */
                font-size: 15px; /* Font size for the date/genre */
                margin-top: 5px; /* Adds a little space between the label and the value */
                color: lightgray; /* Text color for the date/genre */
            }
            .metadata .country{
                margin-left:33px;
            }

            .metadata strong {
                font-size: 15px; /* Font size for the labels */
                color: white; /* Label text color */
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
                background-color: rgba(0,0,0,0.8); /* Màu nền tối */
                justify-content: center;
                align-items: center;
            }

            /* Nội dung của modal */
            .modal-content {
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                position: relative;
                text-align: center;

            }
            /* Nút "Xem Trailer" */
            #reviewBtn,
            #trailerBtn {
                background-color: #ff5733;
                margin-top:57px;
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
            #reviewBtn {
                margin-left:15px;
            }


            /* Hiệu ứng hover cho nút */
            #reviewBtn,
            #trailerBtn:hover {
                background-color: #ff6f47; /* Màu nền thay đổi khi hover */
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Tăng cường hiệu ứng đổ bóng */
                transform: translateY(-3px); /* Đẩy nút lên khi hover */
            }

            /* Hiệu ứng khi nút bị nhấn */
            #reviewBtn,
            #trailerBtn:active {
                transform: translateY(1px); /* Đẩy nút xuống nhẹ khi nhấn */
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Giảm bóng khi nhấn */
            }


            /* Nút đóng modal */
            .close {
                position: absolute;
                top: 10px;
                right: 20px;
                color: #000;
                font-size: 60px; /* Tăng kích thước của dấu X */
                font-weight: bold;
                cursor: pointer;
                transition: color 0.5s ease; /* Hiệu ứng khi hover */
            }

            /* Hiệu ứng hover cho dấu X */
            .close:hover {
                color: red; /* Đổi màu khi di chuột qua */
            }

            /* Responsive adjustments */
            @media (max-width: 768px) {
                .container {
                    flex-direction: column; /* Stack the poster and details on top of each other */
                    align-items: center;
                }
                .movie-details {
                    width: 100%;
                    padding-left: 0;
                }
                .poster {
                    width: 100%; /* Full width for smaller screens */
                    height: 350px; /* Adjust height for responsiveness */
                }
            }
        </style>
    </head>
    <body>

        <div class="container">
            <!-- Movie Poster -->
          <div class="poster" style="background-image: url('<%= movie.getImageURL() %>');">
                
                <button type="button" class="btn btn-danger play-button" data-toggle="modal" data-target="#trailerModal" style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); padding: 28px 32px;">
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
                <p class="description">
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
                        <span> Quốc Gia:</span>
                        <strong><%= movie.getCountry()%></strong>
                       
                    </div>
                </div>

                <!-- code chuc nang xem trailer-->
                <button id="trailerBtn"> Xem Trailer</button>
                <button id="reviewBtn"> Xem Review</button>
                <button id="favoriteBtn"></button>

                <!-- Modal để chứa video -->
                <div id="trailerModal" class="modal">
                    <div class="modal-content">
                        <span class="close">&times;</span>
                        <iframe width="560" height="315" src="https://www.youtube.com/embed/t9qQRG2ZMhQ?si=GnQTvvbeGQ4IT5lt" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
                    </div>
                </div>

                <script src="script.js"></script>



            </div>
        </div>

    </body>
    <script>
        // Lấy các phần tử DOM
        const trailerBtn = document.getElementById("trailerBtn");
        const trailerModal = document.getElementById("trailerModal");
        const closeBtn = document.querySelector(".close");
        const trailerVideo = document.getElementById("trailerVideo");

// Khi nhấn nút "Xem Trailer", mở modal
        trailerBtn.onclick = function () {
            trailerModal.style.display = "flex";
            trailerVideo.src += "?autoplay=1";  // Bật tự động phát video
        }

// Khi nhấn vào dấu X, đóng modal và dừng video
        closeBtn.onclick = function () {
            trailerModal.style.display = "none";
            trailerVideo.src = trailerVideo.src.split("?")[0]; // Dừng video
        }

// Khi nhấn ra ngoài modal, đóng modal và dừng video
        window.onclick = function (event) {
            if (event.target == trailerModal) {
                trailerModal.style.display = "none";
                trailerVideo.src = trailerVideo.src.split("?")[0]; // Dừng video
            }
        }


    </script>
</html>
