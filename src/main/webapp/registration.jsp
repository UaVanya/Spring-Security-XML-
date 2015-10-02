<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>index</title>
    <script>
        function checkPassword(formRegister) {
            var password = formRegister.password.value;
            var repeatPassword = formRegister.repeatPassword.value;

            if (password != repeatPassword) {
                alert("Passwords do not match");
                return false;
            } else return true;
        }
    </script>
    <style>
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
<body style="text-align: center; margin: 0 auto;">
<h1>${error}</h1>

<div id="login-box">
    <h2>Enter the data for registration:</h2>

    <form action="/registration" method="post" name="registerForm" onSubmit="return checkPassword(this)">
        <table>
            <tr>
                <td><input type="text" name="nickname" maxlength="45" required placeholder="Nickname"/></td>
            </tr>
            <tr>
                <td><input type="password" name="password" maxlength="45" required placeholder="Password"/></td>
            </tr>
            <tr>
                <td><input type="password" name="repeatPassword" maxlength="45" required placeholder="Repeat password"/>
                </td>
            </tr>
            <tr>
                <td><input type="text" name="name" maxlength="45" placeholder="Name"/></td>
            </tr>
            <tr>
                <td><input type="email" name="email" maxlength="45" placeholder="E-mail"></td>
            </tr>
            <tr>
                <td>Select area: <input type="radio" name="area" id="area1" value="AREA1" required><label
                        for="area1">Area1</label>
                    <input type="radio" name="area" id="area2" value="AREA2"><label for="area2">Area2</label>
                    <input type="radio" name="area" id="area3" value="AREA3"><label for="area3">Area3</label></td>
            </tr>
            <tr>
                <td><input type="submit" name="enter" value="Save"/></td>
            </tr>
        </table>
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </form>
</div>
</body>
</html>