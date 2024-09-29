<%-- 
    Document   : ForgetPassword
    Created on : May 18, 2024, 8:12:31 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forget Password</title>

        <style>
         body {
    margin: 0;
    padding: 0;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(270deg, #FFE6E6, #F2D1D1);
    animation: gradient-animation 10s ease infinite;
}

@keyframes gradient-animation {
    0% { background-position: 0% 50%; }
    50% { background-position: 100% 50%; }
    100% { background-position: 0% 50%; }
}

.bg-light {
    background: rgba(255, 255, 255, 0.9);
    border-radius: 10px;
    box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
    transition: transform 0.5s ease; /* Thêm hiệu ứng chuyển động cho .bg-light */
}

.bg-light:hover {
    transform: translateY(-5px); /* Hiệu ứng hover cho .bg-light */
}

h2 {
    font-family: 'Arial', sans-serif;
    font-size: 1.5rem;
    color: #333;
    transition: color 0.3s ease; /* Thêm hiệu ứng chuyển động cho màu chữ */
}

h2:hover {
    color: #ff7e5f; /* Thay đổi màu chữ khi hover */
}

.input-group {
    border-radius: 5px;
    overflow: hidden;
    transition: box-shadow 0.3s ease; /* Thêm hiệu ứng chuyển động cho bóng */
}

.input-group:hover {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2); /* Hiệu ứng bóng khi hover */
}

.input-group-text {
    background: #ff7e5f;
    color: white;
}

.form-control {
    border: 1px solid #ff7e5f;
    border-radius: 0;
    transition: border 0.3s, box-shadow 0.3s; /* Thêm hiệu ứng chuyển động cho viền và bóng */
}

.form-control:focus {
    border: 2px solid #feb47b;
    box-shadow: 0 0 5px rgba(254, 180, 123, 0.5); /* Hiệu ứng bóng khi focus */
}

.btn-primary {
    background-color: #feb47b;
    border: none;
    transition: background-color 0.3s; /* Thêm hiệu ứng chuyển động cho nút */
}

.btn-primary:hover {
    background-color: #ff7e5f; /* Thay đổi màu nền khi hover */
}

/* Thêm hiệu ứng chuyển động cho các nút button */
.btn {
    transition: background-color 0.3s ease, transform 0.2s ease; /* Hiệu ứng chuyển động */
}

/* Hiệu ứng hover cho nút */
.btn:hover {
    background-color: #CDC1FF; /* Thay đổi màu nền khi hover */
    transform: scale(1.05); /* Tăng kích thước khi hover */
}

