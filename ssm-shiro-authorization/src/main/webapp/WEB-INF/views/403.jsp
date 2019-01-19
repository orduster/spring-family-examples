<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>未授权界面</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="workingroom">

    权限不足,具体原因：${ex.message}
    <br>
    <a href="#" onClick="javascript:history.back()">返回</a>
</div>
</body>
</html>