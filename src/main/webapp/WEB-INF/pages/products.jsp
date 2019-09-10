<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>FILMS</title>
    <link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="icon" type="image/png" href="<c:url value="/res/favicon.png"/>"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<div class="b">
    <h6>Products</h6>

    <div>
        <form method="post">
            <input type="text" name="name" placeholder="Название товара" required="required">
            <input type="number" name="price" placeholder="Введите цену товара" required="required">
            <button type="submit" class="w3-btn w3-black">Добавить</button>
        </form>
    </div>

    <table>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>PRICE</th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.getId()}</td>
                <td>${product.getName()}</td>
                <td>${product.getPrice()}</td>
                <td><a href="/products/${product.getId()}" class="w3-btn w3-black">Delete</a></td>
                <td><a href="/products-to-basket/${product.getId()}" class="w3-btn w3-black">Add to BASKET</a></td>

            </tr>
        </c:forEach>
    </table>
    <a href="/logout" class="w3-btn w3-black">Logout</a>
    <a href="/basket" class="w3-btn w3-black">Basket</a>
    <a href="/orders" class="w3-btn w3-black">Orders</a>
</div>
</body>
</html>