/* Hiệu ứng cho background gradient */
.bg-light {
    background: linear-gradient(135deg, #E5D9F2, #CDC1FF);
    border-radius: 10px;
    box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
    padding: 2rem;
    transition: background 0.5s ease; /* Hiệu ứng chuyển động cho background */
}

/* Thêm hiệu ứng chuyển động cho các trường input */
.form-control {
    transition: border-color 0.3s ease;
}

/* Hiệu ứng khi focus vào trường input */
.form-control:focus {
    border-color: #feb47b; /* Đổi màu viền khi focus */
    box-shadow: 0 0 5px rgba(254, 180, 123, 0.5); /* Hiệu ứng bóng khi focus */
}


        </style>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script> 

    </head>
    <body>
        <!-- Password Reset 1 - Bootstrap Brain Component -->
        <div class="bg-light py-3 py-md-5">
            <div class="container">
                <div class="row justify-content-md-center">
                    <div class="col-12 col-md-11 col-lg-8 col-xl-7 col-xxl-6">
                        <div class="bg-white p-4 p-md-5 rounded shadow-sm">
                            <div class="row gy-3 mb-5">
                                <div class="col-12">
                                    <h2 class="fs-6 fw-normal text-center text-secondary m-0 px-md-5">Provide the email address associated with your account to recover your password.</h2>
                                </div>
                            </div>
                            <form action="forgetpassword" method="post">
                                <div class="row gy-3 gy-md-4 overflow-hidden">
                                    <div class="col-12">
                                        <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                                        <div class="input-group">
                                            <span class="input-group-text">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
                                                <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z" />
                                                </svg>
                                            </span>
                                            <c:set var="email" value="${requestScope.email}" scope="request"/>

                                            <c:choose>
                                                <c:when test="${not empty email}">
                                                    <input type="email" class="form-control" name="email" id="email" value="${email}" readonly required>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="email" class="form-control" name="email" id="email" required>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                        <c:set var="isExistedEmail" value="${requestScope.isExistedEmail}"></c:set>
                                        <c:set var="sendEmailOk" value="${requestScope.sendEmailOk}"></c:set>
                                        <c:set var="verifyOTPOk" value="${requestScope.verifyOTPOk}"></c:set>
                                        <c:set var="isValidPassword" value="${requestScope.isValidPassword}"></c:set>
                                        <c:set var="confirmPasswordOk" value="${requestScope.confirmPasswordOk}"></c:set>                                        
                                        <c:set var="changePasswordOk" value="${requestScope.changePasswordOk}"></c:set>

                                        <c:if test="${(isExistedEmail != null && isExistedEmail && sendEmailOk != null && sendEmailOk) || (verifyOTPOk != null && !verifyOTPOk)}">

                                            <br>
                                            <label for="OTP" class="form-label"> OTP <span class="text-danger">*</span></label>
                                            <div class="input-group">
                                                <span class="input-group-text">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
                                                    <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z" />
                                                    </svg>
                                                </span>
                                                <input style="width: 80%" type="text" class="form-control" name="OTP" id="OTP" required>
                                                <br>
                                                
                                                 <c:if test="${verifyOTPOk != null && !verifyOTPOk}">                                            
                                                     <div style="color: red">The OTP you entered is incorrect. Please try again.                                                                                  </div>
                                                </c:if>
                                                
                                                <c:if test="${isExistedEmail != null && isExistedEmail && sendEmailOk != null && sendEmailOk}">
                                                    <div style="color: red">We have sent a One-Time Password (OTP) to your email. Please check your inbox and enter the OTP to complete the verification process.</div>
                                                </c:if>
                                                    

                                            </div>   

                                        </c:if>
                                            

                                        <c:if test="${isExistedEmail != null && !isExistedEmail}">
                                            <p style="color: red">Your entered Email does not exist. Please enter Email again.</p>
                                        </c:if>

                                        <c:if test="${sendEmailOk != null && !sendEmailOk}">
                                            <p style="color: red">We encountered an issue while sending the OTP to you. Please try again.</p>
                                        </c:if>
                                            
                                        <c:if test="${(verifyOTPOk != null && verifyOTPOk) || (changePasswordOk != null && !changePasswordOk)}">
                                            <br>
                                            <label for="new-password" class="form-label"> New password <span class="text-danger">*</span></label>
                                            <div class="input-group">
                                                <span class="input-group-text">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
                                                    <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z" />
                                                    </svg>
                                                </span>
                                                <input type="password" class="form-control" name="new-password" id="new-password" required>
                                                <c:if test="${isValidPassword != null && !isValidPassword}">
                                                    <p style="color: red">Your password must...</p>
                                                </c:if>
                                            </div>   
                                            
                                            <br>
                                            <label for="confirm-password" class="form-label"> Confirm password <span class="text-danger">*</span></label>
                                            <div class="input-group">
                                                <span class="input-group-text">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
                                                    <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z" />
                                                    </svg>
                                                </span>
                                                <input type="password" class="form-control" name="confirm-password" id="confirm-password" required>
                                                <c:if test="${isValidPassword != null && isValidPassword && confirmPasswordOk != null && !confirmPassword}">
                                                    <p style="color: red">The confirmation password you entered does not match. Please try again.</p>
                                                </c:if>
                                            </div>   
                                        </c:if>
                                            
                                        <c:if test="${changePasswordOk != null && changePasswordOk}">
                                            <p style="color: red">Change password successfully!</p>
                                        </c:if>

                                    </div>
                                    <div class="col-12">
                                        <div class="d-grid">
                                        <c:choose>
                                            <c:when test="${(verifyOTPOk != null && verifyOTPOk) || (changePasswordOk != null && !changePasswordOk)}">
                                                <button class="btn btn-primary btn-lg" type="submit">Reset Password</button>
                                            </c:when>
                                                
                                            <c:when test="${changePasswordOk != null && changePasswordOk}">
                                                <button class="btn btn-primary btn-lg" type="submit" name="back-to-login">Back to login</button>
                                            </c:when>
                                                
                                            <c:otherwise>
                                                <button class="btn btn-primary btn-lg" type="submit">Next Step</button>
                                            </c:otherwise>                                                
                                                
                                        </c:choose>
                                            
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <div class="row">
                                <div class="col-12">
                                    <hr class="mt-5 mb-4 border-secondary-subtle">
                                    <div class="d-flex gap-4 justify-content-center">
                                        <a href="#!" class="link-secondary text-decoration-none">Log In</a>
                                        <a href="#!" class="link-secondary text-decoration-none">Register</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>    
    </body>
</html>
