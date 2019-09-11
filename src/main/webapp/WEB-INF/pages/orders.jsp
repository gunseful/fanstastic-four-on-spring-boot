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
                <a href="/products" class="w3-btn w3-black">Products</a>
            </td>
            <td>
                <a href="/basket" class="w3-btn w3-black">Basket</a>
            </td>
        </tr>
        <tr>
            <td><a href="/logout" class="w3-btn w3-black">Logout</a>
            </td>
        </tr>
    </table>
</div>
<div class="content">
    <h6>Orders</h6>

    <c:if test="${orders.isEmpty()}">
        <p>There is no orders</p>
    </c:if>

    <c:if test="${!orders.isEmpty()}">
    <c:forEach var="order" items="${orders}">
        <table>
            <tr>
                <th style="text-align:left">
                    ORDER ID
                </th>
                <td>${order.id}</td>
                <td><a href="/orders/${order.id}" class="w3-btn w3-black">Delete</a></td>
            </tr>
            <tr>
                <th style="text-align:left">
                    User name
                </th>
                <td>${order.user.username}</td>
            </tr>
            <tr>
                <th style="text-align:left">
                    Status
                </th>
                <td>${order.status}</td>
                <td><a href="/orders/paid/${order.id}" class="w3-btn w3-black">Paid</a>
                </td>
            </tr>
            <tr>
                <table>
                    <tr>
                        <th>Product id</th>
                        <th>Product name</th>
                        <th>Price (USD)</th>
                    </tr>
                    <c:forEach var="product" items="${order.products}">
                        <tr>
                            <td>${product.id}</td>
                            <td>${product.name}</td>
                            <td>${product.price}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td>Total price</td>
                        <td></td>
                        <td>${order.totalPrice()}</td>
                    </tr>
                </table>
                <p>-------------------------------------</p>
            </tr>
        </table>
    </c:forEach>
    </c:if>

</div>
</body>
</html>