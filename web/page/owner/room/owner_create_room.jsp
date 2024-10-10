<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Create Room</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-5">     
            <h1>Create Room</h1>

            <form action="${pageContext.request.contextPath}/owner/createRoom" method="post">
                <input type="hidden" name="cinemaID" value="${cinemaID}"/>

                <div class="form-group">
                    <label for="roomName">Room Name:</label>
                    <input type="text" class="form-control" id="roomName" name="roomName" required>
                </div>

                <div class="form-group">
                    <label for="capacity">Capacity:</label>
                    <input type="number" class="form-control" id="capacity" name="capacity" required min="1">
                </div>

                <div class="form-group">
                    <label for="screenType">Screen Type:</label>
                    <input type="text" class="form-control" id="screenType" name="screenType" required>
                </div>

                <div class="form-group">
                    <label>Select Room Types:</label><br>
                    <input type="checkbox" name="roomType" value="3D"> 3D<br>
                    <input type="checkbox" name="roomType" value="IMAX"> IMAX<br>
                    <input type="checkbox" name="roomType" value="Standard"> Standard<br>
                </div>

                <button type="submit" class="btn btn-primary">Create Room</button>
            </form>

            <a href="${pageContext.request.contextPath}/owner/manageCinema?cinemaID=${cinemaID}" class="btn btn-secondary mt-3">Back to Cinema Management</a>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
