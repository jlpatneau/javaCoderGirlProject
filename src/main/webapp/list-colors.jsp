<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Patneau Ceramics</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link rel="stylesheet" href="/ceramics.css" type="text/css">

</head>

<body>
    <!--Nav bar html-->
    <jsp:include page="ceramicsNav.html" />

    <div class="container">

        <form class="form-inline" action="/ColorServlet" method="POST">
             <input type="hidden" name="command" value="ADD"/>
                <div class="form-group">
                    <label for="color">Add Color: </label>
                    <input type="text" class="form-control" id="color" name="newColor" placeholder="Color">
                </div>
              <button type="submit" class="btn btn-primary">Save</button>
        </form>
        <table class="table table-striped table-condensed">
            <tr>
                <th>Glaze color</th>
                <th>Action</th>
            </tr>


            <c:forEach var="tempColor" items="${COLOR_LIST}">
                <tr>
                    <td>${tempColor.colorName}</td>
                    <td>
                        <div class="form-inline">
                            <form class="form-group" action="/ColorServlet" method="GET">
                                <input type="hidden" name="command" value="LOAD">
                                <input type="hidden" name="colorId" value="${tempColor.colorId}">
                                <button type="submit" class="btn btn-link">Update</button>
                            </form>
                            <form class="form-group" action="/ColorServlet" method="POST">
                                <input type="hidden" name="command" value="DELETE">
                                <input type="hidden" name="colorId" value="${tempColor.colorId}">
                                <button type="submit" class="btn btn-link"
                                     onclick="if (!(confirm('Are you sure you want to delete this color'))) return false">
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