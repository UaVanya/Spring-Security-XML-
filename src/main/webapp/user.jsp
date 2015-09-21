<%@ page import="com.spilnasprava.entity.mysql.User" %>
<%@ page import="com.spilnasprava.entity.postgresql.Area" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>ER</title>
</head>
<body>
<h1>Message : ${message}</h1>

<c:url value="/login" var="logoutUrl"/>

<!-- csrt for log out-->
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>

<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h2>
        Welcome : ${pageContext.request.userPrincipal.name} | <a
            href="javascript:formSubmit()"> Logout</a>
    </h2>
</c:if>

<table align="center" border="" width="300">
    <tr>
        <th width="170">Nickname</th>
        <th width="170">Name</th>
        <th width="170">E-mail</th>
        <th width="170">Area</th>
    </tr>
    <% Map<User, Area> result = (Map<User, Area>) request.getAttribute("result");
        User user = new User();
        Area area = new Area();
        if (!result.isEmpty()) {
            Set<User> users = (Set<User>) result.keySet();
            if (result != null && !result.isEmpty()) {
                for (User iterUser : users) {
                    user = iterUser;
                    area = result.get(user);
    %>
    <tr>
        <td width="170"><%=user.getNickname()%>
        </td>
        <td width="170"><%=user.getName()%>
        </td>
        <td width="170"><%=user.getEmail()%>
        </td>
        <td width="170"><%=area.getArea()%>
        </td>
    </tr>
    <%
                }
            }
        }
    %>
</table>
</body>
</html>