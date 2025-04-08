<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Viết Đánh Giá Phim</title>
        <style>
            body {
                height: 100vh;
                margin: 0;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #f5f5f5;
                font-family: Arial, sans-serif;
            }

            .review-container {
                width: 50%;
                background-color: #ffffff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            }

            .review-container h2 {
                text-align: center;
                color: #4a00e0;
            }

            .movie-card {
                display: flex;
                flex-direction: row;
                align-items: flex-start;
                width: 100%;
                background-color: #f9f9f9;
                border-radius: 8px;
                padding: 10px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
            }

            .movie-poster-container {
                width: 80px;
                height: auto;
                margin-right: 10px;
                border-radius: 4px;
                overflow: hidden;
            }

            .movie-poster {
                width: 100%;
                height: auto;
                object-fit: cover;
            }

            .movie-info {
                display: flex;
                flex-direction: column;
                justify-content: center;
            }

            .movie-title {
                font-size: 1.2rem;
                margin: 0;
                color: #333;
            }

            .movie-genre, .movie-rating {
                font-size: 0.9rem;
                color: #666;
                margin: 5px 0;
            }

            .stars {
                display: flex;
                cursor: pointer;
                gap: 5px;
            }

            .stars svg {
                width: 40px;
                fill: gray;
                transition: fill 0.2s;
            }

            .form-group h3 {
                margin-top: 20px;
                font-size: 18px;
                color: #333;
            }

            .submit-btn {
                width: 100%;
                padding: 10px;
                background-color: #4a00e0;
                color: #ffffff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                margin-top: 20px;
                transition: background-color 0.3s ease;
            }

            .submit-btn:hover {
                background-color: #3b00c4;
            }
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

        </style>
    </head>
    <body>
        <div class="review-container">
            <h2>Viết Đánh Giá Phim</h2>
            <form action="${pageContext.request.contextPath}/movie/reviewMovie" method="post">
                <input type="hidden" name="movieID" value="${movieID}" />
                <input type="hidden" id="ratingValue" name="rating" />

                <div class="movie-card">
                    <div class="movie-poster-container">
                        <img src="${movieImageURL}" alt="${movieTitle}" class="movie-poster">
                    </div>
                    <div class="movie-info">
                        <h3 class="movie-title">${movieTitle}</h3>
                        <p class="movie-genre">
                            <c:forEach var="genre" items="${genres}" varStatus="genreStatus">
                                ${genre}${!genreStatus.last ? ', ' : ''}
                            </c:forEach>
                        </p>
                        <p class="movie-rating">Rating: ${movieRating}</p>
                    </div>
                </div>

                <div class="rating-container">
                    <div class="stars" id="starContainer" onMouseLeave="resetStars()">
                        <svg viewBox="0 0 940.688 940.688" data-value="1"><path d="M885.344,319.071l-258-3.8l-102.7-264.399c-19.8-48.801-88.899-48.801-108.6,0l-102.7,264.399l-258,3.8 c-53.4,3.101-75.1,70.2-33.7,103.9l209.2,181.4l-71.3,247.7c-14,50.899,41.1,92.899,86.5,65.899l224.3-122.7l224.3,122.601 c45.4,27,100.5-15,86.5-65.9l-71.3-247.7l209.2-181.399C960.443,389.172,938.744,322.071,885.344,319.071z"/></svg>
                        <svg viewBox="0 0 940.688 940.688" data-value="2"><path d="M885.344,319.071l-258-3.8l-102.7-264.399c-19.8-48.801-88.899-48.801-108.6,0l-102.7,264.399l-258,3.8 c-53.4,3.101-75.1,70.2-33.7,103.9l209.2,181.4l-71.3,247.7c-14,50.899,41.1,92.899,86.5,65.899l224.3-122.7l224.3,122.601 c45.4,27,100.5-15,86.5-65.9l-71.3-247.7l209.2-181.399C960.443,389.172,938.744,322.071,885.344,319.071z"/></svg>
                        <svg viewBox="0 0 940.688 940.688" data-value="3"><path d="M885.344,319.071l-258-3.8l-102.7-264.399c-19.8-48.801-88.899-48.801-108.6,0l-102.7,264.399l-258,3.8 c-53.4,3.101-75.1,70.2-33.7,103.9l209.2,181.4l-71.3,247.7c-14,50.899,41.1,92.899,86.5,65.899l224.3-122.7l224.3,122.601 c45.4,27,100.5-15,86.5-65.9l-71.3-247.7l209.2-181.399C960.443,389.172,938.744,322.071,885.344,319.071z"/></svg>
                        <svg viewBox="0 0 940.688 940.688" data-value="4"><path d="M885.344,319.071l-258-3.8l-102.7-264.399c-19.8-48.801-88.899-48.801-108.6,0l-102.7,264.399l-258,3.8 c-53.4,3.101-75.1,70.2-33.7,103.9l209.2,181.4l-71.3,247.7c-14,50.899,41.1,92.899,86.5,65.899l224.3-122.7l224.3,122.601 c45.4,27,100.5-15,86.5-65.9l-71.3-247.7l209.2-181.399C960.443,389.172,938.744,322.071,885.344,319.071z"/></svg>
                        <svg viewBox="0 0 940.688 940.688" data-value="5"><path d="M885.344,319.071l-258-3.8l-102.7-264.399c-19.8-48.801-88.899-48.801-108.6,0l-102.7,264.399l-258,3.8 c-53.4,3.101-75.1,70.2-33.7,103.9l209.2,181.4l-71.3,247.7c-14,50.899,41.1,92.899,86.5,65.899l224.3-122.7l224.3,122.601 c45.4,27,100.5-15,86.5-65.9l-71.3-247.7l209.2-181.399C960.443,389.172,938.744,322.071,885.344,319.071z"/></svg>
                    </div>
                    <p>Đánh giá: <span id="ratingText">0</span></p>
                </div>

                <div class="form-group">
                    <h3>Nội Dung Đánh Giá:</h3>
                    <textarea name="content" rows="5" style="width: 100%; padding: 10px; border-radius: 5px; resize: vertical;" required></textarea>
                </div>

                <button type="submit" class="submit-btn">Gửi Đánh Giá</button>
            </form>
        </div>
        <script>
            let currentRating = 0;

            function updateRating(index) {
                const stars = document.querySelectorAll('.stars svg');
                currentRating = index + 1;
                document.getElementById('ratingValue').value = currentRating;
                document.getElementById('ratingText').innerText = currentRating;
                stars.forEach((star, idx) => {
                    star.style.fill = idx < currentRating ? 'gold' : 'gray';
                });
            }

            document.getElementById('starContainer').addEventListener('click', (event) => {
                const target = event.target.closest('svg');
                if (target) {
                    const index = Array.from(target.parentNode.children).indexOf(target);
                    updateRating(index);
                }
            });

            document.getElementById('starContainer').addEventListener('mouseover', (event) => {
                const target = event.target.closest('svg');
                if (target) {
                    const index = Array.from(target.parentNode.children).indexOf(target);
                    updateRating(index);
                }
            });

            function resetStars() {
                updateRating(currentRating - 1);
            }
        </script>
    </body>
</html>
