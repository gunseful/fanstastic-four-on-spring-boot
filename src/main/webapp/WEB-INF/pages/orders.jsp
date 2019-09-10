<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Orders</title>
    <link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="icon" type="image/png" href="<c:url value="/res/favicon.png"/>"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<div class="b">
    <h6>Orders</h6>
    <table>
        <c:forEach var="order" items="${ordered}">
            <br>
            <p>----------------------</p>
            Айди заказа - ${order.getId()}
            <br>
            <c:forEach var="product" items="${order.products}">
                <p>${product.name} ${product.price}</p>
            </c:forEach>
            Общая сумма - ${order.totalPrice()}
        </c:forEach>
    </table>
    <table>
        <c:forEach var="order" items="${paid}">
            <br>
            <p>----------------------</p>
            ID заказа - ${order.getId()}
            <br>
            <c:forEach var="product" items="${order.products}">
                <p>${product.name} ${product.price}</p>
            </c:forEach>
            Общая сумма - ${order.totalPrice()}
        </c:forEach>
    </table>
    <a href="/logout" class="w3-btn w3-black">Logout</a>
</div>
</body>
</html>