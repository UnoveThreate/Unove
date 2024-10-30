<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Mua vé xem phim</title>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 20px;
                background: #ffe6ef;
                color: #ffffff;
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
                background: #ffccd5;
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

            h1, h2, h3 {
                text-align: center;
                color: #ff4081;
            }
            .screen {
                text-align: center;
                margin: 20px 0;
                font-weight: bold;
                font-size: 24px;
                
                 border: 3px dashed #ff4081;
    padding: 15px;
    margin: 30px auto;
    max-width: 900px;
    border-radius: 10px;
            }
            .seats-container {
                display: grid;
                grid-template-columns: repeat(18, 1fr);
                gap: 5px;
                margin: 20px auto;
                max-width: 900px;
                 border: 2px dashed #722ed1;
    padding: 20px;
    border-radius: 15px;
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
                 border: 2px dashed #e71a0f;
    padding: 15px;
    margin: 20px auto;
    max-width: 900px;
    border-radius: 8px;
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
        </style>
    </head>
    <body>
        <div class="wave-container">
            <div class="wave wave-1"></div>
            <div class="wave wave-2"></div>
        </div>

        <h1 data-aos="fade-down">Mua vé xem phim</h1>
        <div class="screen" data-aos="fade-up">MÀN HÌNH</div>

        <div class="seats-container" data-aos="fade-up" data-aos-delay="200">
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

        <div class="total-price" data-aos="fade-up" data-aos-delay="300">
            Tạm tính: <span id="totalPrice">0 ₫</span>
        </div>

        <form action="selectSeat" method="post" data-aos="fade-up" data-aos-delay="400">
            <input type="hidden" name="movieSlotID" value="${selectedSlot.movieSlotID}">
            <input type="hidden" id="selectedSeatID" name="selectedSeatID" value="">
            <button type="submit" disabled id="bookButton" class="book-button">Đặt Ghế</button>
        </form>

        <div class="info" data-aos="fade-up" data-aos-delay="500">
            <div class="seat available">.</div>
            <div class="seat selected">.</div>
            <div class="seat unavailable">.</div>
            <div class="description">
                <span class="available">Có sẵn</span>
                <span class="selected">Đã chọn</span>
                <span class="unavailable">Đã đặt</span>
            </div>
        </div>

        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>
            AOS.init({
                duration: 1000,
                easing: 'ease-in-out',
                once: true,
                mirror: false
            });

            const selectedSeats = [];
            let totalPrice = 0;
            const movieSlotPrice = ${selectedSlot.price};
            const movieSlotDiscount = ${selectedSlot.discount};

            function selectSeat(seat) {
                if (!seat.classList.contains('available')) {
                    return;
                }

                const seatID = seat.dataset.id;
                const seatPrice = movieSlotPrice * (1 - movieSlotDiscount);

                if (selectedSeats.includes(seatID)) {
                    seat.classList.remove('selected');
                    selectedSeats.splice(selectedSeats.indexOf(seatID), 1);
                    totalPrice -= seatPrice;
                } else {
                    seat.classList.add('selected');
                    selectedSeats.push(seatID);
                    totalPrice += seatPrice;
                }

                updateUI();
            }

            function updateUI() {
                document.getElementById('selectedSeatID').value = selectedSeats.join(',');
                document.getElementById('totalPrice').textContent = 
                    totalPrice.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
                document.getElementById('bookButton').disabled = selectedSeats.length === 0;
            }

            document.addEventListener('DOMContentLoaded', function() {
                document.querySelector('form').addEventListener('submit', function(e) {
                    if (selectedSeats.length === 0) {
                        e.preventDefault();
                        alert('Vui lòng chọn ít nhất một ghế.');
                        return;
                    }

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
    </body>
</html>