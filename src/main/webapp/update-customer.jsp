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

        <form class="form-inline" action="/CustomerServlet" method="POST">
             <input type="hidden" name="command" value="UPDATE" />
             <input type="hidden" name="custId" value="${THE_CUSTOMER.custId}" />

                <label for="customer">Update Customer: </label>
                <input type="text" class="form-control" id="customer"
                        name="newCustName" value="${THE_CUSTOMER.custName}" />

                <label for="custEmail">Email: </label>
                <input type="email" class="form-control" id="custEmail"
                        name="newCustEmail" value="${THE_CUSTOMER.custEmail}" />
                <div class="checkbox">
                  <label for="mailing">Recieve News?</label>
                    <input type="checkbox" name="newMailing" id="mailing"
                        <c:if test="${THE_CUSTOMER.mailing}">checked="checked"</c:if>
                        />
                  </div>
              <button type="submit" class="btn btn-primary">Update</button>
        </form>
        <hr>
        <form class="form-inline" action="/CustomerServlet" method="GET">
            <input type="hidden" name="command" value="LIST" />
            <button type="submit" class="btn btn-primary">Back to List</button>
        </form>

    </div>


<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>