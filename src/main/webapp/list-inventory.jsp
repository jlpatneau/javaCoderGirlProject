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
        <input type="button" value="Add Sales Tax Rate" class="btn btn-primary"
                    onclick="window.location.href='add-location.jsp'; return false;"/>
        <p><p>

        <table class="table table-striped table-condensed">
            <tr>
                <th>Location Name</th>
                <th>State</th>
                <th>Tax rate</th>
                <th>Start date</th>
                <th>End date</th>
                <th>Action</th>
            </tr>
            <c:forEach var="tempLocation" items="${LOCATION_LIST}">

                <tr>
                    <td>${tempLocation.locationName}</td>
                    <td>${tempLocation.locationState}</td>
                    <td>${tempLocation.taxRate}</td>
                    <td>${tempLocation.taxStartDate}</td>
                    <td>${tempLocation.taxEndDate}</td>
                    <td>
                        <div class="form-inline">
                        <form class="form-group" action="/LocationServlet" method="GET">
                            <input type="hidden" name="command" value="LOAD">
                            <input type="hidden" name="locationId" value="${tempLocation.locationId}">
                            <button type="submit" class="btn btn-link">Update</button>
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