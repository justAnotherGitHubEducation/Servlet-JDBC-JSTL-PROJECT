<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<%@include file="header.jsp" %>

<a href="/sales/list">К списку продаж</a>
    <table>
        <table>

            <caption>
                <h2>
                    Список продуктов
                </h2>
            </caption>

        <tr>
            <th>Id</th>
            <th>Product</th>
            <th>Description</th>

        </tr>

        <c:forEach var="product" items="${requestScope.products}">
        <tr>
            <th>   ${product.id} </th>
            <th>   ${product.name} </th>
            <th>   ${product.description} </th>
            <th>
                <a href="/products/edit?id=<c:out value='${product.id}' />">Edit</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="/products/delete?id=<c:out value='${product.id}' />">Delete</a>
            <th/>

        </tr>
        </c:forEach>

        <table/>

        <a href = "/products/create">
            <button type="button">Добавить</button>
        </a><br>

</body>
</html>
