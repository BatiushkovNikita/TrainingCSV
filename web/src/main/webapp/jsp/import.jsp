<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Import</title>
    <%@ include file="include.jsp" %>
</head>
<body>
<%@ include file="fragments/header.jsp" %>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading"><strong><fmt:message key="import.upload.file"/></strong></div>
        <div class="panel-body">
            <h4><fmt:message key="import.select.file"/></h4>

            <form action="controller" method="POST" enctype="multipart/form-data" id="js-upload-form">
                <input type=hidden name="command" value="import_file">
                <div class="form-inline">
                    <div class="form-group">
                        <input type="file" class="file" name="content" id="js-upload-file">
                    </div>
                    <button class="btn btn-sm btn-primary" id="js-upload-submit"><fmt:message
                            key="import.button.upload.file"/></button>
                </div>
            </form>
            <span>Test files: web/src/main/resources/*.csv</span><br>
            <span style="color: #008000;">${infoCreateRecords}</span><br>
            <span style="color: #008000;">${infoUpdateRecords}</span>
            <span style="color: #ff0000;">${errorDataFormat}</span><br>
            <span style="color: #ff0000;">${formatExample}</span>
        </div>
    </div>
</div>
</body>
</html>