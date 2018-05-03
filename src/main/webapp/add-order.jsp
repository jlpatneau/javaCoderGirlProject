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
        <form class="form-horizontal" action="/OrderServlet" method="POST">
             <input type="hidden" name="command" value="ADD"/>
             <div class="form-group">
                <label for="newOrder" class="col-sm-2 control-label">Customer: </label>
                <div class="col-sm-6">
                    <select name="newCustomer" class="form-control">
                        <option>Name</option>
                        <c:forEach var="tempCustomer" items="${CUST_LIST}">
                            <option value="${tempCustomer.custId}"> ${tempCustomer.custName}</option>
                        </c:forEach>
                    </select>
                </div>
             </div>

             <div class="form-group dropdown">
                <label for="newPymtType" class="col-sm-2 control-label">Payment Type: </label>
                <div class="col-sm-6">
                   <select name="newPymtType" id="newPymtType" class="form-control">
                           <option value="">Payment type</option>
                           <option value="Cash">Cash</option>
                           <option value="Check">Check</option>
                           <option value="Credit">Credit Card</option>
                           <option value="Paypal">Paypal</option>
                           <option value="Other">Other</option>
                   </select>
                </div>

            </div>
            <div class="form-group">
                <label for="newDeliveryDate" class="col-sm-2 control-label">Delivery date: </label>
                <div class="col-sm-4">
                    <input type="date" class="form-control" id="newDeliveryDate" name="newDeliveryDate" >
                </div>
             </div>

              <div class="form-group">
                <label for="newLocation" class="col-sm-2 control-label">Location: </label>
                <div class="col-sm-6">
                  <select name="newLocation" class="form-control">
                      <option>Tax Location</option>
                      <c:forEach var="tempLocation" items="${LOCATION_LIST}">
                          <option value="${tempLocation.locationId}"> ${tempLocation.locationName} </option>
                      </c:forEach>
                  </select>
                </div>
            </div>


            <div class="form-group">Items for Order:
            <table class="table table-striped table-condensed">
                    <tr>
                        <th>Product</th>
                        <th>Color</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>

                        <tr>
                            <td>
                                <select name="products" class="form-control">
                                    <option>Product</option>
                                    <c:forEach var="tempProducts" items="${PRODUCT_LIST}">
                                        <option value="${tempProducts.productId}"> ${tempProducts.productName} </option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <select name="colors" class="form-control">
                                    <option>Glaze</option>
                                    <c:forEach var="tempColors" items="${COLOR_LIST}">
                                        <option value="${tempColors.ColorId}"> ${tempColors.colorName} </option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <input type="text" name="qty" placeholder="qty" />
                            </td>
                            <td>$ ${tempOrder.paidAmt}
                                <input type="text" name="price" placeholder="price" />
                            </td>
                            <td>

                            </td>
                        </tr>
                    </c:forEach>


            </table>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </div>
        </form>

        <p>
            <a href="OrderServlet">Back to Order List</a>
        </p>


    </div>

<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>