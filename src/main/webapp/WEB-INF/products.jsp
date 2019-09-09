<%--
  Created by IntelliJ IDEA.
  User: Gunseful
  Date: 08.09.2019
  Time: 20:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
    <title>Home Page</title>
</head>
<body>

<form action="<c:url value="/logout"/>" method="post">
    <input type="submit" value="Sign Out"/>
</form>

<div>
    <form method="post">
        <input type="text" name="name" placeholder="Название товара">
        <input type="number" name="price" placeholder="Введите цену товара">
        <button type="submit">Добавить</button>
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
    </tr>
    </c:forEach>
</table>




</body>
</html>
