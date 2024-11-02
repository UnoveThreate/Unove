<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
  <!-- Brand Logo -->
  <a href="index3.html" class="brand-link">
    <img src="https://adminlte.io/themes/v3/dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
    <span class="brand-text font-weight-light">Unove Admin</span>
  </a>

  <!-- Sidebar -->
  <div class="sidebar">
    <!-- Sidebar Menu -->
    <nav class="mt-2">
      <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
        <li class="nav-item">
          <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link">
            <i class="nav-icon fas fa-tachometer-alt"></i>
            <p>Dashboard</p>
          </a>
        </li>
        <li class="nav-item">
          <a href="${pageContext.request.contextPath}/admin/users" class="nav-link">
            <i class="nav-icon fas fa-chart-pie"></i>
            <p>Quản Lí Người Dùng</p>
          </a>
        </li>
        <li class="nav-item">
          <a href="${pageContext.request.contextPath}/admin/cinemaChains" class="nav-link">
            <i class="nav-icon fas fa-film"></i>
            <p>Quản Lí Chuỗi Rạp</p>
          </a>
        </li>
        <li class="nav-item">
          <a href="${pageContext.request.contextPath}/admin/cinemas" class="nav-link">
            <i class="nav-icon fas fa-theater-masks"></i>
            <p>Quản Lí Rạp</p>
          </a>
        </li>
        <li class="nav-item">
          <a href="${pageContext.request.contextPath}/admin/movies" class="nav-link">
            <i class="nav-icon fas fa-video"></i>
            <p>Quản Lí Phim</p>
          </a>
        </li>
        <li class="nav-item">
          <a href="${pageContext.request.contextPath}/admin/statistics" class="nav-link">
            <i class="nav-icon fas fa-chart-pie"></i>
            <p>Thống Kê Chi Tiết</p>
          </a>
        </li>
      </ul>
    </nav>
    <!-- /.sidebar-menu -->
  </div>
  <!-- /.sidebar -->
</aside>