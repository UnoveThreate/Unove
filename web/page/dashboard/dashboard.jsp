<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Unove Dashboard</title>

  <!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/css/adminlte.min.css">
  <!-- overlayScrollbars -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/overlayscrollbars/1.13.0/css/OverlayScrollbars.min.css">
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
    <a href="#" class="brand-link">
      <span class="brand-text font-weight-light">Unove Dashboard</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <li class="nav-item">
            <a href="#" class="nav-link active">
              <i class="nav-icon fas fa-tachometer-alt"></i>
              <p>Dashboard</p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-dollar-sign"></i>
              <p>Doanh thu</p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-ticket-alt"></i>
              <p>Vé đã bán</p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-film"></i>
              <p>Suất chiếu</p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-chair"></i>
              <p>Tỷ lệ lấp đầy ghế</p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-video"></i>
              <p>Phim đang chiếu</p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-calendar-alt"></i>
              <p>Phim sắp chiếu</p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-building"></i>
              <p>Quản lý rạp</p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-chart-bar"></i>
              <p>Thống kê rạp</p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-users"></i>
              <p>Quản lý khách hàng</p>
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
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0">Dashboard</h1>
          </div>
        </div>
      </div>
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <!-- Small boxes (Stat box) -->
        <div class="row">
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-info">
              <div class="inner">
                <h3><fmt:formatNumber value="${dashboardData.totalRevenue}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></h3>
                <p>Tổng doanh thu</p>
              </div>
              <div class="icon">
                <i class="ion ion-bag"></i>
              </div>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-success">
              <div class="inner">
                <h3>${dashboardData.totalTicketsSold}</h3>
                <p>Số lượng vé đã bán</p>
              </div>
              <div class="icon">
                <i class="ion ion-stats-bars"></i>
              </div>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-warning">
              <div class="inner">
                <h3>${dashboardData.totalMovieSlots}</h3>
                <p>Số lượng suất chiếu</p>
              </div>
              <div class="icon">
                <i class="ion ion-person-add"></i>
              </div>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-danger">
              <div class="inner">
                <h3><fmt:formatNumber value="${dashboardData.averageSeatOccupancy}" type="number" maxFractionDigits="2"/>%</h3>
                <p>Tỷ lệ lấp đầy ghế trung bình</p>
              </div>
              <div class="icon">
                <i class="ion ion-pie-graph"></i>
              </div>
            </div>
          </div>
          <!-- ./col -->
        </div>
        <!-- /.row -->

        <!-- Thêm thông tin mới -->
        <div class="row">
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-primary">
              <div class="inner">
                <h3>${dashboardData.currentMoviesCount}</h3>
                <p>Số lượng phim đang chiếu</p>
              </div>
              <div class="icon">
                <i class="fas fa-film"></i>
              </div>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-secondary">
              <div class="inner">
                <h3>${dashboardData.upcomingMoviesCount}</h3>
                <p>Số lượng phim sắp chiếu</p>
              </div>
              <div class="icon">
                <i class="fas fa-calendar-alt"></i>
              </div>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-info">
              <div class="inner">
                <h3>${dashboardData.totalCinemas}</h3>
                <p>Tổng số rạp</p>
              </div>
              <div class="icon">
                <i class="fas fa-building"></i>
              </div>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-success">
              <div class="inner">
                <h3>${dashboardData.topCinema.name}</h3>
                <p>Rạp có doanh thu cao nhất</p>
              </div>
              <div class="icon">
                <i class="fas fa-crown"></i>
              </div>
              <a href="#" class="small-box-footer">
                <fmt:formatNumber value="${dashboardData.topCinema.revenue}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
              </a>
            </div>
          </div>
          <!-- ./col -->
        </div>
        <!-- /.row -->

        <!-- Main row -->
        <div class="row">
          <!-- Left col -->
          <section class="col-lg-7 connectedSortable">
            <!-- Custom tabs (Charts with tabs)-->
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">
                  <i class="fas fa-chart-pie mr-1"></i>
                  Biểu đồ doanh thu theo thời gian
                </h3>
              </div><!-- /.card-header -->
              <div class="card-body">
                <div class="chart">
                  <canvas id="revenueChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                </div>
              </div><!-- /.card-body -->
            </div>
            <!-- /.card -->

            <!-- Top 5 phim có doanh thu cao nhất -->
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">Top 5 phim có doanh thu cao nhất</h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body p-0">
                <ul class="products-list product-list-in-card pl-2 pr-2">
                  <c:forEach items="${dashboardData.topMovies}" var="movie">
                    <li class="item">
                      <div class="product-info">
                        <a href="javascript:void(0)" class="product-title">${movie.title}
                          <span class="badge badge-warning float-right"><fmt:formatNumber value="${movie.revenue}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></span></a>
                      </div>
                    </li>
                  </c:forEach>
                </ul>
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </section>
          <!-- /.Left col -->

          <!-- right col (We are only adding the ID to make the widgets sortable)-->
          <section class="col-lg-5 connectedSortable">
            <!-- Tỷ lệ lấp đầy ghế theo rạp -->
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">
                  <i class="fas fa-chart-bar mr-1"></i>
                  Tỷ lệ lấp đầy ghế theo rạp
                </h3>
              </div>
              <div class="card-body">
                <div class="chart">
                  <canvas id="cinemaOccupancyChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                </div>
              </div>
            </div>
            <!-- /.card -->

            <!-- Top 5 khách hàng thân thiết -->
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">Top 5 khách hàng thân thiết</h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body p-0">
                <ul class="users-list clearfix">
                  <c:forEach items="${dashboardData.topCustomers}" var="customer">
                    <li>
                      <img src="https://via.placeholder.com/128" alt="User Image">
                      <a class="users-list-name" href="#">${customer.name}</a>
                      <span class="users-list-date"><fmt:formatNumber value="${customer.totalSpent}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></span>
                    </li>
                  </c:forEach>
                </ul>
                <!-- /.users-list -->
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </section>
          <!-- right col -->
        </div>
        <!-- /.row (main row) -->
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <strong>Copyright &copy; 2023 <a href="#">Unove</a>.</strong>
    All rights reserved.
  </footer>
</div>
<!-- ./wrapper -->

<!-- jQuery -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.0/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/js/adminlte.min.js"></script>
<!-- ChartJS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.7.0/chart.min.js"></script>
pt>
<script>
  // Biểu đồ doanh thu
  var ctx = document.getElementById('revenueChart').getContext('2d');
  var revenueChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: ${dashboardData.revenueChartLabels},
      datasets: [{
        label: 'Doanh thu',
        data: ${dashboardData.revenueChartData},
        borderColor: 'rgb(75, 192, 192)',
        tension: 0.1
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            callback: function(value, index, values) {
              return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
            }
          }
        }
      }
    }
  });

  // Biểu đồ tỷ lệ lấp đầy ghế theo rạp
  var ctxOccupancy = document.getElementById('cinemaOccupancyChart').getContext('2d');
  var occupancyChart = new Chart(ctxOccupancy, {
    type: 'bar',
    data: {
      labels: ${dashboardData.cinemaNames},
      datasets: [{
        label: 'Tỷ lệ lấp đầy ghế',
        data: ${dashboardData.cinemaOccupancyRates},
        backgroundColor: 'rgba(75, 192, 192, 0.6)'
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true,
          max: 100,
          ticks: {
            callback: function(value) {
              return value + '%';
            }
          }
        }
      }
    }
  });
</script>
</body>
</html>