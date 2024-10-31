<%-- 
    Document   : OwnerRegist
    Created on : Oct 19, 2024, 10:05:55 AM
    Author     : Kaan
--%>

<%@page import="util.RouterURL"%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Owner Registration Approval</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Optional: Style for loading indicators and error messages */
        .loading {
            display: none;
        }
        .validation-error {
            color: #dc3545;
            font-size: 0.875em;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h1>Owner Registration Approval</h1>

        <!-- Display success or error messages -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <!-- Pending Requests Table -->
        <c:if test="${not empty pendingRequests}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Username</th> <!-- New column for Username -->
                        <th>Email</th> <!-- New column for Email -->
                        <th>Tax ID</th>
                        <th>Business License</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="request" items="${pendingRequests}">
                        <tr id="request-${request.requestID}">
                            <td>${request.username}</td> <!-- Display Username -->
                            <td>${request.email}</td> <!-- Display Email -->
                            <td>${request.taxNumber}</td>
                            <td>
                                <img src="${request.businessLicenseFile}" alt="Business License" style="width: 100px; height: auto;" />
                            </td>
                            <td>${request.status}</td>
                            <td>
                                <form class="approval-form" method="post">
                                    <input type="hidden" name="requestID" value="${request.requestID}" />
                                    <select name="status" class="form-control status-select" required>
                                        <option value="">Select...</option>
                                        <option value="approved">Approve</option>
                                        <option value="rejected">Reject</option>
                                    </select>
                                    <div class="validation-error status-error" style="display: none;">Please select a status.</div>
                                    <input type="text" name="reason" placeholder="Optional reason" class="form-control mt-2" />
                                    <button type="submit" class="btn btn-primary mt-2 submit-button">Submit</button>
                                    <span class="loading ml-2">Processing...</span>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty pendingRequests}">
            <div class="alert alert-warning">No pending owner registration requests at the moment.</div>
        </c:if>

        <!-- Handled Requests Section -->
        <h2 class="mt-5">Request History</h2>
        <table class="table" id="handledRequestsTable">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Tax ID</th>
                    <th>Business License</th>
                    <th>Status</th>
                    <th>Reason</th>
                </tr>
            </thead>
            <tbody id="handledRequests">
                <c:forEach var="handledRequest" items="${handledRequests}">
                    <tr>
                        <td>${handledRequest.username}</td>
                        <td>${handledRequest.email}</td>
                        <td>${handledRequest.taxNumber}</td>
                        <td>
                            <img src="${handledRequest.businessLicenseFile}" alt="Business License" style="width: 100px; height: auto;" />
                        </td>
                        <td>${handledRequest.status}</td>
                        <td>${handledRequest.reason}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script>
        $(document).ready(function() {
            // Handle form submission
            $('.approval-form').on('submit', function(e) {
                e.preventDefault(); // Prevent default form submission

                var $form = $(this);
                var requestID = $form.find('input[name="requestID"]').val();
                var status = $form.find('select[name="status"]').val();
                var reason = $form.find('input[name="reason"]').val();

                // Check if a valid status is selected
                if (!status) {
                    $form.find('.status-error').show();
                    return;
                } else {
                    $form.find('.status-error').hide();
                }

                // Disable the button and show loading indicator
                $form.find('.submit-button').prop('disabled', true);
                $form.find('.loading').show();

                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/ownerApproval",
                    type: "POST",
                    data: {
                        requestID: requestID,
                        status: status,
                        reason: reason
                    },
                    success: function(response) {
                        // On success, remove the row from pending requests
                        $('#request-' + requestID).remove();

                        // Add the handled request to the handled requests table
                        var newRow = `<tr>
                                        <td>${$form.closest('tr').find('td:eq(0)').text()}</td>
                                        <td>${$form.closest('tr').find('td:eq(1)').text()}</td>
                                        <td>${$form.closest('tr').find('td:eq(2)').text()}</td>
                                        <td>${$form.closest('tr').find('td:eq(3)').html()}</td>
                                        <td>${status}</td>
                                        <td>${reason}</td>
                                    </tr>`;
                        $('#handledRequests').append(newRow);
                    },
                    error: function(xhr, status, error) {
                        // Display inline error
                        $form.append(`<div class="validation-error">Error occurred: ${error}</div>`);
                    },
                    complete: function() {
                        // Re-enable button and hide loading indicator after request is complete
                        $form.find('.submit-button').prop('disabled', false);
                        $form.find('.loading').hide();
                    }
                });
            });
        });
    </script>
</body>
</html>


