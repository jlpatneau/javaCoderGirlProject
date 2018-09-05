<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Patneau Ceramics</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link rel="stylesheet" href="ceramics.css" type="text/css">
    <script src="https://code.jquery.com/jquery-2.1.4.js"></script>

</head>

<body>
    <!--Nav bar html-->
    <jsp:include page="ceramicsNav.html" />

    <div class="container">
        <form class="form-inline" action="/CustomerServlet" method="POST">
             <input type="hidden" name="command" value="ADD"/>
             <div class="form-group">
                <label for="newCustomer">Add Customer: </label>
                <input type="text" class="form-control" id="newCustomer" name="newCustomer" placeholder="Name">
              </div>
              <div class="form-group">
                  <label for="newEmail">Email: </label>
                  <input type="email" class="form-control" id="newEmail" name="newEmail" placeholder="Email address">
              </div>
              <div class="checkbox">
                  <label>
                    <input type="checkbox" name="newMailing" id="newMailing" disabled> Receive news?
                  </label>
                </div>
              <button type="submit" class="btn btn-primary" id="saveNew">Save</button>
        </form>

        <table class="table table-striped table-condensed">
            <tr>
                <th>Customer Name</th>
                <th>Email</th>
                <th>Mailing</th>
                <th>Action</th>
            </tr>
            <c:forEach var="tempCustomer" items="${CUSTOMER_LIST}">

                <tr>
                    <td>${tempCustomer.custName}</td>
                    <td>${tempCustomer.custEmail}</td>
                    <td>${tempCustomer.mailing ? 'Yes' : 'No'}</td>
                    <td>
                        <div class="form-inline">
                        <form class="form-group" action="/CustomerServlet" method="GET">
                                <input type="hidden" name="command" value="LOAD">
                                <input type="hidden" name="custId" value="${tempCustomer.custId}">
                                <button type="submit" class="btn btn-link">Update</button>
                        </form>
                        <form class="form-group" action="/CustomerServlet" method="POST">
                                <input type="hidden" name="command" value="DELETE">
                                <input type="hidden" name="custId" value="${tempCustomer.custId}">
                                <button type="submit" class="btn btn-link"
                                     onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">
                                     Delete</button>
                        </form>
                        <form class="form-group" action="/OrderServlet" method="GET">
                            <input type="hidden" name="command" value="ADDCURRENT">
                            <input type="hidden" name="custId" value="${tempCustomer.custId}">
                            <button type="submit" class="btn btn-primary" >New Order</button>
                        </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>


        </table>
    </div>

    <script type="text/javascript" src="customer.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>