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

<a href="/users/list">К списку пользователей</a>

<h1>

    <c:if test="${userNew != null}">
    <form action="/users/edit" method="post">

        </c:if>
        <c:if test="${userNew == null}">
        <form action="/users/create" method="post">

            </c:if>

            <input type="text" hidden="hidden" name="id" id="id"  value="${userNew.id}" >

            <table >
                <caption>
                    <h2>
                        <c:if test="${userNew != null}">
                            Редактирование пользователя

                        </c:if>
                        <c:if test="${userNew == null}">
                            Создание нового пользователя

                        </c:if>
                    </h2>
                </caption>

                <tr>
                    <th>login: </th>
                    <td>
                        <input type="text" name="login" id="login"  value="${userNew.login}" >
                    </td>
                </tr>

                <tr>
                    <th>Password: </th>
                    <td>
                        <input type="text" name="password" id="password"  value="${userNew.password}" >
                    </td>
                </tr>

                <tr>
                    <th>Bithday: </th>
                    <td>
                        <input type="date" name="bithday" id="bithday"  value="${userNew.bithday}" >
                    </td>
                </tr>

                <tr>
                    <th>Role: </th>
                    <td>
                        <select name="role" id="role">
                            <c:forEach  var="role" items="${roles}">
                                <option value="${role}"  ${role== userNew.role ? 'selected="selected"' : ''}>${role}</option>
                            </c:forEach>

                        </select><br><br>
                    </td>
                </tr>

                <tr>
                    <th>Email: </th>
                    <td>
                        <input type="text" name="email" id="email"  value="${userNew.email}" >
                    </td>
                </tr>

            </table>

            <c:if test="${userNew != null}">
                <a href = "/users/edit">
                    <button type="submit">Изменить пользователя</button>
                </a><br>
            </c:if>
            <c:if test="${userNew == null}">
                <a href = "/users/create">
                    <button type="submit">Cоздать пользователя</button>
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
