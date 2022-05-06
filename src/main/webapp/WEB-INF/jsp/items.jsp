<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Продажи</title>
</head>
<body>

<a href="/sales/list">К списку продаж</a>


    <input type="text" hidden="hidden" name="saleId" id="saleId"  value="${saleId}" >

    <table>

        <caption>
            <h2>
                Список позиций продажи

            </h2>
        </caption>
        <tr>
            <th>Product</th>
            <th>Quantity</th>
            <th>Сomment</th>
            <th>Operations</th>
        </tr>

        <c:forEach var="item" items="${requestScope.items}">
        <tr>
            <th>   <a href="/products?productId=${item.getProduct_id()}">${item.getProductName()}</a> </th>
            <th>   ${item.getQuantity()}</th>
            <th>  ${item.getComment()}</th>
            <th>
                <a href="/items/edit?itemId=${item.id}&saleId=${saleId}">Edit</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="/items/delete?itemId=${item.id}&saleId=${saleId}">Delete</a>
            <th/>
        </tr>
        </c:forEach>

        <table/>

        <a href="/items/create?saleId=<c:out value='${saleId}' />">
            <button type="button">Add</button> </a><br>

</body>
</html>
