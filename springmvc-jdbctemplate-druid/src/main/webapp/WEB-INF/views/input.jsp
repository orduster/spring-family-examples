<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>input</title>
</head>
<body>
<form:form action="${pageContext.request.contextPath }/emp" method="POST" modelAttribute="employee">
    <c:if test="${employee.id == null}">
        LastName：<form:input path="lastName"/> <br>
    </c:if>
    <c:if test="${employee.id!=null}">
        <form:hidden path="id"/>
        <input type="hidden" name="_method" value="PUT"/>
    </c:if>

    Email：<form:input path="email"/> <br>
    <%
        Map<String, String> genders = new HashMap<>();
        genders.put("1", "Male");
        genders.put("0", "Female");
        request.setAttribute("genders", genders);
    %>
    gender：<form:radiobuttons path="gender" items="${genders}" delimiter="<br>"/> <br>
    Department: <form:select path="department.id" items="${departments }" itemLabel="departmentName" itemValue="id"/>
    <br>

    <input type="submit" value="提交"/> <br>
</form:form>
</body>
</html>
