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

<div>
    <c:if test="${message!=null}">
        ${message}
    </c:if>
</div>
Registration
<form action="/registration" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
</form>
</body>
</html>