<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Spring Security Example </title>
</head>
<body>
<div>
<c:if test="${param.error}">
    Invalid username and password.
</c:if>
</div>

<div>
    <c:if test="${param.logout}">
        You have been logged out.
    </c:if>
</div>
Login page
<form action="/login" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
</form>
<a href="registration">Add new User</a>
</body>
</html>