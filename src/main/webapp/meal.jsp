<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/functions" %>


<html>
<head>
    <title>Meal add/edit</title>
</head>
<body>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meal add/edit</h2>

<form method="POST" action='meals'>
    <table>
        <c:if test="${!empty meal.id}">
            <tr>
                <td>
                    ID :
                </td>
                <td>
                    <input type="text" readonly="readonly" name="mealId"
                           value="<c:out value="${meal.id}" />"/>
                </td>

            </tr>
        </c:if>
        <tr>
            <td>
                Date (yyyy.MM.dd-kk:mm):
            </td>
            <td>
                <c:if test="${!empty meal.dateTime}">
                    <%--<input type="datetime-local" name="date" value=${f:formatLocalDateTime(meal.dateTime, 'yyyy.MM.dd-kk:mm')}>--%>
                    <input type="datetime-local" name="date" value=${meal.dateTime}>
                </c:if>
                <c:if test="${empty meal.dateTime}">
                    <input type="datetime-local" name="date" value="<%=LocalDateTime.now() %>">
                </c:if>
            </td>

        </tr>

        <tr>
            <td>
                Description :
            </td>
            <td>
                <input
                        type="text" name="desc"
                        value="<c:out value="${meal.description}"/>"/>
            </td>

        </tr>
        <tr>
            <td>
                Calories :
            </td>
            <td>
                <input
                        type="number" name="calories"
                        value="<c:out value="${meal.calories}"/>"/>
            </td>

        </tr>

    </table>
    <input type="submit" value="Submit"/>


</form>
</body>
</html>
