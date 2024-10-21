<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    <a href="${pageContext.request.contextPath}/owner/dashboard" class="nav-link active">
                        <i class="nav-icon fas fa-tachometer-alt"></i>
                        <p>Dashboard</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/owner/manageCinema" class="nav-link">
                        <i class="nav-icon fas fa-film"></i>
                        <p>Quản lý Rạp</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/owner/manageMovies" class="nav-link">
                        <i class="nav-icon fas fa-video"></i>
                        <p>Quản lý Phim</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/owner/manageRooms" class="nav-link">
                        <i class="nav-icon fas fa-door-open"></i>
                        <p>Quản lý Phòng</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/owner/manageMovieSlots" class="nav-link">
                        <i class="nav-icon fas fa-calendar-alt"></i>
                        <p>Quản lý Suất Chiếu</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/owner/statistics" class="nav-link">
                        <i class="nav-icon fas fa-chart-bar"></i>
                        <p>Thống kê</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/logout" class="nav-link">
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