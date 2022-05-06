<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 26.11.2021
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Item edit</title>
</head>
<body>

<a href="/items/list?saleId=${saleId}">К списку позиций продажи</a>

<h1>

    <c:if test="${item != null}">
    <form action="/items/edit" method="post">

        </c:if>
        <c:if test="${item == null}">
        <form action="/items/create" method="post">
            </c:if>
        <table >
            <caption>
                <h2>
                    <c:if test="${item != null}">
                        Редактирование позиции
                        <input type="text" hidden="hidden" name="itemId" id="itemId"  value="${item.id}" >
                        <input type="text" hidden="hidden" name="saleId" id="saleId"  value="${saleId}" >
                    </c:if>
                    <c:if test="${item == null}">
                        Создание новой позиции
                        <input type="text" hidden="hidden"  name="saleId" id="saleId"  value="${saleId}" >
                    </c:if>
                </h2>
            </caption>
            <tr>
                <th>Product: </th>
                <td>
                    <select name="productId" id="productId" >

                        <c:forEach  var="product" items="${products}">
                            <option value="${product.id}" ${product.id== item.product_id ? 'selected="selected"' : ''}>${product.name}</option>
                        </c:forEach>

                    </select>

                </td>
            </tr>
            <tr>
                <th>Quantity: </th>
                <td>
                    <input type="text" name="quantity" id="quantity"  value="${item.quantity}" >
                </td>
            </tr>

            <tr>
                <th>Comment: </th>
                <td>
                    <input type="text" name="comment" id="comment"  value="${item.comment}" >
                </td>
            </tr>

        </table>
                <br>

                <c:if test="${item != null}">
                    <a href = "/items/edit">
                        <button type="submit">Изменить позицию</button>
                    </a><br>
                </c:if>
                <c:if test="${item == null}">
                    <a href = "/items/create">
                        <button type="submit">Cоздать позицию</button>
                    </a><br>
                </c:if>

                <c:if test = "${not empty requestScope.errors }">

                    <div style="color: red">
                        <c:forEach var="error" items="${requestScope.errors}">

                            <span>${error.message}</span>

                        </c:forEach>
                    </div>
                    ></c:if>
        </form>
</body>
</html>
