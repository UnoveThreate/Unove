<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Confirm Ticket</title>
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

            .form-group {
                margin-bottom: 20px;
            }

            label {
                font-size: 14px;
                color: #555555;
                margin-bottom: 8px;
                display: block;
            }

            input[type="text"] {
                width: 100%;
                padding: 12px 15px;
                border: 1px solid #dddddd;
                border-radius: 8px;
                background-color: #f9fafb;
                transition: border-color 0.3s ease;
                font-size: 14px;
                color: #333333;
            }

            input[type="text"]:focus {
                border-color: #007bff;
                outline: none;
            }

            input[type="submit"] {
                width: 100%;
                padding: 12px 15px;
                background-color: #007bff;
                color: #ffffff;
                border: none;
                border-radius: 8px;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
                transition: background-color 0.3s ease, box-shadow 0.3s ease;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            input[type="submit"]:focus {
                outline: none;
            }

            input[type="submit"]:active {
                background-color: #004b9c;
                box-shadow: none;
            }

            .btn-primary {
                display: inline-block;
                text-align: center;
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
            <h2>Confirm Ticket Details</h2>

            <form action="${pageContext.request.contextPath}/order/confirm" method="post">
                <input type="hidden" name="orderID" value="${orderID}" />
                <input type="hidden" name="userID" value="${userID}" />

                <div class="form-group">
                    <label for="code">OrderID: </label>
                    <input type="text" id="orderID" name="orderID" value="${orderID}" readonly />
                     <label for="code">Code: </label>
                    <input type="text" id="code" name="code" value="${code}" readonly />
                </div>

                <div class="form-group">
                    <input type="submit" value="Confirm Ticket" class="btn btn-primary" />
                </div>
            </form>
        </div>

    </body>
</html>
