<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Favourite Movie</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">        

        <style>
            body {
                background-color: #f0f2f5;
                font-family: 'Arial', sans-serif;
            }

            .container {
                background-color: #fff;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                margin-top: 30px;
            }

            h2 {
                color: #343a40;
                font-size: 2.5rem;
                font-weight: bold;
                margin-bottom: 20px;
                text-align: center;
                position: relative;
            }

            h2::after {
                content: "";
                display: block;
                width: 50px;
                height: 4px;
                background-color: #007bff;
                margin: 0.5rem auto 0;
                border-radius: 2px;
            }

            .movie-table {
                margin: 20px auto;
                width: 100%;
                border-collapse: separate;
                border-spacing: 0 15px;
            }

            .movie-table th, .movie-table td {
                vertical-align: middle;
                padding: 10px 15px;
                text-align: left;
            }

            .movie-table img {
                width: 50px;
                height: auto;
                margin-right: 15px;
                border-radius: 5px;
            }

            .movie-title {
                display: flex;
                align-items: center;
                font-weight: bold;
            }

            .table-header {
                background-color: #343a40;
                color: white;
            }

            .table-header th {
                border: none;
            }

            .table-row {
                background-color: #ffffff;
                transition: background-color 0.3s ease;
                box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
                cursor: pointer;
            }

            .table-row:hover {
                background-color: #f8f9fa;
            }
            
            .button-borderless {
                border: none;
                background-color: transparent;
                color: #dc3545;
                cursor: pointer;
                font-size: 1.2rem;
            }
            
            .button-borderless:hover {
                color: #bd2130;
            }
            
            .movie-genres {
                padding-left: 5px;
            }
        </style>
        <script src="javascript/style.js"></script>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center my-4">Bộ phim yêu thích</h2>
            <c:set var="movies" value="${requestScope.favouriteMovies}" scope="request"></c:set>
            <form id="favouriteMoviesForm">
            <table class="table movie-table">
                <thead class="table-header">
                    <tr>
                        <th scope="col">Tên phim</th>
                        <th scope="col">Thể loại</th>
                        <th scope="col">Tình trạng</th>
                        <th scope="col">Điểm số</th>
                        <th scope="col">Xóa khỏi yêu thích</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="movie" items="${movies}">
                    <tr class="table-row" onclick="window.location.href='HandleDisplayMovieInfo?movieID=${movie.movieID}'">
                        <td>
                            <div class="movie-title">
                                <img src="${movie.imageURL}" alt="${movie.title}">
                                ${movie.title}
                            </div>
                        </td>
                        <c:set var="movieGenres" value="${fn:replace(movie.genres.toString(), '[', '')}" />
                        <c:set var="movieGenres" value="${fn:replace(movieGenres, ']', '')}" />
                        <td class="movie-genres">${movieGenres}</td>
                        <td>${movie.status}</td>
                        <td>${movie.rating}</td>
                        <td>
                            <button id="delete_${movie.movieID}" class="button-borderless" onclick="deleteFavouriteMovie('${movie.movieID}');">
                                <i class="fa-solid fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            </form>
        </div>
    </body>
    <script>
        function deleteFavouriteMovie(movieID) {
            const deletedFavouriteMovieInput = document.createElement('input');
            deletedFavouriteMovieInput.type = 'hidden';
            deletedFavouriteMovieInput.name = 'deletedFavouriteMovieInput';
            deletedFavouriteMovieInput.value = movieID;
            document.getElementById('favouriteMoviesForm').appendChild(deletedFavouriteMovieInput);
            callServlet('favouriteMoviesForm', '/movie/myfavouritemovie', 'POST');
        }
    </script>
</html>
