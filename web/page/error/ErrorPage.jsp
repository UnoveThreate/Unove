<%-- 
    Document   : ErrorPage
    Created on : Sep 28, 2024, 10:33:18 AM
    Author     : Per
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Error Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f8f8;
                color: #333;
                margin: 0;
                padding: 0;
            }
            .container {
                width: 100%;
                max-width: 600px;
                margin: 50px auto;
                padding: 20px;
                background-color: #fff;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            h1 {
                color: #e74c3c;
            }
            p {
                margin: 10px 0;
            }
            .details {
                margin-top: 20px;
                padding: 10px;
                background-color: #f1f1f1;
                border: 1px solid #ccc;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Something Went Wrong</h1>
            <p>We're sorry, but an unexpected error has occurred.</p>
            <div class="details">
                <h2>OOps!</h2>
                <p><strong>Message:</strong> 404 !</p>
               
            </div>
            <p><a href="/movie">Return to Home Page</a></p>
        </div>
    </body>
</html>