<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>500</title>
    <%@ include file="../include.jsp"%>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1>Oops!</h1>
                <h2>500 Something went wrong...</h2>
                <div class="error-actions">
                    <a href="controller?command=view_data" class="btn btn-primary btn-lg">
                        <span class="glyphicon glyphicon-list"></span>&nbsp;Back to data list</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
