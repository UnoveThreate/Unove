<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chọn Đồ Ăn</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: white;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 1.5rem;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin-bottom: 0.5rem;
            color: #555;
        }
        select, input[type="number"] {
            padding: 0.5rem;
            margin-bottom: 1rem;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1rem;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 0.75rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Chọn Đồ Ăn</h1>
        <form action="SelectFoodServlet" method="post">
            <label for="canteenItem">Chọn Món:</label>
            <select id="canteenItem" name="canteenItemID">
                <option value="1">Popcorn Large - 4.50$</option>
                <option value="2">Soda Medium - 3.00$</option>
                <option value="3">Nachos - 5.00$</option>
                <option value="4">Candy Bar - 2.00$</option>
                <option value="5">Ice Cream - 3.50$</option>
                <option value="6">Hot Dog - 4.00$</option>
                <option value="7">Popcorn Small - 3.00$</option>
                <option value="8">Water Bottle - 1.50$</option>
                <option value="9">Chocolate - 2.50$</option>
            </select>
            
            <label for="quantity">Số Lượng:</label>
            <input type="number" id="quantity" name="quantity" min="1" value="1" required>
            
            <button type="submit">Mua</button>
        </form>
    </div>
</body>
</html>