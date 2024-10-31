<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Phim Yêu Thích</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"> 
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <style>
            body {
                background-color: #fff1f6;
                font-family: 'Poppins', sans-serif;
                color: #333;
            }

            .container {
                background-color: #ffffff;
                padding: 30px;
                box-shadow: 0 4px 20px rgba(226, 191, 217, 0.3);
                border-radius: 12px;
                margin: 40px auto;
                max-width: 1200px;
                border: 3px dashed #E2BFD9;
            }

            h2 {
                color: #e2147e;
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
                background: linear-gradient(to right, #E2BFD9, #e2147e);
                margin: 0.75rem auto 0;
                border-radius: 4px;
            }

            .movie-table {
                width: 100%;
                border-spacing: 0 15px;
                border-collapse: separate;
            }

            .movie-table td, .movie-table th {
                padding: .75rem;
                vertical-align: middle;
                border: none;
            }

            .movie-table img {
                width: 60px;
                height: 90px;
                object-fit: cover;
                margin-right: 20px;
                border-radius: 6px;
                border: 3px dashed #E2BFD9;
                padding: 3px;
                transition: transform 0.3s ease;
            }

            .movie-table img:hover {
                transform: scale(1.1);
            }

            .table-header {
                background: linear-gradient(45deg, #E2BFD9, #e2147e);
                color: white;
                font-weight: 600;
                text-transform: uppercase;
                border-radius: 10px;
            }

            .table-header th {
                border: none;
                padding: 15px;
            }

            .table-row {
                background-color: #ffffff;
                transition: all 0.3s ease;
                border-radius: 10px;
                border: 3px dashed #E2BFD9;
                cursor: pointer;
            }

            .table-row:hover {
                background-color: #fff1f6;
                transform: translateY(-3px);
            }

            .movie-title {
                display: flex;
                align-items: center;
                padding: 10px;
                border-radius: 10px;
                width: 100%;
            }

            .delete-btn {
                background: none;
                border: none;
                color: #e2147e;
                cursor: pointer;
                padding: 8px;
                border-radius: 50%;
                transition: all 0.3s ease;
            }

            .delete-btn:hover {
                color: #E2BFD9;
                transform: scale(1.1);
                background-color: rgba(226, 191, 217, 0.1);
            }

            .delete-btn i {
                font-size: 1.4rem;
            }

            @media (max-width: 768px) {
                .container {
                    padding: 15px;
                    margin: 20px;
                }

                h2 {
                    font-size: 2rem;
                }

                .movie-table th, .movie-table td {
                    padding: 10px;
                }

                .movie-table img {
                    width: 40px;
                    height: 60px;
                }
            }
        </style>
    </head>
    <body>
        <jsp:include page="/page/landingPage/Header.jsp"/>

        <div class="container" data-aos="fade-up">
            <h2 class="text-center my-4" data-aos="fade-down">Phim Yêu Thích</h2>
            <c:set var="movies" value="${requestScope.favouriteMovies}" scope="request"></c:set>

                <table class="table movie-table">
                    <thead class="table-header">
                        <tr>
                            <th scope="col">Tên phim</th>                         
                            <th scope="col">Đánh giá</th>
                            <th scope="col">Xóa khỏi yêu thích</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="movie" items="${movies}" varStatus="status">
                        <tr class="table-row" 
                            data-aos="fade-up" 
                            data-aos-delay="${status.index * 100}">
                            <td onclick="window.location.href = 'HandleDisplayMovieInfo?movieID=${movie.movieID}'">
                                <div class="movie-title">
                                    <img src="${movie.imageURL}" alt="${movie.title}">
                                    ${movie.title}
                                </div>
                            </td>
                            <td onclick="window.location.href = 'HandleDisplayMovieInfo?movieID=${movie.movieID}'">
                                ${movie.rating}
                            </td>
                            <td>
                                <form action="myfavouritemovie" method="POST" style="margin:0">
                                    <input type="hidden" name="deletedFavouriteMovieInput" value="${movie.movieID}">
                                    <button type="submit" class="delete-btn" onclick="event.stopPropagation();">
                                        <i class="fa-solid fa-trash"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>
                                        AOS.init({
                                            duration: 800,
                                            once: true,
                                            offset: 100
                                        });
        </script>
    </body>
</html>