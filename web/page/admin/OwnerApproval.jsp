<%-- 
    Document   : admindashboard
    Created on : Oct 17, 2024, 11:31:44 AM
    Author     : Kaan
--%>

<%@page import="util.RouterURL"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Owner Approval Requests</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Pending Owner Approval Requests</h1>
        <table class="table table-bordered table-responsive">
            <thead class="thead-light">
                <tr>
                    <th>Request ID</th>
                    <th>User ID</th>
                    <th>Request Date</th>
                    <th>Status</th>
                    <th>Reason</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="request" items="${pendingRequests}">
                    <tr>
                        <td>${request.requestID}</td>
                        <td>${request.userID}</td>
                        <td>${request.requestDate}</td>
                        <td>${request.status}</td>
                        <td>${request.reason}</td>
                        <td>
                            <form action="<%= RouterURL.ADMIN_OWNER_APPROVAL %>" method="post" class="form-inline">
                                <input type="hidden" name="requestID" value="${request.requestID}"/>
                                <select name="status" class="form-control mr-2" required>
                                    <option value="">Select...</option>
                                    <option value="approved">Approve</option>
                                    <option value="rejected">Reject</option>
                                </select>
                                <input type="text" name="reason" placeholder="Optional reason" class="form-control mr-2"/>
                                <input type="submit" value="Submit" class="btn btn-primary"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS and dependencies (Optional if using Bootstrap features that require JS) -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
