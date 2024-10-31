<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Thống kê</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/css/adminlte.min.css">
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    <style>
        .info-box {
            box-shadow: 0 0 1px rgba(0,0,0,.125), 0 1px 3px rgba(0,0,0,.2);
            border-radius: 0.25rem;
            background-color: #fff;
            display: flex;
            margin-bottom: 1rem;
            min-height: 80px;
            padding: .5rem;
            position: relative;
            width: 100%;
        }
        .info-box .info-box-icon {
            border-radius: 0.25rem;
            align-items: center;
            display: flex;
            font-size: 1.875rem;
            justify-content: center;
            text-align: center;
            width: 70px;
        }
        .info-box .info-box-content {
            display: flex;
            flex-direction: column;
            justify-content: center;
            line-height: 1.8;
            flex: 1;
            padding: 0 10px;
        }
        .info-box .info-box-number {
            display: block;
            margin-top: .25rem;
            font-weight: 700;
        }
        .chart-container {
            position: relative;
            height: 300px;
            background-color: #fff;
            padding: 1rem;
            border-radius: 0.25rem;
            box-shadow: 0 0 1px rgba(0,0,0,.125), 0 1px 3px rgba(0,0,0,.2);
            margin-bottom: 1rem;
        }
    </style>
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    <jsp:include page="sidebar.jsp" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <div class="container-fluid">
                <h1 data-aos="fade-right">Thống kê</h1>
            </div>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <!-- Info boxes -->
                <div class="row">
                    <div class="col-12 col-sm-6 col-md-3" data-aos="fade-up" data-aos-delay="100">
                        <div class="info-box">
                            <span class="info-box-icon bg-info"><i class="fas fa-shopping-cart"></i></span>
                            <div class="info-box-content">
                                <span class="info-box-text">Tổng doanh thu</span>
                                <span class="info-box-number" id="totalRevenue"></span>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-sm-6 col-md-3" data-aos="fade-up" data-aos-delay="200">
                        <div class="info-box">
                            <span class="info-box-icon bg-success"><i class="fas fa-ticket-alt"></i></span>
                            <div class="info-box-content">
                                <span class="info-box-text">Tổng số vé bán</span>
                                <span class="info-box-number" id="totalTickets"></span>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-sm-6 col-md-3" data-aos="fade-up" data-aos-delay="300">
                        <div class="info-box">
                            <span class="info-box-icon bg-warning"><i class="fas fa-film"></i></span>
                            <div class="info-box-content">
                                <span class="info-box-text">Số lượng phim</span>
                                <span class="info-box-number" id="totalMovies"></span>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-sm-6 col-md-3" data-aos="fade-up" data-aos-delay="400">
                        <div class="info-box">
                            <span class="info-box-icon bg-danger"><i class="fas fa-building"></i></span>
                            <div class="info-box-content">
                                <span class="info-box-text">Số lượng chuỗi rạp</span>
                                <span class="info-box-number" id="totalCinemaChains"></span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Charts -->
                <div class="row">
                    <div class="col-md-6" data-aos="fade-right">
                        <div class="chart-container">
                            <canvas id="revenuePercentageChart"></canvas>
                        </div>
                    </div>
                    <div class="col-md-6" data-aos="fade-left">
                        <div class="chart-container">
                            <canvas id="ticketsByCinemaChainChart"></canvas>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6" data-aos="fade-right">
                        <div class="chart-container">
                            <canvas id="ticketsByMovieChart"></canvas>
                        </div>
                    </div>
                    <div class="col-md-6" data-aos="fade-left">
                        <div class="chart-container">
                            <canvas id="revenueByCinemaChainChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <footer class="main-footer">
        <strong>Copyright &copy; 2024 <a href="#">Unove Cinema</a>.</strong>
        All rights reserved.
    </footer>
</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/admin-lte@3.1/dist/js/adminlte.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>

