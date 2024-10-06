<%-- 
    Document   : ownerDashBoard
    Created on : 1 thg 10, 2024, 19:19:07
    Author     : nguyendacphong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="navbar.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="util.Role"%>
<%@page import="model.CinemaChain"%>
<%@page import="util.RouterURL"%> <%-- Import RouterURL để sử dụng --%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bảng Điều Khiển Chủ Sở Hữu</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-center">Bảng Điều Khiển Chủ Sở Hữu</h2>
            <div class="text-center mt-4">
                <a href="<%= RouterURL.MANAGE_CINEMA%>">Quản lí chuỗi rạp</a> <%-- Cập nhật đường dẫn --%>
            </div>

            <!-- Optional: Display additional information or actions -->
            <div class="mt-4">
                <h4>Thông tin rạp phim của bạn:</h4>
                <!-- You can loop through and display information about the cinema chains here -->
                <c:forEach var="cinemaChain" items="${cinemaChains}">
                    <div class="card mb-3">
                        <div class="card-body">
                            <h5 class="card-title">${cinemaChain.name}</h5>

                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
