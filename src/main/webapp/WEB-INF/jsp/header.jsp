<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 26.11.2021
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
<c:if test="${not empty sessionScope.user}">

    <table class="text">
        <tr>
            <td><form action="/logout" method="post" >
                <button type="submit"> Logout</button>
            </form> </td>
            <td>     </td>

            <td class="rightcol"> Пользователь: <b>${sessionScope.user.login}<b/></td>
        </tr>
    </table>

</c:if>

</div>