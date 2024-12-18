<%-- 
    Document   : createCinemaChain
    Created on : 28 thg 9, 2024, 05:39:44
    Author     : nguyendacphong
--%>

<%@page import="java.util.List"%>
<%@page import="util.RouterURL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Create Cinema Chain</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                color: #333;
                line-height: 1.6;
            }
            .container {
                max-width: 600px;
                margin: 80px auto;
                padding: 40px;
                background-color: rgba(255, 255, 255, 0.95);
                border-radius: 10px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            }
            h2 {
                color: #2c3e50;
                text-align: center;
                margin-bottom: 30px;
            }
            label {
                font-weight: 600;
                margin-top: 10px;
            }
            input[type="file"] {
                margin-top: 10px;
                margin-bottom: 20px;
            }
            .btn-primary {
                background-color: #3498db;
                border: none;
                padding: 12px 20px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            .btn-primary:hover {
                background-color: #2980b9;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Create Cinema Chain</h2>
            <form action="<%= RouterURL.MANAGE_CINEMA%>" method="POST" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" class="form-control" id="name" name="name" required>
                </div>

                <div class="form-group">
                    <label for="information">Information:</label>
                    <textarea class="form-control" id="information" name="information" rows="4" required></textarea>
                </div>

                <div class="form-group">
                    <label for="avatar">Upload Avatar:</label>
                    <input type="file" class="form-control-file" id="avatar" name="avatar">
                </div>

                <button type="submit" class="btn btn-primary btn-block">Create Cinema Chain</button>
            </form>
        </div>

    
    </body>
</html>
