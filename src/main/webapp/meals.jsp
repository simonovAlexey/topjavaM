<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/functions" %>
<html>
<head>
    <title>Meals</title>
</head>
<style>
    table {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        width: 620px;
    }

    td, th {
        border: 1px solid #dddddd;
        text-align: center;
        padding: 8px;

    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meal list</h2>
<br>
<table >
    <tr>
        <th>Id</th>
        <th width = "30%">Время</th>
        <th width = "40%">Описание</th>
        <th>Калории</th>
        <th>Обновить</th>
        <th>Удалить</th>
    </tr>

    <c:forEach items="${list}" var="entry">
    <tr style = "${entry.exceed ? "color:red;" : "color:green;"}">
        <td><c:out value="${entry.id}"/></td>
        <td>${f:formatLocalDateTime(entry.dateTime, 'dd.MM.yyyy kk:mm')}</td>
        <td><c:out value="${entry.description}"/></td>
        <td><c:out value="${entry.calories}"/></td>
        <td><a href="meals?action=edit&mealId=<c:out value="${entry.id}"/>">Update</a></td>
        <td><a href="meals?action=delete&mealId=<c:out value="${entry.id}"/>">Delete</a></td>
    </tr>
    </c:forEach>


</table>
<p><a href="meals?action=insert">Add User</a></p>
</body>
</html>
