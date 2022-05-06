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
    <title>Login</title>
</head>
<body>

<form method="post" action="/login">


    <table >
        <caption>
            <h2>
                Введите логин и пароль
            </h2>
        </caption>

        <tr>
            <th>Login: </th>
            <td>
                <input type="text" name="login" id="login"  value="${param.login}" required>

            </td>
        </tr>

        <tr>
            <th>Password: </th>
            <td>
                <input name="password" id="password" type="password" required>

            </td>
        </tr>

    </table>

    <button type="submit">Login</button><br>

    <a href = "/registration">

        <button type="button"> Register</button>
    </a><br>


    <c:if test="${param.error!=null}">
        <div style="color: red">
            <span>Error. Login or password is not correct!<span/>
        </div>
    </c:if>

</form>

</body>
</html>
