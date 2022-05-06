
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="/registration" method="post">

    <label for="name">
        Name:
        <input type="text" name="name" id="name">
    </label><br>

    <label for="password">
        Password:
        <input type="password" name="password" id="password">
    </label><br>

    <label for="bithday">
        Bithday:
        <input type="date" name="bithday" id="bithday">
    </label><br>

    <label for="email">
        Email:
        <input type="text" name="email" id="email">
    </label><br>



    <select name="role" id="role">
        <c:forEach  var="role" items="${roles}">
            <option value="${role}">${role}</option>
        </c:forEach>

    </select><br><br>

    <button type="submit">Send</button>

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
