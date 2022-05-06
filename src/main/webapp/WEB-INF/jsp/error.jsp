<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 30.03.2022
  Time: 13:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<c:if test = "${not empty requestScope.errors }">

    <div style="color: red">
        <c:forEach var="error" items="${requestScope.errors}">

            <span>${error.message}</span>

        </c:forEach>
    </div>
    ></c:if>
</body>
</html>
