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

        <caption>
            <h2>
                Список пользователей

            </h2>
        </caption>

        <tr>
            <th>Id</th>
            <th>Login</th>
            <th>Password</th>
            <th>Bithday</th>
            <th>Role</th>
            <th>Email</th>
            <th>Operations</th>
        </tr>

        <c:forEach var="user" items="${requestScope.users}">
        <tr>
            <th>   ${user.id} </th>
            <th>   ${user.login} </th>
            <th>   ${user.password} </th>
            <th>   ${user.bithday} </th>
            <th>   ${user.role} </th>
            <th>   ${user.email} </th>
            <th>
                <a href="/users/edit?id=<c:out value='${user.id}' />">Edit</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="/users/delete?id=<c:out value='${user.id}' />">Delete</a>
            <th/>

        </tr>
        </c:forEach>

        <table/>

        <a href = "/users/create">
            <button type="button">Добавить</button>
        </a><br>
</body>
</html>
