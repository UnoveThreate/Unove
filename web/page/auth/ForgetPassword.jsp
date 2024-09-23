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
