<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
                <a href="/orders" class="w3-btn w3-black"><spring:message code="orders" text="default"/></a>
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
    <h6 style="align-content: center">Products</h6>
    <h1><spring:message code="greeting" text="default"/></h1>


    <c:if test="${user.roles.contains(admin)}">
        <form method="post" enctype="multipart/form-data">
            <input type="text" name="name" placeholder="<spring:message code="add.name" text="default"/>" required="required">
            <input type="number" name="price" placeholder="<spring:message code="add.price" text="default"/>" required="required">
            <input type="file" name="file">
            <button type="submit" class="w3-btn w3-black">><spring:message code="add" text="default"/></button>
        </form>
    </c:if>

    <table>
        <tr>
            <th>ID</th>
            <th><spring:message code="product.name" text="default"/></th>
            <th><spring:message code="price" text="default"/></th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.getId()}</td>
                <td>${product.getName()}</td>
                <td>${product.getPrice()}</td>

                <c:if test="${user.roles.contains(admin)}">
                    <td><a href="/products/${product.getId()}" class="w3-btn w3-black"><spring:message code="delete" text="default"/></a></td>
                </c:if>
                <c:if test="${!user.roles.contains(admin)}">
                    <td><a href="/basket/add/${product.getId()}" class="w3-btn w3-black"><spring:message code="add.to.basket" text="default"/></a></td>
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