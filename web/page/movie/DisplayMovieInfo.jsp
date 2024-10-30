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
                        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4);
                    }

                    .poster {
                        width: 40%;
                        min-width: 300px;
                        height: 450px;
                        border-radius: 20px;
                        background-size: cover;
                        background-position: center;
                        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
                        position: relative;
                        overflow: hidden;
                        transition: transform 0.5s ease, box-shadow 0.5s ease;
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
                        text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
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
                        background: linear-gradient(45deg, #ffd700, #ffa500);
                        padding: 8px 15px;
                        border-radius: 30px;
                        color: #000000;
                        font-weight: bold;
                        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                    }

                    .rating i {
                        margin-right: 10px;
                        color: #000000;
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
                        color: #ffd700;
                        font-weight: 600;
                    }

                    .buttons {
                        margin-top: 40px;
                        display:flex;
                    }

                    button {
                        padding: 15px 30px;
                        border: none;
                        cursor: pointer;
                        font-weight: bold;
                        border-radius: 50px;
                        font-size: 18px;
                        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
                        transition: all 0.3s ease;
                    }
                    #favourite
                    #unFavourite:hover {
                        transform: translateY(-5px);
                        box-shadow: 0 10px 20px rgba(255, 75, 43, 0.4);
                    }
                    

                    #trailerBtn {
                        background: linear-gradient(45deg, #ff4b2b, #ff416c);
                        color: white;
                        margin-right: 20px;
                    }

                    #trailerBtn:hover {
                        transform: translateY(-5px);
                        box-shadow: 0 10px 20px rgba(255, 75, 43, 0.4);
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
                </style>
            </head>
            <body>
                <div class="container">
                    <div
                        class="poster"
                        data-aos="fade-right"
                        style="background-image: url('<%= movie.getImageURL()%>')"
                        >
                        <button class="play-button" id="posterPlayButton">
                            <i class="fas fa-play"></i>
                        </button>
                    </div>
                    <div class="movie-details" data-aos="fade-left">
                        <h1 class="movie-title"><%= movie.getTitle()%></h1>
                        <div class="ratings">
                            <div class="rating">
                                <i class="fas fa-star"></i> <%= movie.getRating()%>
                            </div>
                        </div>
                        <p><%= movie.getSynopsis()%></p>
                        <div class="metadata">
                            <div>Published: <strong><%= movie.getDatePublished()%></strong></div>
                            <div>Genre: <strong><%= movie.getGenresAsString()%></strong></div>
                            <div>Country: <strong><%= movie.getCountry()%></strong></div>
                        </div>
                        <div class="buttons">
                            <button id="trailerBtn">View Trailer</button>
                        

                        <!-- Favorite Actions -->
                        <c:if test="${not empty sessionScope.userID}">
                            <c:if test="${sessionScope.role ne 'OWNER'}">
                                <c:set var="isFavoritedMovie" value="${requestScope.isFavoritedMovie}"></c:set>

                                <c:if test="${isFavoritedMovie == false}">
                                    <form action="HandleDisplayMovieInfo"  method="post">
                                        <input type="hidden" name="isAddingToFavorite" value="true"/>
                                        <input type="hidden" name="movieID" value="${movie.movieID}"/> <!-- Include the movieID -->
                                        <button id="favourite" type="submit" style="display: inline-block; text-decoration: none; cursor: pointer; margin-left: -25px; background: none; border: none; padding: 0;">
                                            <span class="clWhite">
                                                <img src="assets/images/add-to-favorites.png"></img>

                                            </span>
                                        </button>
                                    </form>
                                </c:if>

                                <c:if test="${isFavoritedMovie == true}">
                                    <form action="myfavouritemovie" method="post">
                                        <input type="hidden" name="deletedFavouriteMovieInput" value="${movie.movieID}"/>
                                        <input type="hidden" name="isDeletingInMovieInfo" value="true"/>
                                        <button id="unFavourite" type="submit" style="display: inline-block; text-decoration: none; cursor: pointer; margin-left: -25px; background: none; border: none; padding: 0;">
                                            <span class="clWhite">
                                                <img src="assets/images/delete-favorite.png"></img>

                                            </span>
                                        </button>
                                    </form>                                    
                                </c:if>


                            </c:if>


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


                        </script>
                        </body>
                        </html>
