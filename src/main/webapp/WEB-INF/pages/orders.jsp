<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
        $(document).ready(function () {
            $("#locales").change(function () {
                var selectedOption = $('#locales').val();
                if (selectedOption != '') {
                    window.location.replace('?lang=' + selectedOption);
                }
            });
        });
    </script>
    <p align="center" ; style="font-size: 20px; color: white">DIGITAL STORE</p>
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
                    <a href="/basket" class="w3-btn w3-black"><spring:message code="basket" text="default"/></a>
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
    <h6><spring:message code="orders" text="default"/></h6>

    <c:if test="${orders.isEmpty()}">
        <p><spring:message code="no.orders" text="default"/></p>
    </c:if>

    <c:if test="${!orders.isEmpty()}">
        <c:forEach var="order" items="${orders}">
            <table>
                <tr>
                    <th style="text-align:left">
                        <spring:message code="order.id" text="default"/>
                    </th>
                    <td>${order.id}</td>
                    <td><a href="/orders/${order.id}" class="w3-btn w3-black"><spring:message code="delete"
                                                                                              text="default"/>
                    </a></td>
                </tr>
                <tr>
                    <th style="text-align:left">
                        <spring:message code="order.date" text="default"/>
                    </th>
                    <td>${order.date}</td>
                </tr>
                <tr>
                    <th style="text-align:left">
                        <spring:message code="user.name" text="default"/>
                    </th>
                    <td>${order.user.username}</td>
                    <td>
                        <c:if test="${user.roles.contains(admin)}">
                            <c:if test="${order.user.roles.contains(block)}">
                                <spring:message code="blocked" text="default"/>
                            </c:if>
                            <c:if test="${!order.user.roles.contains(block)}">
                                <a href="/user/block/${order.user.id}" class="w3-btn w3-black"><spring:message
                                        code="block.user" text="default"/></a>
                            </c:if>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th style="text-align:left">
                        <spring:message code="status" text="default"/>
                    </th>
                    <td>
                        <spring:message code="${order.status}" text="default"/></td>
                    <td>
                        <c:if test="${!order.status.equals(paid)}">
                            <c:if test="${!user.roles.contains(admin)}">
                                <a href="/orders/paid/${order.id}" class="w3-btn w3-black"><spring:message code="pay"
                                                                                                           text="default"/></a>
                            </c:if>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <table>
                        <tr>
                            <th><spring:message code="product.id" text="default"/></th>
                            <th><spring:message code="product.name" text="default"/></th>
                            <th><spring:message code="count" text="default"/></th>
                            <th><spring:message code="price" text="default"/></th>

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
                            <th><spring:message code="total.price" text="default"/></th>
                            <td></td>
                            <td></td>
                            <td align="center">${order.totalPrice()}</td>
                        </tr>
                        <tr>
                            <td colspan="4" align="center"> ----------------------------------</td>
                        </tr>
                    </table>
                </tr>
            </table>
        </c:forEach>
    </c:if>

</div>
</body>
</html>