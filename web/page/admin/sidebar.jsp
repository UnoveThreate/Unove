<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String currentURL = request.getRequestURI();
%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <style>
        .main-sidebar {
            background-color: #2c3e50 !important;
            border-right: 1px solid #dee2e6;
        }

        .sidebar {
            position: fixed;
        }

        .nav-link.active {
            background-color: #d5ccff !important;
            color: #fff !important;
        }

        .nav-link {
            transition: all 0.3s ease;
        }

        .nav-link:hover {
            transform: translateX(5px);
        }
    </style>
    
    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/admin/dashboard" 
                       class="nav-link <%= currentURL.contains("/admin/dashboard") ? "active" : ""%>">
                        <i class="nav-icon fa-solid fa-gauge-high"></i>
                        <p>Dashboard</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/admin/users" 
                       class="nav-link <%= currentURL.contains("/admin/users") ? "active" : ""%>">
                        <i class="nav-icon fa-solid fa-users"></i>
                        <p>Quản Lí Người Dùng</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/admin/cinemaChains" 
                       class="nav-link <%= currentURL.contains("/admin/cinemaChains") ? "active" : ""%>">
                        <i class="nav-icon fa-solid fa-building"></i>
                        <p>Quản Lí Chuỗi Rạp</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/admin/cinemas" 
                       class="nav-link <%= currentURL.contains("/admin/cinemas") ? "active" : ""%>">
                        <i class="nav-icon fa-solid fa-film"></i>
                        <p>Quản Lí Rạp</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/admin/movies" 
                       class="nav-link <%= currentURL.contains("/admin/movies") ? "active" : ""%>">
                        <i class="nav-icon fa-solid fa-video"></i>
                        <p>Quản Lí Phim</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/admin/statistics" 
                       class="nav-link <%= currentURL.contains("/admin/statistics") ? "active" : ""%>">
                        <i class="nav-icon fa-solid fa-chart-line"></i>
                        <p>Thống Kê Chi Tiết</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/logout" class="nav-link" id="logoutLink">
                        <i class="nav-icon fa-solid fa-right-from-bracket"></i>
                        <p>Đăng xuất</p>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</aside>