<%@page import="util.RouterURL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Ticket Confirmation Result</title>
        <style>/* style.css */
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f0f4f8;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .container {
                background-color: #ffffff;
                padding: 40px;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                max-width: 500px;
                width: 100%;
            }

            h2 {
                text-align: center;
                color: #333333;
                font-size: 24px;
                margin-bottom: 20px;
            }

            .alert {
                padding: 15px;
                border-radius: 8px;
                font-size: 16px;
                text-align: center;
            }

            .alert-info {
                background-color: #e7f3fe;
                color: #31708f;
                border: 1px solid #bce8f1;
            }

            p {
                text-align: center;
                font-size: 16px;
                color: #555555;
                margin-top: 15px;
            }

            a.btn {
                display: inline-block;
                text-decoration: none;
                color: #ffffff;
                background-color: #007bff;
                padding: 12px 20px;
                border-radius: 8px;
                font-weight: bold;
                font-size: 16px;
                text-align: center;
                transition: background-color 0.3s ease, box-shadow 0.3s ease;
                margin-top: 20px;
            }

            a.btn:hover {
                background-color: #0056b3;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            a.btn:focus {
                outline: none;
            }

            a.btn:active {
                background-color: #004b9c;
                box-shadow: none;
            }

            @media (max-width: 600px) {
                .container {
                    padding: 20px;
                    margin: 10px;
                }
            }
        </style>
    </head>
    <body>

        <div class="container">
            <h2>Confirmation Result</h2>

            <c:if test="${not empty message}">
                <div class="alert alert-info">
                    ${message}
                </div>
            </c:if>

            <c:choose>
                <c:when test="${message == 'Xác nhận vé đặt thành công.'}">
                    <p>Thank you! The ticket has been successfully confirmed.</p>
                </c:when>
                <c:otherwise>
                    <p>There was an issue with the confirmation. Please try again or contact support.</p>
                </c:otherwise>
            </c:choose>

                    <form action="<%= RouterURL.LANDING_PAGE%>" method="get">
                        <button type="submit" class="home" >
                           Back Home
                        </button>
                    </form>

            </div>
        </div>

    </body>
</html>
