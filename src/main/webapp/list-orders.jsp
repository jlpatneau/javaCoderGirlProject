<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Patneau Ceramics</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link rel="stylesheet" href="ceramics.css" type="text/css">

</head>

<body>
    <!--Nav bar html-->
    <jsp:include page="ceramicsNav.html" />

    <div class="container">
        <input type="button" value="Add New Order" class="btn btn-primary"
                    onclick="window.location.href='add-order.jsp'; return false;" disabled="disabled"/>
        <p><p>

        <table class="table table-striped table-condensed">
            <tr>
                <th>Order #</th>
                <th>Date </th>
                <th>Customer</th>
                <th>Total</th>
                <th>Delivery Date</th>
                <th>Amount Paid</th>
                <th>Action</th>
            </tr>
            <c:forEach var="tempOrder" items="${ORDER_LIST}">

                <tr>
                    <td>${tempOrder.orderId}</td>
                    <td>${tempOrder.orderDate}</td>
                    <td>${tempOrder.customer.custName}</td>
                    <td>$ ${tempOrder.orderTotalAmt}</td>
                    <td>${tempOrder.deliveryDate}</td>
                    <td>$ ${tempOrder.paidAmt}</td>
                    <td>
                        <div class="form-inline">
                        <form class="form-group" action="/OrderServlet" method="GET">
                            <input type="hidden" name="command" value="LOAD">
                            <input type="hidden" name="orderId" value="${tempOrder.orderId}">
                            <button type="submit" class="btn btn-link">View Details</button>
                        </form>
                        <!--
                        <form class="form-group" action="/OrderServlet" method="POST">
                            <input type="hidden" name="command" value="DELETE">
                            <input type="hidden" name="orderId" value="${tempOrder.orderId}">
                            <button type="submit" class="btn btn-link" disabled="disabled">Delete</button>
                        </form>
                        -->
                        </div>
                    </td>
                </tr>
            </c:forEach>


        </table>
    </div>

<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>