<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<%@include file="header.jsp" %>

<a href="/users/list" ${ sessionScope.isAdmin !=true ? 'hidden="hidden"' : ''}>К списку пользователей</a>
<br>
<a href="/products/list" ${ sessionScope.isAdmin !=true ? 'hidden="hidden"' : ''}>К списку продуктов</a>

    <table>

        <caption>
            <h2>
                Список продаж
            </h2>
        </caption>
        <tr>
            <th>ID</th>
            <th>Date</th>
            <th>User</th>
            <th>Items</th>
            <th>Description</th>
            <th>Operations</th>
        </tr>

            <c:forEach var="sale" items="${requestScope.sales}">
        <tr>
            <th>   <a href="/items/list?saleId=${sale.id}">${sale.id}</a> </th>
            <th>   <a href="/items/list?saleId=${sale.id}">${sale.date}</a> </th>
            <th>   <a href="/items/list?saleId=${sale.id}">${sale.userLogin}</a> </th>
            <th width="100">   <a href="/items/list?saleId=${sale.id}">Items</a> </th>
            <th>   <a href="/items/list?saleId=${sale.id}">${sale.description}</a> </th>
            <th>
            <a href="/sales/edit?id=<c:out value='${sale.id}' />">Edit</a>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/sales/delete?id=<c:out value='${sale.id}' />">Delete</a>
            <th/>
        </tr>
            </c:forEach>

        <table/>

        <a href = "/sales/create">
            <button type="button">Добавить</button>
        </a><br>

</body>
</html>
