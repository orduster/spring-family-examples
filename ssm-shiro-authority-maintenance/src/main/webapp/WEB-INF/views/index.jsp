<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>index</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="workingroom">
    <div class="loginDiv">

        <c:if test="${empty userName}">
            <a href="login">登录</a><br>
        </c:if>
        <c:if test="${!empty userName}">
            <span class="desc">你好，${userName}，</span>
            <a href="doLogout">退出</a><br>
        </c:if>

        <a href="listProduct">查看产品</a><span class="desc">(登录后才可以查看) </span><br>
        <a href="deleteProduct">删除产品</a><span class="desc">(要有产品管理员角色, zhangsan没有，lisi 有) </span><br>
        <a href="deleteOrder">删除订单</a><span class="desc">(要有删除订单权限, zhangsan有，lisi没有) </span><br>
    </div>
</div>
</body>
</html>