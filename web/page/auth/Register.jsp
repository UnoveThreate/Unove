<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Sign Up</title>

        <!-- Font Icon -->
        <link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css" />
          
        <!-- Main css -->
        <link rel="stylesheet" href="style.css" />
        <style>
            /* General Styles */
/* General Styles */
body {
    font-family: Arial, sans-serif;
    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
    margin: 0;
    padding: 0;
}

.main {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
}

.container {
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 12px;
    box-shadow: 0 8px 32px rgba(31, 38, 135, 0.37);
    overflow: hidden;
    width: 100%;
    max-width: 900px;
    transition: all 0.3s ease;
}

.container:hover {
    transform: translateY(-5px);
    box-shadow: 0 12px 40px rgba(31, 38, 135, 0.5);
}

.signup-content {
    display: flex;
}


.signup-form {
    flex: 1;
    padding: 40px;
    background: linear-gradient(45deg, #f3e7e9 0%, #e3eeff 99%, #e3eeff 100%);
}

.form-title {
    color: #333;
    font-size: 28px;
    margin-bottom: 30px;
    text-align: center;
    text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
}

.form-group {
    margin-bottom: 25px;
    position: relative;
}

.form-group label {
    position: absolute;
    left: 15px;
    top: 50%;
    transform: translateY(-50%);
    color: #6c63ff;
    transition: all 0.3s ease;
}

.form-group input:not([type="checkbox"]) {
    width: 100%;
    padding: 12px 12px 12px 45px;
    border: 2px solid #ddd;
    border-radius: 25px;
    font-size: 16px;
    transition: all 0.3s ease;
}

.form-group input:focus {
    outline: none;
    border-color: #6c63ff;
    box-shadow: 0 0 10px rgba(108, 99, 255, 0.2);
}

.form-group input:focus + label {
    color: #5a52cc;
    font-size: 12px;
    top: -10px;
    background-color: white;
    padding: 0 5px;
}

.agree-term {
    display: inline-block;
    margin-right: 5px;
}

.label-agree-term {
    font-size: 14px;
    color: #666;
}

.term-service {
    color: #6c63ff;
    text-decoration: none;
    transition: all 0.3s ease;
}

.term-service:hover {
    color: #5a52cc;
    text-decoration: underline;
}

.form-button {
    text-align: center;
}

.form-submit {
    background: linear-gradient(45deg, #6c63ff, #4834d4);
    border: none;
    color: white;
    padding: 12px 40px;
    border-radius: 25px;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 15px rgba(108, 99, 255, 0.3);
}

.form-submit:hover {
    background: linear-gradient(45deg, #5a52cc, #3c2eb0);
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(108, 99, 255, 0.4);
}


.signup-image {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background: linear-gradient(135deg, #e0c3fc 0%, #8ec5fc 100%);
    padding: 40px;
}

.signup-image-link {
    color: #6c63ff;
    font-size: 20px;
    text-decoration: none;
    margin-bottom: 20px;
    transition: all 0.3s ease;
    text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
}

.signup-image-link:hover {
    color: #5a52cc;
    transform: scale(1.05);
}

.signup-image figure {
    margin: 0;
    transition: all 0.3s ease;
}

.signup-image figure:hover {
    transform: scale(1.05);
}

.signup-image img {
    max-width: 100%;
    height: auto;
    border-radius: 12px;
    box-shadow: 0 8px 20px rgba(0,0,0,0.2);
}

/* Responsive Design */
@media (max-width: 768px) {
    .signup-content {
        flex-direction: column;
    }
    
    .signup-form, .signup-image {
        width: 100%;
    }
}


.error-message {
    color: #ff3860;
    font-size: 14px;
    margin-top: 5px;
    text-align: center;
    background-color: rgba(255, 56, 96, 0.1);
    padding: 5px;
    border-radius: 4px;
    transition: all 0.3s ease;
}

.error-message:hover {
    background-color: rgba(255, 56, 96, 0.2);
}
        </style>
    </head>
    <body>
        <div class="main">
            <!-- Sign up form -->
            <section class="signup">
                <div class="container">
                    <div class="signup-content">
                        <div class="signup-form">
                            <h2 class="form-title">Sign up</h2>
                            <form method="POST" class="register-form" id="register-form" action="register">
                                
                                <div class="form-group">
                                    <label for="fullName"><i class="zmdi zmdi-account-box"></i></label>
                                    <input type="text" name="fullName" id="fullName" placeholder="Full Name" required />
                                </div>
                                
                                <div class="form-group">
                                    <label for="username"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                    <input type="text" name="username" id="username" placeholder="Username" required />
                                </div>
                                
                                <div class="form-group">
                                    <label for="email"><i class="zmdi zmdi-email"></i></label>
                                    <input type="email" name="email" id="email" placeholder="Your Email" required />
                                </div>
                                
                                <div class="form-group">
                                    <label for="password"><i class="zmdi zmdi-lock"></i></label>
                                    <input type="password" name="password" id="password" placeholder="Password" required />
                                </div>
                                
                                <div class="form-group">
                                    <label for="confirmPassword"><i class="zmdi zmdi-lock-outline"></i></label>
                                    <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password" required />
                                </div>
                                
                                <div class="form-group">
                                    <input type="checkbox" name="agree-term" id="agree-term" class="agree-term" checked />
                                    <label for="agree-term" class="label-agree-term">
                                        <span><span></span></span>I agree to all statements in
                                        <a href="#" class="term-service">Terms of service</a>
                                    </label>
                                </div>
                                <% 
                            String error = (String)request.getAttribute("error");
                            if (error != null) {
                                out.println("<p style='color:red;'>" + error + "</p>");
                            }
                                %>
                                <div class="form-group form-button">
                                    <input type="submit" name="signup" id="signup" class="form-submit" value="Register" />
                                </div>
                            </form>
                        </div>
                        <div class="signup-image">
                            <a href="#" class="signup-image-link">Welcome to Unove</a>
                            <figure>
                                <img src="images/signup-image.jpg" alt="sign up image" />
                            </figure>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <!-- JS -->
        <script src="vendor/jquery/jquery.min.js"></script>
    </body>
</html>
