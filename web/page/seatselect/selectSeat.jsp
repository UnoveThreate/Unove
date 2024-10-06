<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Mua vé xem phim</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #1c1c1c; /* Màu nền tối */
            color: white;
            margin: 0;
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: #ff4081; /* Màu hồng cho tiêu đề */
        }
        .screen {
            text-align: center;
            margin: 20px 0;
            font-weight: bold;
            font-size: 24px;
        }
        .seat {
            display: inline-block;
            width: 40px;
            height: 40px;
            margin: 5px;
            text-align: center;
            line-height: 40px;
            border: 1px solid #ccc;
            border-radius: 5px;
            cursor: pointer;
        }
        .available {
            background-color: #722ed1; /* Ghế có sẵn */
        }
        .selected {
            background-color: #d42a87; /* Ghế đã chọn */
        }
        .unavailable {
            background-color: #404040; /* Ghế đã đặt */
        }
        .row {
            text-align: center;
            margin-bottom: 10px;
        }
        .book-button {
            display: block;
            margin: 20px auto;
            padding: 10px 20px;
            background-color: #ff4081; /* Màu hồng cho nút đặt */
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 18px;
        }
        .book-button:disabled {
            background-color: #ccc; /* Màu xám khi không thể đặt */
        }
        .info {
    text-align: center;
    margin-top: 20px;
}

.info h3 {
    margin-bottom: 10px;
    color: #ff4081; /* Màu hồng cho tiêu đề */
    font-size: 24px; /* Kích thước chữ lớn hơn */
}

.info .seat {
    display: inline-block;
    width: 50px; /* Kích thước ghế */
    height: 50px; /* Kích thước ghế */
    margin: 5px;
    text-align: center;
    line-height: 50px; /* Căn giữa chữ */
    border-radius: 10px; /* Bo góc */
    font-weight: bold; /* Chữ đậm */
    color: white; /* Màu chữ trắng */
}

.info .available {
    background-color: #722ed1; /* Ghế có sẵn */
}

.info .selected {
    background-color: #d42a87; /* Ghế đã chọn */
}

.info .unavailable {
    background-color: #404040; /* Ghế đã đặt */
}

.info .description {
    display: block;
    margin-top: 10px; /* Khoảng cách giữa ghế và mô tả */
}

.info .description span {
    margin: 0 10px; /* Khoảng cách giữa các mô tả */
    font-size: 16px; /* Kích thước chữ mô tả */
    color: #ffffff; /* Màu chữ trắng */
}
    </style>
    <script>
        const selectedSeats = [];

        function selectSeat(seat) {
            const seatID = seat.dataset.id;
            if (seat.classList.contains('available')) {
                if (selectedSeats.includes(seatID)) {
                    // Nếu ghế đã được chọn, bỏ chọn
                    seat.classList.remove('selected');
                    selectedSeats.splice(selectedSeats.indexOf(seatID), 1);
                } else {
                    // Nếu ghế chưa được chọn, chọn ghế
                    seat.classList.add('selected');
                    selectedSeats.push(seatID);
                }
                // Cập nhật giá trị của selectedSeatID
                document.getElementById('selectedSeatID').value = selectedSeats.join(',');
                // Kích hoạt hoặc vô hiệu hóa nút đặt ghế
                document.getElementById('bookButton').disabled = selectedSeats.length === 0;
            }
        }
    </script>
</head>
<body>
    <h1>Mua vé xem phim</h1>
    <div class="screen">MÀN HÌNH</div>
<!--    <h2>Suất chiếu: ${movieSlotID}</h2>-->

    <div id="seats">
        <c:forEach var="seat" items="${seats}">
            <div class="seat 
                ${seat.available ? 'available' : 'unavailable'} 
                ${seat.available && selectedSeats.contains(seat.seatID) ? 'selected' : ''}" 
                data-id="${seat.seatID}" 
                onclick="selectSeat(this)">
                ${seat.name}
            </div>
        </c:forEach>
    </div>

    <form action="selectSeat" method="post">
        <input type="hidden" name="movieSlotID" value="${movieSlotID}">
        <input type="hidden" id="selectedSeatID" name="selectedSeatID" value="">
        <button type="submit" disabled id="bookButton" class="book-button">Đặt Ghế</button>
    </form>

    <<div class="info">
<!--    <h3>Chú thích:</h3>-->
    <div class="seat available">.</div>
    <div class="seat selected">.</div>
    <div class="seat unavailable">.</div>
    <div class="description">
        <span class="available">Có sẵn</span>
        <span class="selected">Đã chọn</span>
        <span class="unavailable">Đã đặt</span>
    </div>
</div>
</body>
</html>