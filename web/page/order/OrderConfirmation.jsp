<%-- 
    Document   : OderConfirmation
    Created on : Oct 6, 2024, 4:36:53 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!--link font icon :-->  
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!--link boostrap :-->  
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

        <style>
            body {
                background-color: #f4f4f4;
                font-family: Arial, sans-serif;
            }
            .header {
                background-color: #dc3545;
                color: white;
                padding: 20px;
                text-align: center;
                border-radius: 0 0 10px 10px;
            }
            .order-details {
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
                margin-top: 20px;
            }
            .total-price {
                font-weight: bold;
                font-size: 1.5em;
                color: #dc3545;
                margin-top: 10px;
            }
            .btn-custom {
                background-color: #dc3545;
                color: white;
                border: none;
            }
            .btn-custom:hover {
                background-color: #c82333;
            }
            .footer {
                background-color: #dc3545;
                color: white;
                padding: 20px 0;
                margin-top: 40px;
            }
            .footer h5 {
                margin: 10px 0;
            }
            .footer a {
                color: white;
                text-decoration: none;
            }
            .footer a:hover {
                text-decoration: underline;
            }
        </style>

    </head>
    <body>
        <div class="header">
            <h2>Your Order</h2>
        </div>

        <div class="container mt-4">
            <div class="order-details">
                <p>${orderDetails}</p>
                <p class="total-price">Total: $${totalPrice}</p>
            </div>

            <div class="button-container d-flex justify-content-center mt-4">
                <button type="button" class="btn btn-outline-light me-3">
                    <a href="page/landingPage/Item.jsp" style="text-decoration: none; color: black;">Go Back</a>
                </button>
                <button type="submit" class="btn btn-custom">Done</button>
            </div>
        </div>


        <!--footer-->
        <footer class="footer" style="background-color: whitesmoke; color: gray; padding: 20px 0;">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-4 text-center">
                        <h5>Contact information</h5>
                        <p>Email: Unove@gmail.com</p>
                        <p>Phone: (123) 456-7890</p>
                    </div>
                    <div class="col-md-4 text-center">
                        <h5>Quick Links</h5>
                        <ul style="list-style: none; padding: 0;">
                            <li><a href="#" style="color: gray; text-decoration: none;">Home page</a></li>
                            <li><a href="#" style="color: gray; text-decoration: none;">Movie is showing</a></li>
                            <li><a href="#" style="color: gray; text-decoration: none;">Book tickets</a></li>
                            <li><a href="#" style="color: gray; text-decoration: none;">Contact</a></li>
                        </ul>
                    </div>
                    <div class="col-md-4 text-center">
                        <h5>About us</h5>
                        <img src="http://localhost:8080/Unove/page/image/logoHeader.png" style="width:80px;height:80px">
                        <p>We provide fast and convenient online movie ticket booking service.</p>
                    </div>
                </div>
                <div class="text-center" style="margin-top: 20px;">
                    <p>&copy;2024 Your Company. All rights reserved.</p>
                </div>
            </div>
        </footer>
    </body>
</html>
