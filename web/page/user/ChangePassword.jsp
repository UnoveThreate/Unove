


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="UserInfoCss/changPasswordCss.css"/>
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            /* General Body Styling */
body {
    font-family: 'Poppins', sans-serif;
    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
    margin: 0;
    padding: 0;
    color: #333;
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}

.container {
    max-width: 500px;
    width: 90%;
    padding: 40px;
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 20px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
    backdrop-filter: blur(10px);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.container:hover {
    transform: translateY(-5px);
    box-shadow: 0 15px 35px rgba(0, 0, 0, 0.15);
}

h2 {
    color: #3a7bd5;
    font-size: 32px;
    font-weight: 700;
    text-align: center;
    margin-bottom: 30px;
    text-transform: uppercase;
    letter-spacing: 2px;
    position: relative;
}

h2::after {
    content: '';
    display: block;
    width: 50px;
    height: 3px;
    background: #3a7bd5;
    margin: 10px auto 0;
}

.form-group {
    margin-bottom: 25px;
    position: relative;
}

.form-group label {
    font-size: 16px;
    color: #555;
    display: flex;
    align-items: center;
    margin-bottom: 8px;
    transition: color 0.3s ease;
}

.form-group label i {
    margin-right: 10px;
    color: #3a7bd5;
    font-size: 18px;
}

input[type="password"] {
    width: 100%;
    padding: 15px;
    border: 2px solid #e0e0e0;
    border-radius: 10px;
    font-size: 16px;
    box-sizing: border-box;
    transition: all 0.3s ease;
    background-color: rgba(255, 255, 255, 0.8);
}

input[type="password"]:focus {
    border-color: #3a7bd5;
    outline: none;
    box-shadow: 0 0 10px rgba(58, 123, 213, 0.3);
}

button {
    width: 100%;
    background: linear-gradient(135deg, #3a7bd5, #00d2ff);
    color: #ffffff;
    padding: 15px;
    border: none;
    border-radius: 10px;
    font-size: 18px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    text-transform: uppercase;
    letter-spacing: 1px;
    box-shadow: 0 5px 15px rgba(58, 123, 213, 0.3);
}

button:hover {
    background: linear-gradient(135deg, #00d2ff, #3a7bd5);
    transform: translateY(-3px);
    box-shadow: 0 8px 20px rgba(58, 123, 213, 0.4);
}

button:active {
    transform: translateY(-1px);
}

#error-message {
    font-size: 14px;
    color: #ff4757;
    display: block;
    margin-top: 10px;
    text-align: center;
    font-weight: 500;
}

@media (max-width: 768px) {
    .container {
        padding: 30px;
    }

    h2 {
        font-size: 28px;
    }

    input[type="password"] {
        font-size: 14px;
        padding: 12px;
    }

    button {
        font-size: 16px;
        padding: 12px;
    }
}

        </style>
    </head>
   <body>
       
       
    <div class="container">
        
        <h2>Change Password</h2>
        
        <form action="/Unove/change" method="post" onsubmit="return validateForm()">
           
            
            <div class="form-group">
                <label for="current-password">
                                        <i class="fas fa-user-lock"></i>   Current Password:</label>
                <input type="password" id="current-password" name="current-password" required>
            </div>
            
            
            <div class="form-group">
                <label for="new-password">
                    <i class="fas fa-user-edit"></i>
                    New Password:</label>
                <input type="password" id="new-password" name="new-password" required>
            </div>
            
            
            <div class="form-group">
                <label for="confirm-password">
                <i class="fas fa-user-check"></i>     Confirm Password:</label>
             
                <input type="password" id="confirm-password" name="confirm-password" required>
                <span id="error-message" style="color: red;"></span>
            </div>
            
            
            <button type="submit"><i class="fas fa-redo"></i> Change Password</button>
        </form>


    </div>

    <script>
        function validateForm() {
            var newPassword = document.getElementById("new-password").value;
            var confirmPassword = document.getElementById("confirm-password").value;

            if (newPassword !== confirmPassword) {
                document.getElementById("error-message").innerText = "Passwords do not match!";
                return false;
            }
            return true;
        }
    </script>
</body>
</html>
