<%@page import="util.RouterURL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Create Cinema </title>
    </head>
    <body>


        <form action="<%= RouterURL.OWNER_CREATE_CINEMA%>" method="POST">




            <!-- Province -->
            <label for="province">Province:</label>
            <input type="text" id="province" name="province" required><br><br>

            <!-- District -->
            <label for="district">District:</label>
            <input type="text" id="district" name="district" required><br><br>

            <!-- Commune -->
            <label for="commune">Commune:</label>
            <input type="text" id="commune" name="commune" required><br><br>

            <!-- Address -->
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" required><br><br>

            <!-- Submit Button -->
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
