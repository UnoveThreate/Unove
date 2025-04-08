<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Confirm Ticket</title>

        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">

        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                margin: 0;
                padding: 20px;
                min-height: 100vh;
                background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
                background-size: 400% 400%;
                animation: gradient 15s ease infinite;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            @keyframes gradient {
                0% {
                    background-position: 0% 50%;
                }
                50% {
                    background-position: 100% 50%;
                }
                100% {
                    background-position: 0% 50%;
                }
            }

            .container {
                background: rgba(255, 255, 255, 0.9);
                padding: 40px;
                border-radius: 30px;
                box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
                max-width: 800px;
                width: 100%;
                backdrop-filter: blur(20px);
                border: 1px solid rgba(255, 255, 255, 0.2);
                position: relative;
                overflow: hidden;
            }

            .container::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                height: 5px;
                background: linear-gradient(90deg, #ff0000, #ff7300, #fffb00, #48ff00, #00ffd5, #002bff, #7a00ff, #ff00c8, #ff0000);
                background-size: 200%;
                animation: animate 15s linear infinite;
            }

            @keyframes animate {
                0% {
                    background-position: 0%;
                }
                100% {
                    background-position: 200%;
                }
            }

            h1 {
                font-size: 3em;
                text-align: center;
                margin-bottom: 40px;
                background: linear-gradient(45deg, #12c2e9, #c471ed, #f64f59);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                position: relative;
            }

            h2 {
                color: #2d3436;
                font-size: 1.8em;
                margin-top: 40px;
                padding-bottom: 15px;
                border-bottom: 3px solid #e0e0e0;
                position: relative;
            }

            h2::after {
                content: '';
                position: absolute;
                bottom: -3px;
                left: 0;
                width: 50px;
                height: 3px;
                background: linear-gradient(45deg, #12c2e9, #c471ed);
            }

            p {
                font-size: 1.1em;
                color: #2d3436;
                margin: 15px 0;
                line-height: 1.8;
                padding: 10px;
                border-radius: 8px;
                transition: all 0.3s ease;
            }

            p:hover {
                background: rgba(255, 255, 255, 0.8);
                transform: translateX(10px);
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            }

            strong {
                color: #6c5ce7;
                font-weight: 600;
            }

            ul {
                list-style-type: none;
                padding: 0;
                margin: 20px 0;
            }

            li {
                background: rgba(255, 255, 255, 0.8);
                margin: 15px 0;
                padding: 20px;
                border-radius: 15px;
                box-shadow: 0 5px 15px rgba(0,0,0,0.05);
                transition: all 0.4s ease;
                border-left: 5px solid #6c5ce7;
                display: flex;
                align-items: center;
            }

            li:before {
                content: '\f0da';
                font-family: 'Font Awesome 5 Free';
                font-weight: 900;
                margin-right: 10px;
                color: #6c5ce7;
            }

            li:hover {
                transform: translateX(10px) translateY(-5px);
                box-shadow: 0 10px 25px rgba(108, 92, 231, 0.2);
                background: white;
            }

            .form-group {
                margin-top: 50px;
                text-align: center;
            }

            input[type="submit"] {
                width: 100%;
                max-width: 400px;
                padding: 20px 40px;
                background: linear-gradient(45deg, #12c2e9, #c471ed, #f64f59);
                color: white;
                border: none;
                border-radius: 50px;
                font-size: 1.2em;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.4s ease;
                box-shadow: 0 10px 20px rgba(0,0,0,0.1);
                position: relative;
                overflow: hidden;
            }

            input[type="submit"]:hover {
                transform: translateY(-3px);
                box-shadow: 0 15px 30px rgba(0,0,0,0.2);
            }

            input[type="submit"]::before {
                content: '';
                position: absolute;
                top: 0;
                left: -100%;
                width: 100%;
                height: 100%;
                background: linear-gradient(120deg, transparent, rgba(255,255,255,0.3), transparent);
                transition: 0.5s;
            }

            input[type="submit"]:hover::before {
                left: 100%;
            }

            .section {
                padding: 20px;
                border-radius: 20px;
                margin: 30px 0;
                transition: all 0.3s ease;
            }

            .section:hover {
                background: rgba(255, 255, 255, 0.9);
                box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            }

            @media (max-width: 768px) {
                .container {
                    padding: 20px;
                    margin: 10px;
                }

                h1 {
                    font-size: 2em;
                }

                input[type="submit"] {
                    max-width: 100%;
                }
            }
        </style>
    </head>
    <body>

        <div class="container" data-aos="fade-up">
            <h1 data-aos="fade-down">Confirm Ticket Details</h1>

            <form action="${pageContext.request.contextPath}/order/confirm" method="post">

                <input type="hidden" name="orderID" value="${orderID}" />
                <input type="hidden" name="userID" value="${userID}" />
                <input type="hidden" name="code" value="${code}" />


                <div class="section" data-aos="fade-right" data-aos-delay="100">
                    <h2><i class="fas fa-clock"></i> Order Information</h2>
                    <p><strong>Time Created:</strong> ${orderDetails.timeCreated}</p>
                </div>


                <div class="section" data-aos="fade-right" data-aos-delay="200">
                    <h2><i class="fas fa-film"></i> Movie Information</h2>
                    <p><strong>Title:</strong> ${orderDetails.movieTitle}</p>
                    <p><strong>Room:</strong> ${orderDetails.roomName}</p>
                    <p><strong>Synopsis:</strong> ${orderDetails.synopsis}</p>
                    <p><strong>Country:</strong> ${orderDetails.country}</p>
                    <p><strong>Start Time:</strong> ${orderDetails.startTime}</p>
                    <p><strong>End Time:</strong> ${orderDetails.endTime}</p>
                </div>


                <div class="section" data-aos="fade-right" data-aos-delay="300">
                    <h2><i class="fas fa-building"></i> Cinema Information</h2>
                    <p><strong>Cinema:</strong> ${orderDetails.cinemaName}</p>
                    <p><strong>Address:</strong> ${orderDetails.address}, ${orderDetails.commune}, ${orderDetails.district}, ${orderDetails.province}</p>
                </div>


                <div class="section" data-aos="fade-right" data-aos-delay="400">
                    <h2><i class="fas fa-chair"></i> Seats</h2>
                    <ul>
                        <c:forEach var="seat" items="${seats}">
                            <li data-aos="fade-left">${seat.name}</li>
                            </c:forEach>
                    </ul>
                </div>
                <div class="section" data-aos="fade-right" data-aos-delay="500">
                    <h2><i class="fas fa-utensils"></i> Canteen Items</h2>
                    <ul>
                        <c:forEach var="item" items="${canteenItems}">
                            <li data-aos="fade-left">${item.name} - ${item.price}</li>
                            </c:forEach>
                    </ul>
                </div>

                <div class="form-group" data-aos="zoom-in" data-aos-delay="600">
                    <input type="submit" value="Confirm Ticket" class="btn btn-primary" />
                </div>
            </form>
        </div>


        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>
            AOS.init({
                duration: 1000,
                once: true,
                offset: 100
            });
        </script>
    </body>
</html>
