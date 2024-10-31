<%-- 
    Document   : updateCinemaChain
    Created on : 8 thg 10, 2024, 02:52:29
    Author     : nguyendacphong
--%>
<%-- 
    Document   : updateCinemaChain
    Created on : 07 thg 10, 2024
    Author     : nguyendacphong
--%>

<%@page import="util.RouterURL"%>
<%@page import="model.CinemaChain"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update Cinema Chain</title>
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
            <h2>Update Cinema Chain</h2>
            <form action="<%= RouterURL.OWNER_UPDATE_CINEMACHAIN%>" method="POST" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" class="form-control" id="name" name="name" value="<%= ((CinemaChain) request.getAttribute("cinemaChain")).getName()%>" required>
                </div>

                <div class="form-group">
                    <label for="information">Information:</label>
                    <textarea class="form-control" id="information" name="information" rows="4" required><%= ((CinemaChain) request.getAttribute("cinemaChain")).getInformation()%></textarea>
                </div>

                <div class="form-group">
                    <label for="avatar">Current Avatar:</label><br>
                    <img src="<%= ((CinemaChain) request.getAttribute("cinemaChain")).getAvatarURL()%>" alt="Cinema Chain Avatar" style="max-width: 100%; height: auto; margin-bottom: 10px;">
                </div>

                <div class="form-group">
                    <label for="avatar">Upload New Avatar (optional):</label>
                    <input type="file" class="form-control-file" id="avatar" name="avatar">
                </div>

                <button type="submit" class="btn btn-primary btn-block">Update Cinema Chain</button>
            </form>
        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
