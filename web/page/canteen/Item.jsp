<%-- 
    Document   : Item
    Created on : Oct 5, 2024, 8:25:07 PM
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
            .menu {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
            }

            .button-container {
                display: flex;
                justify-content: center; /* Căn giữa theo chiều ngang */
                align-items: center; /* Căn giữa theo chiều dọc nếu cần */
            }
        </style>
    </head>
    <body>
        <div style="background-color:whitesmoke">
            <h1 style="text-align: center;color: red">Food or Drink:</h1>
        </div>
        <!--        <form action="SelectFoodServlet" method="post">
                    <label for="canteenItem">Chọn Món:</label>
                    <select id="canteenItem" name="canteenItemID">
                        <option value="1">Popcorn Large - $4.50</option>
                        <option value="2">Soda Medium - $3.00</option>
                        <option value="3">Nachos - $5.00</option>
                        <option value="4">Candy Bar - $2.00</option>
                        <option value="5">Ice Cream - $3.50</option>
                        <option value="6">Hot Dog - $4.00</option>
                        <option value="7">Popcorn Small - $3.00</option>
                        <option value="8">Water Bottle - $1.50</option>
                        <option value="9">Chocolate - $2.50</option>
                    </select>
                    <br>
                    <label for="quantity">Số Lượng:</label>
                    <input type="number" id="quantity" name="quantity" min="1" value="1" required>
                    <br>
                    <button type="submit">Mua</button>
                </form>-->

        <form action="${pageContext.request.contextPath}/SelectFoodServlet" method="post">
            <div class="menu" style="margin-left:150px">
                <div class="card" style="width: 18rem;" data-value="1">
                    <img src="https://media.istockphoto.com/photos/large-buttered-popcorn-picture-id133920708?k=6&m=133920708&s=612x612&w=0&h=cAz5k8QskQ7mj4BHRI_Hit_rRii3CY-QN7bLgQW_Gbs=" style="height:280px" class="card-img-top" alt="Popcorn Large">
                    <div class="card-body">
                        <h5 class="card-title">Popcorn Large</h5>
                        <p class="card-text" style="color:red">$4.50</p>
                        <input type="number" min="0" placeholder="Quantity" class="form-control mb-2" name="quantity_1">             
                        <input type="hidden" name="canteenItemID" value="1">
                        <button class="btn btn-warning">Select</button>
                    </div>
                </div>

                <div class="card" style="width: 18rem;" data-value="2">
                    <img src="https://images.heb.com/is/image/HEBGrocery/002102899?fit=constrain,1&wid=800&hei=800&fmt=jpg&qlt=85,0&resMode=sharp2&op_usm=1.75,0.3,2,0" class="card-img-top" alt="Soda Medium">
                    <div class="card-body">
                        <h5 class="card-title">Soda Medium</h5>
                        <p class="card-text" style="color:red">$3.00</p>
                        <input type="number" min="0" placeholder="Quantity" class="form-control mb-2" name="quantity_2"> 
                        <input type="hidden" name="canteenItemID" value="2">
                        <button class="btn btn-warning">Select</button>
                    </div>
                </div>

                <div class="card" style="width: 18rem;" data-value="3">
                    <img src="https://thumbs.dreamstime.com/b/nacho-tray-melted-cheese-black-plastic-round-yellow-nachos-dip-isolated-white-background-179343942.jpg" style="height:280px" class="card-img-top" alt="Nachos">
                    <div class="card-body">
                        <h5 class="card-title">Nachos</h5>
                        <p class="card-text" style="color:red">$5.00</p>
                        <input type="number" min="0" placeholder="Quantity" class="form-control mb-2" name="quantity_3">           
                        <input type="hidden" name="canteenItemID" value="3">
                        <button class="btn btn-warning">Select</button>
                    </div>
                </div>

                <div class="card" style="width: 18rem;" data-value="4">
                    <img src="https://www.foodandwine.com/thmb/VOCShOG5uYdtSsDtT88m-M8WYt0=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/history-of-american-candy-bars-4-FT-BLOG1018-c717e0e2e09b4b509791cc08afc1ee26.jpg" style="height:280px" class="card-img-top" alt="Candy Bar">
                    <div class="card-body">
                        <h5 class="card-title">Candy Bar</h5>
                        <p class="card-text" style="color:red">$2.00</p>
                        <input type="number" min="0" placeholder="Quantity" class="form-control mb-2" name="quantity_4">        
                        <input type="hidden" name="canteenItemID" value="4">
                        <button class="btn btn-warning">Select</button>
                    </div>
                </div>

                <div class="card" style="width: 18rem;" data-value="5">
                    <img src="https://insanelygoodrecipes.com/wp-content/uploads/2022/10/Homemade-Sweet-Ice-Cream-with-Different-Flavors-1024x1024.jpg" class="card-img-top" alt="Ice Cream">
                    <div class="card-body">
                        <h5 class="card-title">Ice Cream</h5>
                        <p class="card-text" style="color:red">$3.50</p>
                        <input type="number" min="0" placeholder="Quantity" class="form-control mb-2" name="quantity_5">          
                        <input type="hidden" name="canteenItemID" value="5">
                        <button class="btn btn-warning">Select</button>
                    </div>
                </div>

                <div class="card" style="width: 18rem;" data-value="6">
                    <img src="https://potatorolls.com/wp-content/uploads/2020/10/Basic-Hot-Dogs.jpg" style="height:287px" class="card-img-top" alt="Hot Dog">
                    <div class="card-body">
                        <h5 class="card-title">Hot Dog</h5>
                        <p class="card-text" style="color:red">$4.00</p>
                        <input type="number" min="0" placeholder="Quantity" class="form-control mb-2" name="quantity_6">          
                        <input type="hidden" name="canteenItemID" value="6">
                        <button class="btn btn-warning">Select</button>
                    </div>
                </div>

                <div class="card" style="width: 18rem;" data-value="7">
                    <img src="https://thumbs.dreamstime.com/b/red-stripped-popcorn-small-bucket-overflowing-popcorn-red-stripped-popcorn-small-bucket-overflowing-popcorn-122720918.jpg" style="height:287px" class="card-img-top" alt="Popcorn Small">
                    <div class="card-body">
                        <h5 class="card-title">Popcorn Small</h5>
                        <p class="card-text" style="color:red">$3.00</p>
                        <input type="number" min="0" placeholder="Quantity" class="form-control mb-2" name="quantity_7">          
                        <input type="hidden" name="canteenItemID" value="7">
                        <button class="btn btn-warning">Select</button>
                    </div>
                </div>

                <div class="card" style="width: 18rem;" data-value="8">
                    <img src="https://truongphatdat.com/wp-content/uploads/2019/12/Dasani-350ml.jpg" class="card-img-top" alt="Water Bottle">
                    <div class="card-body">
                        <h5 class="card-title">Water Bottle</h5>
                        <p class="card-text" style="color:red">$1.50</p>
                        <input type="number" min="0" placeholder="Quantity" class="form-control mb-2" name="quantity_8">          
                        <input type="hidden" name="canteenItemID" value="8">
                        <button class="btn btn-warning">Select</button>
                    </div>
                </div>

                <div class="card" style="width: 18rem;" data-value="9">
                    <img src="https://images-na.ssl-images-amazon.com/images/I/818Xz4cHOqL._SL1500_.jpg" class="card-img-top" alt="Chocolate">
                    <div class="card-body">
                        <h5 class="card-title">Chocolate</h5>
                        <p class="card-text" style="color:red">$2.50</p>
                        <input type="number" min="0" placeholder="Quantity" class="form-control mb-2" name="quantity_9">      
                        <input type="hidden" name="canteenItemID" value="9">
                        <button class="btn btn-warning">Select</button>
                    </div>
                </div>
            </div>
            <div class="button-container" style="display: flex">
                <button type="button" class="btn btn-outline-secondary" style="margin-right: 50px;">No need and continue</button>
                <button type="submit" class="btn btn-outline-danger" style="text-align: center">Done</button>
            </div>
        </form>
        <br>

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
