<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Admin Unove</title>

  <!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/css/adminlte.min.css">
  <!-- AOS CSS -->
  <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
  <!-- Chart.js -->
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  
  <style>
    :root {
      --primary-color: #3498db;
      --secondary-color: #2ecc71;
      --accent-color: #e74c3c;
      --text-color: #34495e;
      --bg-color: #ecf0f1;
    }

    body {
      font-family: 'Source Sans Pro', sans-serif;
      background-color: var(--bg-color);
      color: var(--text-color);
    }

    .main-sidebar {
      background-color: #2c3e50;
    }

    .brand-link {
      background-color: #34495e;
    }

    .nav-sidebar .nav-item .nav-link {
      color: #ecf0f1;
      transition: all 0.3s ease;
    }

    .nav-sidebar .nav-item .nav-link:hover {
      background-color: var(--primary-color);
      color: #fff;
    }

    .content-wrapper {
      background-color: #f5f7fa;
    }

    .small-box {
      border-radius: 15px;
      overflow: hidden;
      transition: all 0.3s ease;
    }

    .small-box:hover {
      transform: translateY(-5px);
      box-shadow: 0 10px 20px rgba(0,0,0,0.1);
    }

    .small-box .icon {
      transition: all 0.3s ease;
    }

    .small-box:hover .icon {
      transform: scale(1.1);
    }

    .card {
      border-radius: 15px;
      overflow: hidden;
      box-shadow: 0 5px 15px rgba(0,0,0,0.1);
    }

    .card-header {
      background-color: var(--primary-color);
      color: #fff;
      border-bottom: none;
    }

    .main-footer {
      background-color: #2c3e50;
      color: #ecf0f1;
    }

    .main-footer a {
      color: var(--secondary-color);
    }

    /* Custom scrollbar */
    ::-webkit-scrollbar {
      width: 10px;
    }

    ::-webkit-scrollbar-track {
      background: #f1f1f1;
    }

    ::-webkit-scrollbar-thumb {
      background: var(--primary-color);
      border-radius: 5px;
    }

    ::-webkit-scrollbar-thumb:hover {
      background: #2980b9;
    }
  </style>
</head>
<body class="hold-transition sidebar-mini layout-fixed">
    
<div class="wrapper">

  <!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
    </ul>
  </nav>
  <!-- /.navbar -->

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
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link active">
              <i class="nav-icon fas fa-tachometer-alt"></i>
              <p>Dashboard</p>
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

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <h1 class="m-0" data-aos="fade-right">Dashboard</h1>
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <!-- Small boxes (Stat box) -->
        <div class="row">
          <div class="col-lg-3 col-6" data-aos="fade-up" data-aos-delay="100">
            <!-- small box -->
            <div class="small-box bg-info">
              <div class="inner">
                <h3>${totalCinemaChains}</h3>
                <p>Số lượng chuỗi rạp</p>
              </div>
              <div class="icon">
                <i class="ion ion-bag"></i>
              </div>
              <a href="${pageContext.request.contextPath}/admin/cinemaChains" class="small-box-footer">Xem thêm <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6" data-aos="fade-up" data-aos-delay="200">
            <!-- small box -->
            <div class="small-box bg-success">
              <div class="inner">
                <h3>${totalCinemas}</h3>
                <p>Số lượng rạp</p>
              </div>
              <div class="icon">
                <i class="ion ion-stats-bars"></i>
              </div>
              <a href="${pageContext.request.contextPath}/admin/cinemas" class="small-box-footer">Xem thêm <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6" data-aos="fade-up" data-aos-delay="300">
            <!-- small box -->
            <div class="small-box bg-warning">
              <div class="inner">
                <h3>${totalMovies}</h3>
                <p>Số lượng phim </p>
              </div>
              <div class="icon">
                <i class="ion ion-person-add"></i>
              </div>
              <a href="${pageContext.request.contextPath}/admin/movies" class="small-box-footer">Xem thêm <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
        </div>
        <!-- /.row -->

        <!-- Charts -->
        <div class="row mt-4">
          <div class="col-md-6" data-aos="fade-up" data-aos-delay="400">
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">Doanh thu theo chuỗi rạp</h3>
              </div>
              <div class="card-body">
                <canvas id="revenueByCinemaChainChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
              </div>
            </div>
          </div>
          <div class="col-md-6" data-aos="fade-up" data-aos-delay="500">
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">Số vé bán được theo chuỗi rạp</h3>
              </div>
              <div class="card-body">
                <canvas id="ticketsSoldByCinemaChainChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
              </div>
            </div>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <strong>Copyright &copy; 2024 <a href="https://adminlte.io">Unove Cinema</a>.</strong>
    All rights reserved.
    <div class="float-right d-none d-sm-inline-block">
      <b>Version</b> 3.1.0
    </div>
  </footer>

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

