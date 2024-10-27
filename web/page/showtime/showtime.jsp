<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <title>Lịch chiếu phim</title>
    <style>
      body {
        font-family: "Roboto", sans-serif;
        background: linear-gradient(135deg, #7e60bf, #b7e0ff);
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
        background: linear-gradient(135deg, #7e60bf, #7e60bf);
        color: white;
        padding: 20px;
        border-radius: 10px;
        margin-bottom: 30px;
      }

      .title {
        font-size: 32px;
        color: #fcfaee;
        text-align: center;
        margin-bottom: 20px;
        text-shadow: 2px 2px 4px rgba(255, 105, 180, 0.3);
      }

      .selector {
        margin-bottom: 20px;
      }

      .selector h3 {
        color: #7e60bf;
        margin-bottom: 10px;
      }

      .button-group {
        display: flex;
        flex-wrap: wrap;
        gap: 10px;
      }

      .selector-button {
        padding: 10px 15px;
        border: none;
        background-color: #ffe4e1;
        color: #7e60bf;
        border-radius: 5px;
        cursor: pointer;
        transition: all 0.3s ease;
      }

      .selector-button:hover,
      .selector-button.active {
        background-color: #7e60bf;
        color: white;
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
        background-color: #7e60bf;
        color: white;
        border-color: #7e60bf;
      }

      .date-number {
        font-size: 18px;
        font-weight: bold;
      }

      .date-day {
        font-size: 14px;
      }

      .date-button:hover {
        background-color: #f8bbd0;
        border-color: #7e60bf;
      }

      .movie-list {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
        gap: 30px;
      }

      .movie-item {
        background: #fff0f5;
        border-radius: 15px;
        box-shadow: 0 10px 20px rgba(255, 105, 180, 0.2);
        padding: 25px;
        transition: all 0.3s ease;
      }

      .movie-title {
        font-size: 24px;
        color: #7e60bf;
        margin-bottom: 15px;
        border-bottom: 2px solid #7e60bf;
        padding-bottom: 10px;
      }

      .movie-image {
        width: 100%;
        max-width: 250px;
        border-radius: 10px;
        margin-bottom: 15px;
      }

      .showtime-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
        gap: 10px;
        margin-top: 15px;
      }

      .showtime-item {
        display: inline-block;
        padding: 5px 10px;
        background-color: #e6f7ff;
        border: 1px solid #91d5ff;
        border-radius: 4px;
        cursor: pointer;
        transition: all 0.3s ease;
      }

      .showtime-item:hover {
        background-color: #f5efff;
        color: white;
      }
      .showtime-group {
        margin-bottom: 10px;
      }

      .showtime-time {
        font-weight: bold;
        font-size: 16px;
      }

      .showtime-type {
        font-weight: bold;
        font-size: 14px;
        color: #0c0b0b;
        margin-bottom: 5px;
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
      .start-time {
        font-weight: bold;
        color: #0ea5e9;
      }
      .end-time {
        color: #0ea5e9;
      }
      .time-separator {
        margin: 0 2px;
        color: #91d5ff;
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
            <button
              class="selector-button ${chain.cinemaChainID == selectedCinemaChainID ? 'active' : ''}"
              data-id="${chain.cinemaChainID}"
              onclick="selectCinemaChain(${chain.cinemaChainID})"
            >
              ${chain.name}
            </button>
          </c:forEach>
        </div>
      </div>

      <div class="selector">
        <h3>Chọn rạp:</h3>
        <div class="button-group" id="cinemaButtons">
          <c:forEach var="cinema" items="${cinemas}">
            <button
              class="selector-button ${cinema.cinemaID == selectedCinemaID ? 'active' : ''}"
              data-id="${cinema.cinemaID}"
              onclick="selectCinema(${cinema.cinemaID})"
            >
              ${cinema.name}
            </button>
          </c:forEach>
        </div>
      </div>

      <div class="selector">
        <h3>Chọn ngày:</h3>
        <div class="date-selector">
          <c:forEach var="date" items="${availableDates}" varStatus="status">
            <button
              class="date-button ${date == selectedDate ? 'active' : ''}"
              onclick="selectDate('${date}')"
            >
              <span class="date-number">${date.dayOfMonth}</span>
              <span class="date-day">
                <c:choose>
                  <c:when test="${status.index == 0}">Hôm nay</c:when>
                  <c:otherwise>
                    <c:set var="dayOfWeek" value="${date.dayOfWeek}" />
                    <c:choose>
                      <c:when test="${dayOfWeek == 'MONDAY'}">Thứ 2</c:when>
                      <c:when test="${dayOfWeek == 'TUESDAY'}">Thứ 3</c:when>
                      <c:when test="${dayOfWeek == 'WEDNESDAY'}">Thứ 4</c:when>
                      <c:when test="${dayOfWeek == 'THURSDAY'}">Thứ 5</c:when>
                      <c:when test="${dayOfWeek == 'FRIDAY'}">Thứ 6</c:when>
                      <c:when test="${dayOfWeek == 'SATURDAY'}">Thứ 7</c:when>
                      <c:when test="${dayOfWeek == 'SUNDAY'}">CN</c:when>
                    </c:choose>
                  </c:otherwise>
                </c:choose>
              </span>
            </button>
          </c:forEach>
        </div>
      </div>

      <div class="movie-list">
        <c:if test="${not empty movies}">
          <c:forEach var="movie" items="${movies}">
            <c:if test="${not empty movieSlotsByMovie[movie]}">
              <div class="movie-item">
                <h3 class="movie-title">${movie.title}</h3>
                <img
                  src="${movie.imageURL}"
                  alt="${movie.title}"
                  class="movie-image"
                />

                <p><strong>Mô tả:</strong> ${movie.synopsis}</p>
                <p>
                  <strong>Ngày công chiếu:</strong>
                  <fmt:formatDate
                    value="${movie.datePublished}"
                    pattern="dd/MM/yyyy"
                  />
                </p>
                <p>
                  <strong>Đánh giá:</strong> ${movie.rating != null ?
                  movie.rating : 'Chưa có đánh giá'}
                </p>
                <p>
                  <strong>Quốc gia:</strong> ${not empty movie.country ?
                  movie.country : 'Chưa có thông tin'}
                </p>

                <h4>Suất chiếu:</h4>
                <div class="showtime-grid">
                  <c:forEach var="slot" items="${movieSlotsByMovie[movie]}">
                    <div class="showtime-group">
                      <div class="showtime-type">${slot.type}</div>
                      <div
                        class="showtime-item"
                        onclick="selectSlot(${slot.movieSlotID})"
                      >
                        <span class="start-time"
                          ><fmt:formatDate
                            value="${slot.startTime}"
                            pattern="HH:mm"
                        /></span>
                        <span class="time-separator">~</span>
                        <span class="end-time"
                          ><fmt:formatDate
                            value="${slot.endTime}"
                            pattern="HH:mm"
                        /></span>
                      </div>
                    </div>
                  </c:forEach>
                </div>
              </div>
            </c:if>
          </c:forEach>
        </c:if>
        <c:if test="${empty movies}">
          <p>Không có phim nào đang chiếu.</p>
        </c:if>
      </div>

      <c:if test="${empty movieSlotsByMovie}">
        <p>Không có suất chiếu nào cho ngày đã chọn.</p>
      </c:if>

      <c:if test="${not empty errorMessage}">
        <p class="error-message">${errorMessage}</p>
      </c:if>
    </div>

    <script>
      function selectCinemaChain(cinemaChainID) {
        window.location.href = "showtimes?cinemaChainID=" + cinemaChainID;
      }

      function selectCinema(cinemaID) {
        const cinemaChainID = document.querySelector(
          "#cinemaChainButtons .active"
        ).dataset.id;
        window.location.href =
          "showtimes?cinemaChainID=" + cinemaChainID + "&cinemaID=" + cinemaID;
      }

      function selectDate(date) {
        const cinemaChainID = document.querySelector(
          "#cinemaChainButtons .active"
        ).dataset.id;
        const cinemaID = document.querySelector("#cinemaButtons .active")
          .dataset.id;
        window.location.href =
          "showtimes?cinemaChainID=" +
          cinemaChainID +
          "&cinemaID=" +
          cinemaID +
          "&date=" +
          date;
      }

      function selectSlot(movieSlotID) {
        var form = document.createElement("form");

        form.method = "GET";
        form.action = "selectSeat";

        var actionInput = document.createElement("input");
        actionInput.type = "hidden";
        actionInput.name = "action";
        actionInput.value = "selectSlot";
        form.appendChild(actionInput);

        var slotInput = document.createElement("input");
        slotInput.type = "hidden";
        slotInput.name = "movieSlotID";
        slotInput.value = movieSlotID;
        form.appendChild(slotInput);

        document.body.appendChild(form);
        form.submit();
      }
    </script>
  </body>
</html>
