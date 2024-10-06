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
            padding: 20px;
            box-shadow: 0 2px 10px rgba(233, 230, 230, 0.1); /* Bóng đổ cho container */
        }

        .header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 10px 0;
            background-color: #eceff1; /* Màu nền cho header */
            color: white; /* Màu chữ trắng cho header */
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* Bóng đổ cho header */
            font-family: Arial, sans-serif; /* Kiểu chữ cho header */
        }

        .header select {
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            background-color: #343a40; /* Màu nền cho select */
            color: white; /* Màu chữ trắng cho select */
        }

        .title {
            font-size: 24px;
            color: #007bff; /* Màu xanh cho tiêu đề */
            text-align: center;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .label {
            font-weight: bold;
        }

        .form-select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            background-color: #e9ecef; /* Màu nền cho form-select */
        }

        .movie-list {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        .movie-item {
            background: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            transition: transform 0.3s ease; /* Hiệu ứng chuyển động */
        }

        .movie-item:hover {
            transform: translateY(-5px); /* Hiệu ứng di chuyển lên khi hover */
        }

        .movie-title {
            font-size: 20px;
            color: #007bff;
            margin-bottom: 10px;
        }

        .movie-image {
            width: 100%;
            max-width: 200px;
            border-radius: 5px;
        }

        .slot-list {
            list-style-type: none;
            padding: 0;
            margin: 10px 0;
        }

        .slot-item {
            background: #e9ecef;
            margin: 5px 0;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer; /* Thay đổi con trỏ khi hover */
            transition: background-color 0.3s ease; /* Hiệu ứng chuyển động */
        }

        .slot-item:hover {
            background: #d1d1d1; /* Màu nền khi hover */
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

            <label class="label" for="cinemaID">Chọn rạp:</label>
            <select name="cinemaID" id="cinemaID" class="form-select" onchange="this.form.submit()">
                <c:forEach var="cinema" items="${cinemas}">
                    <option value="${cinema.cinemaID}" 
                            <c:if test="${cinema.cinemaID == selectedCinemaID}">selected</c:if>>
                        ${cinema.name}
                    </option>
                </c:forEach>
            </select>

            <label class="label" for="date">Chọn ngày:</label>
            <select name="date" id="date" class="form-select" onchange="this.form.submit()">
                <c:forEach var="date" items="${availableDates}">
                    <option value="${date}" 
                            <c:if test="${date == selectedDate}">selected</c:if>>
                        ${date}
                    </option>
                </c:forEach>
            </select>
        </form>

        <h2 class="movie-list-title">Danh sách phim</h2>
        <div class="movie-list">
            <c:if test="${not empty movies}">
                <c:forEach var="movie" items="${movies}">
                    <div class="movie-item">
                        <h3 class="movie-title">${movie.title}</h3>
                        <img src="${movie.imageURL}" alt="${movie.title}" class="movie-image"/>

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
                            </ul>
                        </c:if>
                        <c:if test="${empty movieSlotsByMovie[movie]}">
                            <p>Không có suất chiếu cho phim này.</p>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty movies}">
                <p>Không có phim nào đang chiếu.</p>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <p class="error-message">${errorMessage}</p>
            </c:if>
        </div>
    </div>
</body>
</html>