<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--java import-->

<%@ page import="java.util.ArrayList" %>
<%@ page import="jakarta.servlet.ServletContext" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Cine Pa</title>
        <!--link font icon :-->  
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!--link boostrap :-->  
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">


        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/movie/movie-style.css">

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

        <style type="text/css">
            @media only screen and (min-width:768px){
                .nav-item.dropdown:hover .dropdown-menu{
                    display:block;
                }
                .dropdown>.dropdown-toggle:active {
                    /*Without this, clicking will make it sticky*/
                    pointer-events: none;
                }
            }
        </style>

        <style>

            .titlee {
                text-align: center;
                font-size: 28px; /* Kích thước chữ */
                color: #333; /* Màu chữ */
                font-weight: bold; /* Đậm */
                text-transform: uppercase; /* Chuyển đổi thành chữ in hoa */
                letter-spacing: 2px; /* Khoảng cách giữa các ký tự */
                margin: 59px 0;
            }

        </style>


        <!--Include necessary CSS and JavaScript files only once--> 
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/movie/movie-style.css">



        <style>
            iframe {
                width: 100%;
                height: 250px;
            }
        </style>


    </head>
    <body>

        <jsp:include page="Header.jsp" />

       

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

        <!--phan Quang VInh :--> 
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>

       

    </body>
</html>