<!-- jQuery -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/js/adminlte.min.js"></script>
<!-- AOS JS -->
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script>
  AOS.init({
    duration: 1000,
    once: true
  });

  // Hàm định dạng số tiền
  function formatCurrency(value) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
  }

  // Hàm định dạng số
  function formatNumber(value) {
    return new Intl.NumberFormat('vi-VN').format(value);
  }

  // Biểu đồ doanh thu
  var revenueByCinemaChainData = ${revenueByCinemaChainJson};
  var ctx = document.getElementById('revenueByCinemaChainChart').getContext('2d');
  var chart = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: Object.keys(revenueByCinemaChainData),
      datasets: [{
        label: 'Doanh thu',
        data: Object.values(revenueByCinemaChainData),
        backgroundColor: 'rgba(23, 162, 184, 0.2)',
        borderColor: 'rgba(23, 162, 184, 1)',
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: {
          beginAtZero: true,
          title: {
            display: true,
            text: 'Doanh thu (VNĐ)',
            font: {
              size: 14,
              weight: 'bold'
            }
          },
          ticks: {
            callback: function(value) {
              return formatCurrency(value);
            }
          },
          grid: {
            color: 'rgba(0, 0, 0, 0.1)',
            drawBorder: false,
            lineWidth: 1
          }
        },
        x: {
          title: {
            display: true,
            font: {
              size: 14,
              weight: 'bold'
            }
          },
          grid: {
            display: false
          }
        }
      },
      plugins: {
        legend: {
          display: false
        },
        title: {
          display: true,
          font: {
            size: 18,
            weight: 'bold'
          }
        },
        tooltip: {
          callbacks: {
            label: function(context) {
              return 'Doanh thu: ' + formatCurrency(context.parsed.y);
            }
          }
        }
      },
      barThickness: 50,
      maxBarThickness: 70
    }
  });

  // Biểu đồ số vé bán được
  var ticketsSoldByCinemaChainData = ${ticketsSoldByCinemaChainJson};
  var ticketsCtx = document.getElementById('ticketsSoldByCinemaChainChart').getContext('2d');
  var ticketsChart = new Chart(ticketsCtx, {
    type: 'pie',
    data: {
      labels: Object.keys(ticketsSoldByCinemaChainData),
      datasets: [{
        data: Object.values(ticketsSoldByCinemaChainData),
        backgroundColor: [
          'rgba(255, 99, 132, 0.8)',
          'rgba(54, 162, 235, 0.8)',
          'rgba(255, 206, 86, 0.8)',
          'rgba(75, 192, 192, 0.8)',
          'rgba(153, 102, 255, 0.8)',
          'rgba(255, 159, 64, 0.8)'
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          position: 'right',
        },
        title: {
          display: true,
          text: 'Số vé bán được theo chuỗi rạp',
          font: {
            size: 18,
            weight: 'bold'
          }
        },
        tooltip: {
          callbacks: {
            label: function(context) {
              var label = context.label || '';
              var value = context.parsed || 0;
              var total = context.dataset.data.reduce((a, b) => a + b, 0);
              var percentage = ((value / total) * 100).toFixed(2) + '%';
              return label + ': ' + formatNumber(value) + ' (' + percentage + ')';
            }
          }
        }
      }
    }
  });
</script>
</body>
</html>