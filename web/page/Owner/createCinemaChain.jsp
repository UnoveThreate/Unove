<%-- 
    Document   : createCinemaChain
    Created on : 28 thg 9, 2024, 05:39:44
    Author     : nguyendacphong
--%>

<%@page import="util.RouterURL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Create Cinema Chain</title>
    </head>
    <body>
        <h2>Create Cinema Chain</h2>
        <form action="<%= RouterURL.OWNER_PAGE%>" method="POST" enctype="multipart/form-data">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required><br>

            <label for="information">Information:</label>
            <textarea id="information" name="information" required></textarea><br>

            <input type="file" name="avatar" />

            <input type="submit" value="Create Cinema Chain">
        </form>
    </body>
</html>