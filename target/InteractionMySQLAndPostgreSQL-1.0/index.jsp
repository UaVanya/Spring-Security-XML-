<html>
<head>
    <meta charset="UTF-8">
    <title>index</title>
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
<body>
<div id="login-box">
    <form action="/login.jsp" method="get" name="formLogin">
        <table>
            <tr>
                <td><input type="submit" name="enter" value="Login"/></td>
            </tr>
        </table>
    </form>

    <form action="/registration.jsp" method="get" name="formRegister">
        <table>
            <tr>
                <td><input type="submit" name="enter" value="Registration"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>