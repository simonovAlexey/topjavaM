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
        width: 500px;
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
        <th width = "30%">Время</th>
        <th width = "50%">Описание</th>
        <th>Калории</th>
    </tr>

    <c:forEach items="${list}" var="entry">
    <tr style = "${entry.exceed ? "color:red;" : "color:green;"}">
        <td>  ${f:formatLocalDateTime(entry.dateTime, 'dd.MM.yyyy kk:mm')} </td>
        <td><c:out value="${entry.description}"/></td>
        <td><c:out value="${entry.calories}"/></td>
    </tr>
    </c:forEach>


</table>
</body>
</html>
