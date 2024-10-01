<%-- 
    Document   : Login
    Created on : May 18, 2024, 1:13:09 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập</title>

    <!-- Link to Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: 'Arial', sans-serif;
        }

        body {
    background: linear-gradient(-45deg, #BBD2C5, #536976, #fbc7d4, #E5D9F2, #9796f0, #acb6e5, #E4E5E6,#86A8E7,#E4B1F0,#B7E0FF,#CDC1FF); 
    background-size: 400% 400%;
    animation: gradient 3s ease infinite; 
    display: flex;
    flex-direction: column;
}

@keyframes gradient {
    0% {
        background-position: 0% 50%;
    }
    50% {
        background-position: 100% 50%;
    }
    100% {
        background-position: 0% 50%;
    }
}
        .main-container {
            flex: 1 0 auto;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .login-container {
            background: rgba(255, 255, 255, 0.9);
            border-radius: 20px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
            overflow: hidden;
            width: 100%;
            max-width: 1000px;
        }

        .login-form {
            padding: 40px;
        }

        .login-image {
            background: url('https://i.pinimg.com/564x/06/77/b7/0677b7e8b114bf017dd901385c111833.jpg') center/cover;
            min-height: 300px;
            border-radius: 20px 0 0 20px;
        }

        .form-control {
            border-radius: 10px;
            border: none;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .btn-custom {
            background: linear-gradient(45deg, #007bff, #00bfff);
            border: none;
            border-radius: 10px;
            color: white;
            padding: 10px 20px;
            font-weight: bold;
            transition: all 0.3s ease;
        }

        .btn-custom:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
        }

        .footer {
            background: rgba(0, 0, 0, 0.8);
            color: white;
            padding: 20px 0;
            flex-shrink: 0;
            width: 100%;
            position: relative;
            bottom: 0;
        }

        .footer a {
            color: #00bfff;
            transition: color 0.3s ease;
        }

        .footer a:hover {
            color: #007bff;
        }

        .footer .social-icons a {
            margin: 0 10px;
            color: #00bfff;
            transition: color 0.3s ease;
        }

        .footer .social-icons a:hover {
            color: #007bff;
        }

        @media (max-width: 768px) {
            .login-image {
                border-radius: 20px 20px 0 0;
                min-height: 200px;
            }
            .login-container {
                max-width: 90%;
            }
        }
    </style>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
    <div class="main-container">
        <div class="login-container row g-0">
            <div class="col-md-6 login-image"></div>
            <div class="col-md-6 login-form">
                <h2 class="text-center mb-4">Đăng Nhập</h2>
                <form action="login" method="post">
                    <div class="mb-3">
                        <input type="text" class="form-control" name="username-email" placeholder="Tên đăng nhập hoặc email" required>
                    </div>
                    <div class="mb-3">
                        <input type="password" class="form-control" name="password" placeholder="Mật khẩu" required>
                    </div>
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="remember">
                            <label class="form-check-label" for="remember">Ghi nhớ tôi</label>
                        </div>
                        <a href="/Unove/forgetpassword" class="text-primary">Quên mật khẩu?</a>
                    </div>
                    <button type="submit" class="btn btn-custom w-100">Đăng Nhập</button>
                    <p class="text-center mt-3">Chưa có tài khoản? <a href="/Unove/register" class="text-primary">Đăng ký</a></p>
                </form>
            </div>
        </div>
    </div>

    <footer class="footer text-center">
        <div class="container">
            <span>Bản quyền © 2024 Unove Cinema.</span>
            <div class="mt-2 social-icons">
                <a href="#!" class="me-3"><i class="fab fa-facebook-f"></i></a>
                <a href="#!" class="me-3"><i class="fab fa-twitter"></i></a>
                <a href="#!" class="me-3"><i class="fab fa-google"></i></a>
                <a href="#!"><i class="fab fa-linkedin-in"></i></a>
            </div>
        </div>
    </footer>
</body>

</html>
