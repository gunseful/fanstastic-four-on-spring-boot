<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>BASKET</title>
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
            <td>
                <a href="/products" class="w3-btn w3-black">Products</a>
            </td>
        </tr>
        <tr>
            <td><a href="/logout" class="w3-btn w3-black">Logout</a>
            </td>
        </tr>
    </table>
</div>
<div class="content">
    <h6>User Basket</h6>
    <c:if test="${order.products.keySet().isEmpty()}">
        <p>User basket is empty</p>
    </c:if>
    <c:if test="${order.products.keySet().size()>0}">

        <table>
            <tr>
                <th>Product Name</th>
                <th>Count</th>
                <th>Price (USD)</th>

            </tr>
            <c:forEach var="product" items="${order.products}">
                <tr>
                    <td>${product.key.name}</td>
                    <td><a href="/basket/product/minus/${product.key.id}" class="w3-btn w3-black">-</a>
                            ${product.value} <a href="/basket/product/plus/${product.key.id}"
                                                class="w3-btn w3-black">+</a></td>
                    <td align="center">${product.key.price}</td>
                    <td><a href="/basket/product/${product.key.id}" class="w3-btn w3-black">Remove</a>
                </tr>
            </c:forEach>
            <tr>
                <th>Total price</th>
                <td></td>
                <td align="center">${order.totalPrice()}</td>
            </tr>
            <tr></tr>
            <tr>
                <td><a href="/basket/${order.id}" class="w3-btn w3-black">Make Order</a>
                </td>
            </tr>
        </table>

    </c:if>
    <c:if test="${order.products.keySet().size()<=0}">
        User basket is Empty
    </c:if>


</div>
</body>
</html>