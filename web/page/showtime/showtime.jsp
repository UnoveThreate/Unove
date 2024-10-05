<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Showtimes</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa; /* Màu nền sáng */
            margin: 0;
            padding: 20px;
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%); /* Hiệu ứng gradient */
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

        .error-message {
            color: red;
            font-weight: bold;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1 class="title">Lịch chiếu phim</h1>
        </div>

        <form action="showtimes" method="get" class="form-group">
            <label class="label" for="cinemaChainID">Chọn chuỗi rạp:</label>
            <select name="cinemaChainID" id="cinemaChainID" class="form-select" onchange="this.form.submit()">
                <c:forEach var="chain" items="${cinemaChains}">
                    <option value="${chain.cinemaChainID}" 
                            <c:if test="${chain.cinemaChainID == selectedCinemaChainID}">selected</c:if>>
                        ${chain.name}
                    </option>
                </c:forEach>
            </select>

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
                        <p><strong>Ngày công chiếu:</strong> ${movie.datePublished != null ? fn:substring(movie.datePublished, 0, 10) : 'Chưa có thông tin'}</p>
                        <p><strong>Đánh giá:</strong> ${movie.rating != null ? movie.rating : 'Chưa có đánh giá'}</p>
                        <p><strong>Quốc gia:</strong> ${movie.country != null ? movie.country : 'Chưa có thông tin'}</p>

                        <h4>Suất chiếu:</h4>
                        <c:if test="${not empty movieSlotsByMovie[movie]}">
                            <ul class="slot-list">
                                <c:forEach var="slot" items="${movieSlotsByMovie[movie]}">
                                    <li class="slot-item" onclick="location.href='selectSeat?movieSlotID=${slot.movieSlotID}'">
                                        Thời gian: ${slot.startTime} - ${slot.endTime} <br>
                                        Giá: ${slot.price} VND <br>
                                        <c:if test="${slot.discount != null}">
                                            Giảm giá: ${slot.discount}%
                                        </c:if>
                                        <c:if test="${slot.discount == null}">
                                            Giảm giá: Không có giảm giá
                                        </c:if>
                                        <br>
                                        Trạng thái: ${slot.status}
                                    </li>
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