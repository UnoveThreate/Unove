<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Lịch chiếu phim</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <style>
            body {
                font-family: 'Source Sans Pro', sans-serif;
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
                background: #f1daff;
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
                box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
                backdrop-filter: blur(10px);
                position: relative;
                z-index: 1;
                border: 3px dashed rgba(126, 96, 191, 0.2);
            }

            .header {
                background:#f8f0ff;
                color: white;
                padding: 20px;
                border-radius: 10px;
                margin-bottom: 30px;
                border: 3px dashed #7e60bf;
            }

            .title {
                font-size: 32px;
                color: #7E60BF;
                text-align: center;
                margin-bottom: 20px;
                text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
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
                border: 3px dashed #e0e0e0;
            }

            .selector-button:hover,
            .selector-button.active {
                border-color: #7E60BF;
                box-shadow: 0 0 10px rgba(126, 96, 191, 0.3);
                transform: translateY(-2px);
                border: 3px dashed #7e60bf;
            }

            .cinema-chain-avatar {
                width: 56px;
                height: 56px;
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
                box-shadow: 0 5px 15px rgba(126, 96, 191, 0.2);
                border: 3px dashed #7e60bf;

            }

            .date-button.active {
                background-color: #f8f0ff;
                color: black;
                border-color: #7E60BF;
                border: 3px dashed #7E60BF;
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
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
                width: 100%;
                text-align: left;
                color: #876ac3;
            }

            .cinema-item:hover {
                transform: translateX(10px);
                background-color: #f8f0ff;
                box-shadow: 0 5px 15px rgba(126, 96, 191, 0.1);
            }

            .cinema-item.active {
                background-color: #f8f0ff;
                color: #876ac3;
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
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
                border: 3px dashed #e0e0e0;
            }

            .movie-item:hover {
                transform: translateY(-5px);
                box-shadow: 0 8px 25px rgba(126, 96, 191, 0.15);
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
                background: #D4BEE4;
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
                border: 2px dashed rgba(126, 96, 191, 0.2);
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

            .breadcrumb-nav {
                background-color: transparent;
                padding: 15px 0;
                margin: 20px auto;
                max-width: 1200px;
            }

            .breadcrumb {
                margin-bottom: 0;
                padding: 15px 30px;
                background: rgba(255, 255, 255, 0.95);
                border-radius: 20px;
                backdrop-filter: blur(10px);
            }

            .breadcrumb-item a {
                color: #7E60BF;
                text-decoration: none;
                transition: all 0.3s ease;
                font-weight: 500;
            }

            .breadcrumb-item a:hover {
                color: #D4BEE4;
                transform: translateX(5px);
            }

            .breadcrumb-item.active {
                color: #666;
                font-weight: 500;
            }

            .breadcrumb-item + .breadcrumb-item::before {
                content: "\276F" !important;
                color: #b2b2b2;
            }


            @media (max-width: 768px) {
                .breadcrumb-nav {
                    padding: 10px;
                    margin: 10px;
                }

                .breadcrumb {
                    padding: 10px 15px;
                    font-size: 14px;
                }
            }
            .start-time strong {
                color : #7e60bf;
            }
            .showtime-grid {
                margin-top: 15px;
                display: flex;
                flex-direction: column;
                gap: 15px;
            }

            .showtime-slots {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                align-items: center;
            }
            .movie-poster-link {
                display: block;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .movie-poster-link:hover {
                transform: scale(1.05);
                opacity: 0.9;
            }
        </style>
    </head>
    <body>

        <jsp:include page="/page/landingPage/Header.jsp" />

        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb" class="breadcrumb-nav">
            <div class="container">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Trang chủ</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Lịch chiếu phim</li>
                </ol>
            </div>
        </nav>
        <div class="wave-container">
            <div class="wave wave-1"></div>
            <div class="wave wave-2"></div>
        </div>

        <div class="container">
            <div class="header" data-aos="fade-down">
                <h1 class="title">Lịch chiếu phim</h1>
            </div>

            <div class="selector" data-aos="fade-up" data-aos-delay="100">
                <!--                <h3>Chọn chuỗi rạp:</h3>-->
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

            <div class="selector" data-aos="fade-up" data-aos-delay="200">
                <!--                <h3>Chọn rạp:</h3>-->
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
            <div class="selector" data-aos="fade-up" data-aos-delay="300">
                <!--                <h3>Chọn ngày:</h3>-->
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

            <div class="movie-list" data-aos="fade-up" data-aos-delay="400">
                <c:choose>
                    <c:when test="${not empty movies}">
                        <c:forEach var="movie" items="${movies}">
                            <c:if test="${not empty movieSlotsByMovie[movie]}">
                                <div class="movie-item">
                                    <a href="${pageContext.request.contextPath}/HandleDisplayMovieInfo?movieID=${movie.movieID}" class="movie-poster-link">
                                        <img src="${movie.imageURL}" 
                                             alt="${movie.title}" 
                                             class="movie-poster" 
                                             onerror="this.src='path/to/default/image.jpg';" />
                                    </a>
                                    <div class="movie-info">
                                        <h3 class="movie-title">${movie.title}</h3>
                                        <div class="movie-genres">
                                            <c:forEach var="genre" items="${movieGenres[movie.movieID]}">
                                                <span class="genre-tag">${genre}</span>
                                            </c:forEach>
                                        </div>
                                        <div class="showtime-grid">
                                            <c:set var="currentType" value="" />
                                            <!-- nhóm type suất chiêuys -->
                                            <c:forEach var="slot" items="${movieSlotsByMovie[movie]}">
                                                <c:if test="${currentType ne slot.type}">
                                                    <c:if test="${not empty currentType}">
                                                    </div> 
                                                </div> 
                                            </c:if>
                                            <div class="showtime-group">
                                                <div class="showtime-type">${slot.type}</div>
                                                <div class="showtime-slots">
                                                    <c:set var="currentType" value="${slot.type}" />
                                                </c:if>
                                                <div class="showtime-item" onclick="selectSlot(${slot.movieSlotID})">
                                                    <fmt:formatDate value="${slot.startTime}" pattern="HH:mm" var="formattedStartTime" />
                                                    <fmt:formatDate value="${slot.endTime}" pattern="HH:mm" var="formattedEndTime" />
                                                    <span class="start-time"><strong>${formattedStartTime}</strong></span>
                                                    <span class="time-separator">~</span>
                                                    <span class="end-time">${formattedEndTime}</span>
                                                </div>
                                            </c:forEach>
                                            <c:if test="${not empty currentType}">
                                            </div> 
                                        </div> 
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </c:when>

        </c:choose>
    </div>

    <c:if test="${empty movieSlotsByMovie}">
        <div class="empty-state" data-aos="fade-up">
            <img src="images/not-found.svg" alt="No showtime found" 
                 style="width: 150px; height: 150px; opacity: 0.5; margin-bottom: 20px;"
                 data-aos="zoom-in">
            <h2 style="color: #000000; font-size: 24px; margin-bottom: 10px;"
                data-aos="fade-up" data-aos-delay="100">
                Úi, Suất chiếu không tìm thấy
            </h2>
            <p style="color: #666; font-size: 16px; margin-bottom: 30px;"
               data-aos="fade-up" data-aos-delay="200">
                Bạn hãy thử tìm ngày khác nhé
            </p>
        </div>
    </c:if>
</div>

<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script>
                                                    AOS.init({
                                                        duration: 1000,
                                                        easing: 'ease-in-out',
                                                        once: true,
                                                        mirror: false,
                                                        offset: 50
                                                    });

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