<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Combo&Bắp nước - Cinema</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <style>
            :root {
                --momo-pink: #A50064;
                --momo-pink-light: #FF6B9C;
                --card-pink: #FFE6EF;
            }

            body {
                background: #ffffff;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                min-height: 100vh;
                color: #333;
                position: relative;
            }
            .container {
    border: 3px dashed var(--momo-pink);
    padding: 30px;
    border-radius: 20px;
    margin-top: 30px;
    margin-bottom: 30px;
}

            .pattern-bg {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                z-index: -1;
                overflow: hidden;
                background: linear-gradient(to bottom, #fff, #ffe6ef30);
            }

            .pattern-bg::before,
            .pattern-bg::after {
                content: '';
                position: absolute;
                left: 0;
                width: 200%;
                height: 100%;
                background-repeat: repeat-x;
                opacity: 0.3;
            }

            .pattern-bg::before {
                top: 15%;
                background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 1440 320'%3E%3Cpath fill='%23FFB6C1' fill-opacity='1' d='M0,96L48,112C96,128,192,160,288,186.7C384,213,480,235,576,213.3C672,192,768,128,864,128C960,128,1056,192,1152,208C1248,224,1344,192,1392,176L1440,160L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z'%3E%3C/path%3E%3C/svg%3E");
                animation: wave 20s linear infinite;
            }

            .pattern-bg::after {
                top: 35%;
                background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 1440 320'%3E%3Cpath fill='%23FFC0CB' fill-opacity='1' d='M0,160L48,176C96,192,192,224,288,213.3C384,203,480,149,576,149.3C672,149,768,203,864,224C960,245,1056,235,1152,208C1248,181,1344,139,1392,117.3L1440,96L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z'%3E%3C/path%3E%3C/svg%3E");
                animation: wave 15s linear infinite reverse;
            }


            .pattern-bg::before {
                content: '';
                position: absolute;
                top: 25%;
                left: -50%;
                width: 200%;
                height: 100%;
                background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 1440 320'%3E%3Cpath fill='%23FFB6C1' fill-opacity='0.8' d='M0,128L48,144C96,160,192,192,288,197.3C384,203,480,181,576,165.3C672,149,768,139,864,154.7C960,171,1056,213,1152,218.7C1248,224,1344,192,1392,176L1440,160L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z'%3E%3C/path%3E%3C/svg%3E");
                background-repeat: repeat-x;
                animation: wave 25s linear infinite;
                opacity: 0.4;
            }

            @keyframes wave {
                0% {
                    transform: translateX(0);
                }
                100% {
                    transform: translateX(-50%);
                }
            }
            .page-title {
                color: var(--momo-pink);
                font-size: 2.8rem;
                font-weight: bold;
                text-transform: uppercase;
                margin: 40px 0;
                text-align: center;
                text-shadow: 2px 2px 4px rgba(165, 0, 100, 0.1);
            }

            .card {
                background: var(--card-pink);
                border: none;
                border-radius: 20px;
                transition: all 0.3s ease;
                height: 100%;
                margin: 15px 0;
                overflow: hidden;
                box-shadow: 0 4px 15px rgba(165, 0, 100, 0.1);
                border: 2px dashed var(--momo-pink-light) !important;
    background: var(--card-pink);
            }

            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 8px 25px rgba(165, 0, 100, 0.2);
            }

            .card-img-top {
                width: 140px;
                height: 170px;
                object-fit: contain;
                margin: 20px auto;
                transition: transform 0.3s;
                padding: 10px;
            }

            .card:hover .card-img-top {
                transform: scale(1.08);
            }

            .card-title {
                color: var(--momo-pink);
                font-size: 1.4rem;
                font-weight: bold;
                margin: 15px 0;
            }

            .card-text {
                color: #FF4081 !important;
                font-size: 1.2rem;
                font-weight: bold;
                margin: 10px 0;
            }

            .quantity-wrapper {
                position: relative;
                display: inline-block;
            }

            .quantity {
                width: 80px;
                text-align: center;
                padding: 8px 25px 8px 10px;
                border: 2px solid #FF69B4;
                border-radius: 50px;
                background: white;
                color: #FF69B4;
                font-size: 1.1rem;
                font-weight: bold;
                position: relative;
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: textfield;
                border: 2px dashed #FF69B4;
            }

            .quantity-controls {
                position: absolute;
                right: 5px;
                top: 50%;
                transform: translateY(-50%);
                display: flex;
                flex-direction: column;
                gap: 2px;
            }

            .quantity-btn {
                background: none;
                border: none;
                color: #FF69B4;
                padding: 0;
                font-size: 12px;
                cursor: pointer;
                line-height: 1;
            }

            .quantity-btn:hover {
                color: #FF1493;
            }


            .quantity::-webkit-inner-spin-button,
            .quantity::-webkit-outer-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }

            .total-price {
                position: fixed;
                right: 30px;
                top: 30px;
                background: var(--momo-pink);
                color: white !important;
                padding: 15px 30px;
                border-radius: 50px;
                font-size: 1.3rem;
                font-weight: bold;
                box-shadow: 0 5px 15px rgba(165, 0, 100, 0.2);
                z-index: 1000;
                animation: pulse 2s infinite;
                border: 2px dashed #fff;
            }

            @keyframes pulse {
                0% {
                    box-shadow: 0 5px 15px rgba(165, 0, 100, 0.2);
                }
                50% {
                    box-shadow: 0 5px 25px rgba(165, 0, 100, 0.4);
                }
                100% {
                    box-shadow: 0 5px 15px rgba(165, 0, 100, 0.2);
                }
            }

            .btn {
                padding: 12px 35px;
                border-radius: 30px;
                font-weight: bold;
                font-size: 1.1rem;
                transition: all 0.3s;
                margin: 0 10px;
            }


            .btn-next {
                background: #ffe6ef;
                border: 2px dashed var(--momo-pink-light);
            }

            .btn-back {
                background: white;
                border: 2px solid #ffe6ef;
                border: 2px dashed var(--momo-pink);
            }
            .btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(165, 0, 100, 0.2);
            }

            .btn-back:hover {
                background: #e5e5e5;
            }

            .btn-next:hover {
                background: #FF4081;
            }

            .action-buttons {
                display: flex;
                justify-content: center;
                margin-top: 40px;
                padding-bottom: 40px;
            }

            @media (max-width: 768px) {
                .page-title {
                    font-size: 2rem;
                }

                .total-price {
                    right: 50%;
                    transform: translateX(50%);
                    top: auto;
                    bottom: 20px;
                    width: 90%;
                    text-align: center;
                }

                .action-buttons {
                    flex-direction: column;
                    gap: 10px;
                    align-items: center;
                     border-top: 2px dashed var(--momo-pink);
    padding-top: 20px;
    margin-top: 30px;
                }

                .btn {
                    width: 80%;
                    margin: 5px 0;
                }
            }
        </style>
    </head>
    <body>
        <div class="pattern-bg"></div>
        <div class="container mt-5">
            <h1 class="page-title" data-aos="fade-down">COMBO - BẮP NƯỚC</h1>

            <form action="submitCanteenItems" method="post">
                <div class="row">
                    <c:forEach var="item" items="${canteenItemList}" varStatus="status">
                        <div class="col-md-4" data-aos="fade-up" data-aos-delay="${status.index * 100}">
                            <div class="card text-center">
                                <img src="${item.imageURL}" class="card-img-top" alt="${item.name}">
                                <div class="card-body">
                                    <h2 class="card-title">${item.name}</h2>
                                    <p class="card-text">Giá: ${item.price} VNĐ</p>
                                    <div class="quantity-wrapper">
                                        <input type="number" min="0" value="0" class="quantity" 
                                               data-price="${item.price}" 
                                               name="quantity_${item.canteenItemID}" 
                                               onchange="calculateTotal()" />
                                        <div class="quantity-controls">
                                            <button type="button" class="quantity-btn" onclick="increaseQuantity(this)">▲</button>
                                            <button type="button" class="quantity-btn" onclick="decreaseQuantity(this)">▼</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="total-price" id="totalPrice" data-aos="fade-left">
                    Tổng: 0 VNĐ
                </div>

                <div class="action-buttons" data-aos="fade-up">
                    <button type="button" class="btn btn-back" onclick="history.back()">
                        <i class="fas fa-arrow-left me-2"></i>Quay lại
                    </button>
                    <button type="submit" class="btn btn-next">
                        Tiếp tục<i class="fas fa-arrow-right ms-2"></i>
                    </button>
                </div>
            </form>
        </div>

        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>
                        AOS.init({
                            duration: 800,
                            easing: 'ease-in-out',
                            once: true
                        });

                        function calculateTotal() {
                            let total = 0;
                            const quantities = document.querySelectorAll('.quantity');

                            quantities.forEach(input => {
                                const price = parseFloat(input.dataset.price);
                                const quantity = parseInt(input.value);
                                total += price * quantity;
                            });

                            document.getElementById('totalPrice').innerText = 'Tổng: ' + total + ' VNĐ';
                        }
                        function increaseQuantity(btn) {
                            const input = btn.closest('.quantity-wrapper').querySelector('.quantity');
                            input.value = parseInt(input.value) + 1;
                            calculateTotal();
                        }

                        function decreaseQuantity(btn) {
                            const input = btn.closest('.quantity-wrapper').querySelector('.quantity');
                            if (parseInt(input.value) > 0) {
                                input.value = parseInt(input.value) - 1;
                                calculateTotal();
                            }
                        }
        </script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>