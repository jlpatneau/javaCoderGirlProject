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
             <input type="hidden" name="command" value="UPDATE" />
             <input type="hidden" name="orderId" value="${THE_ORDER.orderId}" />
             <div class="form-group">
                <label for="newCustomer" class="col-sm-2 control-label">Customer: </label>
                <div class="col-sm-6">
                    <input type="hidden" name="custId" value="${THE_ORDER.customer.custId}" />
                    <input type="text" class="form-control" name="newCustomer" id="newCustomer" value="${THE_ORDER.customer.custName}" />
                </div>
             </div>
             <div class="form-group form-inline">
                <label for="newPymtType" class="col-sm-2 control-label">Payment Type: </label>
                <div class="col-sm-4">
                   <input type="text" class="form-control" id="newPymtType" name="newPymtType" value="${THE_ORDER.pymtType}"  />
                </div>
                <label for="newDeliveryDate" class="col-sm-2 control-label">Delivery date: </label>
                <div class="col-sm-4">
                    <input type="date" class="form-control" id="newDeliveryDate" name="newDeliveryDate" value="${THE_ORDER.deliveryDate}" />
                </div>
            </div>
            <div class="form-group form-inline">
                <label for="newLocation" class="col-sm-2 control-label">Location: </label>
                <div class="col-sm-4">
                    <input type="hidden" name="locId" value="${THE_ORDER.location.locationId}" />
                    <input type="text" class="form-control" id="newLocation" name="newLocation" value="${THE_ORDER.location.locationName}" />
                </div>
                <label for="taxRate" class="col-sm-2 control-label">Tax Rate: </label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="taxRate" name="taxRate" value="${THE_ORDER.location.taxRate}" readonly>
                </div>
            </div>

            <div class="form-group form-inline">
                <label for="taxableAmt" class="col-sm-2 control-label">Taxable Amt: </label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="taxableAmt" name="taxableAmt" value="${THE_ORDER.taxableAmt}" readonly>
                </div>
                <label for="salesTaxAmt" class="col-sm-2 control-label">Tax Amt: </label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="saleTaxAmt" name="saleTaxAmt" value="${THE_ORDER.salesTaxAmt}" readonly>
                </div>
            </div>
            <div class="form-group form-inline">
                <label for="totalAmt" class="col-sm-2 control-label">Total Amt: </label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="totalAmt" name="totalAmt" value="${THE_ORDER.orderTotalAmt}" readonly>
                </div>
                <label for="paidAmt" class="col-sm-2 control-label">Paid Amt: </label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="paidAmt" name="paidAmt" value="${THE_ORDER.paidAmt}">
                </div>
            </div>
            <div class="form-group">
                <label for="note" class="col-sm-2">Notes: </label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="note" name="notes" value="${THE_ORDER.orderNote}">
                </div>
            </div>

            <div class="form-group">
            <table class="table table-striped table-condensed">
                    <tr>
                        <th>Product</th>
                        <th>Color</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                        <c:forEach var="tempOrderProduct" items="${ORDER_PROD}">

                            <tr>
                                <td>
                                    <input type="hidden" name="prodId" value="${tempOrderProduct.product.prodId}" />
                                    <input type="text" name="prodName" class="form-control" value="${tempOrderProduct.product.prodName}" />
                                </td>
                                <td>
                                    <input type="hidden" name="colorId" value="${tempOrderProduct.color.colorId}" />
                                    <input type="text" name="colorName" class="form-control" value="${tempOrderProduct.color.colorName}" />
                                </td>
                                <td>
                                    <input type="text" name="qty" class="form-control" value="${tempOrderProduct.qty}" />
                                </td>
                                <td>
                                    <input type="text" name="price" class="form-control" value="${tempOrderProduct.salePrice}" />
                                </td>
                                <td>
                                    <button type="button" class="btn btn-primary" disabled="disabled">Update Item</button>
                                </td>
                            </tr>
                        </c:forEach>

            </table>
            </div>

            <div class="form-group">
                <div class="col-sm-2">
                    <button type="submit" class="btn btn-primary" disabled="disabled">Update Order</button>
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