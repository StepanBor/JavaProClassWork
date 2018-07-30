
<%@ page import="java.util.TreeMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Set" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored ="false" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <title>Prog.kiev.ua</title>
  </head>
  <body>
     <div align="center">
        <form action="/view" method="POST">
            Photo id: <input type="text" name="photo_id">
            <input type="submit" />
        </form>

        <form action="/add_photo" enctype="multipart/form-data" method="POST">
            Photo: <input type="file" name="photo">
            <input type="submit" />
        </form>
      </div>

     <table border="1">
         <caption>Adres list</caption>
         <tr>
             <th>id</th>

         </tr>
         <c:if test="${requestScope.photos!=null}">

             <c:forEach var="entry" items="${requestScope.photos}">
                 <tr>

                     <td><img src="/photo/<c:out value="${entry.key}"></c:out>" width="189" height="189"> alt="photo"></td>
                     <td><a href="/photo/<c:out value="${entry.key}"></c:out>"><c:out value="${entry.key}"></c:out></a></td>
                     <td><a href="/delete/<c:out value="${entry.key}"></c:out>">delete</a></td>

                 </tr>
             </c:forEach>
         </c:if>
     </table><br>

  </body>
</html>
