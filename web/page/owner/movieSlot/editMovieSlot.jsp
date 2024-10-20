<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="model.owner.MovieSlot"%>
<%@page import="model.owner.Room"%>
<%@page import="java.util.List"%>

<jsp:include page="/page/owner/navbar.jsp" />
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Movie Slot</title>
    </head>
    <body>
        <div class="container mt-4">
            <h1 class="text-center mb-4">Edit Movie Slot</h1>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
            </c:if>

            <form action="editMovieSlot" method="post">
                <input type="hidden" name="movieSlotID" value="${movieSlot.movieSlotID}" />
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
                    <label for="inputRoom" class="form-label">Select Room</label>
                    <select id="inputRoom" name="roomID" class="form-select" required>
                        <c:forEach var="room" items="${rooms}">
                            <option value="${room.roomID}" ${room.roomID == movieSlot.roomID ? 'selected' : ''}>${room.roomName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="inputStartTime" class="form-label">Start Time</label>
                    <input type="datetime-local" id="inputStartTime" name="startTime" value="${fn:substring(movieSlot.startTime, 0, 16)}" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="inputEndTime" class="form-label">End Time</label>
                    <input type="datetime-local" id="inputEndTime" name="endTime" value="${fn:substring(movieSlot.endTime, 0, 16)}" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-primary">Update Movie Slot</button>
            </form>
        </div>
    </body>
</html>
