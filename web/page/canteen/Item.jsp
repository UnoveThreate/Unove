<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!--        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">-->
        <!-- Slick Carousel -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>


            .total-price {
                font-weight: bold;
                margin-top: 20px;
                font-size: 1.5em; /* Kích cỡ chữ lớn hơn */
                color: red; /* Màu chữ đỏ */
            }

        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center" style="text-align:center">Canteen Items</h1>

            <c:if test="${not empty message}">
                <div class="alert alert-warning">${message}</div>
            </c:if>       
            <form action="submitCanteenItems" method="post">
                <div class="row" style="display: flex; flex-wrap: wrap; text-align: center">
                    <c:forEach var="item" items="${canteenItemList}">
                        <div class="col-md-4 mb-4" style="flex: 0 0 33.33%;">
                            <div class="card" style="margin: auto;">
                                <img src="${item.imageURL}" style="width: 150px; height: 180px" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h2 class="card-title">${item.name}</h2>
                                    <p class="card-text" style="color: red">Price: ${item.price} VNĐ</p>
                                    <input type="number" min="0" value="0" class="quantity" data-price="${item.price}" 
                                           name="quantity_${item.canteenItemID}" onchange="calculateTotal()" />
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <!-- Tổng giá -->
                <div class="total-price text-center" id="totalPrice" style="position: absolute; right: 20px; top: 50px; font-size: 1.5em; color: red;">Tổng giá: 0 VNĐ</div>
                <div class="d-flex justify-content-center" style="margin-top: 30px;">
                    <button type="button" class="btn btn-secondary" style="margin-right: 10px;">Back</button>
                    <button type="submit" class="btn btn-warning">Next</button>
                </div>
            </form>


        </div>

        <script>
            function calculateTotal() {
                let total = 0;
                const quantities = document.querySelectorAll('.quantity');

                quantities.forEach(input => {
                    const price = parseFloat(input.dataset.price);
                    const quantity = parseInt(input.value);
                    total += price * quantity;
                });

                document.getElementById('totalPrice').innerText = 'Tổng giá: ' + total + ' VNĐ';
            }
        </script>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
