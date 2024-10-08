<%-- 
    Document   : canteenItemUpload
    Created on : Oct 5, 2024, 6:20:43 PM
    Author     : Kaan
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.CanteenItem" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Canteen Items Management</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center">Canteen Items Management</h1>

            <form action="${pageContext.request.contextPath}/CanteenItemServlet" method="post" class="mb-4">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="cinemaID" value="${cinemaID}"> <!-- Sử dụng cinemaID -->
                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="name">Name</label>
                        <input type="text" name="name" class="form-control" required>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="price">Price</label>
                        <input type="number" step="0.01" name="price" class="form-control" required>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="stock">Stock</label>
                        <input type="number" name="stock" class="form-control" required>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="status">Status</label>
                        <select name="status" class="form-control">
                            <option value="true">Available</option> <!-- Giá trị cho status -->
                            <option value="false">Unavailable</option>
                        </select>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Add Canteen Item</button>
            </form>

            <h2>Existing Canteen Items</h2>
            <table class="table table-bordered table-striped">
                <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Stock</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${canteenItems}">
                        <tr>
                            <td>${item.canteenItemID}</td>
                            <td>${item.name}</td>
                            <td>${item.price}</td>
                            <td>${item.stock}</td>
                            <td>${item.status}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/CanteenItemServlet" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="canteenItemID" value="${item.canteenItemID}">
                                    <input type="hidden" name="cinemaID" value="${cinemaID}">
                                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                </form>
                                <button class="btn btn-warning btn-sm" onclick="editItem(${item.canteenItemID}, '${item.name}', ${item.price}, ${item.stock}, '${item.status}')">Edit</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <script>
                                    function editItem(id, name, price, stock, status) {
                                        document.querySelector('[name="action"]').value = 'update';
                                        document.querySelector('[name="canteenItemID"]').value = id;
                                        document.querySelector('[name="name"]').value = name;
                                        document.querySelector('[name="price"]').value = price;
                                        document.querySelector('[name="stock"]').value = stock;
                                        document.querySelector('[name="status"]').value = status; // Cập nhật status
                                        document.querySelector('button[type="submit"]').textContent = 'Update Canteen Item';
                                    }
        </script>
    </body>
</html>
