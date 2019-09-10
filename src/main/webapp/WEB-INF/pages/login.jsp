<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login page</title>
    <link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="icon" type="image/png" href="<c:url value="/res/favicon.png"/>"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">


</head>
<body>
<div>

</div>

<div>

</div>
<div class="b">
    <caption class="heading">Login Page</caption>
    <c:if test="${param.error}">
        <caption class="heading">Invalid username and password.</caption>

    </c:if>
    <c:if test="${param.logout}">
        <caption class="heading"> You have been logged out.</caption>

    </c:if>
    <form action="/login" method="post">
        <div><label> User Name : <input type="text" name="username" required="required"/> </label></div>
        <div><label> Password: <input type="password" name="password" required="required"/> </label></div>
        <button type="submit" class="w3-btn w3-black">Sign In</button>
    </form>
    <a href="/registration" class="w3-btn w3-black">Add new User</a>
</div>

</body>
</html>