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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <!-- Inline CSS for validation error messages -->
        <style>
            .validation-error {
                color: #dc3545;
                font-size: 0.875em;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <!-- Success Message -->
            <c:if test="${not empty message}">
                <div class="alert alert-success">
                    ${message}
                    <form action="${pageContext.request.contextPath}/Unove/display" method="get" class="mt-2">
                        <button type="submit" class="btn btn-primary">Click here to return to the user profile</button>
                    </form>
                </div>
            </c:if>

            <!-- Registration Form -->
            <c:if test="${empty message}">
                <h1>Register to Become an Owner</h1>
                <form action="${pageContext.request.contextPath}/registerAsOwner" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                    <div class="form-group">
                        <label for="fullName">Full Name</label> 
                        <input type="text" class="form-control" id="fullName" name="fullName" value="${fullName}" readonly>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email" value="${email}" readonly>
                    </div>
                    <div class="form-group">
                        <label for="taxNumber">Tax ID</label>
                        <input type="text" class="form-control" id="taxNumber" name="taxNumber" 
                               pattern="\d{10,13}" title="Tax ID must be between 10 and 13 digits" required>
                        <small class="form-text text-muted">Enter your 10-13 digit Tax ID number.</small>
                        <div class="validation-error" id="taxNumberError"></div>
                    </div>
                    <div class="form-group">
                        <label for="businessLicenseFile">Upload Business License (PDF, JPG, PNG)</label>
                        <input type="file" class="form-control" id="businessLicenseFile" name="businessLicenseFile" 
                               accept=".pdf, .jpg, .jpeg, .png" required>
                    </div>
                    <!-- Display error messages if any -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <button type="submit" class="btn btn-primary">
                        <c:out value="${buttonLabel != null ? buttonLabel : 'Submit Request'}"/>
                    </button>
                </form>
            </c:if>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        
        <!-- JavaScript for client-side validation -->
        <script>
            function validateForm() {
                const taxNumber = document.getElementById("taxNumber").value;
                const taxNumberError = document.getElementById("taxNumberError");

                // Clear previous error messages
                taxNumberError.textContent = "";

                // Validate Tax ID format
                const taxIdPattern = /^\d{10,13}$/;
                if (!taxIdPattern.test(taxNumber)) {
                    taxNumberError.textContent = "Tax ID must be between 10 and 13 digits.";
                    return false;
                }
                return true;
            }
        </script>
    </body>
</html>
