

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="changPasswordCss.css"/>
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    </head>
   <body>
       
       
    <div class="container">
        
        <h2>Change Password</h2>
        
        <form action="${pageContext.request.contextPath}/changPasswordServlet" method="post" onsubmit="return validateForm()">
           
            
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
