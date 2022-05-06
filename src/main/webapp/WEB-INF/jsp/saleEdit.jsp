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

<a href="/sales/list">К списку продаж</a>

    <c:if test="${sale != null}">
    <form action="/sales/edit" method="post">
        </c:if>
        <c:if test="${sale == null}">
        <form action="/sales/create" method="post">
            </c:if>


    <input type="text" hidden="hidden" name="id" id="id"  value="${sale.id}" >

                <table >
                    <caption>
                        <h2>
                            <c:if test="${sale != null}">
                                Редактирование продажи
                            </c:if>
                            <c:if test="${sale == null}">
                                Создание новой Продажи
                            </c:if>
                        </h2>
                    </caption>


                    <tr>
                        <th>Date: </th>
                        <td>
                            <input type="date" name="date" id="date"  value="${sale.date}" >

                        </td>
                    </tr>

                    <tr>
                        <th>Description: </th>
                        <td>
                            <input type="text" name="description" id="description"  value="${sale.description}" >

                        </td>
                    </tr>

                    <tr>
                        <th>User: </th>
                        <td>
                            <select name="userId" id="userId" >

                                <c:forEach  var="user" items="${users}">
                                    <c:if test="${sessionScope.isAdmin ==true }">

                                        <option value="${user.id}" ${user.id== sale.user_id ? 'selected="selected"' : ''} } >${user.login}</option>


                                    </c:if>

                                    <c:if test="${sessionScope.isAdmin !=true  }">
                                       <c:if test="${user.id== selectedUserId}">
                                        <option value="${user.id}" ${user.id== selectedUserId ? 'selected="selected"' : ''}>${user.login}</option>
                                       </c:if>
                                    </c:if>


                                </c:forEach>

                            </select>

                        </td>
                    </tr>




                </table>


                <c:if test="${sale != null}">
                    <p> <a href="/items/list?saleId=${sale.id}">
                        Список позиций продажи
                    </a><p/>
                </c:if>

                <c:if test="${sale != null}">
                    <a href = "/sales/edit">
                        <button type="submit">Изменить продажу</button>
                    </a><br>
                </c:if>
                <c:if test="${sale == null}">
                    <a href = "/sales/create">
                        <button type="submit">Cоздать продажу</button>
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
