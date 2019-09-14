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
                        ORDER DATE
                    </th>
                    <td>${order.date}</td>
                </tr>
                <tr>
                    <th style="text-align:left">
                        User name
                    </th>
                    <td>${order.user.username}</td>
                    <td>
                        <c:if test="${user.roles.contains(admin)}">
                            <c:if test="${order.user.roles.contains(block)}">
                                Blocked
                            </c:if>
                            <c:if test="${!order.user.roles.contains(block)}">
                                <a href="/user/block/${order.user.id}" class="w3-btn w3-black">Block user</a>
                            </c:if>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th style="text-align:left">
                        Status
                    </th>
                    <td>${order.status}</td>
                    <td>
                        <c:if test="${!order.status.equals(paid)}">
                        <c:if test="${!user.roles.contains(admin)}">
                            <a href="/orders/paid/${order.id}" class="w3-btn w3-black">Paid</a>
                        </c:if>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <table>
                        <tr>
                            <th>Product id</th>
                            <th>Product name</th>
                            <th>Count</th>
                            <th>Price (USD)</th>

                        </tr>
                        <c:forEach var="product" items="${order.products}">
                            <tr>
                                <td>${product.key.id}</td>
                                <td>${product.key.name}</td>
                                <td align="center">${product.value}</td>
                                <td align="center">${product.key.price}</td>

                            </tr>
                        </c:forEach>
                        <tr>
                            <th>Total price</th>
                            <td></td>
                            <td></td>
                            <td align="center">${order.totalPrice()}</td>
                        </tr>
                        <tr>
                            <td colspan="4" align="center"> ---------------------------------- </td>
                        </tr>
                    </table>
                </tr>
            </table>
        </c:forEach>
    </c:if>

</div>
</body>
</html>