<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Get the current request URI
    String currentUrl = request.getRequestURI();
%>
<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="#" class="brand-link">
        <span class="brand-text font-weight-light">Unove Dashboard</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/owner/dashboard" class="nav-link" id="dashboardLink">
                        <i class="nav-icon fas fa-tachometer-alt"></i>
                        <p>Dashboard</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/owner/manageCinema" class="nav-link" id="manageCinemaLink">
                        <i class="nav-icon fas fa-film"></i>
                        <p>Quản lý Rạp</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/owner/movie" class="nav-link" id="manageMovieLink">
                        <i class="nav-icon fas fa-video"></i>
                        <p>Quản lý Phim</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/owner/room/manageRoom" class="nav-link" id="manageRoomLink">
                        <i class="nav-icon fas fa-door-open"></i>
                        <p>Quản lý Phòng</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/owner/movieSlot/manageMovieSlot" class="nav-link" id="manageMovieSlotLink">
                        <i class="nav-icon fas fa-calendar-alt"></i>
                        <p>Quản lý Suất Chiếu</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/owner/manageSeat" class="nav-link" id="manageSeatLink">
                        <i class="nav-icon fas fa-chair"></i> <!-- Icon ghế -->
                        <p>Quản lý Ghế</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/owner/genre" class="nav-link" id="genreLink">
                        <i class="nav-icon fas fa-chart-bar"></i>
                        <p>Thể loại phim</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/logout" class="nav-link" id="logoutLink">
                        <i class="nav-icon fas fa-sign-out-alt"></i>
                        <p>Đăng xuất</p>
                    </a>
                </li>
            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>

<script>
    // Log the current URL to the console
    console.log("Current URL:", window.location.href);

    // Function to set active link based on the current URL
    function setActiveLink() {
        var currentUrl = window.location.pathname; // Get the path of the current URL
        // Check each link to see if it matches the current URL
        if (currentUrl.endsWith("/dashboard")) {
            document.getElementById("dashboardLink").classList.add("active");
        } else if (currentUrl.endsWith("/manageCinemaChain")) {
            document.getElementById("manageCinemaLink").classList.add("active");
        } else if (currentUrl.endsWith("/movie")) {
            document.getElementById("manageMovieLink").classList.add("active");
        } else if (currentUrl.endsWith("/manageRoom")) {
            document.getElementById("manageRoomLink").classList.add("active");
        } else if (currentUrl.endsWith("/manageMovieSlot")) {
            document.getElementById("manageMovieSlotLink").classList.add("active");
        } else if (currentUrl.endsWith("/manageSeat")) {
            document.getElementById("manageSeatLink").classList.add("active"); // Thêm đây
        } else if (currentUrl.endsWith("/genre")) {
            document.getElementById("genreLink").classList.add("active");
        } else if (currentUrl.endsWith("/logout")) {
            document.getElementById("logoutLink").classList.add("active");
        }
    }

    // Call the function to set the active link on page load
    setActiveLink();
</script>
