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
                background-color: #f8f9fa;
                font-family: 'Roboto', sans-serif;
                color: #333;
            }

            .container {
                background-color: #fff;
                padding: 30px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                border-radius: 12px;
                margin: 40px auto;
                max-width: 1200px;
            }

            h2 {
                color: #212529;
                font-size: 2.8rem;
                font-weight: 700;
                text-align: center;
                margin-bottom: 40px;
                position: relative;
            }

            h2::after {
                content: "";
                display: block;
                width: 60px;
                height: 4px;
                background-color: #007bff;
                margin: 0.75rem auto 0;
                border-radius: 4px;
            }

            .movie-table {
                width: 100%;
                border-spacing: 0 15px;
            }

            .movie-table th, .movie-table td {
                padding: 15px 20px;
                text-align: left;
                vertical-align: middle;
                border-bottom: 1px solid #dee2e6;
            }

            .movie-table img {
                width: 60px;
                height: auto;
                margin-right: 20px;
                border-radius: 6px;
                box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
            }

            .movie-title {
                display: flex;
                align-items: center;
                font-weight: bold;
            }

            .table-header {
                background-color: #007bff;
                color: white;
                font-weight: 600;
                text-transform: uppercase;
            }

            .table-header th {
                border: none;
            }

            .table-row {
                background-color: #ffffff;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
                transition: background-color 0.3s ease, box-shadow 0.3s ease;
                border-radius: 10px;
            }

            .table-row:hover {
                background-color: #f1f3f5;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }

            .button-borderless {
                border: none;
                background-color: transparent;
                color: #dc3545;
                cursor: pointer;
                font-size: 1.4rem;
                transition: color 0.2s;
            }

            .button-borderless:hover {
                color: #bd2130;
            }

            .movie-genres {
                color: #495057;
                font-size: 0.95rem;
            }

            @media (max-width: 768px) {
                .container {
                    padding: 20px;
                }

                .movie-table th, .movie-table td {
                    padding: 10px;
                }

                h2 {
                    font-size: 2rem;
                }

                .movie-table img {
                    width: 40px;
                }
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
                                <th scope="col">Điểm số</th>
                                <th scope="col">Xóa khỏi yêu thích</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="movie" items="${movies}">
                            <tr class="table-row" onclick="window.location.href = 'HandleDisplayMovieInfo?movieID=${movie.movieID}'">
                                <td>
                                    <div class="movie-title">
                                        <img src="${movie.imageURL}" alt="${movie.title}">
                                        ${movie.title}
                                    </div>
                                </td>
                               
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
            callServlet( 'myfavouritemovie', 'POST');
        }
    </script>
</html>
