<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Unove Dashboard</title>

        <!-- CSS Libraries -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/css/adminlte.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/overlayscrollbars/1.13.0/css/OverlayScrollbars.min.css">
        <link href='https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.css' rel='stylesheet' />
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/@sweetalert2/theme-material-ui/material-ui.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <style>
            :root {
                --primary-color: #007bff;
                --success-color: #28a745;
                --warning-color: #ffc107;
                --danger-color: #dc3545;
                --info-color: #17a2b8;
            }

            .fc-event {
                border: none;
                padding: 2px;
                transition: transform 0.3s ease;
            }

            .fc-event:hover {
                transform: scale(1.02);
            }

            .fc-event-main {
                padding: 2px;
                background-color: var(--primary-color);
                color: white;
                border-radius: 3px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }

            .fc-event-title {
                font-weight: bold;
                margin-bottom: 2px;
            }

            .fc-event-time, .fc-event-location {
                font-size: 0.8em;
                opacity: 0.9;
            }

            .small-box {
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .small-box:hover {
                transform: translateY(-5px);
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            }

            .card {
                border-radius: 10px;
                box-shadow: 0 4px 6px rgba(0,0,0,0.1);
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .card:hover {
                transform: translateY(-2px);
                box-shadow: 0 6px 12px rgba(0,0,0,0.15);
            }

            .table-responsive {
                border-radius: 8px;
                overflow: hidden;
            }

            .table th {
                background-color: #9664dd;
                color: white;
                border: none;
            }

            .table-hover tbody tr:hover {
                background-color: #F5EFFF;
            }

            .badge {
                padding: 5px 10px;
                border-radius: 15px;
            }
            .swal2-popup {
                border-radius: 15px !important;
                padding: 2rem !important;
            }

            .swal2-title {
                color: #9664dd !important;
                font-size: 1.5rem !important;
            }

            .swal2-html-container {
                color: #555 !important;
                font-size: 1.1rem !important;
                line-height: 1.8 !important;
            }

            .swal2-confirm {
                background: #9664dd !important;
                border-radius: 25px !important;
                padding: 8px 25px !important;
            }

            .swal2-confirm:hover {
                background: #7c4ddb !important;
                box-shadow: 0 2px 8px rgba(150, 100, 221, 0.3) !important;
            }
        </style>
    </head>
    <body class="hold-transition sidebar-mini layout-fixed">
        <div class="wrapper">
            <!-- Navbar -->
            <nav class="main-header navbar navbar-expand navbar-white navbar-light">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" data-widget="pushmenu" href="#" role="button">
                            <i class="fas fa-bars"></i>
                        </a>
                    </li>
                </ul>
            </nav>

            <!-- Main Sidebar Container -->
            <jsp:include page="sidebar.jsp" />

            <!-- Content Wrapper -->
            <div class="content-wrapper">
                <!-- Content Header -->
                <div class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1 class="m-0" data-aos="fade-right">Dashboard</h1>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Main content -->
                <section class="content">
                    <div class="container-fluid">
                        <!-- Small boxes (Stat box) -->
                        <div class="row">
                            <div class="col-lg-3 col-6" data-aos="fade-up" data-aos-delay="100">
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
                            <div class="col-lg-3 col-6" data-aos="fade-up" data-aos-delay="200">
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
                            <div class="col-lg-3 col-6" data-aos="fade-up" data-aos-delay="300">
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
                            <div class="col-lg-3 col-6" data-aos="fade-up" data-aos-delay="400">
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
                        </div>

                        <!-- Charts Row -->
                        <div class="row">
                            <section class="col-lg-6 connectedSortable" data-aos="fade-right" data-aos-delay="500">
                                <div class="card">
                                    <div class="card-header">
                                        <h3 class="card-title">
                                            <i class="fas fa-chart-bar mr-1"></i>
                                            Biểu đồ doanh thu theo rạp
                                        </h3>
                                    </div>
                                    <div class="card-body">
                                        <canvas id="revenueChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                                    </div>
                                </div>
                            </section>

                            <section class="col-lg-6 connectedSortable" data-aos="fade-left" data-aos-delay="500">
                                <div class="card">
                                    <div class="card-header">
                                        <h3 class="card-title">
                                            <i class="fas fa-ticket-alt mr-1"></i>
                                            Biểu đồ số lượng vé theo rạp
                                        </h3>
                                    </div>
                                    <div class="card-body">
                                        <canvas id="ticketChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                                    </div>
                                </div>
                            </section>
                        </div>

                        <!-- Movie Revenue Stats -->
                        <div class="row mt-4" data-aos="fade-up" data-aos-delay="600">
                            <div class="col-12">
                                <div class="card">
                                    <div class="card-header">
                                        <h3 class="card-title">Thống kê doanh thu theo phim toàn rạp</h3>
                                    </div>
                                    <div class="card-body">
                                        <canvas id="movieRevenueChart" style="min-height: 300px; height: 300px; max-height: 300px; max-width: 100%;"></canvas>
                                    </div>
                                    <div class="card-body table-responsive p-0">
                                        <table class="table table-hover text-nowrap">
                                            <thead>
                                                <tr>
                                                    <th>Tên phim</th>
                                                    <th>Tổng số vé</th>
                                                    <th>Vé thành công</th>
                                                    <th>Vé thất bại</th>
                                                    <th>Doanh thu</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${dashboardData.movieRevenueStats}" var="movie" varStatus="status">
                                                    <tr data-aos="fade-up" data-aos-delay="${600 + (status.index * 50)}">
                                                        <td>${movie.title}</td>
                                                        <td>${movie.totalTickets}</td>
                                                        <td>${movie.successTickets}</td>
                                                        <td>${movie.failedTickets}</td>
                                                        <td><fmt:formatNumber value="${movie.totalRevenue}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Calendar -->
                        <div class="row mt-4" data-aos="fade-up" data-aos-delay="700">
                            <div class="col-12">
                                <div class="card">
                                    <div class="card-header">
                                        <h3 class="card-title">Lịch chiếu phim sắp tới</h3>
                                    </div>
                                    <div class="card-body">
                                        <div id="calendar" style="height: 600px;"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>

            <footer class="main-footer">
                <strong>Copyright &copy; 2023 <a href="#">Unove</a>.</strong>
                All rights reserved.
            </footer>
        </div>

        <!-- Scripts -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.0/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/js/adminlte.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.7.0/chart.min.js"></script>
        <script src='https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.js'></script>
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>

        <!-- Initialize AOS -->
        <script>
            AOS.init({
                duration: 800,
                easing: 'ease-in-out',
                once: true,
                mirror: false,
                offset: 50
            });
            document.addEventListener('DOMContentLoaded', function () {
                // Biểu đồ doanh thu
                var ctxRevenue = document.getElementById('revenueChart').getContext('2d');
                var revenueData = ${dashboardData.revenueStatsJson};

                console.log("Revenue Data:", revenueData);

                var labels = Object.keys(revenueData);
                var values = Object.values(revenueData);

                if (labels.length === 0) {
                    ctxRevenue.font = '20px Arial';
                    ctxRevenue.fillStyle = 'black';
                    ctxRevenue.textAlign = 'center';
                    ctxRevenue.fillText('Không có dữ liệu doanh thu', ctxRevenue.canvas.width / 2, ctxRevenue.canvas.height / 2);
                } else {
                    new Chart(ctxRevenue, {
                        type: 'bar',
                        data: {
                            labels: labels,
                            datasets: [{
                                    label: 'Doanh thu',
                                    data: values,
                                    backgroundColor: 'rgba(54, 162, 235, 0.8)',
                                    borderColor: 'rgba(54, 162, 235, 1)',
                                    borderWidth: 1
                                }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            scales: {
                                x: {
                                    grid: {
                                        display: false
                                    },
                                    ticks: {
                                        autoSkip: false,
                                        maxRotation: 90,
                                        minRotation: 0
                                    }
                                },
                                y: {
                                    beginAtZero: true,
                                    ticks: {
                                        callback: function (value) {
                                            return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND', maximumFractionDigits: 0}).format(value);
                                        }
                                    }
                                }
                            },
                            plugins: {
                                legend: {
                                    display: true,
                                    position: 'top'
                                },
                                tooltip: {
                                    callbacks: {
                                        label: function (context) {
                                            return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND', maximumFractionDigits: 0}).format(context.parsed.y);
                                        }
                                    }
                                }
                            },
                            barPercentage: 0.5,
                            categoryPercentage: 0.7
                        }
                    });
                }

                // Biểu đồ số lượng vé
                var ctxTicket = document.getElementById('ticketChart').getContext('2d');
                var ticketData = ${dashboardData.ticketStatsJson};

                console.log("Ticket Data:", ticketData);

                var labels = Object.keys(ticketData);
                var successData = labels.map(label => ticketData[label]['Success'] || 0);
                var failedData = labels.map(label => ticketData[label]['Failed'] || 0);

                if (labels.length === 0) {
                    ctxTicket.font = '20px Arial';
                    ctxTicket.fillStyle = 'black';
                    ctxTicket.textAlign = 'center';
                    ctxTicket.fillText('Không có dữ liệu vé', ctxTicket.canvas.width / 2, ctxTicket.canvas.height / 2);
                } else {
                    new Chart(ctxTicket, {
                        type: 'bar',
                        data: {
                            labels: labels,
                            datasets: [
                                {
                                    label: 'Vé thành công',
                                    data: successData,
                                    backgroundColor: 'rgba(54, 162, 235, 0.8)',
                                    borderColor: 'rgba(54, 162, 235, 1)',
                                    borderWidth: 1
                                },
                                {
                                    label: 'Vé thất bại',
                                    data: failedData,
                                    backgroundColor: 'rgba(255, 99, 132, 0.8)',
                                    borderColor: 'rgba(255, 99, 132, 1)',
                                    borderWidth: 1
                                }
                            ]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            scales: {
                                x: {
                                    stacked: true,
                                    grid: {
                                        display: false
                                    }
                                },
                                y: {
                                    stacked: true,
                                    beginAtZero: true,
                                    ticks: {
                                        precision: 0
                                    }
                                }
                            },
                            plugins: {
                                legend: {
                                    position: 'top',
                                },
                                tooltip: {
                                    callbacks: {
                                        label: function (context) {
                                            let label = context.dataset.label || '';
                                            if (label) {
                                                label += ': ';
                                            }
                                            if (context.parsed.y !== null) {
                                                label += new Intl.NumberFormat('vi-VN').format(context.parsed.y);
                                            }
                                            return label;
                                        }
                                    }
                                }
                            },
                            barPercentage: 0.5,
                            categoryPercentage: 0.7
                        }
                    });
                }

                // Biểu đồ thống kê doanh thu theo phim toàn rạp
                var ctxMovieRevenue = document.getElementById('movieRevenueChart').getContext('2d');
                var movieStats = ${dashboardData.movieRevenueStatsJson};

                console.log("Movie Revenue Stats:", movieStats);

                if (movieStats.length === 0) {
                    ctxMovieRevenue.font = '20px Arial';
                    ctxMovieRevenue.fillStyle = 'black';
                    ctxMovieRevenue.textAlign = 'center';
                    ctxMovieRevenue.fillText('Không có dữ liệu doanh thu phim', ctxMovieRevenue.canvas.width / 2, ctxMovieRevenue.canvas.height / 2);
                } else {
                    var labels = movieStats.map(item => item.title);
                    var revenueData = movieStats.map(item => item.totalRevenue);

                    new Chart(ctxMovieRevenue, {
                        type: 'bar',
                        data: {
                            labels: labels,
                            datasets: [{
                                    label: 'Doanh thu',
                                    data: revenueData,
                                    backgroundColor: 'rgba(54, 162, 235, 0.8)',
                                    borderColor: 'rgba(54, 162, 235, 1)',
                                    borderWidth: 1
                                }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            scales: {
                                x: {
                                    grid: {
                                        display: false
                                    }
                                },
                                y: {
                                    beginAtZero: true,
                                    ticks: {
                                        callback: function (value) {
                                            return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND', maximumFractionDigits: 0}).format(value);
                                        }
                                    }
                                }
                            },
                            plugins: {
                                legend: {
                                    display: true,
                                    position: 'top'
                                },
                                tooltip: {
                                    callbacks: {
                                        label: function (context) {
                                            return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND', maximumFractionDigits: 0}).format(context.parsed.y);
                                        }
                                    }
                                }
                            },
                            barPercentage: 0.3,
                            categoryPercentage: 0.3
                        }
                    });
                }

                // FullCalendar
                function convertToFullCalendarEvents(scheduleData) {
                    return scheduleData.map(item => {
                        console.log("Processing event:", item); // log debug
                        var event = {
                            title: item.title || 'Chưa có tên phim',
                            extendedProps: {
                                movieID: item.movieID,
                                movieSlotID: item.movieSlotID,
                                cinemaName: item.cinemaName,
                                roomName: item.roomName,
                                imageURL: item.imageURL
                            }
                        };
                        if (item.startTime) {
                            event.start = new Date(item.startTime);
                        }
                        if (item.endTime) {
                            event.end = new Date(item.endTime);
                        }
                        return event;
                    });
                }

                console.log("Initializing FullCalendar");
                var calendarEl = document.getElementById('calendar');
                if (!calendarEl) {
                    console.error("Calendar element not found");
                    return;
                }

                var scheduleData = ${dashboardData.upcomingMovieScheduleJson};
                console.log("Original schedule data:", scheduleData);

                // Kiểm tra xem mỗi sự kiện có startTime và endTime không
                scheduleData.forEach((event, index) => {
                    if (!event.startTime || !event.endTime) {
                        console.warn(`Event at index ${index} is missing startTime or endTime:`, event);
                    }
                });

                var calendarEvents = convertToFullCalendarEvents(scheduleData);
                console.log("Converted calendar events:", calendarEvents);

                var calendar = new FullCalendar.Calendar(calendarEl, {
                    initialView: 'dayGridMonth',
                    events: calendarEvents,
                    eventClick: function (info) {
                        var startTime = info.event.start ? info.event.start.toLocaleString() : 'Không có thời gian bắt đầu';
                        var endTime = info.event.end ? info.event.end.toLocaleString() : 'Không có thời gian kết thúc';

                        Swal.fire({
                            title: 'Phim: ' + info.event.title,
                            html: 'Bắt đầu: ' + startTime +
                                    '<br>Kết thúc: ' + endTime +
                                    '<br>Rạp: ' + (info.event.extendedProps.cinemaName || 'Không có thông tin') +
                                    '<br>Phòng: ' + (info.event.extendedProps.roomName || 'Không có thông tin'),
                            icon: 'info',
                            confirmButtonText: 'Đóng'
                        });
                    },
                    eventContent: function (arg) {
                        var startTime = arg.event.start ? arg.event.start.toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'}) : '';
                        var endTime = arg.event.end ? arg.event.end.toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'}) : '';
                        var timeText = startTime + (endTime ? ' - ' + endTime : '');
                        return {
                            html: '<div class="fc-event-main">' +
                                    '<div class="fc-event-title">' + arg.event.title + '</div>' +
                                    '<div class="fc-event-time">' + timeText + '</div>' +
                                    '<div class="fc-event-location">' + (arg.event.extendedProps.cinemaName || '') + ' - ' + (arg.event.extendedProps.roomName || '') + '</div>' +
                                    '</div>'
                        };
                    },
                    eventDisplay: 'block',
                    displayEventTime: true,
                    eventTimeFormat: {
                        hour: 'numeric',
                        minute: '2-digit',
                        meridiem: 'short'
                    }
                });

                calendar.render();
                console.log("FullCalendar initialized");
            });
        </script>
    </body>
</html>