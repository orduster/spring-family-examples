<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>登录界面</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="workingroom">

    <div class="errorInfo">${error}</div>
    <form action="login" method="post">
        账号： <input type="text" name="username"> <br>
        密码： <input type="password" name="password"> <br>
        <br>
        <input type="submit" value="登录">
        <br>
        <br>
        <div>
            <span class="desc">账号:zhangsan 密码:12345 角色:admin</span><br>
            <span class="desc">账号:lisi 密码:abcde 角色:productManager</span><br>
        </div>

    </form>
</div>
</body>
</html>