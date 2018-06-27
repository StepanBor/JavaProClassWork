<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
      <meta charset="utf-8">
    <title>Prog.kiev.ua</title>
  </head>
  <body>
    <% String login = (String)session.getAttribute("user_login"); %>

    <% if (login == null || "".equals(login)) { %>
        <form action="/login" method="POST">
            Login: <input type="text" name="login"><br>
            Password: <input type="text" name="password"><br>
            <input type="submit" />
        </form>
    <% } else { %>
        <h1>You are logged in as: <%= login %></h1>
        <br>Click this link to <a href="/login?a=exit">logout</a>
    <% } %>
  </body>
</html>
