<%-- 
    Document   : OwnerRegist
    Created on : Oct 19, 2024, 10:05:55 AM
    Author     : Kaan
--%>

<%@page import="util.RouterURL"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Owner Registration Request</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1>Register to Become an Owner</h1>
        
        <!-- Display error message if exists -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                ${error}
            </div>
        </c:if>

        <form action="<%= request.getContextPath() %>/registerAsOwner" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input type="text" class="form-control" id="fullName" name="fullName" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="cinemaName">Cinema Name</label>
                <input type="text" class="form-control" id="cinemaName" name="cinemaName" required>
            </div>
            <div class="form-group">
                <label for="cinemaAddress">Cinema Address</label>
                <input type="text" class="form-control" id="cinemaAddress" name="cinemaAddress" required>
            </div>
            <div class="form-group">
                <label for="businessLicenseNumber">Business License Number</label>
                <input type="text" class="form-control" id="businessLicenseNumber" name="businessLicenseNumber" required>
            </div>
            <div class="form-group">
                <label for="businessLicenseFile">Upload Business License</label>
                <input type="file" class="form-control" id="businessLicenseFile" name="businessLicenseFile" required>
            </div>
            <button type="submit" class="btn btn-primary">Submit Request</button>
        </form>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
