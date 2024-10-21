<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Movie" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


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
            /* Global Styles */
            body, html {
                margin: 0;
                padding: 0;
                font-family: 'SairaSemiCondensed-Bold', sans-serif;
                background-color: whitesmoke;
                color: white;
            }

            .container {
                display: flex;
                background-color: black;
                border-radius: 12px;
                padding: 74px;
                max-width: 1200px;
                margin: 40px auto;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.4);
            }

            /* Poster Styles */
            .poster {
                width: 30%;
                min-width: 250px;
                height: 450px;
                border-radius: 10px;
                background-size: cover;
                background-position: center;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
                position: relative;
            }

            .play-button {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: rgba(255, 255, 255, 0.9);
                border-radius: 50%;
                padding: 15px;
                transition: background-color 0.3s ease;
            }

            .play-button:hover {
                background-color: rgba(255, 255, 255, 1);
            }

            .play-button i {
                font-size: 32px;
                color: #ff5733;
            }

            /* Movie Details */
            .movie-details {
                width: 65%;
                padding-left: 20px;
            }

            .movie-title {
                font-size: 40px;
                font-weight: bold;
                color: #ff5733;
                margin-bottom: 10px;
            }

            .ratings {
                display: flex;
                align-items: center;
                margin: 10px 0;
            }

            .rating {
                font-size: 20px;
                margin-right: 15px;
                display: flex;
                align-items: center;
                background-color: #ffca28;
                padding: 5px 10px;
                border-radius: 5px;
                color: #1c1c1c;
            }

            .metadata {
                display: flex;
                margin-top: 20px;
            }

            .metadata div {
                flex-basis: 30%;
                padding-right: 10px;
            }

            .metadata strong {
                font-size: 16px;
                color: #ccc;
            }

            /* Buttons */
            .buttons {
                margin-top: 30px;
            }

            button {
                padding: 12px 25px;
                border: none;
                cursor: pointer;
                font-weight: bold;
                border-radius: 50px;
                font-size: 15px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
                transition: background-color 0.4s ease, transform 0.2s ease;
            }

            #reviewBtn, #trailerBtn {
                background: linear-gradient(45deg, #ff5733, #feb47b);
                color: white;
                margin-right: 20px;
                transition: all 0.3s ease;
            }

            #reviewBtn:hover, #trailerBtn:hover {
                background: linear-gradient(45deg, #feb47b, #ff7e5f);
                transform: translateY(-3px);
            }

            /* Favorite Button */
            #viewFavouriteMoviesForm{
                margin-top: 44px;
            }

            /* Modal Styles */
            .modal {
                display: none;
                position: fixed;
                z-index: 999;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.85);
                justify-content: center;
                align-items: center;
                transition: opacity 0.5s ease;
            }

            .modal-content {
                background-color: #1e1e1e;
                color: #f0f0f0;
                padding: 30px;
                border-radius: 12px;
                position: relative;
                text-align: center;
                box-shadow: 0 8px 30px rgba(0, 0, 0, 0.5);
            }

            .close {
                position: absolute;
                top: 15px;
                right: 20px;
                color: #ff5733;
                font-size: 40px;
                font-weight: bold;
                cursor: pointer;
                transition: transform 0.3s ease, color 0.3s ease;
            }

            .close:hover {
                color: darkred;
                transform: scale(1.2);
            }
            /* Favorite Button Styles */


            @media (max-width: 768px) {
                .container {
                    flex-direction: column;
                    align-items: center;
                }

                .movie-details {
                    width: 100%;
                    padding-left: 0;
                    text-align: center;
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
            <!-- Movie Poster -->
            <div class="poster" style="background-image: url('<%= movie.getImageURL()%>');">
                <button class="play-button">
                    <i class="fa fa-play"></i>
                </button>
            </div>

            <!-- Movie Details -->
            <div class="movie-details" style="margin-left:43px">
                <h1 class="movie-title"><%= movie.getTitle()%></h1>
                <div class="ratings">
                    <div class="rating">
                        <i class="fa fa-star"></i> <%= movie.getRating()%>
                    </div>
                </div>
                <p><%= movie.getSynopsis()%></p>

                <div class="metadata">
                    <div>Published: <strong><%= movie.getDatePublished()%></strong></div>
                    <div>Genre: <strong><%= movie.getGenresAsString()%></strong></div>
                    <div>Country: <strong><%= movie.getCountry()%></strong></div>
                </div>

                <!-- Action Buttons -->

                <div class="buttons">
                    <button id="trailerBtn">View Trailer</button>


                </div>

                <!-- Modal for movie trailer -->                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
                <div id="trailerModal" class="modal">
                    <div class="modal-content">
                        <span class="close">&times;</span>
                        <iframe id="trailerVideo" width="560" height="315" src="<%= movie.getLinkTrailer()%>" title="YouTube video player" frameborder="0" allowfullscreen></iframe>
                    </div>
                </div>


                <!-- Favorite Actions -->

                <c:if test="${ not empty sessionScope.userID}">
                    <c:set var="isFavoritedMovie" value="${requestScope.isFavoritedMovie}"></c:set>
                    <c:if test="${isFavoritedMovie == false}">
                        <form id="addToFavoriteForm">
                            <input type="hidden" name="isAddingToFavorite" value="true"/>
                            <input type="hidden" id="favoritedAtInput" name="favoritedAt"/>
                            <a onclick="addToFavorite();" style="display: inline-block;text-decoration: none;cursor: pointer;margin-left: -6px;margin-top: 18px;">
                                <span class="clWhite">
                                    <img src="assets/images/add-to-favorites.png"></img>
                                    Thêm vào yêu thích
                                </span>
                            </a>
                        </form>
                    </c:if>

                    <c:if test="${isFavoritedMovie == true}">
                        <form id="deleteFavoriteMovieForm">
                            <input type="hidden" name="deletedFavouriteMovieInput" value="${movie.movieID}"/>
                            <input type="hidden" name="isDeletingInMovieInfo" value="true"/>
                            <a onclick="deleteFavoriteMovie();" style="display: inline-block;text-decoration: none;cursor: pointer;margin-left: -6px;margin-top: 18px;">
                                <span class="clWhite">
                                    <img src="assets/images/delete-favorite.png"></img>
                                    Hủy yêu thích
                                </span>
                            </a>
                        </form>                                    
                    </c:if>
                    <form id="viewFavouriteMoviesForm">
                        <a onclick="viewFavouriteMovies();" style="display: inline-block; text-decoration: none; cursor: pointer; margin-left: -25px;">
                            <span class="clWhite">
                                <img src="assets/images/view-favourite-movies.png"></img>
                                Xem phim đã yêu thích
                            </span>
                        </a>
                    </form>


                </c:if>



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

            function getCurrentDateTime() {
                const now = new Date();

                const year = now.getFullYear();
                const month = String(now.getMonth() + 1).padStart(2, '0');
                const day = String(now.getDate()).padStart(2, '0');

                const hours = String(now.getHours()).padStart(2, '0');
                const minutes = String(now.getMinutes()).padStart(2, '0');
                const seconds = String(now.getSeconds()).padStart(2, '0');
                const milliseconds = String(now.getMilliseconds()).padStart(3, '0');

                return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds + ':' + milliseconds;
            }
            //ham add favorite movie
            function addToFavorite() {
                let favoritedAtInput = document.getElementById('favoritedAtInput');
                favoritedAtInput.value = getCurrentDateTime();

                callServlet('addToFavoriteForm', 'HandleDisplayMovieInfo?movieID=' + movieID, 'POST');
            }

            function deleteFavoriteMovie() {
                callServlet('deleteFavoriteMovieForm', 'myfavouritemovie', 'POST');
            }

            function viewFavouriteMovies() {
                callServlet('viewFavouriteMoviesForm', 'myfavouritemovie', 'GET');
            }


        </script>
    </body>
</html> 