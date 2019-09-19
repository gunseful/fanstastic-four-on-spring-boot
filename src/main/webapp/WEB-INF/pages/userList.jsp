<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>User List</title>
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
            <td>
                <a href="/orders" class="w3-btn w3-black"><spring:message code="orders" text="default"/></a>
            </td>
        </tr>
        <tr>
            <td><a href="/logout" class="w3-btn w3-black"><spring:message code="log.out" text="default"/></a>
            </td>
        </tr>
    </table>
</div>
<div class="content">
    <table>
    <tr>
        <th><spring:message code="user.name" text="default"/></th>
        <th><spring:message code="roles" text="default"/></th>
    </tr>

    <c:if test="${userlist!=null}">
        <c:forEach var="user" items="${userlist}">
                <tr>
                    <td>${user.username}</td>

                    <c:forEach var="role" items="${user.roles}">
                        <td>${role}</td>
                    </c:forEach>
                    <td><a href="/user/${user.id}"><spring:message code="edit" text="default"/></a></td>
                </tr>
        </c:forEach>
    </c:if>
    </table>

</div>
</body>
</html>