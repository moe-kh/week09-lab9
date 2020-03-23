<%-- 
    Document   : reset
    Created on : Mar 23, 2020, 11:38:28 AM
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
          <h1>Reset Password</h1>
        <p>Please enter your email address to reset your password!</p>
        
          <form action="reset" method="post" >
         Email Address: <input type="text" required name="emailaddress"><br>
          <input type="submit" value="Submit">
          <input type="hidden" name="action" value="sendemail">
         </form>
        <p>${wrong}</p>
    </body>
</html>
