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
                background-color: #1c1c1c;
                color: white;
                margin: 0;
                padding: 20px;
            }
            h1, h2, h3 {
                text-align: center;
                color: #ff4081;
            }
            .screen {
                text-align: center;
                margin: 20px 0;
                font-weight: bold;
                font-size: 24px;
            }
            .seats-container {
                display: grid;
                grid-template-columns: repeat(18, 1fr);
                gap: 5px;
                margin: 20px auto;
                max-width: 900px;
            }
            .seat {
                width: 40px;
                height: 40px;
                display: flex;
                align-items: center;
                justify-content: center;
                border: 1px solid #ccc;
                border-radius: 5px;
                cursor: pointer;
                font-size: 12px;
            }
            .available {
                background-color: #722ed1;
            }
            .selected {
                background-color: #d42a87;
            }
            .unavailable {
                background-color: #404040;
                cursor: not-allowed;
            }
            .total-price {
                font-size: 18px;
                font-weight: bold;
                color: #e71a0f;
                text-align: right;
                margin-top: 10px;
            }
            .book-button {
                display: block;
                margin: 20px auto;
                padding: 10px 20px;
                background-color: #e71a0f;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 18px;
                width: 100%;
                max-width: 300px;
            }
            .book-button:disabled {
                background-color: #ccc;
                cursor: not-allowed;
            }
            .info {
                text-align: center;
                margin-top: 20px;
            }
            .info .seat {
                display: inline-block;
                width: 50px;
                height: 50px;
                line-height: 50px;
                margin: 5px;
                border-radius: 10px;
                font-weight: bold;
            }
            .info .description {
                display: block;
                margin-top: 10px;
            }
            .info .description span {
                margin: 0 10px;
                font-size: 16px;
            }
             body {
                font-family: Arial, sans-serif;
                background-color: #1c1c1c;
                color: white;
                margin: 0;
                padding: 20px;
            }
        </style>
        <script>
            const selectedSeats = [];
            let totalPrice = 0;
            const movieSlotPrice = ${selectedSlot.price};
            const movieSlotDiscount = ${selectedSlot.discount};

            function selectSeat(seat) {
                if (!seat.classList.contains('available')) {
                    return;
                }

                const seatID = seat.dataset.id;
                // Tính giá vé đã áp dụng giảm giá
                const seatPrice = movieSlotPrice * (1 - movieSlotDiscount);

                if (selectedSeats.includes(seatID)) {
                    // Bỏ chọn ghế
                    seat.classList.remove('selected');
                    selectedSeats.splice(selectedSeats.indexOf(seatID), 1);
                    totalPrice -= seatPrice;
                } else {
                    // Chọn ghế
                    seat.classList.add('selected');
                    selectedSeats.push(seatID);
                    totalPrice += seatPrice;
                }

                // Cập nhật UI
                updateUI();
            }

            function updateUI() {
                // Cập nhật input hidden chứa danh sách ghế đã chọn
                document.getElementById('selectedSeatID').value = selectedSeats.join(',');
                
                // Cập nhật hiển thị tổng tiền
                document.getElementById('totalPrice').textContent = 
                    totalPrice.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
                
                // Cập nhật trạng thái nút đặt vé
                document.getElementById('bookButton').disabled = selectedSeats.length === 0;
            }

            // Kiểm tra form trước khi submit
            document.addEventListener('DOMContentLoaded', function() {
                document.querySelector('form').addEventListener('submit', function(e) {
                    if (selectedSeats.length === 0) {
                        e.preventDefault();
                        alert('Vui lòng chọn ít nhất một ghế.');
                        return;
                    }

                    // Kiểm tra xem có ghế nào đã bị đặt không
                    const selectedElements = document.querySelectorAll('.seat.selected');
                    for (let seat of selectedElements) {
                        if (seat.classList.contains('unavailable')) {
                            e.preventDefault();
                            alert('Một số ghế bạn chọn đã có người đặt. Vui lòng chọn ghế khác.');
                            return;
                        }
                    }
                });
            });
        </script>
    </head>
    <body>
        <h1>Mua vé xem phim</h1>
        <div class="screen">MÀN HÌNH</div>

        <div class="seats-container">
            <c:forEach var="row" begin="1" end="9">
                <c:forEach var="col" begin="1" end="18">
                    <c:set var="currentSeat" value="${null}" />
                    <c:forEach var="seat" items="${seats}">
                        <c:if test="${seat.coordinateX == col && seat.coordinateY == row}">
                            <c:set var="currentSeat" value="${seat}" />
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${currentSeat != null}">
                            <div class="seat ${currentSeat.available ? 'available' : 'unavailable'}" 
                                 data-id="${currentSeat.seatID}" 
                                 data-price="${selectedSlot.price * (1 - selectedSlot.discount)}"
                                 onclick="selectSeat(this)">
                                ${currentSeat.name}
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="seat" style="visibility: hidden;"></div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:forEach>
        </div>

        <div class="total-price">
            Tạm tính: <span id="totalPrice">0 ₫</span>
        </div>

        <form action="selectSeat" method="post">
            <input type="hidden" name="movieSlotID" value="${selectedSlot.movieSlotID}">
            <input type="hidden" id="selectedSeatID" name="selectedSeatID" value="">
            <button type="submit" disabled id="bookButton" class="book-button">Đặt Ghế</button>
        </form>

        <div class="info">
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