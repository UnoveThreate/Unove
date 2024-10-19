<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="model.owner.MovieSlot"%>
<%@page import="model.owner.Movie"%>
<%@page import="DAO.cinemaChainOwnerDAO.MovieDAO"%>
<%@page import="DAO.cinemaChainOwnerDAO.RoomDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>

<jsp:include page="/page/owner/navbar.jsp" />
<!DOCTYPE html>
<html>
    <head>
        <title>Create Movie Slot</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script>
            function calculatePrice() {
                const priceInput = document.getElementById("inputPrice");
                const discountInput = document.getElementById("inputDiscount");
                const typeSelect = document.getElementById("inputType");
                const priceDisplay = document.getElementById("displayPrice");

                let price = parseFloat(priceInput.value);
                const discount = parseFloat(discountInput.value) / 100;
                let finalPrice = 0;

                if (typeSelect.value === "Weekday") {
                    finalPrice = price - (price * discount);
                } else if (typeSelect.value === "Weekend") {
                    finalPrice = price + (price * 0.2) - (price * discount);
                }

                priceDisplay.innerText = finalPrice.toFixed(2);
            }
        </script>
    </head>
    <body>
        <div class="container mt-4">
            <h1 class="text-center mb-4">Create Movie Slot</h1>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
            </c:if>

            <form action="createMovieSlot" method="post">
                <div class="mb-3">
                    <label for="inputMovie" class="form-label">Select Movie</label>
                    <select id="inputMovie" name="movieID" class="form-select" required>
                        <option value="" disabled selected>Select a movie</option>
                        <c:forEach var="movie" items="${movies}">
                            <option value="${movie.movieID}">${movie.title}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="inputRoom" class="form-label">Select Rooms</label>
                    <select id="inputRoom" name="roomIDs" class="form-select" multiple required>
                        <c:forEach var="room" items="${rooms}">
                            <option value="${room.roomID}">${room.roomName}</option>
                        </c:forEach>
                    </select>
                    <small class="form-text text-muted">Hold down the Ctrl (Windows) or Command (Mac) button to select multiple rooms.</small>
                </div>

                <div class="mb-3">
                    <label for="inputStartTime" class="form-label">Start Time</label>
                    <input type="datetime-local" id="inputStartTime" name="startTime" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="inputEndTime" class="form-label">End Time</label>
                    <input type="datetime-local" id="inputEndTime" name="endTime" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="inputType" class="form-label">Type</label>
                    <select id="inputType" name="type" class="form-select" onchange="calculatePrice()" required>
                        <option value="Weekday">Weekday</option>
                        <option value="Weekend">Weekend</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="inputPrice" class="form-label">Price</label>
                    <input type="number" id="inputPrice" name="price" class="form-control" oninput="calculatePrice()" required>
                </div>

                <div class="mb-3">
                    <label for="inputDiscount" class="form-label">Discount (%)</label>
                    <input type="number" id="inputDiscount" name="discount" class="form-control" oninput="calculatePrice()" required>
                </div>

                <div class="mb-3">
                    <strong>Calculated Price: </strong><span id="displayPrice">0.00</span>
                </div>

                <button type="submit" class="btn btn-primary">Create Movie Slot</button>
            </form>
        </div>
    </body>
</html>
