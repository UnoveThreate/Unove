<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Lịch chiếu phim</title>
        <style>
            body {
                font-family: 'Roboto', sans-serif;
                background: linear-gradient(135deg, #7E60BF, #B7E0FF);
                margin: 0;
                padding: 20px;
                color: #333;
                line-height: 1.6;
            }

            .container {
                max-width: 1200px;
                margin: auto;
                padding: 30px;
                background: linear-gradient(135deg, #fff0f5, #ffe4e1);
                border-radius: 15px;
                box-shadow: 0 10px 30px rgba(255, 105, 180, 0.3);
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
                text-shadow: 2px 2px 4px rgba(255, 105, 180, 0.3);
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
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            .selector-button:hover,
            .selector-button.active {
                border-color: #eb2f96;
                box-shadow: 0 0 10px rgba(235, 47, 150, 0.3);
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
                color: #eb2f96;
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
            }

            .date-button.active {
                background-color: #7E60BF;
                color: white;
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
                width: 100%;
                max-width: 100%;
                margin: 0;
                border: none;
            }

            .cinema-item {
                display: flex;
                align-items: center;
                width: 100%;
                background-color: #fff;
                padding: 12px 15px;
                cursor: pointer;
                transition: all 0.3s ease;
                border: none;
                border-bottom: 1px solid #f0f0f0;
                text-align: left;
                position: relative;
            }

            .cinema-item:last-child {
                border-bottom: none;
            }

            .cinema-item:hover,
            .cinema-item.active {
                background-color: #fce4ec;
            }

            .cinema-name {
                flex-grow: 1;
                font-size: 14px;
                color: #333;
            }

            .cinema-item::after {
                content: '>';
                position: absolute;
                right: 15px;
                color: #e91e63;
                font-size: 18px;
            }

            .cinema-item:hover::after,
            .cinema-item.active::after {
                color: #c2185b;
            }

            .movie-list {
                max-height: 600px;
                overflow-y: auto;
                background-color: white;
                border-radius: 15px;
                box-shadow: 0 10px 20px rgba(255, 105, 180, 0.2);
                padding: 20px;
            }

            .movie-poster {
                width: 100px;
                height: 150px;
                object-fit: cover;
                border-radius: 8px;
                margin-right: 20px; /* Tạo khoảng cách giữa poster và thông tin phim */
                transition: all 0.3s ease;
            }
            .movie-item:hover .movie-poster {
                transform: scale(1.05) rotate(2deg);
                box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
            }
            .movie-item {
                display: flex;
                margin-bottom: 20px;
                padding: 15px;
                border-bottom: 1px solid #eee;
                transition: all 0.3s ease;
                align-items: flex-start;
            }

            .movie-item:hover {
                background-color: rgba(255, 255, 255, 0.1);
                transform: translateY(-5px);
            }

            .movie-info {
                flex: 1;
                display: flex;
                flex-direction: column;
            }

            .movie-title {
                font-size: 18px;
                font-weight: bold;
                color: #7E60BF;
                margin-bottom: 5px;
            }

            .movie-genres {
                font-size: 14px;
                color: #666;
                margin-bottom: 10px;
            }

            .genre-tag {
                display: inline-block;
                background-color: #f0f0f0;
                padding: 2px 8px;
                margin-right: 5px;
                margin-bottom: 5px;
                border-radius: 10px;
                font-size: 12px;
            }

            .showtime-type {
                font-weight: bold;
                margin-top: 10px;
            }

            .showtime-list {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
            }
            .showtime-item {
                display: inline-block;
                background-color: #e6f7ff;
                border: 1px solid #91d5ff;
                border-radius: 20px;
                padding: 5px 12px;
                font-size: 14px;
                color: #0050b3;
                cursor: pointer;
                transition: all 0.3s ease;
                margin-right: 8px;
                margin-bottom: 8px;
            }

            .showtime-item:hover {
                background-color: #bae7ff;
                border-color: #1890ff;
            }

            .start-time {
                font-weight: bold;
            }

            .end-time {
                font-weight: normal;
            }

            .time-separator {
                margin: 0 2px;
                color: #8c8c8c;
            }
            .error-message {
                color: #ff1493;
                font-weight: bold;
                text-align: center;
                padding: 15px;
                background-color: #ffe4e1;
                border: 1px solid #ffb3ba;
                border-radius: 8px;
                margin-top: 20px;
            }

            @media (max-width: 768px) {
                .movie-list {
                    grid-template-columns: 1fr;
                }
            }

        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <h1 class="title">Lịch chiếu phim</h1>
            </div>

            <div class="selector">
                <h3>Chọn chuỗi rạp:</h3>
                <div class="button-group" id="cinemaChainButtons">
                    <c:forEach var="chain" items="${cinemaChains}">
                        <button class="selector-button ${chain.cinemaChainID == selectedCinemaChainID ? 'active' : ''}" 
                                data-id="${chain.cinemaChainID}" 
                                onclick="selectCinemaChain(${chain.cinemaChainID})">
                            <img src="${chain.avatarURL}" alt="${chain.name}" class="cinema-chain-avatar" onerror="this.style.display='none';" />
                            <span>${chain.name}</span>
                        </button>
                    </c:forEach>
                </div>
            </div>

            <div class="selector">
                <h3>Chọn rạp:</h3>
                <div class="cinema-list" id="cinemaButtons">
                    <c:forEach var="cinema" items="${cinemas}">
                        <button class="cinema-item ${cinema.cinemaID == selectedCinemaID ? 'active' : ''}" 
                                data-id="${cinema.cinemaID}" 
                                onclick="selectCinema(${cinema.cinemaID})">
                            <span class="cinema-name">${cinema.name}</span>
                        </button>
                    </c:forEach>
                </div>
            </div>

            <div class="selector">
                <h3>Chọn ngày:</h3>
                <div class="date-selector">
                    <c:forEach var="date" items="${availableDates}" varStatus="status">
                        <button class="date-button ${date == selectedDate ? 'active' : ''} ${datesWithShowtimes.contains(date) ? 'has-showtimes' : ''}" 
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

            <div class="movie-list">
                <c:choose>
                    <c:when test="${not empty movies}">
                        <c:forEach var="movie" items="${movies}">
                            <c:if test="${not empty movieSlotsByMovie[movie]}">
                                <div class="movie-item">
                                    <img src="${movie.imageURL}" alt="${movie.title}" class="movie-poster" onerror="this.src='path/to/default/image.jpg';" />
                                    <div class="movie-info">
                                        <h3 class="movie-title">${movie.title}</h3>
                                        <div class="movie-genres">
                                            <c:forEach var="genre" items="${movieGenres[movie.movieID]}">
                                                <span class="genre-tag">${genre}</span>
                                            </c:forEach>
                                        </div>
                                        <div class="showtime-grid">
                                            <c:forEach var="slot" items="${movieSlotsByMovie[movie]}">
                                                <div class="showtime-group">
                                                    <div class="showtime-type">${slot.type}</div>
                                                    <div class="showtime-item" onclick="selectSlot(${slot.movieSlotID})">
                                                        <fmt:formatDate value="${slot.startTime}" pattern="HH:mm" var="formattedStartTime" />
                                                        <fmt:formatDate value="${slot.endTime}" pattern="HH:mm" var="formattedEndTime" />
                                                        <span class="start-time">${formattedStartTime}</span>
                                                        <span class="time-separator">~</span>
                                                        <span class="end-time">${formattedEndTime}</span>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p class="error-message">Không có phim nào đang chiếu.</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${empty movieSlotsByMovie}">
                <p class="error-message">Không có suất chiếu nào cho ngày đã chọn.</p>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <p class="error-message">${errorMessage}</p>
            </c:if>
        </div>

        <script>
            function selectCinemaChain(cinemaChainID) {
                window.location.href = 'showtimes?cinemaChainID=' + cinemaChainID;
            }

            function selectCinema(cinemaID) {
                const cinemaChainID = document.querySelector('#cinemaChainButtons .active').dataset.id;
                window.location.href = 'showtimes?cinemaChainID=' + cinemaChainID + '&cinemaID=' + cinemaID;
            }

            function selectDate(date) {
                const cinemaChainID = document.querySelector('#cinemaChainButtons .active').dataset.id;
                const cinemaID = document.querySelector('#cinemaButtons .active').dataset.id;
                window.location.href = 'showtimes?cinemaChainID=' + cinemaChainID + '&cinemaID=' + cinemaID + '&date=' + date;
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