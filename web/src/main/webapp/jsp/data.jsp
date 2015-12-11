<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Data</title>
    <%@ include file="include.jsp" %>
</head>

<body>
<%@ include file="fragments/header.jsp" %>
<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th>
                <a href="controller?command=view_data&column=name&page=${currentPage}">
                    <span class="glyphicon glyphicon-sort-by-attributes"></span>
                </a>
                <fmt:message key="data.table.name"/></th>
            <th>
                <a href="controller?command=view_data&column=surname&page=${currentPage}">
                    <span class="glyphicon glyphicon-sort-by-attributes"></span>
                </a>
                <fmt:message key="data.table.surname"/></th>
            <th>
                <a href="controller?command=view_data&column=login&page=${currentPage}">
                    <span class="glyphicon glyphicon-sort-by-attributes"></span>
                </a>
                <fmt:message key="data.table.login"/></th>
            <th>
                <a href="controller?command=view_data&column=email&page=${currentPage}">
                    <span class="glyphicon glyphicon-sort-by-attributes"></span>
                </a>
                <fmt:message key="data.table.email"/></th>
            <th>
                <a href="controller?command=view_data&column=phoneNumber&page=${currentPage}">
                    <span class="glyphicon glyphicon-sort-by-attributes"></span>
                </a>
                <fmt:message key="data.table.phone.number"/></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${persons}" var="person">
            <tr>
                <td>${person.name}</td>
                <td>${person.surname}</td>
                <td>${person.login}</td>
                <td>${person.email}</td>
                <td>${person.phoneNumber}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="text-center">
        <ul class="pagination">
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="active"><a href="#">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="controller?command=view_data&page=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </div>

</div>
</body>
</html>