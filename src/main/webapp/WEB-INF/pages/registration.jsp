
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
    <link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="icon" type="image/png" href="<c:url value="/res/favicon.png"/>"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<div class="header">
    <p align="center"; style="font-size: 30px; color: white" >DIGITAL STORE</p>
</div>
<div class="menu">
    <table>
        <tr>
            <td><a href="/" class="w3-btn w3-black">Home</a>
            </td>
        </tr>
        <tr>
            <td><a href="/login" class="w3-btn w3-black">Log in</a>
            </td>
        </tr>
    </table>
</div>
<div class="content">
<div class="b">
<c:if test="${param.error}">
    Invalid username and password.
</c:if>


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
</div>
</div>
</body>
</html>