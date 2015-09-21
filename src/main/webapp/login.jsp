<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        .error {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
        }

        .msg {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
            color: #31708f;
            background-color: #d9edf7;
            border-color: #bce8f1;
        }

        #login-box {
            width: 300px;
            padding: 20px;
            margin: 100px auto;
            background: #fff;
            -webkit-border-radius: 2px;
            -moz-border-radius: 2px;
            border: 1px solid #000;
        }
    </style>
</head>
<body onload='document.loginForm.username.focus();'>

<div id="login-box">

    <h2>Login with Username and Password:</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>

    <form name='loginForm'
            <c:url var="loginUrl" value="/login"/>
          action="${loginUrl}" method='POST'>

        <table>
            <tr>
                <td>User:</td>
                <td><input type="text" name="username" maxlength="45" required placeholder="Nickname"></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" maxlength="45" required placeholder="Password"/></td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" type="submit" value="Login"/></td>
            </tr>
        </table>

        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </form>
</div>

</body>
</html>