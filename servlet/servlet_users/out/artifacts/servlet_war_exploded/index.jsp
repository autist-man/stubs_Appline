<%--
  Created by IntelliJ IDEA.
  User: mtemu
  Date: 05.10.2020
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ru.appline.logic.Model"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Home</title>
  </head>
  <body>
    <h1>Домашняя страница по работе с пользователями</h1>
    Введите ID пользователя (0 - вывести весь список пользователей)
  </br>
  Доступно:
      <%Model model = Model.getInstance();
        out.print(model.getFromList().size());
      %>
    <form method="get" action="get">
      <label>ID:
        <input type="text" name="ID"> </br>
      </label>
      <button type="submit">Поиск</button>
    </form>
  </body>
<a href="addUser.html">Создать нового пользователя</a>
</html>
