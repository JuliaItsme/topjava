<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактировать еду</title>
</head>
<body>
<section>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">
        <br>
        <dl>
            <dt>Дата/Время</dt>
            <dd><input type="datetime-local" name="dateTime" size=25 value="${meal.dateTime}"></dd>
        </dl>
        <br>
        <dl>
            <dt>Описание</dt>
            <dd><input type="text" name="description" size=25 value="${meal.description}"></dd>
        </dl>
        <br>
        <dl>
            <dt>Калории</dt>
            <dd><input type="number" name="calories" size=25 value="${meal.calories}"></dd>
        </dl>
        <br>
        <button onclick="window.history.back()">Отменить</button>
        <button type="submit">Сохранить</button>
    </form>
</section>
</body>
</html>
