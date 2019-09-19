<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
    <span style="color: white"><spring:message code="lang.change" text="default"/></span>:
    <select id="locales">
        <option value=""></option>
        <option value="fr"><spring:message code="lang.fr" text="default"/></option>
        <option value="ru"><spring:message code="lang.ru" text="default"/></option>
        <option value="gb"><spring:message code="lang.gb" text="default"/></option>

    </select>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
    </script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#locales").change(function () {
                var selectedOption = $('#locales').val();
                if (selectedOption != ''){
                    window.location.replace('?lang=' + selectedOption);
                }
            });
        });
    </script>
    <p align="center"; style="font-size: 20px; color: white" >DIGITAL STORE</p>
</div>
<div class="menu">
    <table>
        <tr>
            <td><a href="/" class="w3-btn w3-black"><spring:message code="home" text="default"/></a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="/products" class="w3-btn w3-black"><spring:message code="to.products" text="default"/></a>
            </td>
            <c:if test="${!user.roles.contains(admin)}">
                <td>
                    <a href="/orders" class="w3-btn w3-black"><spring:message code="orders" text="default"/></a>
                </td>
            </c:if>
        </tr>
        <c:if test="${user.roles.contains(admin)}">
            <tr>
                <td><a href="/user" class="w3-btn w3-black"><spring:message code="users" text="default"/></a>
                </td>
            </tr>
        </c:if>
        <tr>
            <td><a href="/logout" class="w3-btn w3-black"><spring:message code="log.out" text="default"/></a>
            </td>
        </tr>
    </table>
</div>
<div class="content">
    <h6><spring:message code="basket" text="default"/></h6>
    <c:if test="${order.products.keySet()==null}">
        <p><spring:message code="empty.basket" text="default"/></p>
    </c:if>
    <c:if test="${order.products.keySet().size()>0}">

        <table>
            <tr>
                <th><spring:message code="product.name" text="default"/></th>
                <th><spring:message code="count" text="default"/></th>
                <th><spring:message code="price" text="default"/></th>

            </tr>
            <c:forEach var="product" items="${order.products}">
                <tr>
                    <td>${product.key.name}</td>
                    <td><a href="/basket/product/minus/${product.key.id}" class="w3-btn w3-black">-</a>
                            ${product.value} <a href="/basket/product/plus/${product.key.id}"
                                                class="w3-btn w3-black">+</a></td>
                    <td align="center">${product.key.price}</td>
                    <td><a href="/basket/product/${product.key.id}" class="w3-btn w3-black"><spring:message code="remove" text="default"/></a>
                </tr>
            </c:forEach>
            <tr>
                <th><spring:message code="total.price" text="default"/></th>
                <td></td>
                <td align="center">${order.totalPrice()}</td>
            </tr>
            <tr></tr>
            <tr>
                <td><a href="/basket/${order.id}" class="w3-btn w3-black"><spring:message code="make.order" text="default"/></a>
                </td>
            </tr>
        </table>

    </c:if>
    <c:if test="${order.products.keySet().size()<=0}">
        <p><spring:message code="empty.basket" text="default"/></p>
    </c:if>


</div>
</body>
</html>