<%-- 
    Document   : CinemaDetail
    Created on : Sep 28, 2024, 8:36:04 PM
    Author     : Kaan
--%>

<%@page import="model.Cinema"%>
<%@page import="model.CinemaReview"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List" %>

<%
    Cinema cinema = (Cinema) request.getAttribute("cinema");
    List<CinemaReview> reviews = (List<CinemaReview>) request.getAttribute("reviews");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết Rạp Chiếu Phim</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        h1 {
            color: #333;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            margin-bottom: 20px;
            border: 1px solid #ccc;
            padding: 10px;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <h1>Thông tin Rạp Chiếu Phim</h1>
    <% if (cinema != null) { %>
        <p><strong>Địa chỉ:</strong> <%= cinema.getAddress() %></p>
        <p><strong>Tỉnh:</strong> <%= cinema.getProvince() %>, <strong>Quận:</strong> <%= cinema.getDistrict() %>, <strong>Xã:</strong> <%= cinema.getCommune() %></p>
    <% } else { %>
        <p>Không tìm thấy thông tin rạp chiếu phim.</p>
    <% } %>

    <h2>Đánh giá</h2>
    <ul>
        <% if (reviews != null && !reviews.isEmpty()) { %>
            <% for (CinemaReview review : reviews) { %>
                <li>
                    <strong>Đánh giá:</strong> <%= review.getRating() %> / 5
                    <p><strong>Nội dung:</strong> <%= review.getContent() %></p>
                    <p><em>Ngày tạo: <%= review.getTimeCreated() %></em></p>
                </li>
            <% } %>
        <% } else { %>
            <li>Chưa có đánh giá nào.</li>
        <% } %>
    </ul>
</body>
</html>

