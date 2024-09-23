
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/user/UserInfoCss/bootstrap.min.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/user/UserInfoCss/displayUserInfoCss.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/user/UserInfoCss/style.css"/>
        <title>Display User Page</title>

        <style>

            /* General Body Styling */
            /* General Body Styling */
            body {
                font-family: 'Poppins', sans-serif;
                background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                color: #333;
                line-height: 1.8;
            }

            .container {
                max-width: 1000px;
                margin: 80px auto;
                padding: 40px;
                background-color: rgba(255, 255, 255, 0.95);
                border-radius: 20px;
                box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
                backdrop-filter: blur(10px);
            }

            h3 {
                color: #2c3e50;
                font-size: 42px;
                font-weight: 700;
                text-align: center;
                margin-bottom: 50px;
                text-transform: uppercase;
                letter-spacing: 3px;
                position: relative;
            }

            h3::after {
                content: '';
                display: block;
                width: 80px;
                height: 4px;
                background: linear-gradient(to right, #3498db, #2ecc71);
                margin: 15px auto 0;
                border-radius: 2px;
            }

            .user-avatar {
                width: 180px;
                height: 180px;
                background-color: #e0e0e0;
                background-size: cover;
                background-position: center;
                border-radius: 50%;
                margin: 0 auto 40px;
                box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
                border: 8px solid #fff;
                transition: all 0.4s ease;
            }

            .user-avatar:hover {
                transform: scale(1.05) rotate(5deg);
                box-shadow: 0 15px 35px rgba(52, 152, 219, 0.3);
            }

            .user-info {
                text-align: center;
                padding: 40px;
                margin-bottom: 50px;
                background-color: #fff;
                border-radius: 20px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
                transition: transform 0.3s ease;
            }

            .user-info:hover {
                transform: translateY(-5px);
            }

            .form-group label {
                font-weight: 600;
                color: #34495e;
                margin-bottom: 10px;
                font-size: 18px;
            }

            .form-control {
                border: 2px solid #e0e0e0;
                padding: 15px 20px;
                border-radius: 12px;
                font-size: 16px;
                transition: all 0.3s ease;
                background-color: #f8f9fa;
            }

            .form-control:focus {
                border-color: #3498db;
                box-shadow: 0 0 0 4px rgba(52, 152, 219, 0.25);
                background-color: #fff;
            }

            .css_select {
                background-color: #f8f9fa;
                border: 2px solid #e0e0e0;
                border-radius: 12px;
                padding: 15px 20px;
                font-size: 16px;
                transition: all 0.3s ease;
                appearance: none;
                background-image: url('data:image/svg+xml;utf8,<svg fill="%23333" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/><path d="M0 0h24v24H0z" fill="none"/></svg>');
                background-repeat: no-repeat;
                background-position: right 15px top 50%;
            }

            .css_select:focus {
                border-color: #3498db;
                box-shadow: 0 0 0 4px rgba(52, 152, 219, 0.25);
                background-color: #fff;
            }

            .file-input {
                width: 100%;
                padding: 25px;
                background-color: #f1f8ff;
                border: 3px dashed #3498db;
                text-align: center;
                font-size: 18px;
                color: #3498db;
                border-radius: 15px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .file-input:hover {
                background-color: #e1f0ff;
                border-color: #2980b9;
            }


            .submit-button {
                width: 100%;
                background: linear-gradient(to right, #3498db, #2980b9);
                color: white;
                padding: 16px;
                border: none;
                border-radius: 12px;
                font-size: 20px;
                font-weight: 600;
                cursor: pointer;
                margin-top: 20px;
                transition: all 0.3s ease;
            }

            .submit-button:hover {
                background: linear-gradient(to right, #2980b9, #3498db);
                transform: translateY(-3px);
                box-shadow: 0 7px 20px rgba(52, 152, 219, 0.3);
            }

            button.btn-primary {
                background: linear-gradient(to right, #2ecc71, #27ae60);
                border: none;
                padding: 16px 30px;
                font-size: 18px;
                border-radius: 12px;
                color: white;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            button.btn-primary:hover {
                background: linear-gradient(to right, #27ae60, #2ecc71);
                transform: translateY(-3px);
                box-shadow: 0 7px 20px rgba(46, 204, 113, 0.4);
            }

            button.cpw {
                background: linear-gradient(to right, #f39c12, #d35400);
                padding: 16px 30px;
                border: none;
                color: white;
                font-weight: 600;
                font-size: 18px;
                border-radius: 12px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            button.cpw:hover {
                background: linear-gradient(to right, #d35400, #f39c12);
                transform: translateY(-3px);
                box-shadow: 0 7px 20px rgba(243, 156, 18, 0.4);
            }

            button.return-btn {
                background: linear-gradient(to right, #95a5a6, #7f8c8d);
                padding: 16px 30px;
                border: none;
                color: white;
                font-weight: 600;
                font-size: 18px;
                border-radius: 12px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            button.return-btn:hover {
                background: linear-gradient(to right, #7f8c8d, #95a5a6);
                transform: translateY(-3px);
                box-shadow: 0 7px 20px rgba(149, 165, 166, 0.4);
            }

            @media (max-width: 768px) {
                .container {
                    padding: 30px;
                    margin: 40px auto;
                }

                h3 {
                    font-size: 32px;
                }

                .user-avatar {
                    width: 140px;
                    height: 140px;
                }

                .user-info {
                    padding: 30px;
                }

                .form-control, .css_select {
                    font-size: 16px;
                    padding: 12px 15px;
                }

                .submit-button, .btn-primary, .cpw, .return-btn {
                    font-size: 18px;
                    padding: 14px 25px;
                }
            }


        </style>
    </head>

    <body>
        <script src="https://esgoo.net/scripts/jquery.js"></script>

        <jsp:include page="../landingPage/Header.jsp" />

        <div class="d-lg-flex-half">



            <div class="contents order-2 order-md-1">

                <div class="container">
                    <div class="row align-items-center justify-content-center" style ="background-color: ghostwhite">
                        <div class="col-md-7 py-3">
                            <div class="col-md-6" style ="margin-left :174px;">

                                <div class="user-avatar" style="background-image: url('${user.getAvatarLink()}') " >
                                </div>

                                <div class="container">
                                    <form method="post" action="avatar" enctype="multipart/form-data">
                                        <input type="file" name="file" class="file-input" />
                                        <input type="submit" value="Upload" class="submit-button" />
                                    </form>
                                </div>
                            </div>
                            <h3 class="text-center my-5">View User's Information</h3>

                            <form action="/Unove/update" method="post">
                                <div class="hide">
                                    <input type="text" id="username" name="userId" value="${user.getUserID()}" >
                                </div>

                                <div class="row ">
                                    <div class="col-md-6 hide">
                                        <div class="form-group first">
                                            <label for="fname">Username</label>
                                            <input type="text" class="form-control" id="fname" name="username" readonly value="${user.getUsername()}" >
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="fname">Full Name</label>
                                            <input type="text" class="form-control" id="fname" name="fullname" value="${user.getFullName()}">
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">Email</label>
                                            <input type="text" class="form-control" id="lname" name="email" readonly value="${user.getEmail()}" >
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">Birthday</label>
                                            <input type="date" class="form-control" id="lname" name="birthday" value="${user.getBirthday()}">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">Province</label>
                                            <select class="form-control css_select" id="tinh" name="province" title="Chọn Tỉnh Thành">
                                                <option value="${user.getProvince()}">${user.getProvince()}</option>
                                            </select>
                                            <!-- Hidden field to store selected province name -->
                                            <input type="hidden" id="provinceName" name="provinceName" value="${user.getProvince()}">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="fname">Address</label>
                                            <input type="text" class="form-control" id="fname" name="address" value="${user.getAddress()}">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">District</label>
                                            <select class="form-control css_select" id="quan" name="district" title="Chọn Quận Huyện">
                                                <option value="${user.getDistrict()}">${user.getDistrict()}</option>
                                            </select>
                                            <!-- Hidden field to store selected district name -->
                                            <input type="hidden" id="districtName" name="districtName" value="${user.getDistrict()}">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 me-auto">
                                        <div class="form-group first">
                                            <label for="lname">Commune</label>
                                            <select class="form-control css_select" id="phuong" name="commune" title="Chọn Phường Xã">
                                                <option value="${user.getCommune()}">${user.getCommune()}</option>
                                            </select>
                                            <!-- Hidden field to store selected commune name -->
                                            <input type="hidden" id="communeName" name="communeName" value="${user.getCommune()}">
                                        </div>
                                    </div>
                                </div>
                                <div class="row"></div>
                                <div class="d-flex mb-2  align-items-center">
                                    <div class="d-flex align-items-center"></div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        <button class="btn px-5 btn-primary " type="submit"> Update now </button>
                                    </div>
                                </div>
                            </form>

                            <div class="row justify-content-end">

                                <div class="col-3">
                                    <form action="${pageContext.request.contextPath}/page/user/ChangePassword.jsp" class="text-center">
                                        <button class="btn p-1 btn-warning cpw ms-auto">Change Password </button>
                                    </form>
                                </div>

                                <div class="col-3">
                                    <form action="${pageContext.request.contextPath}"  class="text-center">
                                        <button class="btn  btn-warning cpw">Return </button>
                                    </form>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                // Fetch provinces
                $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
                    if (data_tinh.error === 0) {
                        $.each(data_tinh.data, function (key_tinh, val_tinh) {
                            $("#tinh").append('<option value="' + val_tinh.id + '">' + val_tinh.full_name + '</option>');
                        });

                        // Set the default selected province text
                        var defaultProvince = '${user.getProvince()}';
                        if (defaultProvince) {
                            $("#tinh").val(defaultProvince);
                        }

                        // On province change
                        $("#tinh").change(function () {
                            var idtinh = $(this).val();
                            var selectedProvince = $("#tinh option:selected").text();
                            $("#provinceName").val(selectedProvince); // Update hidden field with selected name

                            // Fetch districts
                            $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                                if (data_quan.error === 0) {
                                    $("#quan").html('<option value="0">Quận Huyện</option>');
                                    $("#phuong").html('<option value="0">Phường Xã</option>');
                                    $.each(data_quan.data, function (key_quan, val_quan) {
                                        $("#quan").append('<option value="' + val_quan.id + '">' + val_quan.full_name + '</option>');
                                    });

                                    // Set the default selected district text
                                    var defaultDistrict = '${user.getDistrict()}';
                                    if (defaultDistrict) {
                                        $("#quan").val(defaultDistrict);
                                    }

                                    // On district change
                                    $("#quan").change(function () {
                                        var idquan = $(this).val();
                                        var selectedDistrict = $("#quan option:selected").text();
                                        $("#districtName").val(selectedDistrict); // Update hidden field with selected name

                                        // Fetch wards
                                        $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                                            if (data_phuong.error === 0) {
                                                $("#phuong").html('<option value="0">Phường Xã</option>');
                                                $.each(data_phuong.data, function (key_phuong, val_phuong) {
                                                    $("#phuong").append('<option value="' + val_phuong.id + '">' + val_phuong.full_name + '</option>');
                                                });

                                                // Set the default selected ward text
                                                var defaultWard = '${user.getCommune()}';
                                                if (defaultWard) {
                                                    $("#phuong").val(defaultWard);
                                                }

                                                // On ward change
                                                $("#phuong").change(function () {
                                                    var selectedWard = $("#phuong option:selected").text();
                                                    $("#communeName").val(selectedWard); // Update hidden field with selected name
                                                });
                                            }
                                        });
                                    });
                                }
                            });
                        });
                    }
                });
            });
        </script>

    </body>
</html>
