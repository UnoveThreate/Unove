<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
        <style type="text/css">
            .navbar {
                background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
                padding: 15px 0;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }

            .navbar-brand {
                color: #ffffff;
                font-weight: 700;
                font-size: 1.8rem;
                text-transform: uppercase;
                letter-spacing: 2px;
                transition: all 0.3s ease;
            }

            .navbar-brand:hover {
                color: #f0f4f8;
                text-shadow: 0 0 10px rgba(255, 255, 255, 0.5);
            }

            .nav-link {
                color: #ffffff !important;
                font-weight: 500;
                padding: 10px 15px !important;
                border-radius: 25px;
                transition: all 0.3s ease;
            }

            .nav-link:hover {
                background-color: rgba(255, 255, 255, 0.2);
                transform: translateY(-2px);
            }

            /* Logo styles */
            .icon-logo-header {
                width: 40px; /* Điều chỉnh kích thước logo nếu cần */
                height: auto;
                margin-right: 10px;
            }
        </style>
    </head>

    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/">
                    <img class="icon-logo-header" src="${pageContext.request.contextPath}/page/image/logoHeader.png" alt="Logo" />
                    Unove
                </a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/owner">
                                <i class="fas fa-home"></i> Home
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/owner/manageCinemaChain">
                                <i class="fas fa-film"></i> Manage Cinema
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz4fnFO9gyb5R9cb4P0Hb1M1ljRDE4FzExm6Q3x7U4e+0YwA3yKf8h0nA6" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-F7nD5JSSGImWg3m9fW9R9Oql9KxI+B5i89F8wPPJ9fz8bmC5U1r0WZ05lB4tcnvE" crossorigin="anonymous"></script>
    </body>

</html>
