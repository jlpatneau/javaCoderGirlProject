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
        <form class="form-horizontal" action="/LocationServlet" method="POST">
             <input type="hidden" name="command" value="UPDATE"/>
             <input type="hidden" name="locationId" value="${THE_LOCATION.locationId}">
             <div class="form-group">
                <label for="newLocation" class="col-sm-2 control-label">Location: </label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="newLocation" name="newLocation" value="${THE_LOCATION.locationName}">
                </div>
              </div>
              <div class="form-group">
                  <label for="newState" class="col-sm-2 control-label">State: </label>
                  <div class="col-sm-10">
                    <input type="text" class="form-control" id="newState" name="newState" value="${THE_LOCATION.locationState}">
                  </div>
              </div>
              <div class="form-group">
                <label for="newTaxRate" class="col-sm-2 control-label">Sales Tax Rate: </label>
                <div class="col-sm-10">
                <input type="text" class="form-control" id="newTaxRate" name="newTaxRate" value="${THE_LOCATION.taxRate}">
                </div>
              </div>
              <div class="form-group">
                <label for="newStartDate" class="col-sm-2 control-label">Sales tax start date: </label>
                <div class="col-sm-10">
                    <input type="date" class="form-control" id="newStartDate" name="newStartDate" value="${THE_LOCATION.taxStartDate}" required>
                </div>
              </div>
              <div class="form-group">
                  <label for="newEndDate" class="col-sm-2 control-label">Sales tax end date: </label>
                  <div class="col-sm-10">
                    <input type="date" class="form-control" id="newEndDate" name="newEndDate" value="${THE_LOCATION.taxEndDate}">
                  </div>
              </div>
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-primary">Update</button>
                </div>
              </div>
        </form>

        <p>
            <a href="LocationServlet">Back to List</a>
        </p>

    </div>


<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>