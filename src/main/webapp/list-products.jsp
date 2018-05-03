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
        <form class="form-inline" action="/ProductsServlet" method="POST">
             <input type="hidden" name="command" value="ADD"/>
             <div class="form-group">
                <label for="newProduct">Add Product: </label>
                <input type="text" class="form-control" id="newProduct" name="newProduct" placeholder="Product">
              </div>
              <div class="form-group">
                  <label for="newPrice">Price: </label>
                  <input type="text" class="form-control" id="newPrice" name="newPrice" placeholder="Price">
              </div>
              <button type="submit" class="btn btn-primary">Save</button>
        </form>

        <table class="table table-striped table-condensed">
            <tr>
                <th>Product Name</th>
                <th>Price</th>
                <th>Action</th>
            </tr>
            <c:forEach var="tempProducts" items="${PROD_LIST}">

                <tr>
                    <td>${tempProducts.prodName}</td>
                    <td>$ ${tempProducts.prodPrice}</td>
                    <td>
                        <div class="form-inline">
                        <form class="form-group" action="/ProductsServlet" method="GET">
                            <input type="hidden" name="command" value="LOAD">
                            <input type="hidden" name="prodId" value="${tempProducts.prodId}">
                            <button type="submit" class="btn btn-link">Update</button>
                        </form>
                        <form class="form-group" action="/ProductsServlet" method="POST">
                            <input type="hidden" name="command" value="DELETE">
                            <input type="hidden" name="prodId" value="${tempProducts.prodId}">
                            <button type="submit" class="btn btn-link"
                                onclick="if (!(confirm('Are you sure you want to delete this product?'))) return false">
                                Delete</button>
                        </form>
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