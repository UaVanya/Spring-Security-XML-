<%@ page import="java.util.Map" %>
<%@ page import="com.spilnasprava.entity.mysql.User" %>
<%@ page import="com.spilnasprava.entity.postgresql.Area" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>index</title>
</head>
<body bgcolor="#fafad2">
<h1>Message : ${message} ${error}</h1>

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

<table align="center" border="0" width="300">
    <tr border="0">
        <td border="0"><a href="registration.jsp">Add user</a></td>
    </tr>
</table>

<table align="center" border="" width="300">
    <tr>
        <th width="170">Id</th>
        <th width="170">Nickname</th>
        <th width="170">Name</th>
        <th width="170">E-mail</th>
        <th width="170">Key</th>
        <th width="170">Area</th>
    </tr>
    <% Map<User, Area> result = (Map<User, Area>) request.getAttribute("result");
        Set<User> users = (Set<User>) result.keySet();
        if (result != null && !result.isEmpty()) {
            for (User user : users) {
                Area area = result.get(user);
    %>
    <tr>
        <td width="170"><%=user.getId()%>
        </td>
        <td width="170"><%=user.getNickname()%>
        </td>
        <td width="170"><%=user.getName()%>
        </td>
        <td width="170"><%=user.getEmail()%>
        </td>
        <td width="170"><%=user.getUserKey().getKey()%>
        </td>
        <td width="170"><%=area.getArea().name()%>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>