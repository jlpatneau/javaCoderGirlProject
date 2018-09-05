<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Patneau Ceramics</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link rel="stylesheet" href="ceramics.css" type="text/css">
    <script src="https://code.jquery.com/jquery-2.1.4.js"></script>
    <script src="add-order.js"></script>
</head>

<body>
    <!--Nav bar html-->
    <jsp:include page="ceramicsNav.html" />

    <div class="container">
        <form class="form-horizontal" action="/OrderServlet" method="POST" >
             <input type="hidden" name="command" value="ADD" />
             <!--<input type="hidden" name="orderId" value="${THE_ORDER.orderId}" /> -->
             <div class="form-group">
                <label for="newCustomer" class="col-sm-2 control-label">Customer: </label>
                <div class="col-sm-6">
                    <input type="hidden" name="custId" value="${THE_CUSTOMER.custId}" />
                    <input type="text" class="form-control" name="newCustomer" id="newCustomer" value="${THE_CUSTOMER.custName}" />
                </div>
             </div>
             <div class="form-group form-inline">
                <label for="newPymtType" class="col-sm-2 control-label">Payment Type: </label>
                <div class="col-sm-4">
                   <input type="text" class="form-control" id="newPymtType" name="newPymtType" placeholder="Payment Type"  required />
                </div>
                <label for="newDeliveryDate" class="col-sm-2 control-label">Delivery date: </label>
                <div class="col-sm-4">
                    <input type="date" class="form-control" id="newDeliveryDate"  value="newDeliveryDate"/>
                </div>
            </div>
            <div class="form-group form-inline">
                <label for="newLocation" class="col-sm-2 control-label">Location: </label>
                <div class="col-sm-4">
                <select class="form-control" id="locId" onchange="getTaxRate()" required>
                    <option value="">-Select Tax Location-</option>
                    <c:forEach var="locations" items="${LOCATIONS}">
                        <option value="${locations.locationId}" data-taxRate="${locations.taxRate}"> ${locations.locationName} </option>
                    </c:forEach>
                </select>
                </div>
                <label for="taxRate" class="col-sm-2 control-label">Tax Rate: </label>
                <div class="col-sm-4">
                    <input type="number" class="form-control" id="taxRate" name="taxRate" value="" readonly />
                </div>
            </div>

            <div class="form-group form-inline">
                <label for="taxableAmt" class="col-sm-2 control-label">Taxable Amt: </label>
                <div class="col-sm-4">
                    <input type="number" class="form-control" id="taxableAmt" name="taxableAmt" readonly />
                </div>
                <label for="salesTaxAmt" class="col-sm-2 control-label">Tax Amt: </label>
                <div class="col-sm-4">
                    <input type="number" class="form-control" id="salesTaxAmt" name="salesTaxAmt" readonly />
                </div>
            </div>
            <div class="form-group form-inline">
                <label for="totalAmt" class="col-sm-2 control-label">Total Amt: </label>
                <div class="col-sm-4">
                    <!-- <output name="totalAmt" id="totalAmt" for="taxableAmt salesTaxAmt" class="form-control">0 </output> -->
                    <input type="number" class="form-control" id="totalAmt" name="totalAmt" readonly />
                </div>
                <label for="paidAmt" class="col-sm-2 control-label">Paid Amt: </label>
                <div class="col-sm-4">
                    <input type="number" class="form-control" id="paidAmt" name="paidAmt" />
                </div>
            </div>
            <div class="form-group">
                <label for="note" class="col-sm-2">Notes: </label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="note" name="notes" />
                </div>
            </div>

            <div class="form-group">
            <table class="table table-striped table-condensed">
                    <tr>
                        <th>Product</th>
                        <th>Color</th>
                        <th>Quantity</th>
                        <th>Unit Price</th>
                        <th>Product Total</th>
                        <th>Action</th>
                    </tr>
                    <tbody id="addProductOrder">
                        <tr>
                            <td>
                                <select class="form-control" id="prodId" onchange="findPrice()">
                                    <option value=""> Product </option>
                                    <c:forEach var="products" items="${PRODUCTS}" >
                                        <option value="${products.prodId}" data-price="${products.prodPrice}"> ${products.prodName} </option>

                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <select class="form-control" id="colorId">
                                    <option value=""> Color </option>
                                    <c:forEach var="colors" items="${COLORS}" >
                                        <option value="${colors.colorId}"> ${colors.colorName} </option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <input type="number" min=0 name="qty" class="form-control" id="qty" value=0 />
                            </td>
                            <td>
                                <input type="number" min=0 name="price" class="form-control" id="price" />
                            </td>
                            <td>
                                <input type="number" name="prodTotal" class="form-control" id="prodTotal" readonly />
                            </td>
                            <td>
                                <div class="form-inline">
                                    <button type="button" class="btn btn-primary" id="updateTotal" onclick="calculateTotals('addProductOrder')">Update Total</button>
                                    <button type="button" class="btn btn-primary" id="addRow" onclick="addTableRow('addProductOrder')">Add Another Item</button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
            </table>
            </div>

            <div class="form-group">
                <div class="col-sm-2">
                    <button type="submit" class="btn btn-primary" disabled="disabled">Save Order</button>
                </div>
            </div>
        </form>

        <p>
            <a href="OrderServlet">Back to Order List</a>
        </p>


    </div>



<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>