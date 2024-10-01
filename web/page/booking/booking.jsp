<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.SeatDAO" %>
<%@ page import="model.Seat" %>
<%
    SeatDAO seatDAO = new SeatDAO();
    List<Seat> seats = seatDAO.getAllSeats(); 
%>
<html>
<head>
    <title>Đặt Ghế</title>
    <script>
        let selectedSeats = []; // Mảng lưu ghế đã chọn

        function bookSeat(seatName) {
            const index = selectedSeats.indexOf(seatName);
            if (index > -1) {
                selectedSeats.splice(index, 1); 
                document.querySelector('.seat.' + seatName).classList.remove('selected');
            } else {
                selectedSeats.push(seatName); 
                document.querySelector('.seat.' + seatName).classList.add('selected');
            }
        }

        function confirmBooking() {
            if (selectedSeats.length > 0) {
                fetch('TicketBookingServlet', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'seats=' + selectedSeats.join(',')
                })
                .then(response => response.text())
                .then(data => alert(data));
            } else {
                alert('Vui lòng chọn ghế trước khi mua vé.');
            }
        }
    </script>
    <style>
        .seat { width: 40px; height: 40px; margin: 5px; display: inline-block; text-align: center; cursor: pointer; font-weight: bold; }
        .available { background-color: #4CAF50; color: white; } 
        .booked { background-color: #F44336; color: white; } 
        .vip { background-color: #9C27B0; color: white; } 
        .selected { background-color: #3F51B5; color: white; } 
    </style>
</head>
<body>
    <h1>Chọn Ghế</h1>
    <div>
        <%
            for (Seat seat : seats) {
                String className = seat.isBooked() ? "booked" : "available"; 
        %>
                <div class="seat <%= className %>" onclick="<%= seat.isBooked() ? "" : "bookSeat(\'" + seat.getName() + "\')" %>">
                    <%= seat.getName() %>
                </div>
        <%
            }
        %>
    </div>
    <button onclick="confirmBooking()">Mua vé</button>
</body>
</html>