<script>
    // Khởi tạo AOS
    AOS.init({
        duration: 1000,
        once: true
    });

    // Đặt dữ liệu JSON vào biến JavaScript global
    var revenueByCinemaChainJson = '${revenueByCinemaChainJson}';
    var ticketsSoldByCinemaChainJson = '${ticketsSoldByCinemaChainJson}';
    var ticketsSoldByMovieJson = '${ticketsSoldByMovieJson}';
    var revenueAndPercentageByCinemaChainJson = '${revenueAndPercentageByCinemaChainJson}';
    var totalRevenueJson = '${totalRevenueJson}';
    var revenueByMovieAndChainJson = '${revenueByMovieAndChainJson}';

    // Hàm parse JSON an toàn
    function safeJSONParse(jsonString, defaultValue) {
        try {
            return JSON.parse(jsonString.replace(/&quot;/g, '"'));
        } catch (e) {
            console.error("Error parsing JSON:", e);
            return defaultValue;
        }
    }

    // Parse dữ liệu JSON
    var revenueByCinemaChain = safeJSONParse(revenueByCinemaChainJson, {});
    var ticketsSoldByCinemaChain = safeJSONParse(ticketsSoldByCinemaChainJson, {});
    var ticketsSoldByMovie = safeJSONParse(ticketsSoldByMovieJson, {});
    var revenueAndPercentageByCinemaChain = safeJSONParse(revenueAndPercentageByCinemaChainJson, {});
    var totalRevenue = safeJSONParse(totalRevenueJson, 0);
    var revenueByMovieAndChain = safeJSONParse(revenueByMovieAndChainJson, {});

    // Hiển thị tổng doanh thu
    document.getElementById('totalRevenue').textContent = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalRevenue);
    document.getElementById('totalTickets').textContent = Object.values(ticketsSoldByCinemaChain).reduce((a, b) => a + b, 0);
    document.getElementById('totalMovies').textContent = Object.keys(ticketsSoldByMovie).length;
    document.getElementById('totalCinemaChains').textContent = Object.keys(revenueByCinemaChain).length;

    // Cấu hình chung cho biểu đồ
    var chartOptions = {
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
                grid: {
                    color: 'rgba(0, 0, 0, 0.1)'
                }
            }
        },
        plugins: {
            legend: {
                position: 'top',
                labels: {
                    boxWidth: 12,
                    padding: 10
                }
            }
        },
        animation: {
            duration: 1000,
            easing: 'easeOutQuart'
        }
    };

    var colors = {
        revenue: 'rgba(60, 141, 188, 0.8)',
        percentage: 'rgba(210, 214, 222, 0.8)',
        tickets: 'rgba(0, 192, 239, 0.8)',
        movies: 'rgba(243, 156, 18, 0.8)'
    };

    // Biểu đồ doanh thu và phần trăm theo chuỗi rạp
    var revenuePercentageCtx = document.getElementById('revenuePercentageChart').getContext('2d');
    var labels = Object.keys(revenueAndPercentageByCinemaChain);
    var revenueData = labels.map(label => revenueAndPercentageByCinemaChain[label].revenue);
    var percentageData = labels.map(label => revenueAndPercentageByCinemaChain[label].percentage);

    new Chart(revenuePercentageCtx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Doanh thu',
                data: revenueData,
                backgroundColor: colors.revenue,
                borderColor: colors.revenue,
                borderWidth: 1,
                yAxisID: 'y-axis-1'
            }, {
                label: 'Phần trăm',
                data: percentageData,
                backgroundColor: colors.percentage,
                borderColor: colors.percentage,
                borderWidth: 1,
                yAxisID: 'y-axis-2',
                type: 'line'
            }]
        },
        options: {
            ...chartOptions,
            plugins: {
                ...chartOptions.plugins,
                title: {
                    display: true,
                    text: 'Doanh thu và phần trăm theo chuỗi rạp'
                }
            },
            scales: {
                ...chartOptions.scales,
                'y-axis-1': {
                    type: 'linear',
                    position: 'left',
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Doanh thu (VNĐ)'
                    },
                    grid: {
                        color: 'rgba(0, 0, 0, 0.1)'
                    }
                },
                'y-axis-2': {
                    type: 'linear',
                    position: 'right',
                    beginAtZero: true,
                    max: 100,
                    title: {
                        display: true,
                        text: 'Phần trăm (%)'
                    },
                    grid: {
                        display: false
                    }
                }
            },
            barPercentage: 0.5,
            categoryPercentage: 0.7
        }
    });

    // Biểu đồ số vé bán được theo chuỗi rạp
    var ticketsByCinemaChainCtx = document.getElementById('ticketsByCinemaChainChart').getContext('2d');
    new Chart(ticketsByCinemaChainCtx, {
        type: 'bar',
        data: {
            labels: Object.keys(ticketsSoldByCinemaChain),
            datasets: [{
                label: 'Số vé bán được',
                data: Object.values(ticketsSoldByCinemaChain),
                backgroundColor: colors.tickets,
                borderColor: colors.tickets,
                borderWidth: 1
            }]
        },
        options: {
            ...chartOptions,
            plugins: {
                ...chartOptions.plugins,
                title: {
                    display: true,
                    text: 'Số vé bán được theo chuỗi rạp'
                }
            },
            barPercentage: 0.5,
            categoryPercentage: 0.7
        }
    });

    // Biểu đồ số vé bán được và doanh thu theo phim của tất cả chuỗi rạp
    var ticketsAndRevenueByMovieCtx = document.getElementById('ticketsByMovieChart').getContext('2d');
    
    var movieLabels = Object.keys(ticketsSoldByMovie);
    var ticketsData = Object.values(ticketsSoldByMovie);
    
    var datasets = [{
        label: 'Số vé bán được',
        data: ticketsData,
        backgroundColor: colors.movies,
        borderColor: colors.movies,
        borderWidth: 1,
        yAxisID: 'y-axis-tickets'
    }];
    
    var chainColors = {
        'CGV': 'rgba(255, 99, 132, 0.7)',
        'Lotte': 'rgba(54, 162, 235, 0.7)',
        'Galaxy': 'rgba(255, 206, 86, 0.7)',
        // Thêm màu cho các chuỗi rạp khác nếu cần
    };
    
    Object.keys(revenueByMovieAndChain).forEach((chainName, index) => {
        var chainData = movieLabels.map(movie => revenueByMovieAndChain[chainName][movie] || 0);
        datasets.push({
            label: `Doanh thu ${chainName} (VNĐ)`,
            data: chainData,
            backgroundColor: chainColors[chainName] || `rgba(${Math.random()*255}, ${Math.random()*255}, ${Math.random()*255}, 0.7)`,
            borderColor: chainColors[chainName] || `rgba(${Math.random()*255}, ${Math.random()*255}, ${Math.random()*255}, 1)`,
            borderWidth: 1,
            yAxisID: 'y-axis-revenue'
        });
    });
    
    new Chart(ticketsAndRevenueByMovieCtx, {
        type: 'bar',
        data: {
            labels: movieLabels,
            datasets: datasets
        },
        options: {
            ...chartOptions,
            plugins: {
                ...chartOptions.plugins,
                title: {
                    display: true,
                    text: 'Số vé bán được và doanh thu theo phim của tất cả chuỗi rạp'
                }
            },
            scales: {
                x: {
                    ...chartOptions.scales.x,
                    stacked: false
                },
                'y-axis-tickets': {
                    type: 'linear',
                    position: 'left',
                    title: {
                        display: true,
                        text: 'Số vé'
                    },
                    grid: {
                        drawOnChartArea: false
                    }
                },
                'y-axis-revenue': {
                    type: 'linear',
                    position: 'right',
                    title: {
                        display: true,
                        text: 'Doanh thu (VNĐ)'
                    },
                    grid: {
                        drawOnChartArea: false
                    }
                }
            },
            barPercentage: 0.8,
            categoryPercentage: 0.9
        }
    });

    // Biểu đồ doanh thu theo chuỗi rạp
    var revenueByCinemaChainCtx = document.getElementById('revenueByCinemaChainChart').getContext('2d');
    new Chart(revenueByCinemaChainCtx, {
        type: 'bar',
        data: {
            labels: Object.keys(revenueByCinemaChain),
            datasets: [{
                label: 'Doanh thu',
                data: Object.values(revenueByCinemaChain),
                backgroundColor: colors.revenue,
                borderColor: colors.revenue,
                borderWidth: 1
            }]
        },
        options: {
            ...chartOptions,
            plugins: {
                ...chartOptions.plugins,
                title: {
                    display: true,
                    text: 'Doanh thu theo chuỗi rạp'
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Doanh thu (VNĐ)'
                    }
                }
            },
            barPercentage: 0.5,
            categoryPercentage: 0.7
        }
    });
</script>
</body>
</html>