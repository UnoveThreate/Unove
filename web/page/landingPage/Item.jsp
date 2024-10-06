<%-- 
    Document   : Item
    Created on : Oct 5, 2024, 8:25:07 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Chọn Đồ Ăn</h1>
        <form action="SelectFoodServlet" method="post">
            <label for="canteenItem">Chọn Món:</label>
            <select id="canteenItem" name="canteenItemID">
                <option value="1">Popcorn Large - $4.50</option>
                <option value="2">Soda Medium - $3.00</option>
                <option value="3">Nachos - $5.00</option>
                <option value="4">Candy Bar - $2.00</option>
                <option value="5">Ice Cream - $3.50</option>
                <option value="6">Hot Dog - $4.00</option>
                <option value="7">Popcorn Small - $3.00</option>
                <option value="8">Water Bottle - $1.50</option>
                <option value="9">Chocolate - $2.50</option>
            </select>
            <br>
            <label for="quantity">Số Lượng:</label>
            <input type="number" id="quantity" name="quantity" min="1" value="1" required>
            <br>
            <button type="submit">Mua</button>
        </form>
    </body>
</html>
