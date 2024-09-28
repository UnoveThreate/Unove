<%-- 
    Document   : manageCinemaChain
    Created on : 28 thg 9, 2024, 05:40:19
    Author     : nguyendacphong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Cinema Chain</title>
</head>
<body>
    <h2>Manage Cinema Chain</h2>
    <p>Name: ${cinemaChain.name}</p>
    <p>Information: ${cinemaChain.information}</p>

    <!-- Tạo các chức năng CRUD cho Cinema tại đây -->

    <a href="createCinema.jsp">Add New Cinema</a>
</body>
</html>
