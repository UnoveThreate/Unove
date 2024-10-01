<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Review phim</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }

            .container {
                background-color: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                max-width: 800px;
                width: 100%;
            }

            .rating-container {
                display: flex;
                justify-content: center;
                margin-bottom: 20px;
            }

            .button-star {
                border: none;
                background-color: white;
                cursor: pointer;
                margin: 0 5px;
            }

            .review-container {
                margin-top: 20px;
                text-align: center;
            }

            textarea {
                width: 100%;
                padding: 10px;
                border-radius: 5px;
                border: 1px solid #ccc;
                box-sizing: border-box;
                font-size: 18px;
                resize: none;
                font-family: 'Times New Roman', Times, serif;
            }

            button[type="button"] {
                padding: 10px 20px;
                background-color: rgb(216, 45, 139);
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
            }

            button[type="button"]:hover {
                /*background-color: #0056b3;*/
            }
        </style>
    </head>
    <body>
       <form class="container" action="/movie/reviewmovie" method="post">
            <div class="rating-container">
                <c:forEach var="star" begin="1" end="10">
                    <button id="starButton${star}" class="button-star" onclick="rateMovie('${star}');">
                        <img id="starImage${star}" src="assets/images/white_star_icon.png" alt="white star"/>
                    </button>
                </c:forEach>
            </div>
            <div class="review-container">
                <textarea id="review" name="review" rows="6" required></textarea>
            </div>
            <div class="submit-container" style="text-align: center; margin-top: 20px;">
                <button type="button" onclick="sendFeedback()">Gửi đánh giá</button>
            </div>
           <input type="hidden" id="starOutput"/>
        </form>
    </body>
    
    <script>
        function rateMovie(star) {
            const starImage = document.getElementById('starImage' + star);
            if(starImage.src.includes("white_star_icon.png")) {
                for(let i = 1; i <= star; ++i) {
                    document.getElementById('starImage' + i).src = "assets/images/yellow_star_icon.png";
                }
            }
            else {
                for(let i = 1; i <= 10; ++i) {
                    document.getElementById('starImage' + i).src = "assets/images/white_star_icon.png";
                }
            }
            document.getElementById('starOutput').value = star;
        }
        
        function voteMovie(star, src) {
            for(let i = 1; i <= star; ++i) {
                document.getElementById('starButton' + i).innerHTML = '';
                const starImage = document.createElement('img');
                starImage.src = src;
                document.getElementById('starButton' + i).append(starImage);
            }
        }
        
        function sendFeedback() {
            if(document.getElementById('starOutput').value === '') alert("Hãy chọn sao trước khi gửi đánh giá!");
        }
    </script>
</html>
