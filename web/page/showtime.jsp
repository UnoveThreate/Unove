<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Lịch chiếu phim</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    </head>
    <body>
        <div class="container">
            <h1 class="my-4 text-center text-primary">Lịch chiếu phim</h1>

            <div class="row mb-4">
                <div class="col-md-4">
                    <select id="cinemaChainSelect" class="form-control custom-select" onchange="changeCinemaChain()">
                        <c:forEach var="cinemaChain" items="${cinemaChains}">
                            <option value="${cinemaChain.cinemaChainID}" ${cinemaChain.cinemaChainID eq selectedCinemaChainID ? 'selected' : ''}>${cinemaChain.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4">
                    <select id="cinemaSelect" class="form-control custom-select" onchange="changeCinema()">
                        <c:forEach var="cinema" items="${cinemas}">
                            <option value="${cinema.cinemaID}" ${cinema.cinemaID eq selectedCinemaID ? 'selected' : ''}>${cinema.address}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4">
                    <div class="btn-group date-buttons" role="group">
                        <c:forEach var="date" items="${availableDates}">
                            <button type="button" class="btn btn-outline-primary ${date eq selectedDate ? 'active' : ''}" 
                                    onclick="changeDate('${date}')">
                                <fmt:formatDate value="${date}" pattern="dd/MM" />
                            </button>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <c:forEach var="entry" items="${movieSlotsByMovie}">
                <div class="card mb-4 movie-card">
                    <div class="row no-gutters">
                        <div class="col-md-2">
                            <img src="${entry.key.imageURL}" class="card-img movie-poster" alt="${entry.key.title}">
                        </div>
                        <div class="col-md-10">
                            <div class="card-body">
                                <h5 class="card-title text-primary">${entry.key.title}</h5>
                                <p class="card-text text-muted">${entry.key.synopsis}</p>
                                <div class="showtime-buttons">
                                    <c:forEach var="movieSlot" items="${entry.value}">
                                        <button class="btn btn-outline-primary showtime-button" onclick="bookTicket(${movieSlot.movieSlotID})">
                                            <fmt:formatDate value="${movieSlot.startTime}" pattern="HH:mm" />
                                        </button>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <style>
            body {
                background-color: #f0f2f5;
                font-family: 'Montserrat', sans-serif;
                color: #333;
                transition: background-color 0.3s ease;
            }
            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 30px;
                opacity: 0;
                animation: fadeIn 0.5s ease forwards;
            }
            @keyframes fadeIn {
                from {
                    opacity: 0;
                }
                to {
                    opacity: 1;
                }
            }
            .custom-select {
                border-radius: 25px;
                border: 2px solid #3498db;
                padding: 12px 20px;
                appearance: none;
                background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='14' height='14' viewBox='0 0 24 24' fill='none' stroke='%233498db' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpolyline points='6 9 12 15 18 9'%3E%3C/polyline%3E%3C/svg%3E");
                background-repeat: no-repeat;
                background-position: right 15px center;
                background-size: 14px;
                transition: all 0.3s ease;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            .custom-select:hover, .custom-select:focus {
                border-color: #2980b9;
                box-shadow: 0 4px 15px rgba(52, 152, 219, 0.2);
                transform: translateY(-2px);
            }
            .date-buttons {
                display: flex;
                justify-content: space-between;
                width: 100%;
                margin-top: 20px;
                opacity: 0;
                animation: slideIn 0.5s ease 0.2s forwards;
            }
            @keyframes slideIn {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
            .date-buttons .btn {
                flex: 1;
                border-radius: 25px;
                margin: 0 5px;
                transition: all 0.3s ease;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 1px;
                padding: 10px 0;
            }
            .date-buttons .btn.active {
                background-color: #3498db;
                color: white;
                box-shadow: 0 4px 15px rgba(52, 152, 219, 0.4);
            }
            .movie-card {
                border-radius: 20px;
                overflow: hidden;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
                transition: all 0.4s ease;
                margin-bottom: 30px;
                background-color: #fff;
                opacity: 0;
                animation: fadeInUp 0.5s ease forwards;
                animation-delay: calc(var(--animation-order) * 0.1s);
            }
            @keyframes fadeInUp {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
            .movie-card:hover {
                transform: translateY(-10px);
                box-shadow: 0 15px 40px rgba(52, 152, 219, 0.2);
            }
            .movie-poster {
                height: 100%;
                object-fit: cover;
                transition: all 0.4s ease;
            }
            .movie-card:hover .movie-poster {
                transform: scale(1.05);
            }
            .card-body {
                padding: 25px;
            }
            .card-title {
                font-size: 1.5rem;
                font-weight: 700;
                margin-bottom: 15px;
                color: #2c3e50;
                transition: color 0.3s ease;
            }
            .card-text {
                font-size: 0.9rem;
                line-height: 1.6;
                color: #7f8c8d;
                transition: color 0.3s ease;
            }
            .showtime-buttons {
                display: flex;
                flex-wrap: wrap;
                gap: 12px;
                margin-top: 20px;
            }
            .showtime-button {
                border-radius: 25px;
                padding: 8px 20px;
                transition: all 0.3s ease;
                font-weight: 600;
                border: 2px solid #3498db;
                color: #3498db;
            }
            .showtime-button:hover {
                background-color: #3498db;
                color: white;
                box-shadow: 0 4px 15px rgba(52, 152, 219, 0.3);
                transform: translateY(-2px);
            }
        </style>

        <script>
            function changeCinemaChain() {
                var cinemaChainID = document.getElementById("cinemaChainSelect").value;
                window.location.href = "showtimes?cinemaChainID=" + cinemaChainID;
            }

            function changeCinema() {
                var cinemaChainID = document.getElementById("cinemaChainSelect").value;
                var cinemaID = document.getElementById("cinemaSelect").value;
                window.location.href = "showtimes?cinemaChainID=" + cinemaChainID + "&cinemaID=" + cinemaID;
            }

            function changeDate(date) {
                var cinemaChainID = document.getElementById("cinemaChainSelect").value;
                var cinemaID = document.getElementById("cinemaSelect").value;
                window.location.href = "showtimes?cinemaChainID=" + cinemaChainID + "&cinemaID=" + cinemaID + "&date=" + date;
            }

            function bookTicket(movieSlotID) {
                // Implement booking logic here
                alert("Đặt vé cho suất chiếu ID: " + movieSlotID);
            }
        </script>
    </body>
</html>