<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Lịch Chiếu Phim</title>
        <link rel="stylesheet" href="path/to/your/styles.css"> <!-- Thay đổi đường dẫn cho phù hợp -->
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-color: #f0f4f8;
                margin: 0;
                padding: 20px;
            }

            .container {
                max-width: 1200px;
                margin: auto;
                background: white;
                border-radius: 10px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
                padding: 30px;
            }

            h1 {
                text-align: center;
                color: #e91e63;
                margin-bottom: 20px;
                font-size: 2.5em;
                text-transform: uppercase;
                letter-spacing: 1px;
            }

            h2 {
                color: #333;
                border-bottom: 2px solid #e91e63;
                padding-bottom: 10px;
                margin-bottom: 20px;
                font-size: 1.8em;
            }

            .cinema-chain, .cinema, .date {
                margin-bottom: 30px;
            }

            .cinema-chain div, .cinema div, .date div {
                display: inline-block;
                padding: 12px 20px;
                margin: 5px;
                background: #e0e0e0;
                border-radius: 5px;
                cursor: pointer;
                transition: background 0.3s, transform 0.2s;
                font-weight: bold;
                color: #333;
            }

            .cinema-chain div:hover, .cinema div:hover, .date div:hover {
                background: #d1c4e9;
                transform: translateY(-2px);
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            }

            .movies {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
            }

            .movie {
                background: #fff;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                flex: 1 1 calc(33.333% - 20px);
                min-width: 250px;
                transition: transform 0.2s, box-shadow 0.2s;
            }

            .movie:hover {
                transform: scale(1.05);
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
            }

            .movie h3 {
                color: #e91e63;
                margin: 0 0 10px;
                font-size: 1.5em;
            }

            .movie p {
                margin: 5px 0;
                color: #555;
            }

            .showtime {
                margin-top: 10px;
                color: #333;
                font-weight: bold;
            }

            img {
                width: 100%;
                border-radius: 5px;
                margin-top: 10px;
                transition: transform 0.3s;
            }

            img:hover {
                transform: scale(1.05);
            }

            .footer {
                text-align: center;
                margin-top: 30px;
                font-size: 0.9em;
                color: #777;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Lịch Chiếu Phim</h1>

            <div class="cinema-chain">
                <h2>Chuỗi Rạp</h2>
                <div id="cinemaChainSelect">
                    <c:forEach var="chain" items="${cinemaChains}">
                        <form action="/Unove/showtimes" method="GET">
                            <input type="hidden" name="cinemaChainID" value="${chain.cinemaChainID}"/>
                            <input type="submit" value="${chain.name}"/>
<!--                            <div onclick="handle" style="${sessionScope.selectedCinemaChainID == chain.cinemaChainID ? 'font-weight: bold;' : ''}">
                              
                            </div>                          -->
                        </form>
                    </c:forEach>
                </div>
            </div>

            <div class="cinema">
                <h2>Rạp</h2>
                <div id="cinemaSelect">
                    <c:forEach var="cinema" items="${cinemas}">
                        <div onclick="updateMovies(${cinema.cinemaID})" 
                             style="${sessionScope.selectedCinemaID == cinema.cinemaID ? 'font-weight: bold;' : ''}">
                            ${cinema.name}
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="date">
                <h2>Ngày</h2>
                <div id="dateSelect">
                    <c:forEach var="date" items="${availableDates}">
                        <div onclick="updateShowtimes('${date}')" 
                             style="${sessionScope.selectedDate == date ? 'font-weight: bold;' : ''}">
                            ${date}
                        </div>
                    </c:forEach>
                </div>
            </div>

            <h2>Phim</h2>
            <div class="movies" id="movies">
                <c:forEach var="movie" items="${movies}">
                    <div class="movie">
                        <h3>${movie.title}</h3>
                        <p><strong>Mô tả:</strong> ${movie.synopsis}</p>
                        <p><strong>Ngày phát hành:</strong> ${movie.datePublished}</p>
                        <p><strong>Quốc gia:</strong> ${movie.country}</p>
                        <p><strong>Đánh giá:</strong> ${movie.rating}</p>
                        <img src="${movie.imageURL}" alt="${movie.title}">
                        <div class="showtimes">
                            <c:if test="${not empty movieSlotsByMovie[movie]}">
                                <c:forEach var="entry" items="${movieSlotsByMovie[movie]}">
                                    <div class="showtime">
                                        <span>${entry.startTime} - ${entry.endTime}</span>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty movieSlotsByMovie[movie]}">
                                <p>Không có suất chiếu cho phim này.</p>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <script>
            function updateCinemas(chainID) {
                fetch(`/showtimes?cinemaChainID=${chainID}`)
                        .then(response => response.text())
                        .then(data => {
                            document.getElementById("cinemaSelect").innerHTML = data;
                            updateMovies(document.querySelector("#cinemaSelect div").getAttribute("onclick").split('(')[1].split(')')[0]);
                        })
                        .catch(error => console.error('Error:', error));
            }

            function updateMovies(cinemaID) {
                fetch(`/showtimes?cinemaID=${cinemaID}`)
                        .then(response => response.text())
                        .then(data => {
                            document.getElementById("movies").innerHTML = data;
                            updateShowtimes(document.querySelector("#dateSelect div").innerText);
                        })
                        .catch(error => console.error('Error:', error));
            }

            function updateShowtimes(selectedDate) {
                const cinemaID = document.querySelector("#cinemaSelect div").getAttribute("onclick").split('(')[1].split(')')[0];
                fetch(`/showtimes?cinemaID=${cinemaID}&date=${selectedDate}`)
                        .then(response => response.text())
                        .then(data => {
                            document.getElementById("showtimes").innerHTML = data;
                        })
                        .catch(error => console.error('Error:', error));
            }
        </script>
    </body>
</html>