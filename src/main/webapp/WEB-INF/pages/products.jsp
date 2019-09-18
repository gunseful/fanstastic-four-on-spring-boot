<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>DIGITAL STORE</title>
    <link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="icon" type="image/png" href="<c:url value="/res/favicon.png"/>"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>

<div class="header">
    <p align="center" ; style="font-size: 30px; color: white">DIGITAL STORE</p>

</div>
<div class="menu">
    <table>
        <tr>
            <td><a href="/" class="w3-btn w3-black">Home</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="/orders" class="w3-btn w3-black">Orders</a>
            </td>
            <c:if test="${!user.roles.contains(admin)}">
                <td>
                    <a href="/basket" class="w3-btn w3-black">Basket</a>
                </td>
            </c:if>
        </tr>

        <c:if test="${user.roles.contains(admin)}">
            <tr>
                <td><a href="/user" class="w3-btn w3-black">Users</a>
                </td>
            </tr>
        </c:if>

        <tr>
            <td><a href="/logout" class="w3-btn w3-black">Logout</a>
            </td>
        </tr>
    </table>
</div>
<div class="content">
    <h6 style="align-content: center">Products</h6>

    <c:if test="${user.roles.contains(admin)}">
        <form method="post" enctype="multipart/form-data">
            <input type="text" name="name" placeholder="Название товара" required="required">
            <input type="number" name="price" placeholder="Введите цену товара" required="required">
            <input type="file" name="file">
            <button type="submit" class="w3-btn w3-black">Добавить</button>
        </form>
    </c:if>

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

                <c:if test="${user.roles.contains(admin)}">
                    <td><a href="/products/${product.getId()}" class="w3-btn w3-black">Delete</a></td>
                </c:if>
                <c:if test="${!user.roles.contains(admin)}">
                    <td><a href="/products-to-basket/${product.getId()}" class="w3-btn w3-black">Add to BASKET</a></td>
                </c:if>

            </tr>
            <tr>
                <td colspan="5" align="center"><c:if test="${product.filename!=null}">
                    <img src="/img/${product.filename}">
                </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>