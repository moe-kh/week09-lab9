<%-- 
    Document   : resetpassword
    Created on : Mar 23, 2020, 11:03:57 AM
    Author     : 794456
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Enter your new Password</h1>
        <form action="reset" method="post" >
          <input type="text" required name="newpassword"><br>
          <input type="submit" value="Submit">
          <input type="hidden" name="action" value="newpassword">
         </form>
         <p>${wrong}</p>
    </body>
</html>
