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
             <input type="hidden" name="command" value="UPDATE" />
             <input type="hidden" name="prodId" value="${THE_PRODUCT.prodId}" />

                <label for="product">Update Product: </label>
                <input type="text" class="form-control" id="product"
                        name="newProdName" value="${THE_PRODUCT.prodName}" />

                <label for="prodPrice">Price: </label>
                <input type="text" class="form-control" id="prodPrice"
                        name="newProdPrice" value="${THE_PRODUCT.prodPrice}" />

              <button type="submit" class="btn btn-primary">Update</button>
        </form>
        <hr>
        <form class="form-inline" action="/ProductsServlet" method="GET">
            <input type="hidden" name="command" value="LIST" />
            <button type="submit" class="btn btn-primary">Back to List</button>
        </form>

    </div>


<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>