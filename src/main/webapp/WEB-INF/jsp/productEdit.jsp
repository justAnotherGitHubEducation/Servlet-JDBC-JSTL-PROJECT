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
    <title>User</title>
</head>
<body>

<a href="/products/list">К списку пользователей</a>

<h1>

    <c:if test="${product != null}">
    <form action="/products/edit" method="post">

        </c:if>
        <c:if test="${product == null}">
        <form action="/products/create" method="post">

            </c:if>

            <input type="text" hidden="hidden" name="id" id="id"  value="${product.id}" >

            <table >
                <caption>
                    <h2>
                        <c:if test="${product != null}">
                            Редактирование продукт

                        </c:if>
                        <c:if test="${product == null}">
                            Создание нового продукта

                        </c:if>
                    </h2>
                </caption>

                <tr>
                    <th>Name: </th>
                    <td>
                        <input type="text" name="name" id="name"  value="${product.name}"  required>
                    </td>
                </tr>

                <tr>
                    <th>Description: </th>
                    <td>
                        <input type="text" name="description" id="description"  value="${product.description}" required >
                    </td>
                </tr>

            </table>

            <c:if test="${product != null}">
                <a href = "/products/edit">
                    <button type="submit">Изменить продукт</button>
                </a><br>
            </c:if>
            <c:if test="${product == null}">
                <a href = "/products/create">
                    <button type="submit">Cоздать продукт</button>
                </a><br>
            </c:if>


        </form>
</body>
</html>
